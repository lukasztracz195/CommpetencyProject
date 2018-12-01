package pl.competencyproject.model.DAO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.competencyproject.model.CSV.CSVReader;
import pl.competencyproject.model.DAO.classes.ManageDictionaryWords;
import pl.competencyproject.model.DAO.classes.ManageLevels;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ReaderCSVTest {

    private CSVReader csvReader = CSVReader.getInstance();


        @Test
        public void addFamily() throws FileNotFoundException {
            SessionLogon.IdLoggedUser = 1;
            ManageLevels ML = ManageLevels.getTestInstance();
            ManageDictionaryWords MDW = ManageDictionaryWords.getTestInstance();
            ML.addLevel("B2","Working life");
            int id = ML.existLevel("B2","Working life");
            Assertions.assertEquals(1,id);
            csvReader.chooseCSV("FAMILY");
            csvReader.chooseLevel("B2","Working life");
            int insertet = csvReader.insertFamily(true);
            int insertet2 = MDW.getDictionaryByFamilie(1).size();
            Assertions.assertEquals(6,insertet2);
        }

    @Test
    public void LCSTest() {
        String source = "employ";
        String consider = "employable";
        String result = csvReader.LCS(source, consider);
        Assertions.assertEquals("employ", result);
    }

    @Test
    public void LevensteinTest() {
        String source = "employ";
        String consider = "employable";
        int result = csvReader.levenstein(source, consider);
        Assertions.assertEquals(4, result);
    }

    @Test
    public void isInFamilyTest(){
        String source = "employ";
        String consider = "to employ";
        boolean result = csvReader.isItFamily(source, consider);
        Assertions.assertTrue(result);
    }

    @Test
    public void selectLongestWord(){
        String stringWithSpace = "gsdggggdhd hdfhdhdhfs gggshhs adgfagdg aggaggg aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String result = csvReader.selectlongestWord(stringWithSpace);
        Assertions.assertEquals("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",result);
    }

    @Test
    public void findHeadFamilyTest(){
            List<String> list = new ArrayList<>();
            list.add("employ");
            list.add("employment");
            list.add("unemployment");
            list.add("employee");
            list.add("employer");
            list.add("employable");
            String  head = csvReader.findHeadFamily(list);
            Assertions.assertEquals("employ",head);
    }
}
