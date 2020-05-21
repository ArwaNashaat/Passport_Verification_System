package org.hyperledger.fabric.samples.hospital;
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
    public void issueBirthCertificate(final Context ctx, final BirthCertificate newBirthCertificate){
        newBirthCertificate.validateBirthCertificate();

        ChaincodeStub stub = ctx.getStub();
        String idNumber = newBirthCertificate.getIdNumber();

        String birthCertificateState = stub.getStringState(idNumber);
        if (!birthCertificateState.isEmpty()){
            String errorMessage = String.format("Birth Certificate %s already exists", idNumber);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, BirthCertificateContract.BirthCertificateErrors.BirthCertificate_ALREADY_EXISTS.toString());
        }

        birthCertificateState = genson.serialize(newBirthCertificate);

        stub.putStringState(idNumber, birthCertificateState);


    }

    @Transaction
    public BirthCertificate getBirthCertificate(final Context ctx, final String IDNumber) {
        ChaincodeStub stub = ctx.getStub();

        String birthCertificateState = stub.getStringState(IDNumber);
        if(birthCertificateState.isEmpty()) {
            String errorMessage = String.format("Birth Certificate %s already exists", IDNumber);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, BirthCertificateErrors.BirthCertificate_NOT_FOUND.toString());
        }

        BirthCertificate birthCertificate = genson.deserialize(birthCertificateState, BirthCertificate.class);
        return birthCertificate;

    }
}
