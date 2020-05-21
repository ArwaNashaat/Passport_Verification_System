package chaincodes;

import org.hyperledger.fabric.contract.annotation.Property;

public class Certificate {

    private String fullName;

    private String religion;

    private String gender;

    private String number;

    private String dateOfBirth;

    private String birthPlace;

    private String nationality;

    private String fatherName;

    private String fatherNationality;

    private String fatherReligion;

    private String motherName;

    private String motherNationality;

    private String motherReligion;

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



    public Certificate(String fullName, String religion, String gender, String number,
                       String dateOfBirth, String birthPlace, String nationality,
                       String fatherName, String fatherNationality, String fatherReligion,
                       String motherName, String motherNationality, String motherReligion) {
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


}
