package ru.smth.k8s.operator.api.generator.controller;

import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.javaoperatorsdk.operator.api.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.smth.k8s.operator.api.generator.cr.ApiGenCr;
import ru.smth.k8s.operator.api.generator.cr.ApiGenStatus;
import ru.smth.k8s.operator.api.generator.deployment.ApiGenDeploymentUpdater;

/**
 * @author Shabunina Anita
 */
@Slf4j
@Service
@Controller
@RequiredArgsConstructor
public class ApiGenController implements ResourceController<ApiGenCr> {

    private final ApiGenDeploymentUpdater deploymentUpdater;
    private final KubernetesClient kubeClient;

    @Override
    public UpdateControl<ApiGenCr> createOrUpdateResource(ApiGenCr cr, Context<ApiGenCr> context) {
        try {
            updateDeployment(cr);
            cr.setStatus(ApiGenStatus.builder().status("OK").build());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
        return UpdateControl.updateCustomResourceAndStatus(cr);
    }

    private void updateDeployment(ApiGenCr cr) {
        Deployment deployment = deploymentUpdater.update(cr);
        kubeClient.apps().deployments().createOrReplace(deployment);
    }
}
