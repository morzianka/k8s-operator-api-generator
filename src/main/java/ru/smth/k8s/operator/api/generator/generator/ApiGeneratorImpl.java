package ru.smth.k8s.operator.api.generator.generator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.smth.k8s.operator.api.generator.exception.InternalException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Shabunina Anita
 */
@Slf4j
@Service
public class ApiGeneratorImpl implements ApiGenerator {

    @Value("${dockerfile}")
    private String dockerfile;

    @Override
    public void generate(String api, String uid) {
        ProcessBuilder builder = new ProcessBuilder();
        Path path = generateFiles(api, uid, builder);
        buildJar(builder, path);
        copyDockerFile(builder, path);
        buildImage(builder, path, uid);
        pushImage(builder, path, uid);
    }

    private Path generateFiles(String api, String uid, ProcessBuilder builder) {
        try {
            builder.directory(new File(System.getProperty("user.home")));
            Path path = Files.createTempDirectory(uid);
            Path file = Files.writeString(Path.of(path.toString(), uid), api);
            builder.command("openapi-generator", "generate",
                "-i", file.toFile().getAbsolutePath(),
                "-g", "spring",
                "-o", path.toString());
            process(builder);
            return path;
        } catch (IOException e) {
            throw new InternalException(e);
        }
    }

    private void buildJar(ProcessBuilder builder, Path path) {
        builder.directory(path.toFile());
        builder.command("mvn", "clean", "package");
        process(builder);
    }

    private void copyDockerFile(ProcessBuilder builder, Path path) {
        builder.directory(path.toFile());
        builder.command("cp", dockerfile, path.toString());
        process(builder);
    }

    private void buildImage(ProcessBuilder builder, Path path, String uid) {
        builder.directory(path.toFile());
        builder.command("docker", "build", "-t", "morzianka/test:" + uid, ".");
        process(builder);
    }

    private void pushImage(ProcessBuilder builder, Path path, String uid) {
        builder.directory(path.toFile());
        builder.command("docker", "push", "morzianka/test:" + uid);
        process(builder);
    }

    private void process(ProcessBuilder builder) {
        Process process = null;
        try {
            process = builder.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new InternalException("Openapi generator exit code " + exitCode);
            }
            log.info(new String(process.getInputStream().readAllBytes()));
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            readErrorStream(process, e);
        }
    }

    private void readErrorStream(Process process, Exception e) {
        try {
            throw new InternalException(process == null ? e.getMessage() :
                new String(process.getErrorStream().readAllBytes()), e);
        } catch (IOException ex) {
            throw new InternalException("Couldn't read error stream", ex);
        }
    }
}