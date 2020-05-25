package api;

import chaincodes.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.hyperledger.fabric.gateway.*;
import chaincodes.ID;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sun.nio.ch.Net;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;


@RestController
public class IDController {
    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    String walletPathString = "/home/arwa/.fabric-vscode/environments/airport/wallets/Org1";
    String networkConfigPathString = "/home/arwa/.fabric-vscode/environments/airport/gateways/Org1/Org1.json";
    String orgID = "org1Admin";
    String channleName = "mychannel";
    String chaincodeId = "Chaincode";
    String contractName = "IDContract";

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/Airport/getInfo/{ID}")
    public String GetInfo(@PathVariable String ID) throws IOException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.journaldev.spring");
        context.refresh();

        ConfigurationComponent configurationComponent = context.getBean(ConfigurationComponent.class);
        Gateway gatewayConfig = configurationComponent.getGateway(walletPathString, networkConfigPathString, orgID);


        try (Gateway gateway = gatewayConfig) {


            Contract contract = configurationComponent.getContract(gateway, channleName, chaincodeId, contractName);
            context.close();

            byte[] result;
            result = contract.evaluateTransaction("getID",ID);
            return new String(result);

        } catch (ContractException e) {
            e.printStackTrace();
        }

        return "ID not found";
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value="/Airport/IssueID", consumes= MediaType.APPLICATION_JSON_VALUE)
    public boolean IssueID(@RequestBody ID id) throws IOException {
        Path walletPath = Paths.get("/home","arwa",".fabric-vscode","environments","airport","wallets","Org1");

        Wallet wallet = Wallet.createFileSystemWallet(walletPath);
        // load a CCP

        Path networkConfigPath = Paths.get("/home","arwa",".fabric-vscode","environments","airport","gateways","Org1","Org1.json");

        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, "org1Admin").networkConfig(networkConfigPath).discovery(true);
        try (Gateway gateway = builder.connect()) {
            String x = id.getPersonalPicture().replace("data:image/png;base64,","");
            id.setPersonalPicture(x);
            System.out.println(id.getPersonalPicture());

            Network network = gateway.getNetwork("mychannel");

            Contract contract = network.getContract("Chaincode","IDContractHome");
            byte[] result = id.getPersonalPicture().getBytes();

            //result = Base64.decodeBase64(result);
            //String str = new String(Base64.encodeBase64(result), "UTF-8");
            //System.out.println(str);
            contract.submitTransaction("issueID", id.getNumber(), id.getAddress(), id.getFullName(),
                    id.getGender(),id.getReligion(),id.getJob(), id.getMaritalStatus(),id.getNationality(),id.getDateOfBirth(),String.valueOf(result));
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (ContractException e) {
            e.printStackTrace();
        }
        return false;
    }

}


