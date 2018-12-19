package pl.competencyproject.model.mechanicsOfQuestion;

import pl.competencyproject.model.dao.SessionLogon;
import pl.competencyproject.model.dao.classes.ManageDictionaryWords;
import pl.competencyproject.model.dao.classes.ManageWordsENG;
import pl.competencyproject.model.dao.classes.ManageWordsPL;
import pl.competencyproject.model.enums.TypeDictionaryDownloaded;
import pl.competencyproject.model.enums.TypeOfDictionaryLanguage;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

import java.util.*;

public class Teacher {
    SortedMap<Word, List<String>> currentMapQuestion;
    Map<Word, LinkedList<String>> mapAllWords;

    public void funkcja()
    {
        int licznik=0;
        DictionaryMap map = DictionaryMap.getInstance();
        while(true)
        {
            System.out.println("Wybierz poziom");
            Scanner in = new Scanner(System.in);
            Integer level = in.nextInt();
            map.loadDictionary(level, TypeDictionaryDownloaded.DictionaryOfWords, TypeOfDictionaryLanguage.PLtoENG, TypeOfUsedDatabase.OnlineOrginalDatabase);
            if(level!=null)
            {
                currentMapQuestion = map.getRandTenMap();
                licznik++;
                System.out.println(licznik);
            }
            System.out.println(currentMapQuestion.size());
            for (Map.Entry<Word, List<String>> entry : currentMapQuestion.entrySet()) {
                Word key = entry.getKey();
                List<String> value = entry.getValue();
                System.out.println(key.toString() + ": " + value.toString());
            }
            int rightAnswer = 0;
            int wrongAnswer = 0;
            for (int i = 0; i < currentMapQuestion.size(); i++) {
                Word key = null;
                String question = "";
                int max = 0;
                for (Map.Entry<Word, List<String>> entry : currentMapQuestion.entrySet()) {
                    key = entry.getKey();
                    if (key.getNumberOfTries() > max) {
                        max = key.getNumberOfTries();
                    }
                    if (key.getNumberOfTries() == max) {
                        question = key.getWord();
                    }
                }
                System.out.println("Podaj tłumaczenie: " + question);
                String answer = in.nextLine();
                for (Map.Entry<Word, List<String>> entry : currentMapQuestion.entrySet()) {
                    List<String> value = entry.getValue();
                    if (value.contains(answer)) {
                        rightAnswer++;
                        key = entry.getKey();
                        key.decreasNumberOfAttempts();
                        System.out.println(key.getNumberOfTries());
                    }
                }
                System.out.println(rightAnswer + " " + wrongAnswer);
            }
            for (Map.Entry<Word, List<String>> entry : currentMapQuestion.entrySet()) {
                Word key = entry.getKey();
                List<String> value = entry.getValue();
                System.out.println(key.toString() + ": " + value.toString());
            }
        }
        /*System.out.println("Chcesz kontynuować naukę tego poziomu? ");
        Scanner in = new Scanner(System.in);
        String answer = in.nextLine();
        if(answer=="tak")*/

    }
}