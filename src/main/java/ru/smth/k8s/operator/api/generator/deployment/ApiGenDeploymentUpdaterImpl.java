package ru.smth.k8s.operator.api.generator.deployment;

import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.api.model.apps.DeploymentSpecBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.smth.k8s.operator.api.generator.cr.ApiGenCr;
import ru.smth.k8s.operator.api.generator.meta.ApiGenMetaService;

import java.util.Map;

/**
 * @author Shabunina Anita
 */
@Service
@RequiredArgsConstructor
public class ApiGenDeploymentUpdaterImpl implements ApiGenDeploymentUpdater {

    private final ApiGenMetaService metaService;

    //TODO ConfigProps prefix=project
    @Value("${project.key}")
    private String key;

    @Value("${project.port}")
    private Integer port;

    @Override
    public Deployment update(ApiGenCr cr) {
        return new DeploymentBuilder()
            .withMetadata(metaService.update(cr))
            .withSpec(new DeploymentSpecBuilder()
                .withSelector(new LabelSelectorBuilder()
                    .withMatchLabels(Map.of(key, cr.getMetadata().getName()))
                    .build())
                .withTemplate(new PodTemplateSpecBuilder()
                    .withMetadata(metaService.update(cr))
                    .withSpec(new PodSpecBuilder()
                        .withContainers(new ContainerBuilder()
                            .withName(cr.getMetadata().getName())
                            .withImage(cr.getMetadata().getUid())
                            .withPorts(new ContainerPortBuilder()
                                .withContainerPort(port)
                                .build())
                            .build())
                        .build())
                    .build())
                .build())
            .build();
    }
}
