package ru.smth.k8s.operator.api.generator.deployment;

import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.smth.k8s.operator.api.generator.cr.ApiGenCr;
import ru.smth.k8s.operator.api.generator.service.ApiGenImageService;

/**
 * @author Shabunina Anita
 */
@Service
@RequiredArgsConstructor
public class ApiGenDeploymentUpdaterImpl implements ApiGenDeploymentUpdater {

    private final ApiGenImageService imageService;

    @Override
    public Deployment update(ApiGenCr cr) {
        return new DeploymentBuilder()
                .build();
    }
}
