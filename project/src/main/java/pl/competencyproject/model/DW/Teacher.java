package pl.competencyproject.model.DW;
/*
import pl.competencyproject.model.DAO.classes.ManageDictionaryWords;
import pl.competencyproject.model.DAO.classes.ManageWordsENG;
import pl.competencyproject.model.DAO.classes.ManageWordsPL;
import pl.competencyproject.model.entities.Dictionary_Word;
import pl.competencyproject.model.entities.Word_ENG;
import pl.competencyproject.model.entities.Word_PL;

import java.util.*;

public class Teacher
{
    List <Integer> SortedList = new ArrayList <Integer>();
    Map<Word, List<String>> dictionary= new HashMap<Word, List<String>>();
    int numerPositive=SortedList.size();

    public void addEnglish()
    {
        Set<String> words = new HashSet<>();
        Word_ENG word_ENG;
        Word_ENG word_ENG_pom;
        Word_PL word_PL;
        Dictionary_Word obiekt_ENG;
        Dictionary_Word obiekt_ENG_pom;
        ManageDictionaryWords MDW = ManageDictionaryWords.getInstance();
        ManageWordsENG MWE = ManageWordsENG.getInstance();
        ManageWordsPL MWP = ManageWordsPL.getInstance();
        List lista=new ArrayList();
        lista=MDW.getDictionaryByLevel(1);
        for(int i=0; i<lista.size(); i++)
        {
            Word [] slowo = new Word[lista.size()];
            for(int k=0;k<slowo.length;k++) slowo[k]=new Word();
            List <String> translations = new ArrayList<String>();
            obiekt_ENG= (Dictionary_Word) lista.get(i);
            word_ENG= MWE.getWordENG(obiekt_ENG.getIdWordENG());
            if(words.contains(word_ENG.getWordENG())==false)
            {
                slowo[i].id = dictionary.size();
                slowo[i].word = word_ENG.getWordENG();
                words.add(word_ENG.getWordENG());
                SortedList.add(dictionary.size());
                for (int j = 0; j < lista.size(); j++)
                {
                    obiekt_ENG_pom = (Dictionary_Word) lista.get(j);
                    word_ENG_pom = MWE.getWordENG(obiekt_ENG_pom.getIdWordENG());
                    if (word_ENG.getIdWordENG() == word_ENG_pom.getIdWordENG()) {
                        word_PL = MWP.getWordPL(obiekt_ENG_pom.getIdWordPL());
                        translations.add(word_PL.getWordPL());
                    }
                }
                dictionary.put(slowo[i], translations);
            }
        }
    }

    public void addPolish()
    {
        Set<String> words = new HashSet<>();
        Word_ENG word_ENG;
        Word_PL word_PL;
        Word_PL word_PL_pom;
        Dictionary_Word obiekt_PL;
        Dictionary_Word obiekt_PL_pom;
        ManageDictionaryWords MDW = ManageDictionaryWords.getInstance();
        ManageWordsENG MWE = ManageWordsENG.getInstance();
        ManageWordsPL MWP = ManageWordsPL.getInstance();
        List lista=new ArrayList();
        lista=MDW.getDictionaryByLevel(1);
        for(int i=0; i<lista.size(); i++)
        {
            Word [] slowo = new Word[lista.size()];
            for(int k=0;k<slowo.length;k++) slowo[k]=new Word();
            List <String> translations = new ArrayList<String>();
            obiekt_PL= (Dictionary_Word) lista.get(i);
            word_PL= MWP.getWordPL(obiekt_PL.getIdWordPL());
            if(words.contains(word_PL.getWordPL())==false)
            {
                slowo[i].id = dictionary.size();
                slowo[i].word = word_PL.getWordPL();
                words.add(word_PL.getWordPL());
                SortedList.add(dictionary.size());
                for (int j = 0; j < lista.size(); j++)
                {
                    obiekt_PL_pom = (Dictionary_Word) lista.get(j);
                    word_PL_pom = MWP.getWordPL(obiekt_PL_pom.getIdWordPL());
                    if (word_PL.getIdWordPL() == word_PL_pom.getIdWordPL()) {
                        word_ENG = MWE.getWordENG(obiekt_PL_pom.getIdWordENG());
                        translations.add(word_ENG.getWordENG());
                    }
                }
                dictionary.put(slowo[i], translations);
            }
        }
    }

    public void addAll()
    {
        addEnglish();
        addPolish();
        for (Word entry : dictionary.keySet())
        {
            List value = dictionary.get(entry);
            System.out.println(entry.id + " " + entry.word + "" + value + " ");
        }
    }

    public void losuj()
    {
        Random rand = new Random();
        int r;
        int index;
        String answer;
        Word word=new Word();
        for(int i=0; i<dictionary.size(); i++)
        {
            Iterator<Word> iter = dictionary.keySet().iterator();
            index=rand.nextInt(SortedList.size());
            r=SortedList.get(index);
            while (iter.hasNext())
            {
                Word slowo = iter.next();
                if (slowo.id==r)
                {
                    System.out.println("Podaj tłumaczenie: "+slowo.word);
                    Scanner in = new Scanner(System.in);
                    answer = in.nextLine();
                    if(dictionary.get(slowo).contains(answer))
                    {
                        numerPositive++;
                    }
                    SortedList.remove(index);
                }
            }
        }
        System.out.println(numerPositive+" poprawnych odpowiedzi");
    }
}
*/