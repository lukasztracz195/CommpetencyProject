package pl.competencyproject.model.CSV;

import pl.competencyproject.model.DAO.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private LibraryCSV library;
    private String choosedCSV;
    private Integer choosedLevel;
    private String StringENG;
    private String StringPL;
    private int PLindex;
    private int ENGindex;
    private FileOfCSV fileOfCSV;
    public static CSVReader instance;

    private CSVReader() {
        library = new LibraryCSV();
    }

    public static CSVReader getInstance() {
        if (instance == null) {
            synchronized (ManageFamily.class) {
                if (instance == null) {
                    instance = new CSVReader();
                }
            }
        }
        return instance;
    }


    public void chooseLevel(String nameLevel, String nameCategorie) {
        ManageLevels ml = ManageLevels.getInstance();
        int id = ml.existLevel(nameLevel, nameCategorie);
        if (id != -1) {
            choosedLevel = id;
        }
    }

    public void chooseCSV(String CSVname) throws FileNotFoundException//throws IOException
    {
        if (library.existFileCSVinFolder(CSVname)) {
            choosedCSV = CSVname;
        }
        fileOfCSV = new FileOfCSV(choosedCSV);
    }

    public void chooseCSV(File fileCSV) throws FileNotFoundException//throws IOException
    {
        fileOfCSV = new FileOfCSV(fileCSV);
    }

    public Integer insertDictionarySentences() throws FileNotFoundException {
        ManageDictionarySentences MDS = ManageDictionarySentences.getInstance();
        int records = 0;
        String line = fileOfCSV.getRead().nextLine();
        if (checkHeaderWordsSentenses(line)) {
            String[] headerParts = line.split(";");
            setIndexesPLENG(headerParts);
            while (fileOfCSV.getRead().hasNextLine()) {
                line = fileOfCSV.getRead().nextLine();
                String[] tokens = line.split(";");
                StringPL = tokens[PLindex];
                StringENG = tokens[ENGindex];
                if (MDS.existDictionarySentences(choosedLevel, StringENG, StringPL) == -1) {
                    records++;
                    MDS.insertDictionarySentece(choosedLevel, StringENG, StringPL);
                }
            }
        }
        return records;
    }

    public Integer insertDictionaryWordswithoutFamily() throws FileNotFoundException {
        int records = 0;
        ManageWordsENG MWE = ManageWordsENG.getInstance();
        ManageWordsPL MWP = ManageWordsPL.getInstance();
        ManageDictionaryWords MDW = ManageDictionaryWords.getInstance();
        String line = fileOfCSV.getRead().nextLine();
        if (checkHeaderWordsSentenses(line)) {
            String[] headerParts = line.split(";");
            setIndexesPLENG(headerParts);

            while (fileOfCSV.getRead().hasNextLine()) {
                line = fileOfCSV.getRead().nextLine();
                String[] tokens = line.split(";");
                StringPL = tokens[PLindex];
                StringENG = tokens[ENGindex];
                Integer IdWordENG = checkReturnOrAddWordENG(MWE, StringENG);
                Integer IdWordPL = checkReturnOrAddWordPL(MWP, StringPL);
                System.out.println("exist = " + MDW.existDictionaryWordsWithoutFamilie(choosedLevel, IdWordENG, IdWordPL));
                if (MDW.existDictionaryWordsWithoutFamilie(choosedLevel, IdWordENG, IdWordPL) == -1) {
                    records++;
                    MDW.insertDictionaryWordswithoutFamilie(choosedLevel, IdWordENG, IdWordPL);
                }
            }
        }
        return records;
    }

    public Integer insertFamily() {
        String line = fileOfCSV.getRead().nextLine();
        List<String> listStringPL = new ArrayList<>();
        List<String> listStringENG = new ArrayList<>();
        if (checkHeaderWordsSentenses(line)) {
            String[] headerParts = line.split(";");
            setIndexesPLENG(headerParts);
            while (fileOfCSV.getRead().hasNextLine()) {
                line = fileOfCSV.getRead().nextLine();
                String[] tokens = line.split(";");
                listStringPL.add(tokens[PLindex]);
                listStringENG.add(tokens[ENGindex]);
            }

        }
        return 0;
    }


    private boolean checkHeaderWordsSentenses(String header) {
        if (header.equals("WordPL;WordENG") || header.equals("WordENG;WordPL")) {
            return true;
        }
        return false;
    }

    private void setIndexesPLENG(String[] line) {

        if (line[0].equals("WordPL")) {
            PLindex = 0;
            ENGindex = 1;
        } else {
            PLindex = 1;
            ENGindex = 0;
        }
    }

    private Integer checkReturnOrAddWordPL(ManageWordsPL manager, String wordPL) {
        int addedId = manager.existWordPL(wordPL);
        if (addedId == -1) {
            addedId = manager.addWordPL(wordPL);
        }
        return addedId;
    }

    private Integer checkReturnOrAddWordENG(ManageWordsENG manager, String wordENG) {
        int addedId = manager.existWordENG(wordENG);
        if (addedId == -1) {
            addedId = manager.addWordENG(wordENG);
        }
        return addedId;
    }

    private String findShortestString(List<String> list){
        String shortString = list.get(0);
        for(int i =1;i<list.size();i++){
            if(list.get(i).length() < shortString.length()){
                shortString = list.get(i);
            }
        }
        String[] array = shortString.split(" ");
        int size = array.length;
        String shortStr = array[0];
        int sizeStr = shortStr.length();
        for(int i =1;i<size;i++ ){
            
        }
        return shortString;
    }

}