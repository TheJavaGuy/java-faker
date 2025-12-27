package org.thejavaguy.javafaker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.thejavaguy.javafaker.matchers.IsStringWithContents.isStringWithContents;

import org.junit.jupiter.api.Test;

public class MedicalTest extends AbstractFakerTest {

    @Test
    public void testMedicineName() {
        assertThat(faker.medical().medicineName(), isStringWithContents());
    }

    @Test
    public void testDiseaseName() {
        assertThat(faker.medical().diseaseName(), isStringWithContents());
    }

    @Test
    public void testHospitalName() {
        assertThat(faker.medical().hospitalName(), isStringWithContents());
    }

    @Test
    public void testSymptom() {
        assertThat(faker.medical().symptoms(), isStringWithContents());
    }


}
