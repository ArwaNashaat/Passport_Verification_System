package hospital;
import civilHome.ID;
import civilHome.IDContractHome;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

import com.owlike.genson.annotation.JsonProperty;
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
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

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
    Genson genson = new Genson();//new GensonBuilder().withConverters(new ID.IDConverter()).create();
    private enum BirthCertificateErrors {
        BirthCertificate_ALREADY_EXISTS,
        BirthCertificate_NOT_FOUND
    }

    @Transaction()
    public void initLedger(final Context ctx) {
    }
    //["Amira Nashaat Serry abd","Islam","Female", "05-11-1998", "Giza","Egyptian","23","23"]
    @Transaction()
    public BirthCertificate issueBirthCertificate(final Context ctx, final String fullName, final String religion,
                                                final String gender, final String dateOfBirth,
                                                final String birthPlace, final String nationality,
                                                final String fatherIDNumber, final String motherIDNumber){

        IDContractHome home = new IDContractHome();
        ChaincodeStub stub = ctx.getStub();

        ID fatherID = home.getID(ctx, fatherIDNumber);
        ID motherID = home.getID(ctx, motherIDNumber);

        String idNumber = home.setLastIDNumber(ctx);
        addChild(fatherID, idNumber, stub, fatherIDNumber);
        addChild(motherID, idNumber, stub, motherIDNumber);

        FatherInfo fatherInfo = new FatherInfo(fatherID.getFullName(),"Egyptian",fatherID.getReligion());
        MotherInfo motherInfo = new MotherInfo(motherID.getFullName(), "Egyptian", motherID.getReligion());

        idNumber+="birthCert";

        BirthCertificate newBirthCertificate = new BirthCertificate(fullName,religion,gender, idNumber, dateOfBirth, birthPlace,
                nationality,fatherInfo, motherInfo);

        newBirthCertificate.validateBirthCertificate();

        String birthCertificateState = genson.serialize(newBirthCertificate);

        stub.putStringState(idNumber, birthCertificateState);

        return newBirthCertificate;

    }

    public ID addChild(ID parentID, String idNumber, ChaincodeStub stub, String parentIDNumber){
        parentID.addChild(idNumber);

        String idState = genson.serialize(parentID);
        stub.putStringState(parentIDNumber,idState);
        String f = stub.getStringState(parentIDNumber);
        return genson.deserialize(f, ID.class);
    }

    @Transaction
    public BirthCertificate getBirthCertificate(final Context ctx, String IDNumber) {
        ChaincodeStub stub = ctx.getStub();

        IDNumber += "birthCert";

        String birthCertificateState = stub.getStringState(IDNumber);
        if(birthCertificateState.isEmpty()) {
            String errorMessage = String.format("Birth Certificate %s doesn't exist", IDNumber);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, BirthCertificateErrors.BirthCertificate_NOT_FOUND.toString());
        }

        BirthCertificate birthCertificate = genson.deserialize(birthCertificateState, BirthCertificate.class);
        return birthCertificate;

    }
    @Transaction
    public String query(final Context ctx){
        ChaincodeStub stub = ctx.getStub();
        //String queryStr = "{\"selector\": {\"type\": \"wallet\"}}";
        //QueryResultsIterator<KeyValue> rows = stub.getQueryResult(queryStr);
        /*'SELECT org.hyperledger_composer.marbles.Marble
        WHERE (color == "green")'*/
        //queryString := fmt.Sprintf("{\"selector\":{\"docType\":\"marble\",\"owner\":\"%s\"}}", owner)
        String queryString = "{\"selector\":{\"fullName\":\"Arwa Nashaat Serry Abdl\"}}";
        QueryResultsIterator<KeyValue> queryResult = stub.getQueryResult(queryString);
        Iterator<KeyValue> iter = queryResult.iterator();
        String s = "nothing found";
        while (iter.hasNext()) {
            s = iter.next().getStringValue();
        }
        try {
            queryResult.close();
        } catch (Exception e) {
            return "No exception expected";
        }

        return s;
    }
}
