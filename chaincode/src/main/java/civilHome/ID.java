package civilHome;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

import com.owlike.genson.Context;
import com.owlike.genson.Converter;
import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import com.owlike.genson.stream.ObjectReader;
import com.owlike.genson.stream.ObjectWriter;
import hospital.ValidateInfo;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import com.owlike.genson.annotation.JsonProperty;

@DataType
public  class ID {
    @Property()
    private  String IDNumber;
    @Property()
    private  String address;
    @Property()
    private  String fullName;
    @Property()
    private  String gender;
    @Property()
    private  String religion;
    @Property()
    private  String job;
    @Property()
    private  String maritalStatus;
    @Property()
    private  String nationality;
    @Property()
    private  String dateOfBirth;
    @Property()
    private  String expireDate;
    @Property()
    private  boolean isExpired;
    @Property()
    private  String personalPicture;
    @Property()
    private ArrayList<String> childrenIDs;

    public ID() {
    }
    public ID(@JsonProperty("IDNumber")  String IDNumber, @JsonProperty("address")  String address,
              @JsonProperty("fullName")  String fullName,
              @JsonProperty("gender")  String gender,
              @JsonProperty("religion")  String religion,
              @JsonProperty("job")  String job,
              @JsonProperty("maritalStatus")  String maritalStatus,
              @JsonProperty("nationality")  String nationality,
              @JsonProperty("dateOfBirth")  String dateOfBirth,
              @JsonProperty("expireDate")  String expireDate,
              @JsonProperty("isExpired")  boolean isExpired,
              @JsonProperty("personalPicture")  String personalPicture)
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
        this.personalPicture = personalPicture;

        this.childrenIDs = new ArrayList<>();
    }
    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public void setPersonalPicture(String personalPicture) {
        this.personalPicture = personalPicture;
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
    public String getPersonalPicture(){ return personalPicture;}
    public ArrayList<String> getChildrenIDs() {
        return this.childrenIDs;
    }
    public void setChildrenIDs(ArrayList<String> childrenAges) {
        this.childrenIDs = childrenAges;
    }
    public void addChild(String childID){ this.childrenIDs.add(childID); }

    public void validateID(){
        ValidateInfo validateInfo = new ValidateInfo();

        validateInfo.validateFullName(fullName);
        validateInfo.validateGender(gender);
        validateInfo.validateReligion(religion);
        validateInfo.validateMaritalStatus(maritalStatus);
    }

    @Override
    public boolean equals( Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        ID other = (ID) obj;

        return Objects.deepEquals(new String[] {getIDNumber(), getAddress(), getFullName(), getGender(),
                         getReligion(), getJob(),getMaritalStatus(), getNationality(), String.valueOf(getDateOfBirth()),
                String.valueOf(getExpireDate()), String.valueOf(getIsExpired()), getPersonalPicture(), getChildrenIDs().toString()},
                new String[] {other.getIDNumber(), other.getAddress(), other.getFullName(), other.getGender(),
              	other.getReligion(), other.getJob(), other.getMaritalStatus(), other.getNationality(),
                        String.valueOf(other.getDateOfBirth()), String.valueOf(other.getExpireDate()),
                        String.valueOf(other.getIsExpired()), other.getPersonalPicture(), other.getChildrenIDs().toString()});
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()  + " [IDNumber=" + IDNumber + ", address=" + address +
                ", fullName=" + fullName + ", gender=" + gender  + ", religion=" + religion +
                ", job=" + job + ", maritalStatus=" + maritalStatus + ", nationality=" + nationality +
                ", dateOfBirth="+dateOfBirth + ", expireDate=" + expireDate + ", ID Expired=" + isExpired +
                ", personalPicture" + personalPicture+ ", childrenIDs=" + childrenIDs +"]";
    }
}
