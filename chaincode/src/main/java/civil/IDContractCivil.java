package civil;

import civilHome.ID;
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
import java.util.StringTokenizer;

@Contract(
        name = "IDContractAtCivil",
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
public class IDContractCivil implements ContractInterface {

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

    private boolean isExpired(LocalDate expireDate){
        LocalDate currentDate = LocalDate.now();
        if(currentDate.isAfter(expireDate))
            return true;
        return false;
    }


    @Transaction()
    public ID issueID(final Context ctx, final String IDNumber, final String address, final String fullName,
                      final String gender, final String religion, final String job, final String maritalStatus,
                      final String nationality, final String dateOfBirthString, final String personalPic){

        ChaincodeStub stub = ctx.getStub();

        LocalDate expireDate = setExpireDate();

        ID id = new ID(IDNumber, address, fullName, gender,
                religion, job, maritalStatus, nationality, dateOfBirthString,
                String.valueOf(expireDate), false, personalPic);

        String IDState = genson.serialize(id);
        stub.putStringState(IDNumber, IDState);
        return id;
    }


    private void checkIDExist(String idState, String IDNumber){

        ID id = genson.deserialize(idState,ID.class);
        if (!idState.isEmpty()) {
            String errorMessage = String.format("ID %s already exists", IDNumber);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, IDErrors.ID_ALREADY_EXISTS.toString());
        }
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
