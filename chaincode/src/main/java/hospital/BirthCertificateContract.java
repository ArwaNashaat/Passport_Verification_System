package hospital;
import civilHome.ID;
import civilHome.IDContractHome;
import com.owlike.genson.GenericType;
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

import java.net.IDN;
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

    @Transaction()
    public BirthCertificate issueBirthCertificate(final Context ctx, final String fullName, final String religion,
                                                final String gender, final String dateOfBirth,
                                                final String birthPlace, final String nationality,
                                                final String fatherName, final String motherName){

        IDContractHome home = new IDContractHome();
        ChaincodeStub stub = ctx.getStub();

        ID fatherID = getIDByName(ctx, fatherName);
        ID motherID = getIDByName(ctx, motherName);

        String idNumber = home.setLastIDNumber(ctx);

        FatherInfo fatherInfo = new FatherInfo(fatherID.getFullName(),"Egyptian",fatherID.getReligion(), fatherID.getIDNumber());
        MotherInfo motherInfo = new MotherInfo(motherID.getFullName(), "Egyptian", motherID.getReligion(), motherID.getIDNumber());

        idNumber+="birthCert";

        BirthCertificate newBirthCertificate = new BirthCertificate(fullName,religion,gender, idNumber, dateOfBirth, birthPlace,
                nationality,fatherInfo, motherInfo);

        newBirthCertificate.validateBirthCertificate();

        String birthCertificateState = genson.serialize(newBirthCertificate);

        stub.putStringState(idNumber, birthCertificateState);

        return newBirthCertificate;

    }

    @Transaction
    public ID getIDByName(final Context ctx, String name){
        ChaincodeStub stub = ctx.getStub();
        String queryString = "{\"selector\":{\"fullName\":\""+name+"\"}}";
        QueryResultsIterator<KeyValue> queryResult = stub.getQueryResult(queryString);
        Iterator<KeyValue> iter = queryResult.iterator();
        String s = "nothing found";
        while (iter.hasNext()) {
            s = iter.next().getStringValue();
        }
        try {
            queryResult.close();
        } catch (Exception e) {
        }

        return genson.deserialize(s,ID.class);
    }

    @Transaction
    public BirthCertificate getBirthCertByParentID(final Context ctx, String parentIDNumber, String childName) {
        ChaincodeStub stub = ctx.getStub();

        String queryStringMother = "{\"selector\":{\"fullName\":\""+childName+"\"," +
                                    "\"motherInfo\":{\"idNumber\":\"" + parentIDNumber + "\"}}}";
        String queryStringFather = "{\"selector\":{\"fullName\":\""+childName+"\"," +
                                    "\"fatherInfo\":{\"idNumber\":\"" + parentIDNumber + "\"}}}";

        QueryResultsIterator<KeyValue> queryResultMother = stub.getQueryResult(queryStringMother);
        QueryResultsIterator<KeyValue> queryResultFather = stub.getQueryResult(queryStringFather);
        Iterator<KeyValue> iterMother = queryResultMother.iterator();
        Iterator<KeyValue> iterFather = queryResultFather.iterator();

        String s = "null";
        while (iterMother.hasNext()) {
            s = iterMother.next().getStringValue();
        }
        while (s.equals("null") && iterFather.hasNext()) {
            s = iterFather.next().getStringValue();
        }
        try {
            queryResultMother.close();
            queryResultFather.close();
            if(s.equals("null")){
                String errorMessage = String.format("Birth Certificate %s does not exist", parentIDNumber);
                throw new ChaincodeException(errorMessage, BirthCertificateErrors.BirthCertificate_NOT_FOUND.toString());
            }
        } catch (Exception e) {
            String errorMessage = String.format("Birth Certificate %s does not exist", parentIDNumber);
            throw new ChaincodeException(errorMessage, BirthCertificateErrors.BirthCertificate_NOT_FOUND.toString());
        }
        return genson.deserialize(s,BirthCertificate.class);
    }
}
