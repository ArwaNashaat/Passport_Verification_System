package org.hyperledger.fabric.samples.hospital;

import java.util.Objects;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import com.owlike.genson.annotation.JsonProperty;

@DataType
public final class BirthCertificate {
    @Property()
    private final String fullName;

    @Property()
    private final String religion;

    @Property()
    private final String gender;

    @Property()
    private final String idNumber;

    @Property()
    private final String dateOfBirth;

    @Property()
    private final String birthPlace;

    @Property()
    private final String nationality;

    @Property()
    private final FatherInfo fatherInfo;

    @Property()
    private final MotherInfo motherInfo;

    public BirthCertificate(@JsonProperty("fullName") final String fullName, @JsonProperty("religion") final String religion,
                       @JsonProperty("gender") final String gender, @JsonProperty("idNumber") final String idNumber,
                       @JsonProperty("dateOfBirth") final String dateOfBirth, @JsonProperty("birthPlace") final String birthPlace,
                       @JsonProperty("nationality") final String nationality, @JsonProperty("fatherInfo") final FatherInfo fatherInfo,
                       @JsonProperty("motherInfo") final MotherInfo motherInfo) {
        this.fullName = fullName;
        this.religion = religion;
        this.gender = gender;
        this.idNumber = idNumber;
        this.dateOfBirth = dateOfBirth;
        this.birthPlace = birthPlace;
        this.nationality = nationality;
        this.fatherInfo = fatherInfo;
        this.motherInfo = motherInfo;
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

    public String getIdNumber() {
        return idNumber;
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

    public FatherInfo getFatherInfo() { return  fatherInfo; }

    public MotherInfo getMotherInfo() {
        return motherInfo;
    }

    public boolean validateParentName(String parentName){
        if(motherInfo.getName().equals(parentName) || fatherInfo.getName().equals(parentName))
            return true;
        return false;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        BirthCertificate other = (BirthCertificate) obj;

        return Objects.deepEquals(new String[] {getFullName(), getReligion(), getGender(),
                        getIdNumber(), String.valueOf(getDateOfBirth()), getBirthPlace(), getNationality() ,
                        String.valueOf(getFatherInfo()), String.valueOf(getMotherInfo())},

                new String[] {other.getFullName(), other.getReligion(), other.getGender(),
                              other.getIdNumber(), String.valueOf(other.getDateOfBirth()), other.getBirthPlace(),
                              other.getNationality(), String.valueOf(other.getFatherInfo()), String.valueOf(other.getMotherInfo())});
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFullName(), getReligion(), getGender(),
                getIdNumber(), String.valueOf(getDateOfBirth()), getBirthPlace(), getNationality() ,
                String.valueOf(getFatherInfo()), String.valueOf(getMotherInfo()));
    }

    @Override
    public String toString() {
        return "BirthCertificate{" +
                "FullName='" + fullName + '\'' +
                ", Religion='" + religion + '\'' +
                ", Gender='" + gender + '\'' +
                ", idNumber='" + idNumber +
                ", DateOfBirth='" + dateOfBirth + '\'' +
                ", BirthPlace='" + birthPlace + '\'' +
                ", Nationality='" + nationality + '\'' +
                fatherInfo.toString() + '\'' +
                motherInfo.toString() + '\'' +
                '}';
    }

}

