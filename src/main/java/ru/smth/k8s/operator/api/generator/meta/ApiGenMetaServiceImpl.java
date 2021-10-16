package ru.smth.k8s.operator.api.generator.meta;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.api.model.OwnerReference;
import io.fabric8.kubernetes.api.model.OwnerReferenceBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.smth.k8s.operator.api.generator.cr.ApiGenCr;
import ru.smth.k8s.operator.api.generator.props.ProjectProps;

import java.util.Map;

/**
 * @author Shabunina Anita
 */
@Service
@RequiredArgsConstructor
public class ApiGenMetaServiceImpl implements ApiGenMetaService {

    private final ProjectProps projectProps;

    @Override
    public ObjectMeta update(ApiGenCr cr) {
        return new ObjectMetaBuilder()
            .withName(cr.getMetadata().getName())
            .withNamespace(cr.getMetadata().getNamespace())
            .withLabels(Map.of(projectProps.getKey(), cr.getMetadata().getName()))
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
