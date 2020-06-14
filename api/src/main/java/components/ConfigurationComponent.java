package components;

import org.hyperledger.fabric.gateway.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ConfigurationComponent {
    private final String walletPathString = System.getenv("WALLET_PATH");
    private final String networkConfigPathString = System.getenv("CONNECTION_PROFILE_PATH");
    private final String orgID = "org1Admin";
    private final String channleName = "mychannel";
    private final String chaincodeId = "Chaincode";

    public Gateway setupGatewayConfigurations() throws IOException {
        System.out.println(walletPathString);
        System.out.println(networkConfigPathString);
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
