package ru.bobrov.vyacheslav.minijunit;

import ru.bobrov.vyacheslav.minijunit.annotations.Test;
import ru.bobrov.vyacheslav.minijunit.tests.TestRun;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class Runner {
    public static void main(String[] args) {
        (new Runner()).run(new Class[]{TestRun.class});
    }

    public void run(Class<?>... classes) {
        Arrays.stream(classes).forEach(clazz -> {
            try {
                run(clazz.getConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }

    private void run(final Object object) {
        final Class<?> clazz = object.getClass();

        Arrays.stream(clazz.getMethods())
                .filter(method ->
                        method.isAnnotationPresent(Test.class)
                                && method.getParameters().length == 0
                                && Modifier.isPublic(method.getModifiers())
                                && !Modifier.isStatic(method.getModifiers())
                )
                .forEach(method -> {
                    try {
                        method.invoke(object);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
    }
}
