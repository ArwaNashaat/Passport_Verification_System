package controllers;

import chaincodes.BirthCertificate;
import com.fasterxml.jackson.databind.node.ObjectNode;
import components.ConfigurationComponent;
import org.hyperledger.fabric.gateway.*;
import chaincodes.ID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import services.Services;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;


@RestController
public class IDController {
    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/Airport/getInfo/{ID}")
    public String GetInfo(@PathVariable String ID) throws IOException {

        Services services = new Services();
        return services.getID("IDContractFromHome",ID);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value="/Airport/IssueID", consumes= MediaType.APPLICATION_JSON_VALUE)
    public String IssueID(@RequestBody ID id) throws IOException {

        Services services = new Services();
        return services.issueID(id);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value="/Airport/renewID", consumes= MediaType.APPLICATION_JSON_VALUE)
   // public boolean getTest(@RequestBody ObjectNode objectNode) {
        // And then you can call parameters from objectNode
     //   String strOne = objectNode.get("str1").asText();
       // String strTwo = objectNode.get("str2").asText();
    public String renewID(@RequestBody ObjectNode objectNode) throws IOException {
        String currentID = objectNode.get("currnetID").asText();
        String job = objectNode.get("job").asText();
        String maritalStatus = objectNode.get("maritalStatus").asText();
        System.out.println(currentID);
        System.out.println(job);
        System.out.println(maritalStatus);

        Services services = new Services();
        return services.renewID(currentID, job, maritalStatus);
    }

}


