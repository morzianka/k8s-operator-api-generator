package ru.smth.k8s.operator.api.generator.generator;

import lombok.extern.slf4j.Slf4j;
import ru.smth.k8s.operator.api.generator.exception.InternalException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Shabunina Anita
 */
@Slf4j
public class ApiGeneratorImpl implements ApiGenerator {

    @Override
    public void generate(String api, String uid) {
        ProcessBuilder builder = new ProcessBuilder();
        generateFiles(api, uid, builder);
        try {
            Process process = builder.start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new InternalException("Openapi generator exit code " + exitCode);
            }
            log.info(new String(process.getInputStream().readAllBytes()));
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new InternalException(e);
        }
    }

    private void generateFiles(String api, String uid, ProcessBuilder builder) {
        try {
            builder.directory(new File(System.getProperty("user.home")));
            Path path = Files.createTempDirectory(uid);
            Path file = Files.writeString(Path.of(path.toString(), uid), api);
            builder.command("openapi-generator", "generate",
                    "-i", file.toFile().getAbsolutePath(),
                    "-g", "java",
                    "-o", path.toString(),
                    "--library", "resttemplate");
        } catch (IOException e) {
            throw new InternalException(e);
        }
    }
}