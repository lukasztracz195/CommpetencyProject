package pl.competencyproject.model.csv;

import pl.competencyproject.model.dao.classes.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

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

    private CSVReader(boolean test) {
        library = new LibraryCSV(test);
    }

    public static CSVReader getInstance() {
        if (instance == null) {
            synchronized (ManageFamily.class) {
                if (instance == null) {
                    instance = new CSVReader(false);
                }
            }
        }
        return instance;
    }

    public static CSVReader getTestInstance() {
        if (instance == null) {
            synchronized (ManageFamily.class) {
                if (instance == null) {
                    instance = new CSVReader(true);
                }
            }
        }
        return instance;
    }


    public void chooseLevel(String nameLevel, String nameOfCategory) {
        ManageLevels ml = ManageLevels.getInstance();
        int id = ml.existLevel(nameLevel, nameOfCategory);
        if (id != -1) {
            choosedLevel = id;
        }
    }

    public void chooseCSV(String CSVname, boolean test) throws FileNotFoundException//throws IOException
    {
        if (library.existFileCSVinFolder(CSVname)) {
            choosedCSV = CSVname;
        }
        fileOfCSV = new FileOfCSV(choosedCSV, test);
    }

    public void chooseCSV(File fileCSV) throws FileNotFoundException//throws IOException
    {
        fileOfCSV = new FileOfCSV(fileCSV);
    }

    public Integer insertDictionarySentences(boolean test) throws FileNotFoundException {
        ManageDictionarySentences MDS = null;
        if (!test) {
            MDS = ManageDictionarySentences.getInstance();
        } else {
            MDS = ManageDictionarySentences.getTestInstance();
        }
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

    public Integer insertDictionaryWordswithoutFamily(boolean test) throws FileNotFoundException {
        int records = 0;
        ManageWordsENG MWE = null;
        ManageWordsPL MWP = null;
        ManageDictionaryWords MDW = null;
        if (!test) {
            MWE = ManageWordsENG.getInstance();
            MWP = ManageWordsPL.getInstance();
            MDW = ManageDictionaryWords.getInstance();
        } else {
            MWE = ManageWordsENG.getTestInstance();
            MWP = ManageWordsPL.getTestInstance();
            MDW = ManageDictionaryWords.getTestInstance();
        }


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


    public Integer insertFamily(boolean test) {
        ManageFamily MF = null;
        ManageWordsENG MWE = null;
        ManageWordsPL MWP = null;
        ManageDictionaryWords MDW = null;
        if (!test) {
            MF = ManageFamily.getInstance();
            MWE = ManageWordsENG.getInstance();
            MWP = ManageWordsPL.getInstance();
            MDW = ManageDictionaryWords.getInstance();
        } else {
            MF = ManageFamily.getTestInstance();
            MWE = ManageWordsENG.getTestInstance();
            MWP = ManageWordsPL.getTestInstance();
            MDW = ManageDictionaryWords.getTestInstance();
        }
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
                        if (MDW.insertDictionaryWords(choosedLevel, idFamily, IdWordENG, IdWordPL) != -1) {
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

    public int levenstein(String source, String destiny) {
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