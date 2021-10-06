package ru.smth.k8s.operator.api.generator.controller;

import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.javaoperatorsdk.operator.api.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.smth.k8s.operator.api.generator.cr.ApiGenCr;
import ru.smth.k8s.operator.api.generator.cr.ApiGenStatus;
import ru.smth.k8s.operator.api.generator.deployment.ApiGenDeploymentUpdater;
import ru.smth.k8s.operator.api.generator.generator.ApiGenerator;
import ru.smth.k8s.operator.api.generator.service.ApiGenServiceUpdater;

/**
 * @author Shabunina Anita
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class ApiGenController implements ResourceController<ApiGenCr> {

    private final KubernetesClient kubeClient;
    private final ApiGenerator apiGenerator;
    private final ApiGenDeploymentUpdater deploymentUpdater;
    private final ApiGenServiceUpdater serviceUpdater;

    @Override
    public UpdateControl<ApiGenCr> createOrUpdateResource(ApiGenCr cr, Context<ApiGenCr> context) {
        try {
            updateDeployment(cr);
            updateService(cr);
            cr.setStatus(new ApiGenStatus("OK"));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
        return UpdateControl.updateCustomResourceAndStatus(cr);
    }

    @Override
    public DeleteControl deleteResource(ApiGenCr resource, Context<ApiGenCr> context) {
        return DeleteControl.DEFAULT_DELETE;
    }

    private void updateDeployment(ApiGenCr cr) {
        apiGenerator.generate(cr.getSpec().getApi(), cr.getMetadata().getUid());
        Deployment deployment = deploymentUpdater.update(cr);
        kubeClient.apps().deployments().createOrReplace(deployment);
        log("deployment", cr);
    }

    private void updateService(ApiGenCr cr) {
        Service service = serviceUpdater.update(cr);
        kubeClient.services().createOrReplace(service);
        log("service", cr);
    }

    private void log(String resource, ApiGenCr cr) {
        log.info(String.format("Updated %s with name %s in %s",
            resource, cr.getMetadata().getName(), cr.getMetadata().getNamespace()));
    }
}
