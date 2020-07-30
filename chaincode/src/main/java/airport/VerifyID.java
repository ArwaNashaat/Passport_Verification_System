package airport;

import civilHome.ID;
import civilHome.IDContractHome;
import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;

import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ledger.KeyModification;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


@Contract(
        name = "Airport",
        info = @Info(
                title = "Airport",
                description = "The hyperlegendary ID contract",
                version = "0.0.1-SNAPSHOT",
                license = @License(
                        name = "Apache 2.0 License",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(
                        email = "I.airport@example.com",
                        name = "ID-airport",
                        url = "https://hyperledger.example.com")))
@Default
public class VerifyID implements ContractInterface {

    private final Genson genson = new Genson();

    public enum IDErrors {
        ID_IS_CHANGED
    }

    @Transaction
    public ID[] getIDHistory(final Context ctx, final String idNumber){
        ChaincodeStub stub = ctx.getStub();
        Set<ID> historySet = new HashSet<>();

        QueryResultsIterator<KeyModification> idHistory = stub.getHistoryForKey(idNumber);
        Iterator<KeyModification> idHistoryIterator = idHistory.iterator();

        while (idHistoryIterator.hasNext()){
            ID id = genson.deserialize(idHistoryIterator.next().getStringValue(), ID.class);
            historySet.add(id);
        }

        ID[] idArray= new ID[historySet.size()];
        historySet.toArray(idArray);

        return idArray;
    }

}
