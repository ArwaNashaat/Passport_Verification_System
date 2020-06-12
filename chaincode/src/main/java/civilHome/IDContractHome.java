package civilHome;
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

import java.time.LocalDate;

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

    /*@Transaction()
    public ID issueID(final Context ctx/*, final String IDNumber*//*,final String job, final String maritalStatus,
                      final String personalPic, final String parentIDNumber){
        ChaincodeStub stub = ctx.getStub();

        ID parentID = getID(ctx,parentIDNumber);

        BirthCertificateContract birthCertificateContract = new BirthCertificateContract();
        ArrayList<BirthCertificate> birthCertificate = new ArrayList<>();
        birthCertificate = birthCertificateContract.getBirthCertByParentID(ctx,parentIDNumber);//.getBirthCertificate(ctx,IDNumber);

        String IDNumber = birthCertificate.get(0).getIdNumber();

        String idState = stub.getStringState(IDNumber);
        if (!idState.isEmpty()) {
            String errorMessage = String.format("ID %s already exists", IDNumber);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, IDErrors.ID_ALREADY_EXISTS.toString());
        }

        LocalDate expireDate = setExpireDate();
        IDBuilder builder = new IDBuilder();
        IDDirector IDCreator = new IDDirector(builder);
        IDCreator.construct(birthCertificate.get(0), parentID.getAddress(),job, maritalStatus, String.valueOf(expireDate), personalPic);
        ID id = IDCreator.getID();

        idState = genson.serialize(id);
        stub.putStringState(IDNumber, idState);

        return id;
    }
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
        boolean isExpired = isExpired(LocalDate.parse(id.getExpireDate()));

        ID newID = updateIfExpired(stub,id, isExpired);
        return newID;
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
        ID id = getID(ctx, IDNumber);

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
