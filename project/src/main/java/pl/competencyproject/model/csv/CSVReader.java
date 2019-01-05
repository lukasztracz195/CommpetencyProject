package pl.competencyproject.model.csv;

import lombok.ToString;
import pl.competencyproject.model.dao.classes.*;
import pl.competencyproject.model.enums.TypeOfDictionaryDownloaded;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
@ToString
public class CSVReader {
    public static CSVReader instance;
    private TypeOfUsedDatabase type;
    private TypeOfDictionaryDownloaded typeDictionary;
    private LibraryCSV library;
    private FileOfCSV fileOfCSV;
    private Integer choosedLevel;
    private List<String> listOfLines;
    private boolean transferCommplete =false;

    private String choosedCSV;
    private String StringENG;
    private String StringPL;

    private int PLindex;
    private int ENGindex;
    private int numberOfLines = 0;
    private int valueProgress = 0;

    private static ManageFamily MF = null;
    private static ManageWordsENG MWE = null;
    private static ManageWordsPL MWP = null;
    private static ManageDictionaryWords MDW = null;

    public synchronized boolean getTransferCommplete(){return transferCommplete;}

    private CSVReader(TypeOfUsedDatabase type) {
        library = new LibraryCSV(type);
        this.type = type;
    }

    public static CSVReader getInstance(TypeOfUsedDatabase type) {
        if (instance == null) {
            synchronized (ManageFamily.class) {
                if (instance == null) {
                    instance = new CSVReader(type);
                }
            }
        }
        return instance;
    }

    public boolean chooseLevel(String nameLevel, String nameOfCategory) {
        ManageLevels ml = new ManageLevels(type);
        int id = ml.existLevel(nameLevel, nameOfCategory);
        if (id != -1) {
            choosedLevel = id;
            return true;
        } else {
            System.out.println("NIE MA TAKIEGO LEVELU");
            return false;
        }
    }

    public boolean chooseCSV(String CSVname) {
        if (library.existFileCSVinFolder(CSVname)) {
            choosedCSV = CSVname;
            fileOfCSV = new FileOfCSV(choosedCSV, type);
            readLinesFromFile();
            return true;
        }
        return false;


    }

    public void chooseCSV(File fileCSV) {
        fileOfCSV = new FileOfCSV(fileCSV);
        readLinesFromFile();
    }

    public void setTypeDictionary(TypeOfDictionaryDownloaded type) {
        typeDictionary = type;
    }

    public int initSentToDB() {
        int result = 0;
        if (typeDictionary.equals(TypeOfDictionaryDownloaded.DictionaryOfWords)) {
            result = insertDictionaryWordswithoutFamily();
        } else if (typeDictionary.equals(TypeOfDictionaryDownloaded.DictionaryOfFamilys)) {
            result = insertFamily();
        } else if (typeDictionary.equals(TypeOfDictionaryDownloaded.DictionaryOfSentences)) {
            result = insertDictionarySentences();
        }
        transferCommplete = true;
        return result;
    }

    private int insertDictionarySentences() {
        ManageDictionarySentences MDS = null;

        MDS = new ManageDictionarySentences(type);

        int records = 0;
        String header = listOfLines.get(0);
        if (checkHeaderWordsSentenses(header)) {
            String[] headerParts = header.split(";");
            setIndexesPLENG(headerParts);
            String line = null;
            for (int i = 1; i < listOfLines.size(); i++) {
                valueProgress++;
                line = listOfLines.get(i);
                String[] tokens = line.split(";");
                StringPL = tokens[PLindex];
                StringENG = tokens[ENGindex];
                int check = MDS.existDictionarySentences(choosedLevel, StringENG, StringPL);
                if (check == -1) {
                    records++;
                    MDS.insertDictionarySentece(choosedLevel, StringENG, StringPL);
                }
            }
        }
        return records;
    }

    private void readLinesFromFile() {
        listOfLines = null;
        listOfLines = new ArrayList<>();
        while (fileOfCSV.getRead().hasNextLine()) {
            String line = fileOfCSV.getRead().nextLine();
            if (line.contains(";")) {
                listOfLines.add(line);
            }
        }
        numberOfLines = listOfLines.size();
    }

    private int insertDictionaryWordswithoutFamily() {
        int records = 0;
        MWE = new ManageWordsENG(type);
        MWP = new ManageWordsPL(type);
        MDW = new ManageDictionaryWords(type);
        String header = listOfLines.get(0);
        if (checkHeaderWordsSentenses(header)) {
            String[] headerParts = header.split(";");
            setIndexesPLENG(headerParts);
            for (int i = 1; i < listOfLines.size(); i++) {
                valueProgress++;
                String[] tokens = listOfLines.get(i).split(";");
                StringPL = tokens[PLindex];
                StringENG = tokens[ENGindex];
                Integer IdWordENG = checkReturnOrAddWordENG(MWE, StringENG);
                Integer IdWordPL = checkReturnOrAddWordPL(MWP, StringPL);
                int check = MDW.existDictionaryWordsWithoutFamilie(choosedLevel, IdWordENG, IdWordPL);
                if (check == -1) {
                    MDW.insertDictionaryWordswithoutFamilie(choosedLevel, IdWordENG, IdWordPL);
                    records++;
                }
            }
        } else System.out.println("Nagłówek się nie zgadza");
        return records;
    }

    private int insertFamily() {
        MF = new ManageFamily(type);
        MWE = new ManageWordsENG(type);
        MWP = new ManageWordsPL(type);
        MDW = new ManageDictionaryWords(type);

        String header = listOfLines.get(0);
        if (checkHeaderWordsSentenses(header)) {
            String[] headerParts = header.split(";");
            setIndexesPLENG(headerParts);
            List<String> listStringENG = new ArrayList<>();
            List<String> listWordENG = new ArrayList<>();
            List<String> listStringPL = new ArrayList<>();
            String line = null;
            for (int i = 1; i < listOfLines.size(); i++) {
                line = listOfLines.get(i);
                String[] tokens = line.split(";");
                listStringPL.add(tokens[PLindex]);
                listStringENG.add(tokens[ENGindex]);
                listWordENG.add(selectlongestWord(tokens[ENGindex]));
            }

            String headFamily = findHeadFamily(listWordENG);
            int idFamily = MF.addFamily(choosedLevel, headFamily);
            System.out.println(idFamily);
            int IdWordENG;
            int IdWordPL;
            int howAdded = 0;
            for (int i = 0; i < listStringENG.size(); i++) {
                valueProgress++;
                String considered = listStringENG.get(i);
                String translation = listStringPL.get(i);
                if (isItFamily(headFamily, considered)) {
                    IdWordENG = MWE.addWordENG(considered);
                    IdWordPL = MWP.addWordPL(translation);
                    if (IdWordENG == -1) IdWordENG = MWE.existWordENG(considered);
                    if (IdWordPL == -1) IdWordPL = MWP.existWordPL(translation);
                    if (MDW.existDictionaryWords(choosedLevel, idFamily, IdWordENG, IdWordPL) == -1) {
                        int exist = MDW.existDictionaryWordsWithoutFamilie(choosedLevel, IdWordENG, IdWordPL);
                        if (exist != -1) {
                            MDW.setFamilyinExistedDictionaryWord(exist, idFamily);
                        } else {
                            MDW.insertDictionaryWords(choosedLevel, idFamily, IdWordENG, IdWordPL);
                            howAdded++;
                        }
                    }
                }
            }
            return howAdded;
        }
        return 0;
    }

    public synchronized int getNumberOfLinesToSendTODB() {
        return numberOfLines;
    }

    public synchronized int getValueProgress() {
        return valueProgress;
    }

    public String selectlongestWord(String WordWithSpace) {
        String[] stringArrray = WordWithSpace.split(" ");
        int max = stringArrray[0].length();
        int iterator = 0;
        for (int i = 1; i < stringArrray.length; i++) {
            int maxTmp = stringArrray[i].length();
            if (maxTmp > max) {
                max = maxTmp;
                iterator = i;
            }
        }
        return stringArrray[iterator];
    }

    public boolean isItFamily(String head, String probablyMember) {
        String resultLCS = LCS(head, probablyMember);
        Integer resultLevenstein = levenstein(resultLCS, head);
        Integer difference = abs(resultLevenstein);
        if (difference <= 1) return true;
        return false;
    }

    public String LCS(String word1, String word2) {
        int m, n, maxi, ind;
        int a[][];
        m = word1.length();
        n = word2.length();
        maxi = 0;
        ind = 0;
        a = new int[Math.max(m, n) + 1][Math.max(m, n) + 1];
        for (int i = 0; i < Math.max(m, n) + 1; i++) {
            a[0][i] = 0;
            a[i][0] = 0;
        }
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                if (word1.charAt(i - 1) != word2.charAt(j - 1))
                    a[i][j] = 0;
                else {
                    a[i][j] = a[i - 1][j - 1] + 1;
                    if (a[i][j] > maxi) {
                        maxi = a[i][j];
                        ind = i;
                    }
                }
        return word1.substring(ind - maxi, ind);
    }

    public static Integer levenstein(String source, String destiny) {
        int i, j, m, n, cost;
        int d[][];
        m = source.length();
        n = destiny.length();
        d = new int[m + 1][n + 1];

        for (i = 0; i <= m; i++)
            d[i][0] = i;
        for (j = 1; j <= n; j++)
            d[0][j] = j;

        for (i = 1; i <= m; i++) {
            for (j = 1; j <= n; j++) {
                if (source.charAt(i - 1) == destiny.charAt(j - 1))
                    cost = 0;
                else
                    cost = 1;

                d[i][j] = Math.min(d[i - 1][j] + 1,         /* remove */
                        Math.min(d[i][j - 1] + 1,          /* insert */
                                d[i - 1][j - 1] + cost));   /* change */
            }
        }

        return d[m][n];
    }

    public String findHeadFamily(List<String> list) {
        int min = list.get(0).length();
        int interator = 0;
        for (int i = 1; i < list.size(); i++) {
            int tmpMin = list.get(i).length();
            if (tmpMin < min) {
                min = tmpMin;
                interator = i;
            }
        }
        return list.get(interator);
    }

    public void destroy() {
        CSVReader.instance = null;
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

}