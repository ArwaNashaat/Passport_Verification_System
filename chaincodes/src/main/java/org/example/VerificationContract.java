/*
 * SPDX-License-Identifier: Apache-2.0
 */
package org.example;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import static java.nio.charset.StandardCharsets.UTF_8;

@Contract(name = "VerificationContract",
    info = @Info(title = "Verification contract",
                description = "My Smart Contract",
                version = "0.0.1",
                license =
                        @License(name = "Apache-2.0",
                                url = ""),
                                contact =  @Contact(email = "chaincodes@example.com",
                                                name = "chaincodes",
                                                url = "http://chaincodes.me")))
@Default
public class VerificationContract implements ContractInterface {
    public  VerificationContract() {

    }
    @Transaction()
    public boolean verificationExists(Context ctx, String verificationId) {
        byte[] buffer = ctx.getStub().getState(verificationId);
        return (buffer != null && buffer.length > 0);
    }

    @Transaction()
    public void createVerification(Context ctx, String verificationId, String value) {
        boolean exists = verificationExists(ctx,verificationId);
        if (exists) {
            throw new RuntimeException("The asset "+verificationId+" already exists");
        }
        Verification asset = new Verification();
        asset.setValue(value);
        ctx.getStub().putState(verificationId, asset.toJSONString().getBytes(UTF_8));
    }

    @Transaction()
    public Verification readVerification(Context ctx, String verificationId) {
        boolean exists = verificationExists(ctx,verificationId);
        if (!exists) {
            throw new RuntimeException("The asset "+verificationId+" does not exist");
        }

        Verification newAsset = Verification.fromJSONString(new String(ctx.getStub().getState(verificationId),UTF_8));
        return newAsset;
    }

    @Transaction()
    public void updateVerification(Context ctx, String verificationId, String newValue) {
        boolean exists = verificationExists(ctx,verificationId);
        if (!exists) {
            throw new RuntimeException("The asset "+verificationId+" does not exist");
        }
        Verification asset = new Verification();
        asset.setValue(newValue);

        ctx.getStub().putState(verificationId, asset.toJSONString().getBytes(UTF_8));
    }

    @Transaction()
    public void deleteVerification(Context ctx, String verificationId) {
        boolean exists = verificationExists(ctx,verificationId);
        if (!exists) {
            throw new RuntimeException("The asset "+verificationId+" does not exist");
        }
        ctx.getStub().delState(verificationId);
    }

}
