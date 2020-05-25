package api;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ConfigurationComponent {

    public Gateway getGateway(String walletPathString, String networkConfigPathString, String orgID) throws IOException {

        Path walletPath = Paths.get(walletPathString);
        Wallet wallet = Wallet.createFileSystemWallet(walletPath);

        Path networkConfigPath = Paths.get(networkConfigPathString);

        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, orgID).networkConfig(networkConfigPath).discovery(true);

        return builder.connect();
    }

    public Contract getContract(Gateway gateway, String channleName, String chaincodeId, String contractName){
        Network network = gateway.getNetwork("mychannel");
        return network.getContract("Chaincode","IDContract");

    }
}
