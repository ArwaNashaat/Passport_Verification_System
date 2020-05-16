package org.hyperledger.fabric.samples.hospital;

import java.util.Date;
import java.util.Objects;

import org.bouncycastle.openssl.CertificateTrustBlock;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import com.owlike.genson.annotation.JsonProperty;

@DataType
public final class Certificate {
    @Property()
    private final String fullName;

    @Property()
    private final String religion;

    @Property()
    private final String gender;

    @Property()
    private final String number;

    @Property()
    private final String dateOfBirth;

    @Property()
    private final String birthPlace;

    @Property()
    private final String nationality;

    @Property()
    private final String fatherName;

    @Property()
    private final String fatherNationality;

    @Property()
    private final String fatherReligion;

    @Property()
    private final String motherName;

    @Property()
    private final String motherNationality;

    @Property()
    private final String motherReligion;

    public Certificate(@JsonProperty("fullName") final String fullName, @JsonProperty("religion") final String religion,
                       @JsonProperty("gender") final String gender, @JsonProperty("number") final String number,
                       @JsonProperty("dateOfBirth") final String dateOfBirth, @JsonProperty("birthPlace") final String birthPlace,
                       @JsonProperty("nationality") final String nationality, @JsonProperty("fatherName") final String fatherName,
                       @JsonProperty("fatherNationality") final String fatherNationality, @JsonProperty("fatherReligion") final String fatherReligion,
                       @JsonProperty("motherName") final String motherName, @JsonProperty("motherNationality") final String motherNationality,
                       @JsonProperty("motherReligion") final String motherReligion) {
        this.fullName = fullName;
        this.religion = religion;
        this.gender = gender;
        this.number = number;
        this.dateOfBirth = dateOfBirth;
        this.birthPlace = birthPlace;
        this.nationality = nationality;
        this.fatherName = fatherName;
        this.fatherNationality = fatherNationality;
        this.fatherReligion = fatherReligion;
        this.motherName = motherName;
        this.motherNationality = motherNationality;
        this.motherReligion = motherReligion;
    }

    public String getFullName() {
        return fullName;
    }

    public String getReligion() {
        return religion;
    }

    public String getGender() {
        return gender;
    }

    public String getNumber() {
        return number;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public String getNationality() {
        return nationality;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getFatherNationality() {
        return fatherNationality;
    }

    public String getFatherReligion() {
        return fatherReligion;
    }

    public String getMotherName() {
        return motherName;
    }

    public String getMotherNationality() {
        return motherNationality;
    }

    public String getMotherReligion() {
        return motherReligion;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        Certificate other = (Certificate) obj;

        return Objects.deepEquals(new String[] {getFullName(), getReligion(), getGender(),
                        getNumber(), String.valueOf(getDateOfBirth()), getBirthPlace(), getNationality() ,
                        getFatherName(), getFatherNationality(), getFatherReligion(), getMotherName(), getMotherNationality(),
                        getMotherReligion()},

                new String[] {other.getFullName(), other.getReligion(), other.getGender(),
                              other.getNumber(), String.valueOf(other.getDateOfBirth()),
                              other.getBirthPlace(), other.getNationality(),
                              other.getFatherName(), other.getFatherNationality(),
                              other.getFatherReligion(), other.getMotherName(),getMotherNationality(),
                              other.getMotherReligion()});
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFullName(), getReligion(), getGender(),
                getNumber(), String.valueOf(getDateOfBirth()), getBirthPlace(), getNationality() ,
                getFatherName(), getFatherNationality(), getFatherReligion(), getMotherName(), getMotherNationality(),
                getMotherReligion());
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "FullName='" + fullName + '\'' +
                ", Religion='" + religion + '\'' +
                ", Gender='" + gender + '\'' +
                ", Number=" + number +
                ", DateOfBirth='" + dateOfBirth + '\'' +
                ", BirthPlace='" + birthPlace + '\'' +
                ", Nationality='" + nationality + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", fatherNationality='" + fatherNationality + '\'' +
                ", fatherReligion='" + fatherReligion + '\'' +
                ", motherName='" + motherName + '\'' +
                ", motherNationality='" + motherNationality + '\'' +
                ", motherReligion=" + motherReligion +
                '}';
    }
}

