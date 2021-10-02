package ru.smth.k8s.operator.api.generator.deployment;

import io.fabric8.kubernetes.api.model.apps.Deployment;
import ru.smth.k8s.operator.api.generator.cr.ApiGenCr;

/**
 * @author Shabunina Anita
 */
public interface ApiGenDeploymentUpdater {

    Deployment update(ApiGenCr cr);
}
