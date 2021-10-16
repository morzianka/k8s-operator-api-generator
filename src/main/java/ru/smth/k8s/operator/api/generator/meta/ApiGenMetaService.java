package ru.smth.k8s.operator.api.generator.meta;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import ru.smth.k8s.operator.api.generator.cr.ApiGenCr;

/**
 * @author Shabunina Anita
 */
public interface ApiGenMetaService {

    ObjectMeta update(ApiGenCr cr);
}
