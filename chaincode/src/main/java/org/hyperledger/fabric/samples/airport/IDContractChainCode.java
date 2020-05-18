package org.hyperledger.fabric.samples.airport;
import com.owlike.genson.Genson;

import org.hyperledger.fabric.samples.hospital.BirthCertificate;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.Chaincode;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Contract(
        name = "IDContract",
        info = @Info(
                title = "ID contract",
                description = "The hyperlegendary ID contract",
                version = "0.0.1-SNAPSHOT",
                license = @License(
                        name = "Apache 2.0 License",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(
                        email = "I.airporr@example.com",
                        name = "ID-airport",
                        url = "https://hyperledger.example.com")))
@Default
public class IDContractChainCode implements ContractInterface {

    private final Genson genson = new Genson();

    private enum IDErrors {
        ID_NOT_FOUND,
        ID_ALREADY_EXISTS,
        INVALID_FULL_NAME,
        INVALID_Gender,
        INVALID_Religion,
        INVALID_Marital_Status,
        EXPIRED_ID
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
            throw new ChaincodeException(errorMessage, IDErrors.INVALID_FULL_NAME.toString());
        }

    }

    private void checkGender(@NotNull String gender){
        if(!gender.equals("Female") && !gender.equals("Male")){
            String errorMessage = "Gender is not valid, please Enter Female or Male only";
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, IDErrors.INVALID_Gender.toString());
        }

    }

    private void checkReligion(@NotNull String religion){
        if(!religion.equals("Islam") && !religion.equals("Christianity") && !religion.equals("Judaism")){
            String errorMessage = "Relgion is not valid, please Enter Islam, Christianity or Judaism";
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, IDErrors.INVALID_Religion.toString());
        }

    }

    private void checkMaritalStatus(@NotNull String maritalStatus){
        if(!maritalStatus.equals("Single") && !maritalStatus.equals("Married")){
            String errorMessage = "Marital Status is not valid, please Enter Single or Married";
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, IDErrors.INVALID_Marital_Status.toString());
        }

    }

    private LocalDate setExpireDate(){

        LocalDate currentDate = LocalDate.now();
        LocalDate expireDate = currentDate.plusYears(7);

        return expireDate;
    }

    private void checkNewlyCreatedID(String fullName, String gender, String religion,String maritalStatus){
        checkFullName(fullName);
        checkGender(gender);
        checkReligion(religion);
        checkMaritalStatus(maritalStatus);
    }

    private void checkIDNotExpired(ID id){
        LocalDate expireDate = LocalDate.parse(id.getExpireDate());
        if(LocalDate.now().isAfter(expireDate)){
            String errorMessage = "This ID is expired and no longer can be used";
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, IDErrors.EXPIRED_ID.toString());
        }

    }

    private void checkIfIDExist(ChaincodeStub stub, String IDState, String IDNumber){
        IDState = stub.getStringState(IDNumber);
        if (!IDState.isEmpty()) {
            String errorMessage = String.format("ID %s already exists", IDNumber);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, IDErrors.ID_ALREADY_EXISTS.toString());
        }

    }

    private boolean isExpired(LocalDate expireDate){
        LocalDate currentDate = LocalDate.now();
        if(currentDate.isAfter(expireDate))
            return true;
        return false;
    }

    /**
     * Creates a new ID on the ledger.
     *
     * @param ctx the transaction context
     * @param job the job of the ID's user
     * @param maritalStatus the marital status of the ID's user
    * @return the issued ID
     */
    @Transaction()
    public ID issueID(final Context ctx, final String parentIDNumber, final String job,
                      final String maritalStatus, String encodedPersonalPic){

        ChaincodeStub stub = ctx.getStub();
        //getID of parent
        Chaincode.Response parentID = stub.invokeChaincodeWithStringArgs("IDContract",parentIDNumber);
        ID pID = genson.deserialize(parentID.getMessage(), ID.class);

        //getBirthCertificate
        Chaincode.Response birthCert = stub.invokeChaincodeWithStringArgs("BirthCertificateContract",parentIDNumber);
        BirthCertificate bCert = genson.deserialize(birthCert.getMessage(), BirthCertificate.class);

        //if(!bCert.validateParentName(pID.getFullName()))
        //throw error Please get the parent

        //stub.invokeChaincode()
        String IDState = stub.getStringState(bCert.getNumber());
        checkIfIDExist(stub, IDState,bCert.getNumber());
        checkMaritalStatus(maritalStatus);

        LocalDate expireDate = setExpireDate();
        byte[] decodedPersonalPic = Base64.getDecoder().decode(encodedPersonalPic);

        ID id = new ID(bCert.getNumber(), pID.getAddress(), bCert.getFullName(), bCert.getGender(),
                bCert.getReligion(), job, maritalStatus, bCert.getNationality(), bCert.getDateOfBirth(),
                String.valueOf(expireDate), false, decodedPersonalPic);
        IDState = genson.serialize(id);
        stub.putStringState(bCert.getNumber(), IDState);

        return id;
    }


    private void checkIDExist(String idState, String IDNumber){

        if (idState.isEmpty()) {
            String errorMessage = String.format("ID %s does not exist", IDNumber);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, IDErrors.ID_NOT_FOUND.toString());
        }
    }

    private void updateIfExpired(ChaincodeStub stub,ID id, boolean isExpired){

        if(isExpired) {
            ID newID = new ID(id.getIDNumber(), id.getAddress(), id.getFullName(), id.getGender(), id.getReligion(),
                    id.getJob(), id.getMaritalStatus(), id.getNationality(), id.getDateOfBirth(),
                    id.getExpireDate(), isExpired, id.getPersonalPic());
            String newIDState = genson.serialize(newID);
            stub.putStringState(id.getIDNumber(), newIDState);
        }

    }

    /**
     * Retrieves an ID with the specified ID Number from the ledger.
     *
     * @param ctx the transaction context
     * @param key the key
     * @return the ID found on the ledger if there was one
     */
    @Transaction()
    public ID getID(final Context ctx, final String key) {
        ChaincodeStub stub = ctx.getStub();
        String IDState = stub.getStringState(key);

        if (IDState.isEmpty()) {
            String errorMessage = String.format("ID %s does not exist", key);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, IDErrors.ID_NOT_FOUND.toString());
        }

        ID id = genson.deserialize(IDState, ID.class);
        return id;
    }

    /**
     * Renew an ID on the ledger.
     *
     * @param ctx the transaction context
     * @param IDNumber the key for the new ID
     * @param address the address of the new ID
     * @param fullName the fullName of the ID's user
     * @param religion the religion of the ID's user
     * @param job the job of the ID's user
     * @param maritalStatus the marital status of the ID's user
     * @return the issued ID
     */

    @Transaction
    public ID renewID(final Context ctx, final String IDNumber, final String address, final String fullName,
                      final String religion, final String job, final String maritalStatus){

        ChaincodeStub stub = ctx.getStub();

        String idState = stub.getStringState(IDNumber);
        checkIDExist(idState, IDNumber);

        ID id = genson.deserialize(idState, ID.class);

        String ed = String.valueOf(setExpireDate());

        checkNewlyCreatedID(fullName, id.getGender(),religion, maritalStatus);

        ID newID = new ID(id.getIDNumber(), address, fullName, id.getGender(), religion,
                job, maritalStatus, id.getNationality(), id.getDateOfBirth(), ed, false, id.getPersonalPic());

        String newIDState = genson.serialize(newID);
        stub.putStringState(IDNumber, newIDState);

        return newID;

    }
}