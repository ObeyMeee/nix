package ua.com.andromeda.homework19;

import com.google.common.reflect.ClassPath;
import lombok.SneakyThrows;
import ua.com.andromeda.homework19.annotations.Qualifier;
import ua.com.andromeda.homework19.annotations.Singleton;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationContext {
    private static ApplicationContext instance;
    private final Map<Class<?>, Object> beans;

    private ApplicationContext() {
        this.beans = new HashMap<>();
        config();
    }

    public static ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }

    @SneakyThrows
    private void config() {
        List<? extends Class<?>> classes = getAllSingletons();

        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            classes.stream()
                    .filter(clazz -> Arrays.stream(clazz.getDeclaredConstructors())
                            .anyMatch(constr -> constr.getParameterCount() == finalI))
                    .forEach(clazz -> {
                        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
                            Object[] objects = getParametersInstances(constructor);
                            try {
                                beans.put(clazz, constructor.newInstance(objects));
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
        }
    }

    private List<? extends Class<?>> getAllSingletons() throws IOException {
        return ClassPath.from(ClassLoader.getSystemClassLoader())
                .getAllClasses()
                .stream()
                .filter(classInfo -> classInfo.getPackageName().startsWith("ua.com.andromeda"))
                .map(classInfo -> classInfo.load())
                .filter(clazz -> clazz.isAnnotationPresent(Singleton.class))
                .toList();
    }

    private Object[] getParametersInstances(Constructor<?> constructor) {
        return Arrays.stream(constructor.getParameters())
                .map(parameter -> parameter.isAnnotationPresent(Qualifier.class) ?
                        beans.get(parameter.getAnnotation(Qualifier.class).value()) :
                        beans.get(parameter.getType()))
                .toArray();
    }

    public <T> T get(Class<T> clazz) {
        return (T) beans.get(clazz);
    }
}
