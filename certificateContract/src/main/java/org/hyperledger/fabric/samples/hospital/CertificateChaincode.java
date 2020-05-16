package org.hyperledger.fabric.samples.hospital;
import com.owlike.genson.Genson;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

@Contract(
        name = "CertificateContract",
        info = @Info(
                title = "Certificate contract",
                description = "The hyperlegendary Certificate contract",
                version = "1.0.0-SNAPSHOT",
                license = @License(
                        name = "Apache 2.0 License",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(
                        email = "I.hospital@example.com",
                        name = "Certificate-hospital",
                        url = "https://hyperledger.example.com")))
@Default
public class CertificateChaincode implements ContractInterface{
    private final Genson genson = new Genson();

    private enum IDErrors {
        ID_ALREADY_EXISTS,
        INVALID_FULL_NAME,
        INVALID_Gender,
        INVALID_Religion,
        ID_NOT_FOUND
    }

    /**
     * Creates some initial Cars on the ledger.
     *
     * @param ctx the transaction context
     */
    @Transaction()
    public void initLedger(final Context ctx) {
    }

    private void checkFullName(String fullName){
        StringTokenizer fullNameTest = new StringTokenizer(fullName);

        if(fullNameTest.countTokens()!=4){
            String errorMessage = "Name is not valid, please Enter your full name";
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, CertificateChaincode.IDErrors.INVALID_FULL_NAME.toString());
        }

    }

    private void checkGender(String gender){
        if(!gender.equals("Female") && !gender.equals("Male")){
            String errorMessage = "Gender is not valid, please Enter Female or Male only";
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, CertificateChaincode.IDErrors.INVALID_Gender.toString());
        }

    }

    private void checkReligion(String religion){
        if(!religion.equals("Islam") && !religion.equals("Christianity") && !religion.equals("Judaism")){
            String errorMessage = "Relgion is not valid, please Enter Islam, Christianity or Judaism";
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, CertificateChaincode.IDErrors.INVALID_Religion.toString());
        }

    }

    private void checkNewlyCreatedCertificate(String fullName, String gender, String religion){
        checkFullName(fullName);
        checkGender(gender);
        checkReligion(religion);
    }

    private void checkIfIDExist(ChaincodeStub stub, String IDState, String IDNumber){
        IDState = stub.getStringState(IDNumber);
        if (!IDState.isEmpty()) {
            String errorMessage = String.format("ID %s already exists", IDNumber);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, CertificateChaincode.IDErrors.ID_ALREADY_EXISTS.toString());
        }

    }
    private void checkIDExist(String certificateState, String IDNumber){

        if (certificateState.isEmpty()) {
            String errorMessage = String.format("ID %s does not exist", IDNumber);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, CertificateChaincode.IDErrors.ID_NOT_FOUND.toString());
        }
    }
    private void checkFatherInfo(String name , String religion){
        checkFullName(name);
        checkReligion(religion);
    }
    private void checkMotherInfo(String name , String religion){
        checkFullName(name);
        checkReligion(religion);
    }

    /**
     * Creates a new ID on the ledger.
     *
     * @param ctx the transaction context
     * @param Number the key for the new ID
     * @param birthplace the address of the new ID
     * @param fullName the fullName of the ID's user
     * @param gender the gender of the ID's user
     * @param religion the religion of the ID's user
     * @param nationality the job of the ID's user
     * @param dateOfBirthString the marital status of the ID's user
     * @param fatherName the nationality of the ID's user
     * @param fatherReligion the birth date of the ID's user as a string
     * @param fatherNationality the birth date of the ID's user as a string
     * @param motherName the birth date of the ID's user as a string
     * @param motherReligion the birth date of the ID's user as a string
     * @param motherNationality the birth date of the ID's user as a string
     * @return the issued ID
     */
    @Transaction()
    public Certificate issueCertificate(final Context ctx, final String Number, final String birthplace, final String fullName,
                                        final String gender, final String religion, final String nationality, final String dateOfBirthString,
                                        final String fatherName, final String fatherReligion, final String fatherNationality,
                                        final String motherName , final String motherReligion , final String motherNationality
                                         ){
        ChaincodeStub stub = ctx.getStub();

        String CertificateState = stub.getStringState(Number);
        checkIfIDExist(stub, CertificateState,Number);
        checkNewlyCreatedCertificate(fullName, gender,religion);
        checkFatherInfo(fatherName,fatherReligion);
        checkMotherInfo(motherName,motherReligion);

        Certificate newCertificate = new Certificate(fullName,religion,gender,Number,dateOfBirthString,birthplace,nationality,
                fatherName,fatherNationality,fatherReligion,motherName,motherNationality,motherReligion);
        CertificateState = genson.serialize(newCertificate);
        stub.putStringState(Number, CertificateState);
        return newCertificate;
    }

    /**
     * Retrieves an ID with the specified ID Number from the ledger.
     *
     * @param ctx the transaction context
     * @param IDNumber the key
     * @return the ID found on the ledger if there was one
     */
    @Transaction
    public Certificate getCertificate(final Context ctx, final String IDNumber) {
        ChaincodeStub stub = ctx.getStub();

        String CertificateState = stub.getStringState(IDNumber);
        checkIDExist(CertificateState, IDNumber);

        Certificate Certificate = genson.deserialize(CertificateState, Certificate.class);


        return Certificate;

    }



}
