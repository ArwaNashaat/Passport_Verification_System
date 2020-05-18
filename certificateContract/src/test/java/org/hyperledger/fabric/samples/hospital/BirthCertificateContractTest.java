package org.hyperledger.fabric.samples.hospital;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.Chaincode;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;

public class BirthCertificateContractTest {
    @Test
    //if it doesn't throw an error then the birth cert. is issued
    public void issueBirthCertificateTest(){
        BirthCertificateContract birthCertificateContract = new BirthCertificateContract();

        Context ctxMock = mock(Context.class);
        ChaincodeStub stubMock = mock(ChaincodeStub.class);

        when(ctxMock.getStub()).thenReturn(stubMock);
        when(stubMock.getStringState("001")).thenReturn("");

        MotherInfo motherInfo = new MotherInfo("Mervat Hassan", "00",
                                                "Egyptian","Islam");

        FatherInfo fatherInfo = new FatherInfo("Nashaat Serry", "00",
                                                "Egyptian","Islam");

        BirthCertificate birthCertificate1 = new BirthCertificate("Arwa Nashaat Serry","Islam", "Female",
                                                    "001", "05-11-1998","Giza","Egyptian",
                                                    fatherInfo,motherInfo);
        birthCertificateContract.issueBirthCertificate(
                ctxMock,birthCertificate1);

       /* assertThat(birthCertificate).isEqualTo(new BirthCertificate("Arwa Nashaat Serry",
                                                "Islam", "Female", "001",
                                                "05-11-1998","Giza", "Egyptian",
                                                fatherInfo,motherInfo));
*/
    }

    @Test
    public void getBirthCertificateTest(){
        BirthCertificateContract birthCertificateContract = new BirthCertificateContract();

        Context ctxMock = mock(Context.class);
        ChaincodeStub stubMock = mock(ChaincodeStub.class);

        when(ctxMock.getStub()).thenReturn(stubMock);

        MotherInfo motherInfo = new MotherInfo("Mervat Hassan", "00",
                "Egyptian","Islam");

        FatherInfo fatherInfo = new FatherInfo("Nashaat Serry", "00",
                "Egyptian","Islam");

        when(stubMock.getStringState("001")).thenReturn("{\"birthPlace\":\"Giza\",\"dateOfBirth\":\"05-11-1998\"," +
                "\"fatherInfo\":{\"name\":\"Nashaat Serry\",\"nationality\":\"Egyptian\",\"picture\":\"00\"," +
                "\"religion\":\"Islam\"},\"fullName\":\"Arwa Nashaat\",\"gender\":\"Female\"," +
                "\"motherInfo\":{\"name\":\"Mervat Hassan\",\"nationality\":\"Egyptian\",\"picture\":\"00\"," +
                "\"religion\":\"Islam\"},\"nationality\":\"Egyptian\",\"idNumber\":\"001\",\"religion\":\"Islam\"}");
        BirthCertificate birthCertificate = birthCertificateContract.getBirthCertificate(
                ctxMock,"001");

        assertThat(birthCertificate).isEqualTo(new BirthCertificate("Arwa Nashaat",
                "Islam", "Female", "001",
                "05-11-1998","Giza", "Egyptian",
                fatherInfo,motherInfo));

    }
}