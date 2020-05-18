package org.hyperledger.fabric.samples.hospital;

import java.util.Objects;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import com.owlike.genson.annotation.JsonProperty;

@DataType
public class FatherInfo extends ParentInfoAbstract{
       public FatherInfo(@JsonProperty("fatherName") final String fatherName, @JsonProperty("fatherPicture") final String fatherPicture,
                      @JsonProperty("fatherNationality") final String fatherNationality, @JsonProperty("fatherReligion") final String fatherReligion) {

        this.name = fatherName;
        this.picture = fatherPicture;
        this.nationality = fatherNationality;
        this.religion = fatherReligion;
    }

    @Override
    public String toString() {
        return ", fatherName='" + name + '\'' +
                ", fatherPicture='" + picture + '\'' +
                ", fatherNationality='" + nationality + '\'' +
                ", fatherReligion='" + religion;
    }
}

