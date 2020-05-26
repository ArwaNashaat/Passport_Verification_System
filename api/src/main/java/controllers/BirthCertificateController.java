package controllers;

import chaincodes.BirthCertificate;
import org.hyperledger.fabric.gateway.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;

@RestController
public class BirthCertificateController {
    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    @CrossOrigin(origins = "http://localhost:4300")
    @GetMapping(path = "/Hospital/getCertificate/{ID}")
    public String GetCertificate(@PathVariable String ID) throws IOException {
        Path walletPath = Paths.get("/home","arwa",".fabric-vscode","environments","airport","wallets","Org1");

        Wallet wallet = Wallet.createFileSystemWallet(walletPath);

        Path networkConfigPath = Paths.get("/home","arwa",".fabric-vscode","environments","airport","gateways","Org1","Org1.json");

        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, "org1Admin").networkConfig(networkConfigPath).discovery(true);
        try (Gateway gateway = builder.connect()) {

            Network network = gateway.getNetwork("mychannel");
            Contract contract = network.getContract("Chaincode","BirthCertificateContract");
            byte[] result;

            result = contract.evaluateTransaction("getBirthCertificate",ID);
            System.out.println(new String(result));
            return new String(result);
        } catch (ContractException e) {
            e.printStackTrace();
        }

        return "Certificate not found";
    }


    @CrossOrigin(origins = "http://localhost:4300")
    @PostMapping(value="/Hospital/issueCertificate", consumes= MediaType.APPLICATION_JSON_VALUE)
    public boolean issueCertificate(@RequestBody BirthCertificate birthCertificate) throws IOException {
        Path walletPath = Paths.get("/home","arwa",".fabric-vscode","environments","airport","wallets","Org1");

        Wallet wallet = Wallet.createFileSystemWallet(walletPath);

        Path networkConfigPath = Paths.get("/home","arwa",".fabric-vscode","environments","airport","gateways","Org1","Org1.json");

        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, "org1Admin").networkConfig(networkConfigPath).discovery(true);
        try (Gateway gateway = builder.connect()) {

            Network network = gateway.getNetwork("mychannel");
            Contract contract = network.getContract("Chaincode","BirthCertificateContract");

            contract.submitTransaction("issueBirthCertificate", birthCertificate.getFullName(), birthCertificate.getReligion(),
                    birthCertificate.getGender(),birthCertificate.getIdNumber(), birthCertificate.getDateOfBirth(),
                    birthCertificate.getBirthPlace(), birthCertificate.getNationality(), birthCertificate.getFatherName(),
                    birthCertificate.getFatherNationality(), birthCertificate.getFatherReligion(), birthCertificate.getMotherName(),
                    birthCertificate.getMotherNationality(), birthCertificate.getMotherReligion());
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
