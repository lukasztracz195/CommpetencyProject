package pl.competencyproject.model.dao.interfaces;

import org.hibernate.HibernateException;
import pl.competencyproject.model.entities.User;

public interface ManagingUsers  {

    public Integer addUser(String email, String password);

    public void updatePasswordUser(Integer UserID, String password);

    public void updateActiveUser(Integer UserID, boolean active);

    public void deleteUser(Integer UserID);

    public int existUser(String email) throws HibernateException;

    public void updateEmail(Integer UserID, String email);

    public User getUser(String email);

    public User getUser(int IdUser);

    public String getPassword(int IdUser);

    public boolean checkUserPassword(int IdUser, String password);

    public boolean checkUserEmail(int IdUser, String email);

    public boolean checkLogedUser(int IdUser);

    public String encryptSHA1(String password);

}
