package services;


import chaincodes.ID;
import components.ConfigurationComponent;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class Services {
    public String getFunc(String functionName,String ID) throws IOException {

        ConfigurationComponent configurationComponent = new ConfigurationComponent();
        Gateway gatewayConfig = configurationComponent.setupGatewayConfigurations();

        try (Gateway gateway = gatewayConfig) {

            Contract contract = configurationComponent.getContract(gateway,"IDContract");

            byte[] result;
            result = contract.evaluateTransaction(functionName,ID);
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

            Contract contract = configurationComponent.getContract(gateway, "IDContractAtCivil");

            byte[] result = id.getPersonalPictureBytes();

            //result = Base64.decodeBase64(result);
            //String str = new String(Base64.encodeBase64(result), "UTF-8");
            //System.out.println(str);
            contract.submitTransaction("issueID", id.getNumber(), id.getAddress(), id.getFullName(),
                    id.getGender(),id.getReligion(),id.getJob(), id.getMaritalStatus(),id.getNationality(),id.getDateOfBirth(),String.valueOf(result));
            return true;

        } catch (ContractException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }
}
