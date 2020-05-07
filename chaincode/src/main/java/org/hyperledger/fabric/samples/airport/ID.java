package org.hyperledger.fabric.samples.airport;

public class ID {
    private  String number;
    private  String address;
    private  String fullName;
    private  String gender;
    private  String religion;
    private  String job;
    private  String maritalStatus;
    private  String nationality;
    private  String dateOfBirth;
    private  String expireDate;
    private  boolean isExpired;
    private  byte[] personalPicture;

    @Override
    public String toString() {
        return "ID{" +
                "number='" + number + '\'' +
                ", address='" + address + '\'' +
                ", fullName='" + fullName + '\'' +
                ", gender='" + gender + '\'' +
                ", religion='" + religion + '\'' +
                ", job='" + job + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", nationality='" + nationality + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", expireDate='" + expireDate + '\'' +
                ", isExpired=" + isExpired + '\'' +
                ", personalPicture=" + personalPicture +
                '}';
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public byte[] getPersonalPicture() {
        return personalPicture;
    }

    public void setPersonalPicture(byte[] personalPicture) {
        this.personalPicture = personalPicture;
    }

    public ID(String number, String address, String fullName, String gender, String religion, String job,
              String maritalStatus, String nationality, String dateOfBirth, String expireDate,
              boolean isExpired, byte[] personalPicture) {
        this.number = number;
        this.address = address;
        this.fullName = fullName;
        this.gender = gender;
        this.religion = religion;
        this.job = job;
        this.maritalStatus = maritalStatus;
        this.nationality = nationality;
        this.dateOfBirth = dateOfBirth;
        this.expireDate = expireDate;
        this.isExpired = isExpired;
        this.personalPicture = personalPicture;
    }


}
