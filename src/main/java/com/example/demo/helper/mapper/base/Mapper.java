package com.example.demo.helper.mapper.base;

import java.util.List;

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
        return mapper.map(input);
    }
}
