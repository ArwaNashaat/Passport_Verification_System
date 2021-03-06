package services;


import chaincodes.BirthCertificate;
import chaincodes.ID;
import com.owlike.genson.Genson;
import components.ConfigurationComponent;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Gateway;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.TimeoutException;

@Service
public class Services {
    Genson genson = new Genson();
    String picturePath = System.getenv("PICTURE_PATH");

    public String renewID_Civil(String currentID, String address, String fullName, String religion, String job, String maritalStatus) throws IOException{
        ConfigurationComponent configurationComponent = new ConfigurationComponent();
        Gateway gatewayConfig = configurationComponent.setupGatewayConfigurations();

        try (Gateway gateway = gatewayConfig) {

            Contract contract = configurationComponent.getContract(gateway, "IDContractAtCivil");

            contract.submitTransaction("renewID", currentID,address, fullName,religion,job, maritalStatus);

            return currentID;

        } catch (ContractException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String renewID(String currentID, String job, String maritalStatus) throws IOException{
        ConfigurationComponent configurationComponent = new ConfigurationComponent();
        Gateway gatewayConfig = configurationComponent.setupGatewayConfigurations();

        try (Gateway gateway = gatewayConfig) {

            Contract contract = configurationComponent.getContract(gateway, "IDContractFromHome");

            contract.submitTransaction("renewID", currentID, job, maritalStatus);

            return currentID;

        } catch (ContractException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getID(String contractName,String IDNumber) throws IOException {

        ConfigurationComponent configurationComponent = new ConfigurationComponent();
        Gateway gatewayConfig = configurationComponent.setupGatewayConfigurations();

        try (Gateway gateway = gatewayConfig) {

            Contract contract = configurationComponent.getContract(gateway,contractName);

            byte[] result;
            result = contract.evaluateTransaction("getID",IDNumber);

            ID[] id = genson.deserialize(result,ID[].class);

            String pic = getPicture(IDNumber);
            id[0].setPersonalPicture(pic);

            return genson.serialize(id);

        } catch (ContractException e) {
            e.printStackTrace();
        }

        return IDNumber + "not found";
    }

    public boolean isIDHistoryValid(ID[] idHistory){
        if (idHistory.length != 1){
            return false;
        }
        return true;
    }

    public String getPicture(String idNumber) throws IOException {
        String filePath = picturePath;
        File file = new File(filePath);
        String image = null;

        if(file!=null){
            for(final File f: file.listFiles()){
                if(!f.isDirectory() && f.getName().equals(idNumber+".png")){
                    String encodeBase64 = null;
                    try{
                        FileInputStream fileInputStream = new FileInputStream(f);
                        byte[] bytes = new byte[(int)f.length()];
                        fileInputStream.read(bytes);
                        encodeBase64 = Base64.getEncoder().encodeToString(bytes);
                        image = "data:image/png;base64,"+ encodeBase64;
                        fileInputStream.close();

                        return image;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return "not found";
    }


    public String getBirthCertificate(String contractName, String ID) throws IOException {

        ConfigurationComponent configurationComponent = new ConfigurationComponent();
        Gateway gatewayConfig = configurationComponent.setupGatewayConfigurations();

        try (Gateway gateway = gatewayConfig) {

            Contract contract = configurationComponent.getContract(gateway,contractName);

            byte[] result;
            result = contract.evaluateTransaction("getBirthCertificate",ID);
            return new String(result);

        } catch (ContractException e) {
            e.printStackTrace();
        }

        return ID + "not found";
    }

    public String issueID(ID id) throws IOException {

        ConfigurationComponent configurationComponent = new ConfigurationComponent();
        Gateway gatewayConfig = configurationComponent.setupGatewayConfigurations();

        try (Gateway gateway = gatewayConfig) {

            Contract contract = configurationComponent.getContract(gateway, "IDContractAtCivil");
            Contract contract2 = configurationComponent.getContract(gateway, "IDContractFromHome");

            byte[] lastID = contract2.evaluateTransaction("getLastIDNumber");
            Integer lastIDInt = Integer.parseInt(new String(lastID))+1;
            String lastIDString = lastIDInt.toString();

            String path = picturePath+lastIDString+".png";

            contract.submitTransaction("issueID", id.getAddress(), id.getFullName(),
                    id.getGender(),id.getReligion(),id.getJob(), id.getMaritalStatus(),id.getDateOfBirth(), path);

            savePicture(id.getPersonalPicture(), lastIDString);

            return lastIDString;

        } catch (ContractException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean issueBirthCertificate(BirthCertificate birthCertificate) throws IOException {
        ConfigurationComponent configurationComponent = new ConfigurationComponent();
        Gateway gatewayConfig = configurationComponent.setupGatewayConfigurations();

        try (Gateway gateway = gatewayConfig) {

            Contract contract = configurationComponent.getContract(gateway, "BirthCertificateContract");

            System.out.println(birthCertificate.getFatherInfo());
            System.out.println(birthCertificate.getMotherInfo());
            contract.submitTransaction("issueBirthCertificate", birthCertificate.getFullName(), birthCertificate.getReligion(),
                    birthCertificate.getGender(), birthCertificate.getDateOfBirth(), birthCertificate.getBirthPlace(),
                    birthCertificate.getNationality(), birthCertificate.getFatherInfo().getFullName(),
                    birthCertificate.getMotherInfo().getFullName());
            return true;
        }
        catch (ContractException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean savePicture(String picture, String picName) throws IOException {
        picture = picture.replace("data:image/png;base64,","");

        byte[] decodedImg = Base64.getDecoder().decode(picture);
        Path destinationFile = Paths.get(picturePath,picName+".png");
        Files.write(destinationFile, decodedImg);

        return true;
    }

    public String getBC(String contractName,String ID, String childName) throws IOException {

        ConfigurationComponent configurationComponent = new ConfigurationComponent();
        Gateway gatewayConfig = configurationComponent.setupGatewayConfigurations();

        try (Gateway gateway = gatewayConfig) {

            Contract contract = configurationComponent.getContract(gateway,contractName);

            byte[] result;

            result = contract.evaluateTransaction("getBirthCertByParentID",ID, childName);

            return new String(result);

        } catch (ContractException e) {
            e.printStackTrace();
        }

        return ID + "not found";
    }

}
