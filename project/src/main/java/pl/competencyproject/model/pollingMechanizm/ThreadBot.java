package pl.competencyproject.model.pollingMechanizm;

import pl.competencyproject.model.dao.SessionLogon;
import pl.competencyproject.model.enums.TypeOfDictionaryLanguage;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

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
        SessionLogon.IdLoggedUser = 1;
        Teacher teacher = new Teacher();
        DictionaryMap DM = DictionaryMap.getInstance();
        //Kolejność ustawiania DM ma znaczenie !!!!!!!!
        DM.setTypeDB(TypeOfUsedDatabase.OnlineOrginalDatabase);
        DM.setDictionaryOfWords("B2","Structure of the University");
        DM.setTypeLangToLang(TypeOfDictionaryLanguage.PLtoENG);
        DM.initDownloadDate();
        //Tu masz jak potem korzystac z zaciągnaia danych kolejność jest bardzo istotna
        //*************************
        teacher.setDictionary(DM);
        System.out.println("Start download");
        save.println("Start download");
        SessionLogon.IdLoggedUser = -1;
        System.out.println("koniec zaciągania z bazy dictionarySize = " + teacher.getCurrentMapQuestion().size());
        save.println("koniec zaciągania z bazy dictionarySize = " + teacher.getCurrentMapQuestion().size());
        for (int i = 1; i <= teacher.getNumberMaxOfSessions(); i++) {
            save.println("Sesja nr. " + i + " z " + teacher.getNumberMaxOfSessions());
            while (!teacher.getCurrentMapQuestion().isEmpty()) {
                save.println("");
                save.println("Question: " + teacher.getCurrentQuestion());
                String answer = teacher.randGoodOrBadAnswer();
                boolean check = teacher.checkAnswer(answer, 0);
                if (check) save.println("Good");
                else save.println("Wrong");
                save.println("Answer: " + answer + "\n");
            }
            save.println("");
            save.println("POINTS AFTER SESSION NR."+i);
            save.println("Good Answers: " + teacher.getNumberOfGoodAnswers());
            save.println("Wrong Answers: " + teacher.getNumberOfWrongAnswers());
            save.println("ValueProgress: " + teacher.getValueProgress());
            teacher.initNextRoundOfQuestions();
        }
        save.println("Total Good Answers: " + teacher.getTotalNumberOfGoodAnswers());
        save.println("Total Wrong Answers: " + teacher.getTotalNumberOfWrongAnswers());
        save.println("Total ValueProgress: " + teacher.getTotalValueProgress());
        teacher.getFactoryDictionary().hardReset();
        save.close();
    }
}
