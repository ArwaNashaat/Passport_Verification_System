package org.hyperledger.fabric.samples.airport;
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

import java.time.LocalDate;
import java.util.List;
import java.util.StringTokenizer;

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
public class IDContractChainCode  implements ContractInterface {

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

    private void checkGender(String gender){
        if(!gender.equals("Female") || !gender.equals("Male")){
            String errorMessage = "Gender is not valid, please Enter Female or Male only";
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, IDErrors.INVALID_Gender.toString());
        }

    }

    private void checkReligion(String religion){
        if(!religion.equals("Islam") || !religion.equals("Christianity") ||!religion.equals("Judaism")){
            String errorMessage = "Relgion is not valid, please Enter Islam, Christianity or Judaism";
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, IDErrors.INVALID_Religion.toString());
        }

    }

    private void checkMaritalStatus(String maritalStatus){
        if(!maritalStatus.equals("Single") || !maritalStatus.equals("Married")){
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
        if(LocalDate.now().isAfter(id.getExpireDate())){
            String errorMessage = "This ID is expired and no longer can be used";
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, IDErrors.EXPIRED_ID.toString());
        }

    }

    private void checkIfIDExist(ChaincodeStub stub, String IDSate, String IDNumber){
        String IDState = stub.getStringState(IDNumber);
        if (!IDState.isEmpty()) {
            String errorMessage = String.format("ID %s already exists", IDNumber);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, IDErrors.ID_ALREADY_EXISTS.toString());
        }

    }
    /**
     * Creates a new ID on the ledger.
     *
     * @param ctx the transaction context
     * @param IDNumber the key for the new ID
     * @param address the address of the new ID
     * @param fullName the fullName of the ID's user
     * @param gender the gender of the ID's user
     * @param religion the religion of the ID's user
     * @param job the job of the ID's user
     * @param maritalStatus the marital status of the ID's user
     * @param nationality the nationality of the ID's user
     * @param dateOfBirth the birth date of the ID's user
     * @return the issued ID
     */
    @Transaction()
    public ID issueID(final Context ctx, final String IDNumber, final String address, final String fullName,
                         final String gender, final String religion, final String job, final String maritalStatus,
                      final String nationality, final LocalDate dateOfBirth) {

        ChaincodeStub stub = ctx.getStub();

        String IDState = stub.getStringState(IDNumber);
        checkIfIDExist(stub, IDState,IDNumber);
        checkNewlyCreatedID(fullName, gender,religion, maritalStatus);

        LocalDate expireDate = setExpireDate();

        ID id = new ID(IDNumber, address, fullName, gender, religion,
                        job, maritalStatus, nationality, dateOfBirth, expireDate);
        IDState = genson.serialize(id);
        stub.putStringState(IDNumber, IDState);

        return id;
    }

    /**
     * Retrieves an ID with the specified ID Number from the ledger.
     *
     * @param ctx the transaction context
     * @param IDNumber the key
     * @return the ID found on the ledger if there was one
     */
    @Transaction
    public ID getID(final Context ctx, final String IDNumber) {
        ChaincodeStub stub = ctx.getStub();

        String idState = stub.getStringState(IDNumber);
        if (idState.isEmpty()) {
            String errorMessage = String.format("ID %s does not exist", IDNumber);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, IDErrors.ID_NOT_FOUND.toString());
        }

        ID id = genson.deserialize(idState, ID.class);

        //check if id is valid
        checkIDNotExpired(id);



        return id;

    }

    /**
     * Creates a new car on the ledger.
     *
     * @param ctx the transaction context
     * @param IDNumber the key for the new ID
     * @param address the address of the new ID
     * @param fullName the fullName of the ID's user
     * @param gender the gender of the ID's user
     * @param religion the religion of the ID's user
     * @param job the job of the ID's user
     * @param maritalStatus the marital status of the ID's user
     * @param nationality the nationality of the ID's user
     * @return the issued ID
     */
    /*@Transaction
    public ID renewID(final Context ctx, final String IDNumber, final String address, final String fullName,
                      final String gender, final String religion, final String job, final String maritalStatus,
                      final String nationality, final String dateOfBirth){

    }*/
}
