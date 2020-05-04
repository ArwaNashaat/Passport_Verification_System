package api;

import org.hyperledger.fabric.gateway.*;
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
    @GetMapping(path = "/Airport/getID/{ID}")
    public boolean GetID(@PathVariable String ID) throws IOException {
        //Path walletPath = Paths.get("/tmp","wallet","walletAirport");
        Path walletPath = Paths.get("/home","mohanad-belal",".fabric-vscode","environments","myEnv","wallets","Org1");
        Wallet wallet = Wallet.createFileSystemWallet(walletPath);
        // load a CCP

        Path networkConfigPath = Paths.get("/home","mohanad-belal",".fabric-vscode","environments","myEnv","gateways","Org1","Org1.json");
        //Path networkConfigPath = Paths.get("/tmp","connection-profiles", "connection-airport.json");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, "org1Admin").networkConfig(networkConfigPath).discovery(true);
        try (Gateway gateway = builder.connect()) {
            // get the network and contract
            Network network = gateway.getNetwork("mychannel");
            Contract contract = network.getContract("IDContract");
            byte[] result;
            result = contract.evaluateTransaction("getID",ID);
            return true;
        } catch (ContractException e) {
            e.printStackTrace();
        }

        return false;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/Airport/getInfo/{ID}")
    public String GetInfo(@PathVariable String ID) throws IOException {
        //Path walletPath = Paths.get("/tmp","wallet","walletAirport");
        Path walletPath = Paths.get("/home","mohanad-belal",".fabric-vscode","environments","myEnv","wallets","Org1");
        Wallet wallet = Wallet.createFileSystemWallet(walletPath);
        // load a CCP

        Path networkConfigPath = Paths.get("/home","mohanad-belal",".fabric-vscode","environments","myEnv","gateways","Org1","Org1.json");
        //Path networkConfigPath = Paths.get("/tmp","connection-profiles", "connection-airport.json");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, "org1Admin").networkConfig(networkConfigPath).discovery(true);
        try (Gateway gateway = builder.connect()) {
            // get the network and contract
            Network network = gateway.getNetwork("mychannel");
            Contract contract = network.getContract("IDContract");
            byte[] result;

            result = contract.evaluateTransaction("getID",ID);
            System.out.println(new String(result));
            return new String(result);
        } catch (ContractException e) {
            e.printStackTrace();
        }

        return "ID not found";
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value="/Airport/IssueID", consumes= MediaType.APPLICATION_JSON_VALUE)
    public boolean IssueID(@RequestBody ID ID) throws IOException {
        //Path walletPath = Paths.get("/tmp","wallet","walletAirport");
        Path walletPath = Paths.get("/home","mohanad-belal",".fabric-vscode","environments","myEnv","wallets","Org1");
        Wallet wallet = Wallet.createFileSystemWallet(walletPath);
        // load a CCP

        Path networkConfigPath = Paths.get("/home","mohanad-belal",".fabric-vscode","environments","myEnv","gateways","Org1","Org1.json");
        //Path networkConfigPath = Paths.get("/tmp","connection-profiles", "connection-airport.json");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, "org1Admin").networkConfig(networkConfigPath).discovery(true);
        try (Gateway gateway = builder.connect()) {
            // get the network and contract
            Network network = gateway.getNetwork("mychannel");
            Contract contract = network.getContract("IDContract");
            byte[] result;
            result = contract.submitTransaction("issueID", ID.getnumber(), ID.getAddress(), ID.getFullName(),
                    ID.getGender(),ID.getReligion(),ID.getJob(), ID.getMaritalStatus(),ID.getNationality(),ID.getDateOfBirth());
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


