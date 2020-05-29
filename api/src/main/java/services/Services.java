package services;


import chaincodes.BirthCertificate;
import chaincodes.ID;
import com.owlike.genson.Genson;
import components.ConfigurationComponent;
import org.hyperledger.fabric.gateway.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.TimeoutException;

import static org.apache.tomcat.util.codec.binary.Base64.decodeBase64;

@Service
public class Services {
    Genson genson = new Genson();

    public void getIdNumber(String idNumber) throws IOException {
        System.out.println(idNumber);
    }

    public String getFunc(String contractName, String functionName,String ID) throws IOException {
        ConfigurationComponent configurationComponent = new ConfigurationComponent();
        Gateway gatewayConfig = configurationComponent.setupGatewayConfigurations();

        try (Gateway gateway = gatewayConfig) {

            Contract contract = configurationComponent.getContract(gateway,contractName);

            byte[] result;
            result = contract.evaluateTransaction(functionName,ID);
            System.out.println(new String(result));

            ID id = genson.deserialize(result,ID.class);
            //System.out.println(ID);
            String pic = getPicture(ID);
            id.setPersonalPicture(pic);

            return genson.serialize(id);

        } catch (ContractException e) {
            e.printStackTrace();
        }

        return ID + "not found";
    }

    public String getPicture(String idNumber) throws IOException {
        String filePath = "../Pictures";
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



    public boolean issueID(ID id) throws IOException {

        ConfigurationComponent configurationComponent = new ConfigurationComponent();
        Gateway gatewayConfig = configurationComponent.setupGatewayConfigurations();

        try (Gateway gateway = gatewayConfig) {

            Contract contract = configurationComponent.getContract(gateway, "IDContractAtCivil");

            String path = "../Pictures/"+id.getIDNumber()+".png";
            System.out.println(id.toString());
            System.out.println(path);
            contract.submitTransaction("issueID", id.getIDNumber(), id.getAddress(), id.getFullName(),
                    id.getGender(),id.getReligion(),id.getJob(), id.getMaritalStatus(),id.getNationality(),id.getDateOfBirth(), path);

            savePicture(id.getPersonalPicture(), id.getIDNumber());

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

    public boolean savePicture(String picture, String picName) throws IOException {
        picture = picture.replace("data:image/png;base64,","");
        //id.setPersonalPicture(x);

        byte[] decodedImg = Base64.getDecoder().decode(picture);
        //"/path/to/imageDir", "myImage.jpg"
        //"/home/arwa/go/fabric-samples/Passport_Verification_System/api"
        Path destinationFile = Paths.get("../Pictures",picName+".png");
        Files.write(destinationFile, decodedImg);

        return true;
    }
}
