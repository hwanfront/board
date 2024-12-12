package com.example.jungle_board.util.entity;

import java.lang.reflect.Field;

public class EntityUtils {
    public static <T> void setId(T entity, Long id, Class<T> clazz) {
        try {
            Field idField = clazz.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(entity, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
