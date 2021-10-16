package ru.smth.k8s.operator.api.generator.deployment;

import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.api.model.apps.DeploymentSpecBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.smth.k8s.operator.api.generator.cr.ApiGenCr;
import ru.smth.k8s.operator.api.generator.meta.ApiGenMetaService;
import ru.smth.k8s.operator.api.generator.props.ProjectProps;

import java.util.Map;

/**
 * @author Shabunina Anita
 */
@Service
@RequiredArgsConstructor
public class ApiGenDeploymentUpdaterImpl implements ApiGenDeploymentUpdater {

    private final ApiGenMetaService metaService;
    private final ProjectProps projectProps;

    @Override
    public Deployment update(ApiGenCr cr) {
        return new DeploymentBuilder()
            .withMetadata(metaService.update(cr))
            .withSpec(new DeploymentSpecBuilder()
                .withSelector(new LabelSelectorBuilder()
                    .withMatchLabels(Map.of(projectProps.getKey(), cr.getMetadata().getName()))
                    .build())
                .withTemplate(new PodTemplateSpecBuilder()
                    .withMetadata(metaService.update(cr))
                    .withSpec(new PodSpecBuilder()
                        .withContainers(new ContainerBuilder()
                            .withName(cr.getMetadata().getName())
                            .withImage("morzianka/test:" + cr.getMetadata().getUid())
                            .withPorts(new ContainerPortBuilder()
                                .withContainerPort(projectProps.getPort())
                                .build())
                            .build())
                        .build())
                    .build())
                .build())
            .build();
    }
}
