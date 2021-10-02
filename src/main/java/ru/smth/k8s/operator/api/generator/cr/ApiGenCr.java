package ru.smth.k8s.operator.api.generator.cr;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;

/**
 * @author Shabunina Anita
 */
@Group("ru.smth")
@Version("v1")
public class ApiGenCr extends CustomResource<ApiGenSpec, ApiGenStatus> implements Namespaced {
}
