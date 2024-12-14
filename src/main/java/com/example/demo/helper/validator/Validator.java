package com.example.demo.helper.validator;

public class Validator {

    /**
     * Validate that an object is not null and is of the expected type
     *
     * @param object input object
     * @param expectedType expected type of the object
     * @return the object cast to the expected type
     * @param <T> the expected type
     */
    public static <T> T validateType(Object object, Class<T> expectedType){
        if(object == null){
            throw new IllegalArgumentException("Object is null");
        }
        if(!expectedType.isInstance(object)){
            throw new IllegalArgumentException("Object is not of expected type");
        }
        return expectedType.cast(object);
    }
}