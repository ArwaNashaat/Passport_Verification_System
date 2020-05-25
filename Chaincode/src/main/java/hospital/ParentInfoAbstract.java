package hospital;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Objects;

@DataType
abstract public class ParentInfoAbstract {
    @Property()
    public String fullName;

    @Property()
    public String religion;

    @Property()
    public String nationality;


    public String getFullName(){ return fullName; }
    public String getReligion(){ return religion; }
    public String getNationality(){ return nationality; }

    public void validateParentInfo(){
        ValidateInfo validateInfo = new ValidateInfo();

        validateInfo.checkFullName(fullName);
        validateInfo.checkReligion(religion);

    }
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        ParentInfoAbstract other = (ParentInfoAbstract) obj;

        return Objects.deepEquals(new String[] {getFullName(), getReligion(),getNationality()},
                                  new String[] {other.getFullName(), other.getReligion(), other.getNationality()});
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFullName(), getReligion(), getNationality());
    }

    @Override
    abstract public String toString();
}
