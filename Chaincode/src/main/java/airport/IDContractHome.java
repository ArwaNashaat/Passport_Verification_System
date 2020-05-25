package airport;
import com.owlike.genson.Genson;

import hospital.*;
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
import java.util.StringTokenizer;

@Contract(
        name = "IDContractFromHome",
        info = @Info(
                title = "IDcontract",
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
public class IDContractHome implements ContractInterface {

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
        if(!gender.equals("Female") && !gender.equals("Male")){
            String errorMessage = "Gender is not valid, please Enter Female or Male only";
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, IDErrors.INVALID_Gender.toString());
        }

    }

    private void checkReligion(String religion){
        if(!religion.equals("Islam") && !religion.equals("Christianity") && !religion.equals("Judaism")){
            String errorMessage = "Relgion is not valid, please Enter Islam, Christianity or Judaism";
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, IDErrors.INVALID_Religion.toString());
        }

    }

    private void checkMaritalStatus(String maritalStatus){
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


    @Transaction()
    public ID issueID(final Context ctx, final String IDNumber, final String job, final String maritalStatus,
                      final String personalPic, final String parentIDNumber){
        ChaincodeStub stub = ctx.getStub();

        ID parentID = getID(ctx,parentIDNumber);

        //getBirthCertificate
        /*list = Arrays.asList(new String[]{"getBirthCertificate", IDNumber});
        Chaincode.Response birthCert = stub.invokeChaincodeWithStringArgs("BirthCertificateContract",list);
        BirthCertificate birthCertificate = genson.deserialize(birthCert.getMessage(), BirthCertificate.class);*/

        BirthCertificateContract birthCertificateContract = new BirthCertificateContract();
        BirthCertificate birthCertificate = birthCertificateContract.getBirthCertificate(ctx,IDNumber);

        //if(!bCert.validateParentName(pID.getFullName()))
        //throw error Please get the parent

        //stub.invokeChaincode()
        //String birthCertState = stub.getStringState(birthCertificate.getIdNumber());

        //String IDState = stub.getStringState(IDNumber);
        //checkIfIDExist(stub, birthCertState,IDNumber);
        //checkNewlyCreatedID(fullName, gender,religion, maritalStatus);

        LocalDate expireDate = setExpireDate();

        ID id = new ID(birthCertificate.getIdNumber(), parentID.getAddress(), birthCertificate.getFullName(), birthCertificate.getGender(),
                birthCertificate.getReligion(), job, maritalStatus, birthCertificate.getNationality(), birthCertificate.getDateOfBirth(),
                String.valueOf(expireDate), false, personalPic.getBytes());

        String IDState = genson.serialize(id);
        stub.putStringState(IDNumber, IDState);

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
                    id.getExpireDate(), isExpired, id.getPersonalPicture());
            String newIDState = genson.serialize(newID);
            stub.putStringState(id.getIDNumber(), newIDState);
        }

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
        checkIDExist(idState, IDNumber);

        ID id = genson.deserialize(idState, ID.class);
        boolean isExpired = isExpired(LocalDate.parse(id.getExpireDate()));

        updateIfExpired(stub,id, isExpired);

        return id;

    }

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
                job, maritalStatus, id.getNationality(), id.getDateOfBirth(), ed, false, id.getPersonalPicture());

        String newIDState = genson.serialize(newID);
        stub.putStringState(IDNumber, newIDState);

        return newID;

    }

}
