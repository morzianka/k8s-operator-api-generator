package ru.smth.k8s.operator.api.generator.meta;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.api.model.OwnerReference;
import io.fabric8.kubernetes.api.model.OwnerReferenceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.smth.k8s.operator.api.generator.cr.ApiGenCr;

import java.util.Map;

/**
 * @author Shabunina Anita
 */
@Service
public class ApiGenMetaServiceImpl implements ApiGenMetaService {

    @Value("${project.key}")
    private String key;

    @Override
    public ObjectMeta update(ApiGenCr cr) {
        return new ObjectMetaBuilder()
            .withName(cr.getMetadata().getName())
            .withNamespace(cr.getMetadata().getNamespace())
            .withLabels(Map.of(key, cr.getMetadata().getName()))
            .withOwnerReferences(build(cr))
            .build();
    }

    private OwnerReference build(ApiGenCr cr) {
        return new OwnerReferenceBuilder()
            .withName(cr.getMetadata().getName())
            .withUid(cr.getMetadata().getUid())
            .withApiVersion(cr.getApiVersion())
            .withKind(cr.getKind())
            .build();
    }
}
