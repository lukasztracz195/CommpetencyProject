package pl.competencyproject.model.DAO.interfaces;

import pl.competencyproject.model.entities.Word_PL;

public interface ManagingWordsPL {

    public Integer addWordPL(String strPL);

    public Integer existWordPL(String strPL);

    public void deleteWordPL(Integer idWordPL);

    public Word_PL getWordPL(int idWordPL);
}
