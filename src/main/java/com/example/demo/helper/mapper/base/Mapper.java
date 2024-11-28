package com.example.demo.helper.mapper.base;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Main mapper class that takes a specific mapper and maps the input object to the output object.
 */
public class Mapper {
    /**
     * Maps a DTO to a Model.
     *
     * @param input DTO object
     * @param mapper Mapper function
     * @return mapped Model object
     * @param <D> the type of the DTO object
     * @param <M> the type of the Model object
     */
    public static <D, M> M mapToModel(D input, StaticMapper<D, M> mapper){
        if (input == null) throw new IllegalArgumentException("Input object cannot be null");
        return mapper.map(input);
    }

    /**
     * Maps a Model to a DTO.
     *
     * @param input the Model object
     * @param mapper the Mapper function
     * @return the mapped DTO object
     * @param <D> the type of the DTO object
     * @param <M> the type of the Model object
     */
    public static <D, M> D mapToDTO(M input, StaticMapper<M, D> mapper){
        if (input == null) throw new IllegalArgumentException("Input object cannot be null");
        return mapper.map(input);
    }

    public static <D, M> List<M> mapToModelList(List<D> inputList, StaticMapper<D, M> mapper) {
        if (inputList == null) return Collections.emptyList();
        return inputList.stream().map(mapper::map).collect(Collectors.toList());
    }

    public static <D, M> List<D> mapToDTOList(List<M> inputList, StaticMapper<M, D> mapper) {
        if (inputList == null) return Collections.emptyList();
        return inputList.stream().map(mapper::map).collect(Collectors.toList());
    }

    public static <D, M> Optional<M> mapToModelOptional(D input, StaticMapper<D, M> mapper) {
        return Optional.ofNullable(input).map(mapper::map);
    }

    public static <D, M> Optional<D> mapToDTOOptional(M input, StaticMapper<M, D> mapper) {
        return Optional.ofNullable(input).map(mapper::map);
    }
}
