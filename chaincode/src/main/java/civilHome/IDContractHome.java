package civilHome;
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

    private enum IDErrors {
        ID_NOT_FOUND,
        ID_ALREADY_EXISTS
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

    @Transaction()
    public ID issueID(final Context ctx, String IDNumber, final String job, final String maritalStatus,
                      final String personalPic, final String parentIDNumber){
        ChaincodeStub stub = ctx.getStub();

        ID parentID = getID(ctx,parentIDNumber);

        BirthCertificateContract birthCertificateContract = new BirthCertificateContract();
        BirthCertificate birthCertificate = birthCertificateContract.getBirthCertificate(ctx,IDNumber);

        String idState = stub.getStringState(IDNumber);
        if (!idState.isEmpty()) {
            String errorMessage = String.format("ID %s already exists", IDNumber);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, IDErrors.ID_ALREADY_EXISTS.toString());
        }

        LocalDate expireDate = setExpireDate();

        ID id = new ID(birthCertificate.getIdNumber(), parentID.getAddress(), birthCertificate.getFullName(), birthCertificate.getGender(),
                birthCertificate.getReligion(), job, maritalStatus, birthCertificate.getNationality(), birthCertificate.getDateOfBirth(),
                String.valueOf(expireDate), false, personalPic);

        idState = genson.serialize(id);
        stub.putStringState(IDNumber, idState);

        return id;
    }


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
    public String getPicturePath(final Context ctx, final String IDNumber) {
        ID id = getID(ctx, IDNumber);

        return id.getPersonalPicture();
    }

    @Transaction
    public ID renewID(final Context ctx, final String IDNumber, final String address, final String fullName,
                      final String job, final String maritalStatus, final String personalPicture){

        ChaincodeStub stub = ctx.getStub();

        String idState = stub.getStringState(IDNumber);

        if (idState.isEmpty()) {
            String errorMessage = String.format("ID %s does not exist", IDNumber);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, IDErrors.ID_NOT_FOUND.toString());
        }

        ID id = genson.deserialize(idState, ID.class);

        String ed = String.valueOf(setExpireDate());

        ID newID = new ID(id.getIDNumber(), address, fullName, id.getGender(), id.getReligion(),
                job, maritalStatus, id.getNationality(), id.getDateOfBirth(), ed, false, personalPicture);

        newID.validateID();

        String newIDState = genson.serialize(newID);
        stub.putStringState(IDNumber, newIDState);

        return newID;

    }

}
