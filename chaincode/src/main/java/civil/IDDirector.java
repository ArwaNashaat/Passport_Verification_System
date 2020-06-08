package civil;

import civilHome.ID;
import hospital.BirthCertificate;

public class IDDirector {
    private IDBuilder builder;

    public IDDirector(IDBuilder builder) {
        this.builder = builder;
    }
    public void construct(BirthCertificate Certificate, String address,
                          String job, String maritalStatus, String expireDate, String personalPicture){
        this.builder.buildFullName(Certificate.getFullName());
        this.builder.buildAddress(address);
        this.builder.buildIDNumber(Certificate.getIdNumber());
        this.builder.buildDOB(Certificate.getDateOfBirth());
        this.builder.buildExpired(false);
        this.builder.buildExpiryDate(expireDate);
        this.builder.buildGender(Certificate.getGender());
        this.builder.buildJob(job);
        this.builder.buildMaritalStatus(maritalStatus);
        this.builder.buildNationality(Certificate.getNationality());
        this.builder.buildPersonalPicture(personalPicture);
        this.builder.buildReligion(Certificate.getReligion());
    }
    public ID getID(){
        return this.builder.getID();
    }
}
