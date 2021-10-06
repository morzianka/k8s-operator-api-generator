package ru.smth.k8s.operator.api.generator.cr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shabunina Anita
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiGenStatus {
    private String status;
}
