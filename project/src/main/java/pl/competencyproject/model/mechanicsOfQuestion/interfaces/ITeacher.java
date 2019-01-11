package pl.competencyproject.model.mechanicsOfQuestion.interfaces;

import pl.competencyproject.model.enums.TypeOfDictionaryDownloaded;
import pl.competencyproject.model.enums.TypeOfDictionaryLanguage;

public interface ITeacher {

  //  public void initDictionary(String nameLevel, String nameCategorie, TypeOfDictionaryDownloaded typeDictionary, TypeOfDictionaryLanguage typeLanguage);

    public boolean checkAnswer(String answer, int delayInMilisecundes);

    public void initNextRoundOfQuestions();

    public double getCalcValueProgress();

    public String randGoodOrBadAnswer();


}
