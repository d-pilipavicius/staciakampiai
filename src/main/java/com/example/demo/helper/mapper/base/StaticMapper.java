package com.example.demo.helper.mapper.base;

/**
 * Functional interface for mapping DTO to Model and vice versa
 *
 * @param <I> Input type
 * @param <O> Output type
 */
@FunctionalInterface
public interface StaticMapper<I, O> {
    /**
     * Maps input object of type I to output object of type O
     *
     * @param input Input object
     * @return mapped output object
     */
    O map(I input);
}
