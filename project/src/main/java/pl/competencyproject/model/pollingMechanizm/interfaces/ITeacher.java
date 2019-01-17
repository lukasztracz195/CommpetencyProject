package pl.competencyproject.model.pollingMechanizm.interfaces;

public interface ITeacher {

  //  public void initDictionary(String nameLevel, String nameCategorie, TypeOfDictionaryDownloaded typeDictionary, TypeOfDictionaryLanguage typeLanguage);

    public boolean checkAnswer(String answer, int delayInMilisecundes);

    public void initNextRoundOfQuestions();

    public String randGoodOrBadAnswer();


}
