package components;

import org.hyperledger.fabric.gateway.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ConfigurationComponent {
    private final String walletPathString = "/home/arwa/.fabric-vscode/environments/airport/wallets/Org1";
    private final String networkConfigPathString = "/home/arwa/.fabric-vscode/environments/airport/gateways/Org1/Org1.json";
    private final String orgID = "org1Admin";
    private final String channleName = "mychannel";
    private final String chaincodeId = "Chaincode";
    //private String contractName = "IDContract";

    public Gateway setupGatewayConfigurations() throws IOException {

        Path walletPath = Paths.get(walletPathString);
        Wallet wallet = Wallet.createFileSystemWallet(walletPath);

        Path networkConfigPath = Paths.get(networkConfigPathString);

        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, orgID).networkConfig(networkConfigPath).discovery(true);

        return builder.connect();

    }

    public Contract getContract(Gateway gateway, String contractName){

        Network network = gateway.getNetwork(channleName);
        return network.getContract(chaincodeId,contractName);

    }
}
