package ru.smth.k8s.operator.api.generator.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Shabunina Anita
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "project")
public class ProjectProps {
    private String key;
    private Integer port;
}
