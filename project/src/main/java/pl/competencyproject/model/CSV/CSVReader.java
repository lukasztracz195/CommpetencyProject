package pl.competencyproject.model.CSV;

import pl.competencyproject.model.DAO.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader
{
    public static void SelectCSV(String CSVname)//throws IOException
    {
        final String DELIMITER = ";";
        /*if(CSVname=="DICTIONARY_SENTENCES")
        {
            ManageDictionarySentences MDS = ManageDictionarySentences.getInstance();
            SessionLogon.IdLoggedUser = 98;
            BufferedReader fileReader = null;
            try
            {
                String line = "";
                String fileToParse = System.getProperty("user.dir")+"\\src\\main\\resources\\backubDBinCSV\\DICTIONARY_SENTENCES.csv";
                fileReader = new BufferedReader(new FileReader(fileToParse));
                String SentenceENG="";String SentencePL="";
                int i=0;
                while ((line = fileReader.readLine()) != null)
                {
                    String[] tokens = line.split(DELIMITER);
                    for(String token : tokens)
                    {
                        if(i==0)SentenceENG=token;
                        if(i==1)SentencePL=token;
                        i++;
                    }
                    i=0;
                    MDS.insertDictionarySentece(1,SentenceENG,SentencePL);
                }
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }*/
        if(CSVname=="ENG PL")
        {
            SessionLogon.IdLoggedUser = 98;
            ManageWordsENG MWE = ManageWordsENG.getInstance();
            ManageWordsPL MWP = ManageWordsPL.getInstance();
            ManageDictionaryWords MDW = ManageDictionaryWords.getInstance();
            BufferedReader fileReader = null;
            try
            {
                String line = "";
                String fileToParse = System.getProperty("user.dir") + "\\project\\src\\main\\resources\\backubDBinCSV\\ENG PL.csv";
                fileReader = new BufferedReader(new FileReader(fileToParse));
                String WordENG="";
                String WordPL="";
                int IdWordENG=0;
                int IdWordPL=0;
                int i=0;
                fileReader.readLine();
                while ((line = fileReader.readLine()) != null)
                {
                    String[] tokens = line.split(DELIMITER);
                    for(String token : tokens)
                    {
                        if(i==0) WordENG=token;
                        if(i==1) WordPL=token;
                        i++;
                    }
                    MWE.addWordENG(WordENG);
                    MWP.addWordPL(WordPL);
                    IdWordENG=MWE.existWordENG(WordENG);
                    IdWordPL=MWP.existWordPL(WordPL);
                    MDW.insertDictionaryWords(1,1,IdWordENG, IdWordPL);
                    i=0;
                }
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}