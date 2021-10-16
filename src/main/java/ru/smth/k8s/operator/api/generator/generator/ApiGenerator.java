package ru.smth.k8s.operator.api.generator.generator;

/**
 * @author Shabunina Anita
 */
public interface ApiGenerator {

    void generate(String spec, String uid);
}
