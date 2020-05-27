package airport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import hospital.*;
import org.junit.jupiter.api.Test;

import org.hyperledger.fabric.contract.Context;
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


        MotherInfo motherInfo = new MotherInfo("Mervat Hassan AbdelHamed AbdelGhany",
                                                "Egyptian","Islam");

        FatherInfo fatherInfo = new FatherInfo("Nashaat Serry AbdelBar AbdelGawad",
                                                "Egyptian","Islam");
        BirthCertificate birthCertificate = new BirthCertificate("Arwa Nashaat Serry AbdelBar","Islam", "Female",
                                                    "001", "05-11-1998","Giza","Egyptian",
                                                    fatherInfo,motherInfo);
        birthCertificateContract.issueBirthCertificate(
                ctxMock,birthCertificate);

        assertThat(birthCertificate).isEqualTo(new BirthCertificate("Arwa Nashaat Serry AbdelBar",
                                                "Islam", "Female", "001",
                                                "05-11-1998","Giza", "Egyptian",
                                                fatherInfo,motherInfo));

    }

    @Test
    public void getBirthCertificateTest(){
        BirthCertificateContract birthCertificateContract = new BirthCertificateContract();

        Context ctxMock = mock(Context.class);
        ChaincodeStub stubMock = mock(ChaincodeStub.class);

        when(ctxMock.getStub()).thenReturn(stubMock);

        MotherInfo motherInfo = new MotherInfo("Mervat Hassan AbdelHamed AbdelGhany",
                "Egyptian","Islam");

        FatherInfo fatherInfo = new FatherInfo("Nashaat Serry AbdelBar AbdelGawad",
                "Egyptian","Islam");

        when(stubMock.getStringState("001")).thenReturn("{\"birthPlace\":\"Giza\",\"dateOfBirth\":\"05-11-1998\"," +
                "\"fatherInfo\":{\"fullName\":\"Nashaat Serry AbdelBar AbdelGawad\",\"nationality\":\"Egyptian\"," +
                "\"religion\":\"Islam\"},\"fullName\":\"Arwa Nashaat Serry AbdelBar\",\"gender\":\"Female\"," +
                "\"motherInfo\":{\"fullName\":\"Mervat Hassan AbdelHamed AbdelGhany\",\"nationality\":\"Egyptian\"," +
                "\"religion\":\"Islam\"},\"nationality\":\"Egyptian\",\"idNumber\":\"001\",\"religion\":\"Islam\"}");

        BirthCertificate birthCertificate = birthCertificateContract.getBirthCertificate(ctxMock,"001");

        assertThat(birthCertificate).isEqualTo(new BirthCertificate("Arwa Nashaat Serry AbdelBar",
                "Islam", "Female", "001",
                "05-11-1998","Giza", "Egyptian",
                fatherInfo,motherInfo));

    }
}