package ru.smth.k8s.operator.api.generator.service;

import io.fabric8.kubernetes.api.model.*;
import lombok.RequiredArgsConstructor;
import ru.smth.k8s.operator.api.generator.cr.ApiGenCr;
import ru.smth.k8s.operator.api.generator.meta.ApiGenMetaService;
import ru.smth.k8s.operator.api.generator.props.ProjectProps;

import java.util.Map;

/**
 * @author Shabunina Anita
 */
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ApiGenServiceUpdaterImpl implements ApiGenServiceUpdater {

    private final ApiGenMetaService metaService;
    private final ProjectProps projectProps;

    @Override
    public Service update(ApiGenCr cr) {
        return new ServiceBuilder()
            .withMetadata(metaService.update(cr))
            .withSpec(new ServiceSpecBuilder()
                .withSelector(Map.of(projectProps.getKey(), cr.getMetadata().getName()))
                .withPorts(new ServicePortBuilder()
                    .withPort(projectProps.getPort())
                    .withTargetPort(new IntOrStringBuilder()
                        .withIntVal(projectProps.getPort())
                        .build())
                    .build())
                .build())
            .build();
    }

}
