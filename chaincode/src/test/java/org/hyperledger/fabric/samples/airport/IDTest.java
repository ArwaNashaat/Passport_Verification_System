package org.hyperledger.fabric.samples.airport;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.text.ParseException;

public final class IDTest {

    @Nested
    class Equality {

        @Test
        public void isReflexive(){
            ID id = (new ID("001", "228N", "Arwa Nashaat Serry abdelbar", "Female", "Islam",
                    "Software Engineer", "Single", "Egyption", "1998-05-11",
                    "2000-07-11", false));

            assertThat(id).isEqualTo(id);
        }

        @Test
        public void isSymmetric(){

            ID idA = new ID("001", "228N", "Arwa Nashaat Serry abdelbar", "Female", "Islam",
                    "Software Engineer", "Single", "Egyption",
                    "1998-05-11", "2027-07-11", false);

            ID idB =  new ID("001", "228N", "Arwa Nashaat Serry abdelbar", "Female", "Islam",
                    "Software Engineer", "Single", "Egyption", "1998-05-11",
                    "2000-07-11", true);

            assertThat(idA).isEqualTo(idB);
            assertThat(idB).isEqualTo(idA);
        }

        @Test
        public void isTransitive(){

            ID idA = new ID("001", "228N", "Arwa Nashaat Serry abdelbar", "Female", "Islam",
                    "Software Engineer", "Single", "Egyption",
                    "1998-05-11", "2000-07-11", true);

            ID idB = new ID("001", "228N", "Arwa Nashaat Serry abdelbar", "Female", "Islam",
                    "Software Engineer", "Single", "Egyption",
                    "1998-05-11", "2000-07-11", true);

            ID idC = new ID("001", "228N", "Arwa Nashaat Serry abdelbar", "Female", "Islam",
                    "Software Engineer", "Single", "Egyption",
                    "1998-05-11", "2000-07-11", true);

            assertThat(idA).isEqualTo(idB);
            assertThat(idB).isEqualTo(idC);
            assertThat(idA).isEqualTo(idC);
        }

        @Test
        public void handlesInequality(){

            ID idA = new ID("001", "228N", "Arwa Nashaat Serry abdelbar", "Female", "Islam",
                    "Software Engineer", "Single", "Egyption",
                    "1998-05-11", "2000-07-11", true);

            ID idB = new ID("002", "229N", "Nashaat Serry abdelbar abdelgawad", "male", "Islam",
                    "Accountant", "Married", "Egyption",
                    "1964-05-11", "2000-07-11", true);

            assertThat(idA).isNotEqualTo(idB);
        }

        @Test
        public void handlesOtherObjects() throws ParseException {

            ID idA = new ID("001", "228N", "Arwa Nashaat Serry abdelbar", "Female", "Islam",
                    "Software Engineer", "Single", "Egyption",
                    "1998-05-11", "2000-07-11", true);
            String idB = "not an ID";

            assertThat(idA).isNotEqualTo(idB);
        }

        @Test
        public void handlesNull() throws ParseException {

            ID id = new ID("001", "228N", "Arwa Nashaat Serry abdelbar", "Female", "Islam",
                    "Software Engineer", "Single", "Egyption",
                    "1998-05-11", "2000-07-11", true);

            assertThat(id).isNotEqualTo(null);
        }
    }

}
