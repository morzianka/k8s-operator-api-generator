package ru.smth.k8s.operator.api.generator.service;

import io.fabric8.kubernetes.api.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import ru.smth.k8s.operator.api.generator.cr.ApiGenCr;
import ru.smth.k8s.operator.api.generator.meta.ApiGenMetaService;

import java.util.Map;

/**
 * @author Shabunina Anita
 */
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ApiGenServiceUpdaterImpl implements ApiGenServiceUpdater {

    private final ApiGenMetaService metaService;

    @Value("${project.key}")
    private String key;

    @Value("${project.port}")
    private Integer port;

    @Override
    public Service update(ApiGenCr cr) {
        return new ServiceBuilder()
            .withMetadata(metaService.update(cr))
            .withSpec(new ServiceSpecBuilder()
                .withSelector(Map.of(key, cr.getMetadata().getName()))
                .withPorts(new ServicePortBuilder()
                    .withPort(port)
                    .withTargetPort(new IntOrStringBuilder()
                        .withIntVal(port)
                        .build())
                    .build())
                .build())
            .build();
    }

}
