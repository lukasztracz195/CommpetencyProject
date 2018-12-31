package pl.competencyproject.model.csv;

import pl.competencyproject.model.dao.classes.*;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class CSVReader {
    public static CSVReader instance;

    private static LibraryCSV library;
    private static String choosedCSV;
    private static Integer choosedLevel;
    private static String StringENG;
    private static String StringPL;
    private static int PLindex;
    private static int ENGindex;
    private static FileOfCSV fileOfCSV;
    private static TypeOfUsedDatabase type;
    private List<String> listOfLines;

    private static ManageFamily MF = null;
    private static ManageWordsENG MWE = null;
    private static ManageWordsPL MWP = null;
    private static ManageDictionaryWords MDW = null;
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

    public void chooseLevel(String nameLevel, String nameOfCategory) {
        ManageLevels ml = ManageLevels.getInstance(type);
        int id = ml.existLevel(nameLevel, nameOfCategory);
        if (id != -1) {
            choosedLevel = id;
        } else {
            System.out.println("NIE MA TAKIEGO LEVELU");
        }
    }

    public void chooseCSV(String CSVname) {
        if (library.existFileCSVinFolder(CSVname)) {
            choosedCSV = CSVname;
        }
        fileOfCSV = new FileOfCSV(choosedCSV, type);
    }

    public void chooseCSV(File fileCSV) {
        fileOfCSV = new FileOfCSV(fileCSV);
    }

    public Integer insertDictionarySentences() {
        ManageDictionarySentences MDS = null;

        MDS = ManageDictionarySentences.getInstance(type);

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
    }

    public Integer insertDictionaryWordswithoutFamily() {
        int records = 0;
        readLinesFromFile();
         MWE = ManageWordsENG.getInstance(type);
         MWP = ManageWordsPL.getInstance(type);
         MDW = ManageDictionaryWords.getInstance(type);
        String header = listOfLines.get(0);
        if (checkHeaderWordsSentenses(header)) {
            String[] headerParts = header.split(";");
            setIndexesPLENG(headerParts);
            for (int i = 1; i < listOfLines.size(); i++) {
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


    public Integer insertFamily() {
        MF = ManageFamily.getInstance(type);
        MWE = ManageWordsENG.getInstance(type);
        MWP = ManageWordsPL.getInstance(type);
        MDW = ManageDictionaryWords.getInstance(type);

        String line = fileOfCSV.getRead().nextLine();
        if (checkHeaderWordsSentenses(line)) {
            String[] headerParts = line.split(";");
            setIndexesPLENG(headerParts);
            List<String> listStringENG = new ArrayList<>();
            List<String> listWordENG = new ArrayList<>();
            List<String> listStringPL = new ArrayList<>();
            while (fileOfCSV.getRead().hasNextLine()) {
                line = fileOfCSV.getRead().nextLine();
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

}