package org.thejavaguy.javafaker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class MedicalTest extends AbstractFakerTest {

    @Test
    public void testMedicineName() {
        assertThat(faker.medical().medicineName()).isNotBlank();
    }

    @Test
    public void testDiseaseName() {
        assertThat(faker.medical().diseaseName()).isNotBlank();
    }

    @Test
    public void testHospitalName() {
        assertThat(faker.medical().hospitalName()).isNotBlank();
    }

    @Test
    public void testSymptom() {
        assertThat(faker.medical().symptoms()).isNotBlank();
    }


}
