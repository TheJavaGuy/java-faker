package org.thejavaguy.javafaker;

public class Matz {
    private final Faker faker;

    protected Matz(final Faker faker) {
        this.faker = faker;
    }

    public String quote() {
        return faker.resolve("matz.quotes");
    }
}
