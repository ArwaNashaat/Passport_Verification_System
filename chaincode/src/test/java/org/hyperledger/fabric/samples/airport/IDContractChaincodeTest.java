
package org.hyperledger.fabric.samples.airport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;


import java.util.*;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

public final class IDContractChaincodeTest {

    private final class MockKeyValue implements KeyValue {

        private final String key;
        private final String value;

        MockKeyValue(final String key, final String value) {
            super();
            this.key = key;
            this.value = value;
        }

        @Override
        public String getKey() {
            return this.key;
        }

        @Override
        public String getStringValue() {
            return this.value;
        }

        @Override
        public byte[] getValue() {
            return this.value.getBytes();
        }

    }

    private final class MockIDResultsIterator implements QueryResultsIterator<KeyValue> {

        private final List<KeyValue> idList;

        MockIDResultsIterator() {
            super();

            idList = new ArrayList<KeyValue>();

            idList.add(new MockKeyValue("001",
                    "{\"IDNumber\":\"001\",\"address\":\"228N\",\"fullName\":\"Arwa Nashaat Serry abdelbar\"" +
                    ",\"gender\":\"Female\",\"religion\":\"Islam\",\"job\":\"Sw Eng\",\"maritalStatus\":\"Single\"" +
                    ",\"nationality\":\"Egyption\",\"dateOfBirth\":\"1998-05-11\",\"expireDate\":\"2000-05-11\"" +
                    ",\"ID Expired\":\"true\"}"));

            idList.add(new MockKeyValue("002",
                    "{\"IDNumber\":\"002\",\"address\":\"288N\",\"fullName\":\"Amira Nashaat Serry abdelbar\"" +
                            ",\"gender\":\"Female\",\"religion\":\"Islam\",\"job\":\"Accountant\",\"maritalStatus\":\"Single\"" +
                            ",\"nationality\":\"Egyption\",\"dateOfBirth\":\"1999-08-23\",\"expireDate\":\"2022-05-11\"" +
                    ",\"ID Expired\":\"false\"}"));

            idList.add(new MockKeyValue("003",
                    "{\"IDNumber\":\"003\",\"address\":\"298N\",\"fullName\":\"Nashaat Serry abdelbar abdelgawad\"" +
                            ",\"gender\":\"Male\",\"religion\":\"Islam\",\"job\":\"Accountant\",\"maritalStatus\":\"Married\"" +
                            ",\"nationality\":\"Egyption\",\"dateOfBirth\":\"1964-12-21\",\"expireDate\":\"2022-05-11\""+
                            ",\"ID Expired\":\"false\"}"));

            idList.add(new MockKeyValue("004",
                    "{\"IDNumber\":\"004\",\"address\":\"288N\",\"fullName\":\"Amira Nashaat Serry abdelbar\"" +
                            ",\"gender\":\"Feale\",\"religion\":\"Islam\",\"job\":\"Accountant\",\"maritalStatus\":\"Single\"" +
                            ",\"nationality\":\"Egyption\",\"dateOfBirth\":\"1999-08-23\",\"expireDate\":\"2022-05-11\"" +
                    ",\"ID Expired\":\"false\"}"));
        }

        @Override
        public Iterator<KeyValue> iterator() {
            return idList.iterator();
        }

        @Override
        public void close() throws Exception {
            // do nothing
        }

    }

    @Test
    public void invokeUnknownTransaction() {
        IDContractChainCode contract = new IDContractChainCode();
        Context ctx = mock(Context.class);

        Throwable thrown = catchThrowable(() -> {
            contract.unknownTransaction(ctx);
        });

        assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause()
                .hasMessage("Undefined contract method called");
        assertThat(((ChaincodeException) thrown).getPayload()).isEqualTo(null);

        verifyZeroInteractions(ctx);
    }

    @Nested
    class InvokeGetIDTransaction {

        @Test
        public void whenIDExists(){
            IDContractChainCode contract = new IDContractChainCode();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("001"))
                    .thenReturn("{\"IDNumber\":\"001\",\"address\":\"228N\",\"fullName\":\"Arwa Nashaat Serry abdelbar\"" +
                            ",\"gender\":\"Female\",\"religion\":\"Islam\",\"job\":\"Sw Eng\",\"maritalStatus\":\"Single\"" +
                            ",\"nationality\":\"Egyption\",\"dateOfBirth\":\"1998-05-11\",\"expireDate\":\"2027-04-03\"}");

            ID id = contract.getID(ctx, "001");

            assertThat(id).isEqualTo(new ID("001", "228N", "Arwa Nashaat Serry abdelbar",
                    "Female","Islam","Sw Eng","Single","Egyption",
                    "1998-05-11", "2027-04-03", false));
        }

        @Test
        public void whenIDDoesNotExist() {
            IDContractChainCode contract = new IDContractChainCode();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("011")).thenReturn("");

            Throwable thrown = catchThrowable(() -> {
                contract.getID(ctx, "011");
            });

            assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause()
                    .hasMessage("ID 011 does not exist");
            assertThat(((ChaincodeException) thrown).getPayload()).isEqualTo("ID_NOT_FOUND".getBytes());
        }
    }

    /*@Test
    void invokeInitLedgerTransaction() {
        IDContractChainCode contract = new IDContractChainCode();
        Context ctx = mock(Context.class);
        ChaincodeStub stub = mock(ChaincodeStub.class);
        when(ctx.getStub()).thenReturn(stub);

        contract.initLedger(ctx);

        InOrder inOrder = inOrder(stub);
        inOrder.verify(stub).putStringState("001",
                "{\"color\":\"blue\",\"make\":\"Toyota\",\"model\":\"Prius\",\"owner\":\"Tomoko\"}");
        inOrder.verify(stub).putStringState("CAR001",
                "{\"color\":\"red\",\"make\":\"Ford\",\"model\":\"Mustang\",\"owner\":\"Brad\"}");
        inOrder.verify(stub).putStringState("CAR002",
                "{\"color\":\"green\",\"make\":\"Hyundai\",\"model\":\"Tucson\",\"owner\":\"Jin Soo\"}");
        inOrder.verify(stub).putStringState("CAR003",
                "{\"color\":\"yellow\",\"make\":\"Volkswagen\",\"model\":\"Passat\",\"owner\":\"Max\"}");
        inOrder.verify(stub).putStringState("CAR004",
                "{\"color\":\"black\",\"make\":\"Tesla\",\"model\":\"S\",\"owner\":\"Adrian\"}");
        inOrder.verify(stub).putStringState("CAR005",
                "{\"color\":\"purple\",\"make\":\"Peugeot\",\"model\":\"205\",\"owner\":\"Michel\"}");
        inOrder.verify(stub).putStringState("CAR006",
                "{\"color\":\"white\",\"make\":\"Chery\",\"model\":\"S22L\",\"owner\":\"Aarav\"}");
        inOrder.verify(stub).putStringState("CAR007",
                "{\"color\":\"violet\",\"make\":\"Fiat\",\"model\":\"Punto\",\"owner\":\"Pari\"}");
        inOrder.verify(stub).putStringState("CAR008",
                "{\"color\":\"indigo\",\"make\":\"Tata\",\"model\":\"nano\",\"owner\":\"Valeria\"}");
        inOrder.verify(stub).putStringState("CAR009",
                "{\"color\":\"brown\",\"make\":\"Holden\",\"model\":\"Barina\",\"owner\":\"Shotaro\"}");
    }*/

    @Nested
    class InvokeIssueIDTransaction {

        @Test
        public void whenIDExists() {
            IDContractChainCode contract = new IDContractChainCode();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("001"))
                    .thenReturn("{\"IDNumber\":\"001\",\"address\":\"228N\",\"fullName\":\"Arwa Nashaat Serry abdelbar\"" +
                            ",\"gender\":\"Female\",\"religion\":\"Islam\",\"job\":\"Sw Eng\",\"maritalStatus\":\"Single\"" +
                            ",\"nationality\":\"Egyption\",\"dateOfBirth\":\"1998-05-11\",\"expireDate\":\"2000-05-11\""+
                            ",\"ID Expired\":\"false\"}");

            Throwable thrown = catchThrowable(() -> {
                contract.issueID(ctx, "001", "228N", "Arwa Nashaat Serry abdelbar",
                "Female", "Islam", "Sw Eng", "Single", "Egyption",
                "1998-05-11");
            });

            assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause()
                    .hasMessage("ID 001 already exists");
            assertThat(((ChaincodeException) thrown).getPayload()).isEqualTo("ID_ALREADY_EXISTS".getBytes());
        }

        /*@Test
        public void setExpireDateTest(){
            IDContractChainCode contract = new IDContractChainCode();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("005")).thenReturn("");

            LocalDate ed = contract.setExpireDate();
            assertThat(ed).isEqualTo(LocalDate.parse("2027-04-03"));

        }*/

        /*@Test
        public void testGender(){
            IDContractChainCode contract = new IDContractChainCode();

            assertThat(contract.checkGender("Female")).isEqualTo(true);

        }*/

        @Test
        public void whenIDDoesNotExist(){
            IDContractChainCode contract = new IDContractChainCode();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("005")).thenReturn("");

            IDContractChainCode mockedContract = mock(IDContractChainCode.class);

            ID id = contract.issueID(ctx, "005", "228N", "Aya Nashaat Serry abdelbar",
                    "Female", "Islam", "Teacher", "Married", "Egyption",
                    "1991-10-01");
            assertThat(id).isEqualTo(new ID("005", "228N", "Aya Nashaat Serry abdelbar",
                    "Female", "Islam", "Teacher", "Married", "Egyption",
                    "1991-10-01","2027-04-05", false));
        }
    }


    @Nested
    class renewIDTransaction {

        @Test
        public void whenCarExists() {
            IDContractChainCode contract = new IDContractChainCode();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("001"))
                    .thenReturn("{\"IDNumber\":\"001\",\"address\":\"228N\",\"fullName\":\"Arwa Nashaat Serry abdelbar\"" +
                            ",\"gender\":\"Female\",\"religion\":\"Islam\",\"job\":\"Sw Eng\",\"maritalStatus\":\"Single\"" +
                            ",\"nationality\":\"Egyption\",\"dateOfBirth\":\"1998-05-11\",\"expireDate\":\"2000-05-11\"}");

            ID id = contract.renewID(ctx, "001", "227N", "Arwa Nashaat Serry AbdelBar",
                    "Islam", "Software Engineer", "Single");

            assertThat(id).isEqualTo(new ID("001", "227N", "Arwa Nashaat Serry AbdelBar",
                    "Female", "Islam", "Software Engineer", "Single",
                    "Egyption", "1998-05-11", "2027-04-05", false));
        }

        @Test
        public void whenCarDoesNotExist() {
            IDContractChainCode contract = new IDContractChainCode();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("005")).thenReturn("");

            Throwable thrown = catchThrowable(() -> {
                contract.renewID(ctx, "005", "335B", "Aya Nashat Serry AbdelBar", "Islam",
                        "Teacher", "Married");
            });

            assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause()
                    .hasMessage("ID 005 does not exist");
            assertThat(((ChaincodeException) thrown).getPayload()).isEqualTo("ID_NOT_FOUND".getBytes());
        }
    }
}
