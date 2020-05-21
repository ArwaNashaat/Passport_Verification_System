package org.hyperledger.fabric.samples.hospital;

import org.hyperledger.fabric.shim.ChaincodeException;

import java.util.StringTokenizer;

public class ValidateInfo {
    private enum InformationErrors {
        INVALID_FULL_NAME,
        INVALID_Gender,
        INVALID_Religion,
    }

    public void checkFullName(String fullName){
        StringTokenizer fullNameTest = new StringTokenizer(fullName);

        if(fullNameTest.countTokens()!=4){
            String errorMessage = "Name is not valid, please Enter the full name consisting of 4 names";
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ValidateInfo.InformationErrors.INVALID_FULL_NAME.toString());
        }

    }

    public void checkGender(String gender){
        if(!gender.equals("Female") && !gender.equals("Male")){
            String errorMessage = "Gender is not valid, please Enter Female or Male only";
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ValidateInfo.InformationErrors.INVALID_Gender.toString());
        }

    }

    public void checkReligion(String religion){
        if(!religion.equals("Islam") && !religion.equals("Christianity") && !religion.equals("Judaism")){
            String errorMessage = "Relgion is not valid, please Enter Islam, Christianity or Judaism";
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, ValidateInfo.InformationErrors.INVALID_Religion.toString());
        }

    }

}
