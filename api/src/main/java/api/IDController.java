package api;

import org.hyperledger.fabric.gateway.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class IDController {
    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/Airport/getID/{ID}")
    public String GetID(@PathVariable String ID) throws IOException {
        Path walletPath = Paths.get("..","webapp","walletAirport");
        Wallet wallet = Wallet.createFileSystemWallet(walletPath);
        // load a CCP

        Path networkConfigPath = Paths.get("..","..","connection-profiles", "connection-airport.json");
        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, "user1").networkConfig(networkConfigPath).discovery(true);
        try (Gateway gateway = builder.connect()) {
            // get the network and contract
            Network network = gateway.getNetwork("mychannel");
            Contract contract = network.getContract("IDContract");
            byte[] result;

            result = contract.evaluateTransaction("getID",ID);
            System.out.println(new String(result));
            return String.valueOf(result);
        } catch (ContractException e) {
            e.printStackTrace();
        }

        return "ID not found";
    }

}


