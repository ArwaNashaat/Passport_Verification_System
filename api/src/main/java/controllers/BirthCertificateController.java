package controllers;

import chaincodes.BirthCertificate;
import org.hyperledger.fabric.gateway.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import services.Services;

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

        Services services = new Services();
        return services.getBirthCertificate("BirthCertificateContract",ID);

    }

    @CrossOrigin(origins = "http://localhost:4300")
    @PostMapping(value="/Hospital/issueCertificate", consumes= MediaType.APPLICATION_JSON_VALUE)
    public boolean issueCertificate(@RequestBody BirthCertificate birthCertificate) throws IOException {
        Services services = new Services();
        return services.issueBirthCertificate(birthCertificate);
    }

}
