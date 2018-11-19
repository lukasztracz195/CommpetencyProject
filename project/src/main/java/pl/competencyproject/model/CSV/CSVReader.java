package pl.competencyproject.model.CSV;

import pl.competencyproject.model.DAO.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
    private LibraryCSV library;
    private String choosedCSV;
    private int choosedLevel;
    public static CSVReader instance;

    private CSVReader() {
        library = new LibraryCSV();
    }

    public static CSVReader getInstance() {
        if (instance == null) {
            synchronized (ManageFamilie.class) {
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

    public void chooseCSV(String CSVname)//throws IOException
    {
        if (library.existFileCSVinFolder(CSVname)) {
            choosedCSV = CSVname;
        }
    }

    public void insertDictionarySentences() {
        ManageDictionarySentences MDS = ManageDictionarySentences.getInstance();
        BufferedReader fileReader = null;
        try {
            String line = "";
            fileReader = new BufferedReader(new FileReader(library.getFullFolderPath() + choosedCSV + ".csv"));
            String SentenceENG = "";
            String SentencePL = "";
            int i = 0;
            while ((line = fileReader.readLine()) != null) {
                String[] tokens = line.split(";");
                for (String token : tokens) {
                    if (i == 0) SentenceENG = token;
                    if (i == 1) SentencePL = token;
                    i++;
                }
                i = 0;
                MDS.insertDictionarySentece(choosedLevel, SentenceENG, SentencePL);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertDictionaryWords() {

        try {
            ManageWordsENG MWE = ManageWordsENG.getInstance();
            ManageWordsPL MWP = ManageWordsPL.getInstance();
            ManageDictionaryWords MDW = ManageDictionaryWords.getInstance();
            BufferedReader fileReader = null;

            String line = "";
            fileReader = new BufferedReader(new FileReader(library.getFullFolderPath() + choosedCSV + ".csv"));
            String WordENG = "";
            String WordPL = "";
            Integer IdWordENG = 0;
            Integer IdWordPL = 0;
            int i = 0;
            fileReader.readLine();
            while ((line = fileReader.readLine()) != null) {
                String[] tokens = line.split(";");
                for (String token : tokens) {
                    if (i == 0) WordENG = token;
                    if (i == 1) WordPL = token;
                    i++;
                }
                MWE.addWordENG(WordENG);
                MWP.addWordPL(WordPL);
                IdWordENG = MWE.existWordENG(WordENG);
                if (IdWordENG == -1) {
                    IdWordENG = MWE.addWordENG(WordENG);
                }
                IdWordPL = MWP.existWordPL(WordPL);
                if (IdWordPL == -1) {
                    MWP.addWordPL(WordPL);
                }
                if(MDW.existDictionaryWords(choosedLevel,null, IdWordENG, IdWordPL) ==-1) {
                    MDW.insertDictionaryWordswithoutFamilie(choosedLevel, IdWordENG, IdWordPL);
                }
                i = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}