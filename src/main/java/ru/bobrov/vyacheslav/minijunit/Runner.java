package ru.bobrov.vyacheslav.minijunit;

import ru.bobrov.vyacheslav.minijunit.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Objects;

public class Runner {
    public static void main(String[] args) {
        if (args.length == 0 || args[0].equals("-h") || args[0].equals("--help")) {
            System.out.println("runner package.Class1 package.Class2 ... package.ClassN");
            return;
        }

        Runner runner = new Runner();
        runner.run(
                Arrays.stream(args)
                        .map(className -> {
                            try {
                                return Class.forName(className);
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .toArray(Class<?>[]::new)
        );
    }

    public void run(Class<?>... classes) {
        Arrays.stream(classes).forEach(clazz -> {
            try {
                run(clazz.getConstructor().newInstance());
            } catch (InstantiationException
                    | IllegalAccessException
                    | InvocationTargetException
                    | NoSuchMethodException e) {
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
