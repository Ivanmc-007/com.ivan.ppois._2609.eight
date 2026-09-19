package com.ivan.ppois._2609;

import java.util.HashMap;
import java.util.Map;

// Контейнер для компонентов
public class AppContext {

    private static final Map<Class<?>, Object> components = new HashMap<>();

    public static <T> void register(Class<T> type, T instance) {
        components.put(type, instance);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Class<T> type) {
        T component = (T) components.get(type);
        if (component == null) {
            throw new IllegalStateException("Component " + type + " is not registered in the context");
        }
        return component;
    }
}
