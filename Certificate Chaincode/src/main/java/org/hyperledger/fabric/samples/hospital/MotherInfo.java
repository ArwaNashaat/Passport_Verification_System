package org.hyperledger.fabric.samples.hospital;

import org.hyperledger.fabric.contract.annotation.DataType;
import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.Property;

@DataType
public class MotherInfo extends ParentInfoAbstract{
    public MotherInfo(@JsonProperty("motherName") final String motherName, @JsonProperty("motherPicture") final String motherPicture,
                      @JsonProperty("motherNationality") final String motherNationality, @JsonProperty("motherReligion") final String motherReligion) {

        this.fullName = motherName;
        this.picture = motherPicture;
        this.nationality = motherNationality;
        this.religion = motherReligion;
    }
    
    @Override
    public String toString() {
        return ", motherName='" + fullName + '\'' +
                ", motherPicture='" + picture + '\'' +
                ", motherNationality='" + nationality + '\'' +
                ", motherReligion='" + religion;
    }
}

