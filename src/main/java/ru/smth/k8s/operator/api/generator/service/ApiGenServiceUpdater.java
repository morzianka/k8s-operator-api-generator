package ru.smth.k8s.operator.api.generator.service;

import io.fabric8.kubernetes.api.model.Service;
import ru.smth.k8s.operator.api.generator.cr.ApiGenCr;

/**
 * @author Shabunina Anita
 */
public interface ApiGenServiceUpdater {

    Service update(ApiGenCr cr);
}
