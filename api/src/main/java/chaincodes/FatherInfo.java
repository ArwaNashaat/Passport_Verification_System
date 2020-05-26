package chaincodes;

import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;

@DataType
public class FatherInfo extends ParentInfoAbstract {
    public FatherInfo(@JsonProperty("fatherName") final String fatherName, @JsonProperty("fatherNationality") final String fatherNationality, @JsonProperty("fatherReligion") final String fatherReligion) {

        this.fullName = fatherName;
        this.nationality = fatherNationality;
        this.religion = fatherReligion;
    }

    @Override
    public String toString() {
        return ", fatherName='" + fullName + '\'' +
                ", fatherNationality='" + nationality + '\'' +
                ", fatherReligion='" + religion;
    }
}

