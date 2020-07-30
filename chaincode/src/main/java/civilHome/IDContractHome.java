package civilHome;
import airport.VerifyID;
import civil.IDBuilder;
import civil.IDDirector;
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

import java.net.IDN;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Contract(
        name = "IDContractFromHome",
        info = @Info(
                title = "IDContract",
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
    private final VerifyID idHistory = new VerifyID();
    public enum IDErrors {
        ID_NOT_FOUND,
        ID_ALREADY_EXISTS
    }

    @Transaction()
    public void initLedger(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        stub.putStringState("lastID", "0");
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

    private ID updateIfExpired(ChaincodeStub stub,ID id, boolean isExpired){

        if(isExpired) {
            ID newID = new ID(id.getIDNumber(), id.getAddress(), id.getFullName(), id.getGender(), id.getReligion(),
                    id.getJob(), id.getMaritalStatus(), id.getNationality(), id.getDateOfBirth(),
                    id.getExpireDate(), isExpired, id.getPersonalPicture());
            String newIDState = genson.serialize(newID);
            stub.putStringState(id.getIDNumber(), newIDState);
            return newID;
        }
        return id;
    }

    @Transaction
    public ID[] getID(final Context ctx, final String IDNumber) {
        ChaincodeStub stub = ctx.getStub();

        ID[] ids = idHistory.getIDHistory(ctx, IDNumber);

        if (ids.length==0) {
            String errorMessage = String.format("ID %s does not exist", IDNumber);
            throw new ChaincodeException(errorMessage, IDErrors.ID_NOT_FOUND.toString());
        }

        boolean isExpired = isExpired(LocalDate.parse(ids[0].getExpireDate()));

        ids[0] = updateIfExpired(stub,ids[0], isExpired);
        return ids;
    }

    @Transaction
    public String getLastIDNumber(final Context ctx){

        ChaincodeStub stub = ctx.getStub();
        String idNumber = stub.getStringState("lastID");

        return idNumber;

    }

    @Transaction
    public String setLastIDNumber(final Context ctx){

        ChaincodeStub stub = ctx.getStub();
        Integer id = Integer.parseInt(getLastIDNumber(ctx));

        String idString = String.valueOf(++id);
        stub.putStringState("lastID", idString);

        return idString;
    }

    @Transaction
    public String getPicturePath(final Context ctx, final String IDNumber) {
        ID id = getID(ctx, IDNumber)[0];

        return id.getPersonalPicture();
    }

    @Transaction
    public ID renewID(final Context ctx, final String IDNumber, final String job, final String maritalStatus){

        ChaincodeStub stub = ctx.getStub();

        String idState = stub.getStringState(IDNumber);

        if (idState.isEmpty()) {
            String errorMessage = String.format("ID %s does not exist", IDNumber);
            throw new ChaincodeException(errorMessage, IDErrors.ID_NOT_FOUND.toString());
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
