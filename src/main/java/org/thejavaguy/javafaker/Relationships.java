package org.thejavaguy.javafaker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Relationships {
    private final Faker faker;

    protected Relationships(final Faker faker) {
        this.faker = faker;
    }

    public String direct() {
        return faker.resolve("relationship.familial.direct");
    }

    public String extended() {
        return faker.resolve("relationship.familial.extended");
    }

    public String inLaw() {
        return faker.resolve("relationship.in_law");
    }

    public String spouse() {
        return faker.resolve("relationship.spouse");
    }

    public String parent() {
        return faker.resolve("relationship.parent");
    }

    public String sibling() {
        return faker.resolve("relationship.sibling");
    }

    public String any() {
        try {
            Method[] methods = Relationships.class.getDeclaredMethods();
            List<Method> filteredMethods = Arrays.stream(methods)
                    .filter(method -> method.getModifiers() == Modifier.PUBLIC)
                    .filter(method -> !method.getName().equals("any"))
                    .collect(Collectors.toList());
            int indx = faker.random().nextInt(filteredMethods.size());
            Method runMethod = filteredMethods.get(indx);
            Relationships relationships = new Relationships(faker);
            return (String)runMethod.invoke(relationships);
        } catch (SecurityException e) {
            throw new RuntimeException("SecurityException: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("IllegalArgumentException: " + e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("IllegalAccessException: " + e.getMessage(), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("InvocationTargetException: " + e.getMessage(), e);
        }
    }

}
