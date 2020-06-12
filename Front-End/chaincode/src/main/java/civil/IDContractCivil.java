package civil;

import civilHome.ID;
import civilHome.IDContractHome;
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
        EXPIRED_ID
    }

    @Transaction()
    public void initLedger(final Context ctx) {
    }


    private LocalDate setExpireDate(){

        LocalDate currentDate = LocalDate.now();
        LocalDate expireDate = currentDate.plusYears(7);

        return expireDate;
    }

    private boolean isExpired(LocalDate expireDate){
        LocalDate currentDate = LocalDate.now();
        if(currentDate.isAfter(expireDate))
            return true;
        return false;
    }
//["22","Arwa Nashaat Serry Abdl","Female", "Islam", "SW", "Single", "05-11-1998","cc"]
//["Amira Nashaat Serry Abdl","Islam", "Female", "05-11-1998", "Giza", "Egyptian","49","49"]
    @Transaction()
    public ID issueID(final Context ctx, final String address, final String fullName,
                      final String gender, final String religion, final String job, final String maritalStatus,
                      final String dateOfBirthString, final String personalPic){

        IDContractHome home = new IDContractHome();
        String IDNumber = home.setLastIDNumber(ctx);

        ChaincodeStub stub = ctx.getStub();
        String idState = stub.getStringState(IDNumber);

        if (!idState.isEmpty()) {
            String errorMessage = String.format("ID %s already exists", IDNumber);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, IDErrors.ID_ALREADY_EXISTS.toString());
        }

        LocalDate expireDate = setExpireDate();

        ID id = new ID(IDNumber, address, fullName, gender,
                religion, job, maritalStatus, "Egyptian", dateOfBirthString,
                String.valueOf(expireDate), false, personalPic);

        id.validateID();

        String IDState = genson.serialize(id);
        stub.putStringState(IDNumber, IDState);
        return id;
    }


    private void checkIDExist(String idState, String IDNumber){

        if (!idState.isEmpty()) {
            String errorMessage = String.format("ID %s already exists", IDNumber);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, IDErrors.ID_ALREADY_EXISTS.toString());
        }
    }

    @Transaction
    public ID renewID(final Context ctx, final String IDNumber, final String job, final String maritalStatus){

        ChaincodeStub stub = ctx.getStub();

        String idState = stub.getStringState(IDNumber);

        if (idState.isEmpty()) {
            String errorMessage = String.format("ID %s does not exist", IDNumber);
            throw new ChaincodeException(errorMessage, IDContractHome.IDErrors.ID_NOT_FOUND.toString());
        }

        ID id = genson.deserialize(idState, ID.class);

        String ed = String.valueOf(setExpireDate());

        ID newID = new ID(id.getIDNumber(), id.getAddress(), id.getFullName(), id.getGender(), id.getReligion(),
                job, maritalStatus, id.getNationality(), id.getDateOfBirth(), ed, false, id.getPersonalPicture());

        newID.validateID();

        String newIDState = genson.serialize(newID);
        stub.putStringState(IDNumber, newIDState);

        return newID;

    }

}
