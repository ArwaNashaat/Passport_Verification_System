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

import java.util.StringTokenizer;

@Contract(
        name = "BirthCertificateContract",
        info = @Info(
                title = "Birth Certificate contract",
                description = "The hyperledger Certificate contract",
                version = "1.0.0-SNAPSHOT",
                license = @License(
                        name = "Apache 2.0 License",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(
                        email = "I.hospital@airport.com",
                        name = "Certificate-hospital",
                        url = "https://hospital.airport.com")))
@Default
public class BirthCertificateContract implements ContractInterface{
    private final Genson genson = new Genson();

    private enum BirthCertificateErrors {
        BirthCertificate_ALREADY_EXISTS,
        INVALID_FULL_NAME,
        INVALID_Gender,
        INVALID_Religion,
        BirthCertificate_NOT_FOUND
    }

    /**
     * Creates some initial Birth Certificate on the ledger.
     *
     * @param ctx the transaction context
     */
    @Transaction()
    public void initLedger(final Context ctx) {
    }

    private void checkFullName(String fullName){
        StringTokenizer fullNameTest = new StringTokenizer(fullName);

        if(fullNameTest.countTokens()!=4){
            String errorMessage = "Name is not valid, please Enter the full name consisting of 4 names";
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, BirthCertificateContract.BirthCertificateErrors.INVALID_FULL_NAME.toString());
        }

    }

    private void checkGender(String gender){
        if(!gender.equals("Female") && !gender.equals("Male")){
            String errorMessage = "Gender is not valid, please Enter Female or Male only";
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, BirthCertificateContract.BirthCertificateErrors.INVALID_Gender.toString());
        }

    }

    private void checkReligion(String religion){
        if(!religion.equals("Islam") && !religion.equals("Christianity") && !religion.equals("Judaism")){
            String errorMessage = "Relgion is not valid, please Enter Islam, Christianity or Judaism";
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, BirthCertificateContract.BirthCertificateErrors.INVALID_Religion.toString());
        }

    }

    private void checkNewlyCreatedCertificate(String fullName, String gender, String religion){
        checkFullName(fullName);
        checkGender(gender);
        checkReligion(religion);
    }

    //should be placed inside parentInfo class
    private void checkFatherInfo(String name , String religion){
        checkFullName(name);
        checkReligion(religion);
    }
    private void checkMotherInfo(String name , String religion){
        checkFullName(name);
        checkReligion(religion);
    }

    @Transaction()
    public void issueBirthCertificate(final Context ctx, final BirthCertificate newBirthCertificate){
        ChaincodeStub stub = ctx.getStub();

        String idNumber = newBirthCertificate.getIdNumber();

        String birthCertificateState = stub.getStringState(idNumber);
        if (!birthCertificateState.isEmpty()){
            String errorMessage = String.format("Birth Certificate %s already exists", idNumber);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, BirthCertificateContract.BirthCertificateErrors.BirthCertificate_ALREADY_EXISTS.toString());
        }

  /*      checkNewlyCreatedCertificate(fullName, gender,religion);
        checkFatherInfo(fatherInfo.getName(),fatherInfo.getReligion());
        checkMotherInfo(motherInfo.getName(),motherInfo.getReligion());
*/
        //create validate cert and place it in birth certificate class
        birthCertificateState = genson.serialize(newBirthCertificate);

        stub.putStringState(idNumber, birthCertificateState);
    }

    /**
     * Retrieves an Birth Certificate with the specified ID Number from the ledger.
     *
     * @param ctx the transaction context
     * @param IDNumber the key
     * @return the ID found on the ledger if there was one
     */
    @Transaction
    public BirthCertificate getBirthCertificate(final Context ctx, final String IDNumber) {
        ChaincodeStub stub = ctx.getStub();

        String birthCertificateState = stub.getStringState(IDNumber);
        if(birthCertificateState.isEmpty()) {
            String errorMessage = String.format("Birth Certificate %s already exists", IDNumber);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, BirthCertificateErrors.BirthCertificate_NOT_FOUND.toString());
        }

        BirthCertificate birthCertificate = genson.deserialize(birthCertificateState, BirthCertificate.class);
        return birthCertificate;

    }
}
