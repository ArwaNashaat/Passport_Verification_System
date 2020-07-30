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

    @CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4400"})
    @GetMapping(path = "/Airport/getInfo/{ID}")
    public String GetInfo(@PathVariable String ID) throws IOException {

        Services services = new Services();
        return services.getID("IDContractFromHome",ID);

    }

    @CrossOrigin(origins = {"http://localhost:4400", "http://localhost:4200"})
    @PostMapping(value="/Airport/IssueID", consumes= MediaType.APPLICATION_JSON_VALUE)
    public String IssueID(@RequestBody ID id) throws IOException {

        Services services = new Services();
        return services.issueID(id);
    }

    @CrossOrigin(origins = {"http://localhost:4400", "http://localhost:4200"})
    @PostMapping(value="/Airport/renewID", consumes= MediaType.APPLICATION_JSON_VALUE)
    public String renewID(@RequestBody ObjectNode objectNode) throws IOException {
        String currentID = objectNode.get("currnetID").asText();
        String job = objectNode.get("job").asText();
        String maritalStatus = objectNode.get("maritalStatus").asText();

        Services services = new Services();
        return services.renewID(currentID, job, maritalStatus);
    }

}


