package pl.competencyproject.model.mechanicsOfQuestion;

import pl.competencyproject.model.dao.SessionLogon;
import pl.competencyproject.model.enums.TypeOfDictionaryDownloaded;
import pl.competencyproject.model.enums.TypeOfDictionaryLanguage;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class ThreadBot implements Runnable {

    private PrintWriter save;
    public ThreadBot() {
        try {
            save = new PrintWriter("AskerBotResult.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        SessionLogon.IdLoggedUser = 1;
        Teacher teacher = new Teacher(TypeOfUsedDatabase.OnlineOrginalDatabase);
        save.println("Start download");
        teacher.initDictionary("B2", "Structure of the University", TypeOfDictionaryDownloaded.DictionaryOfWords, TypeOfDictionaryLanguage.ENGtoPL);
        SessionLogon.IdLoggedUser = -1;
        save.println("koniec zaciągania z bazy dictionarySize = " + teacher.getCurrentMapQuestion().size());

        for (Map.Entry<Word, List<String>> entry : teacher.getCurrentMapQuestion().entrySet()) {
            Word key1 = entry.getKey();
            List<String> value1 = entry.getValue();
            save.println(key1.toString() + ": " + value1.toString());
        }
        for (int i = 1; i <= teacher.getNumberMaxOfSessions(); i++) {
            save.println("Sesja nr. " + i + " z " + teacher.getNumberMaxOfSessions());
            while (!teacher.getCurrentMapQuestion().isEmpty()) {
                save.println("\nQuestion: " + teacher.getCurrentQuestion());
                String answer = teacher.randGoodOrBadAnswer();
                teacher.checkAnswer(answer);
                save.println("Answer: " + answer + "\n");
            }
            teacher.initNextRoundOfQuestions();
        }
        save.println("Good Answers: " + teacher.getNumberOfGoodAnswers());
        save.println("Wrong Answers: " + teacher.getNumberOfBadAnswers());
        save.println("ValueProgress: " + teacher.getValueProgress());
        save.close();
    }
}
