package services;


import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.springframework.stereotype.Service;

@Service
public class GetService {
    public String getService(Contract contract,String functionName, String idNumber) throws ContractException {
        byte[] result;
        result = contract.evaluateTransaction(functionName,idNumber);
        return new String(result);
    }
}
