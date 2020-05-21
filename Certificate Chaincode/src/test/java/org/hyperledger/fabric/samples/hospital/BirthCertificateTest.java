package org.hyperledger.fabric.samples.hospital;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;

public final class BirthCertificateTest{
    @Test
    public void toStringTest() {

        MotherInfo motherInfoMock = mock(MotherInfo.class);
        FatherInfo fatherInfoMock = mock(FatherInfo.class);

        when(motherInfoMock.toString()).thenReturn(
                        ", motherName='Mervat Hassan Abdelhamed Abdelghany" + '\'' +
                        ", motherPicture='000" + '\'' +
                        ", motherNationality='Egyptian" + '\'' +
                        ", motherReligion='Islam");

        when(fatherInfoMock.toString()).thenReturn(
                        ", fatherName='Nashaat Serry Abdelbar Abdelgawad" + '\'' +
                        ", fatherPicture='000" + '\'' +
                        ", fatherNationality='Egyptian" + '\'' +
                        ", fatherReligion='Islam");

        BirthCertificate birthCertificate = new BirthCertificate("Arwa Nashaat Serry AbdelBar",
                "Islam", "Female", "002", "05-11-1998",
                "Giza", "Egyptian", fatherInfoMock, motherInfoMock);

        assertThat(birthCertificate.toString()).isEqualTo(
                        "BirthCertificate{" +
                        "FullName='Arwa Nashaat Serry AbdelBar" + '\'' +
                        ", Religion='Islam" + '\'' +
                        ", Gender='Female" + '\'' +
                        ", Number='002" +
                        ", DateOfBirth='05-11-1998" + '\'' +
                        ", BirthPlace='Giza" + '\'' +
                        ", Nationality='Egyptian" + '\'' +
                        ", fatherName='Nashaat Serry Abdelbar Abdelgawad" + '\'' +
                        ", fatherPicture='000" + '\'' +
                        ", fatherNationality='Egyptian" + '\'' +
                        ", fatherReligion='Islam" + '\'' +
                        ", motherName='Mervat Hassan Abdelhamed Abdelghany" + '\'' +
                        ", motherPicture='000" + '\'' +
                        ", motherNationality='Egyptian" + '\'' +
                        ", motherReligion='Islam'" +
                        '}');
    }

    @Test
    public void equalsTest () {

        MotherInfo motherInfoMock1 = mock(MotherInfo.class);
        FatherInfo fatherInfoMock1 = mock(FatherInfo.class);

        when(motherInfoMock1.toString()).thenReturn(
                ", motherName='Mervat Hassan Abdelhamed Abdelghany" + '\'' +
                        ", motherPicture='000" + '\'' +
                        ", motherNationality='Egyptian" + '\'' +
                        ", motherReligion='Islam");

        when(fatherInfoMock1.toString()).thenReturn(
                ", fatherName='Nashaat Serry Abdelbar Abdelgawad" + '\'' +
                        ", fatherPicture='000" + '\'' +
                        ", fatherNationality='Egyptian" + '\'' +
                        ", fatherReligion='Islam");

        BirthCertificate birthCertificate1 = new BirthCertificate("Arwa Nashaat Serry AbdelBar",
                "Islam", "Female", "002", "05-11-1998",
                "Giza", "Egyptian", fatherInfoMock1, motherInfoMock1);

        MotherInfo motherInfoMock2 = mock(MotherInfo.class);
        FatherInfo fatherInfoMock2 = mock(FatherInfo.class);

        when(motherInfoMock2.toString()).thenReturn(
                ", motherName='Mervat Hassan Abdelhamed Abdelghany" + '\'' +
                        ", motherPicture='000" + '\'' +
                        ", motherNationality='Egyptian" + '\'' +
                        ", motherReligion='Islam");

        when(fatherInfoMock2.toString()).thenReturn(
                ", fatherName='Nashaat Serry Abdelbar Abdelgawad" + '\'' +
                        ", fatherPicture='000" + '\'' +
                        ", fatherNationality='Egyptian" + '\'' +
                        ", fatherReligion='Islam");

        BirthCertificate birthCertificate2 = new BirthCertificate("Arwa Nashaat Serry AbdelBar",
                "Islam", "Female", "002", "05-11-1998",
                "Giza", "Egyptian", fatherInfoMock2, motherInfoMock2);

        assertThat(birthCertificate1.equals(birthCertificate2)).isEqualTo(true);
    }

    @Test
    public void notEqualsTest () {

        MotherInfo motherInfoMock1 = mock(MotherInfo.class);
        FatherInfo fatherInfoMock1 = mock(FatherInfo.class);

        when(motherInfoMock1.toString()).thenReturn(
                ", motherName='Mervat Hassan Abdelhamed Abdelghany" + '\'' +
                        ", motherPicture='000" + '\'' +
                        ", motherNationality='Egyptian" + '\'' +
                        ", motherReligion='Islam");

        when(fatherInfoMock1.toString()).thenReturn(
                ", fatherName='Nashaat Serry Abdelbar Abdelgawad" + '\'' +
                        ", fatherPicture='000" + '\'' +
                        ", fatherNationality='Egyptian" + '\'' +
                        ", fatherReligion='Islam");

        BirthCertificate birthCertificate1 = new BirthCertificate("Arwa Nashaat Serry AbdelBar",
                "Islam", "Female", "002", "05-11-1998",
                "Giza", "Egyptian", fatherInfoMock1, motherInfoMock1);

        MotherInfo motherInfo2 = new MotherInfo("Mervat Hassan Abdelhamed Abdelghany",
                "000", "Egyptian", "Islam");

        FatherInfo fatherInfo2 = new FatherInfo("Nashaat Serry Abdelbar Abdelgawad",
                "000", "Egyptian", "Islam");

        MotherInfo motherInfoMock2 = mock(MotherInfo.class);
        FatherInfo fatherInfoMock2 = mock(FatherInfo.class);
        when(motherInfoMock2.toString()).thenReturn(
                ", motherName='Mervat Hassan Abdelhamed Abdelghany" + '\'' +
                        ", motherPicture='000" + '\'' +
                        ", motherNationality='Egyptian" + '\'' +
                        ", motherReligion='Islam");

        when(fatherInfoMock2.toString()).thenReturn(
                ", fatherName='Nashaat Serry Abdelbar Abdelgawad" + '\'' +
                        ", fatherPicture='000" + '\'' +
                        ", fatherNationality='Egyptian" + '\'' +
                        ", fatherReligion='Islam");

        BirthCertificate birthCertificate2 = new BirthCertificate("Amira Nashaat Serry AbdelBar",
                "Islam", "Female", "002", "05-11-1998",
                "Giza", "Egyptian", fatherInfo2, motherInfo2);

        assertThat(birthCertificate1.equals(birthCertificate2)).isNotEqualTo(true);
    }

}