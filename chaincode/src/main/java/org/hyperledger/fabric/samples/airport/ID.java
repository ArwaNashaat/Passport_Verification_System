package org.hyperledger.fabric.samples.airport;


import java.util.Date;
import java.util.Objects;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import com.owlike.genson.annotation.JsonProperty;

@DataType
public final class ID {
    @Property()
    private final String IDNumber;
    @Property()
    private final String address;
    @Property()
    private final String fullName;
    @Property()
    private final String gender;
    @Property()
    private final String religion;
    @Property()
    private final String job;
    @Property()
    private final String maritalStatus;
    @Property()
    private final String nationality;
    @Property()
    private final String dateOfBirth;
    @Property()
    private final String expireDate;
    @Property()
    private final boolean isExpired;

    public ID(@JsonProperty("IDNumber") final String IDNumber, @JsonProperty("address") final String address,
              @JsonProperty("fullName") final String fullName,
              @JsonProperty("gender") final String gender,
              @JsonProperty("religion") final String religion,
              @JsonProperty("job") final String job,
              @JsonProperty("maritalStatus") final String maritalStatus,
              @JsonProperty("nationality") final String nationality,
              @JsonProperty("dateOfBirth") final String dateOfBirth,
              @JsonProperty("expireDate") final String expireDate,
              @JsonProperty("isExpired") final boolean isExpired)
    {
        this.IDNumber = IDNumber;
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
    }

    public String getIDNumber(){ return IDNumber;}
    public String getAddress(){ return address;}
    public String getFullName(){ return fullName;}
    public String getGender(){ return gender;}
    public String getReligion(){ return religion;}
    public String getJob(){ return job;}
    public String getMaritalStatus(){ return maritalStatus;}
    public String getNationality(){ return nationality;}
    public String getDateOfBirth(){ return dateOfBirth;}
    public String getExpireDate(){ return expireDate;}
    public boolean getIsExpired(){ return isExpired;}

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        ID other = (ID) obj;

        return Objects.deepEquals(new String[] {getIDNumber(), getAddress(), getFullName(), getGender(),
                         getReligion(), getJob(),getMaritalStatus(), getNationality(), String.valueOf(getDateOfBirth()),
                String.valueOf(getExpireDate()), String.valueOf(getIsExpired())},
                new String[] {other.getIDNumber(), other.getAddress(), other.getFullName(), other.getGender(),
              	other.getReligion(), other.getJob(), other.getMaritalStatus(), other.getNationality(),
                        String.valueOf(other.getDateOfBirth()), String.valueOf(other.getExpireDate()),
                        String.valueOf(other.getIsExpired())});
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIDNumber(), getAddress(), getFullName(), getGender(),
                 getReligion(), getJob(), getMaritalStatus(), getNationality(), getDateOfBirth(),
                getExpireDate(),getIsExpired());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " [IDNumber=" + IDNumber + ", address="
                + address + ", fullName=" + fullName + ", gender=" + gender  + ", religion=" + religion +
                ", job=" + job + ", maritalStatus=" + maritalStatus + ", nationality=" + nationality +
                ", dateOfBirth="+dateOfBirth + ", expireDate=" + expireDate + ", ID Expired=" + isExpired +"]";
    }
}
