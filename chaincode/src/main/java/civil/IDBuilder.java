package civil;

import civilHome.ID;

public class IDBuilder {
    private ID myID;

    public IDBuilder() {
        this.myID = new ID();
    }
    public void buildIDNumber(String number){
        this.myID.setIDNumber(number);
    }
    public void buildFullName(String Name){
        this.myID.setFullName(Name);
    }
    public void buildAddress(String Address){
        this.myID.setAddress(Address);
    }
    public void buildPersonalPicture(String Picture) {
        this.myID.setPersonalPicture(Picture);
    }
    public void buildGender(String Gender){
        this.myID.setGender(Gender);
    }
    public void buildReligion(String Religion){
        this.myID.setReligion(Religion);
    }
    public void buildJob(String Job){
        this.myID.setJob(Job);
    }
    public void buildNationality(String Nationality){
        this.myID.setNationality(Nationality);
    }
    public void buildExpiryDate(String Date){
        this.myID.setExpireDate(Date);
    }
    public void buildExpired(boolean expired){
        this.myID.setExpired(expired);
    }
    public void buildDOB(String date){
        this.myID.setDateOfBirth(date);
    }
    public void buildMaritalStatus(String Status){
        this.myID.setMaritalStatus(Status);
    }

    public ID getID() {
        return myID;
    }
}
