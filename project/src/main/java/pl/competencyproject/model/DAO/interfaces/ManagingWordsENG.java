package pl.competencyproject.model.DAO.interfaces;

import pl.competencyproject.model.entities.Word_ENG;

public interface ManagingWordsENG {

    public Integer addWordENG(String strENG);

    public Integer existWordENG(String strENG);

    public void deleteWordENG(Integer idWordENG);

    public Word_ENG getWordENG(int idWordENG);
}
