package services;


import chaincodes.BirthCertificate;
import chaincodes.ID;
import components.ConfigurationComponent;
import org.apache.commons.codec.binary.Base64;
import org.hyperledger.fabric.gateway.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;

@Service
public class Services {
    public String getFunc(String contractName, String functionName,String ID) throws IOException {

        ConfigurationComponent configurationComponent = new ConfigurationComponent();
        Gateway gatewayConfig = configurationComponent.setupGatewayConfigurations();

        try (Gateway gateway = gatewayConfig) {

            Contract contract = configurationComponent.getContract(gateway,contractName);

            byte[] result;
            result = contract.evaluateTransaction(functionName,ID);
            System.out.println(new String(result));
            return new String(result);

        } catch (ContractException e) {
            e.printStackTrace();
        }

        return ID + "not found";
    }

    public boolean issueID(ID id) throws IOException {

        ConfigurationComponent configurationComponent = new ConfigurationComponent();
        Gateway gatewayConfig = configurationComponent.setupGatewayConfigurations();

        try (Gateway gateway = gatewayConfig) {
            id.removedataImagString();
            System.out.println(id.getPersonalPicture());
            System.out.println(id.getPersonalPictureBytes());

            Contract contract = configurationComponent.getContract(gateway, "IDContractAtCivil");

            byte[] result = id.getPersonalPictureBytes();
            /*String pic = new String(result, "UTF-8");
            System.out.println(pic);
            */
            contract.submitTransaction("issueID", id.getNumber(), id.getAddress(), id.getFullName(),
                    id.getGender(),id.getReligion(),id.getJob(), id.getMaritalStatus(),id.getNationality(),id.getDateOfBirth(),String.valueOf(result));
            return true;

        } catch (ContractException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean issueBirthCertificate(BirthCertificate birthCertificate) throws IOException {
        ConfigurationComponent configurationComponent = new ConfigurationComponent();
        Gateway gatewayConfig = configurationComponent.setupGatewayConfigurations();

        try (Gateway gateway = gatewayConfig) {

            Contract contract = configurationComponent.getContract(gateway, "BirthCertificateContract");
            System.out.println(birthCertificate.toString());
            contract.submitTransaction("issueBirthCertificate", birthCertificate.getFullName(), birthCertificate.getReligion(),
                    birthCertificate.getGender(),birthCertificate.getIdNumber(), birthCertificate.getDateOfBirth(),
                    birthCertificate.getBirthPlace(), birthCertificate.getNationality(), birthCertificate.getFullName(),
                    birthCertificate.getNationality(), birthCertificate.getReligion(), birthCertificate.getFullName(),
                    birthCertificate.getNationality(), birthCertificate.getReligion());
            return true;
        }
        catch (ContractException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
