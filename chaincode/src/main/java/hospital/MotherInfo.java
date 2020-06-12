package hospital;

import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;

@DataType
public class MotherInfo extends ParentInfoAbstract {
    public MotherInfo(@JsonProperty("motherName") final String motherName, @JsonProperty("motherNationality") final String motherNationality,
                      @JsonProperty("motherReligion") final String motherReligion, @JsonProperty("motherIdNumber") final String motherIdNumber) {

        this.fullName = motherName;
        this.nationality = motherNationality;
        this.religion = motherReligion;
        this.idNumber = motherIdNumber;
    }
    
    @Override
    public String toString() {
        return ", motherName='" + fullName + '\'' +
                ", motherNationality='" + nationality + '\'' +
                ", motherReligion='" + religion;
    }
}

