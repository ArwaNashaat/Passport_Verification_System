package api;

import org.hyperledger.fabric.gateway.*;
import chaincodes.ID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;

@RestController
public class IDController {
    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/Airport/getInfo/{ID}")
    public String GetInfo(@PathVariable String ID) throws IOException {

        Path walletPath = Paths.get("/home","mohanad-belal",".fabric-vscode","environments","myEnv","wallets","Org1");
        Wallet wallet = Wallet.createFileSystemWallet(walletPath);
        // load a CCP

        Path networkConfigPath = Paths.get("/home","mohanad-belal",".fabric-vscode","environments","myEnv","gateways","Org1","Org1.json");

        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, "org1Admin").networkConfig(networkConfigPath).discovery(true);
        try (Gateway gateway = builder.connect()) {

            Network network = gateway.getNetwork("mychannel");
            Contract contract = network.getContract("IDContract");
            byte[] result;

            result = contract.evaluateTransaction("getID",ID);
            return new String(result);
        } catch (ContractException e) {
            e.printStackTrace();
        }

        return "False";
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value="/Airport/IssueID", consumes= MediaType.APPLICATION_JSON_VALUE)
    public boolean IssueID(@RequestBody ID id) throws IOException {

        Path walletPath = Paths.get("/home","mohanad-belal",".fabric-vscode","environments","myEnv","wallets","Org1");
        Wallet wallet = Wallet.createFileSystemWallet(walletPath);
        // load a CCP

        Path networkConfigPath = Paths.get("/home","mohanad-belal",".fabric-vscode","environments","myEnv","gateways","Org1","Org1.json");

        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, "org1Admin").networkConfig(networkConfigPath).discovery(true);
        try (Gateway gateway = builder.connect()) {

            Network network = gateway.getNetwork("mychannel");
            Contract contract = network.getContract("IDContract");
            byte[] result;
            result = contract.submitTransaction("issueID", id.getNumber(), id.getAddress(), id.getFullName(),
                    id.getGender(),id.getReligion(),id.getJob(), id.getMaritalStatus(),id.getNationality(),id.getDateOfBirth());
            return true;
        } catch (ContractException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        return false;
    }

}


