package hospital;
import com.owlike.genson.Genson;

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

import java.util.StringTokenizer;

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
    private final Genson genson = new Genson();

    private enum BirthCertificateErrors {
        BirthCertificate_ALREADY_EXISTS,
        BirthCertificate_NOT_FOUND
    }

    @Transaction()
    public void initLedger(final Context ctx) {
    }

    @Transaction()
    public BirthCertificate issueBirthCertificate(final Context ctx, final String fullName, final String religion,
                                      final String gender, String idNumber, final String dateOfBirth,
                                      final String birthPlace, final String nationality,
                                      final String fatherName, final String fatherNationality,
                                      final String fatherReligion, final String motherName, final String motherNationality,
                                      final String motherReligion){

        FatherInfo fatherInfo = new FatherInfo(fatherName,fatherNationality,fatherReligion);
        MotherInfo motherInfo = new MotherInfo(motherName,motherNationality,motherReligion);
        BirthCertificate newBirthCertificate = new BirthCertificate(fullName,religion,gender, idNumber, dateOfBirth, birthPlace,
                nationality,fatherInfo, motherInfo);

        //newBirthCertificate.validateBirthCertificate();

        ChaincodeStub stub = ctx.getStub();
        //String idNumber = newBirthCertificate.getIdNumber();

        String birthCertificateState = stub.getStringState(idNumber);
        if (!birthCertificateState.isEmpty()){
            String errorMessage = String.format("Birth Certificate %s already exists", idNumber);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, BirthCertificateContract.BirthCertificateErrors.BirthCertificate_ALREADY_EXISTS.toString());
        }

        birthCertificateState = genson.serialize(newBirthCertificate);

        stub.putStringState(idNumber+"birthCert", birthCertificateState);

        return newBirthCertificate;
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
}
