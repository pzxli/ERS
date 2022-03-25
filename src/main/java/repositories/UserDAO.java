package repositories;

import models.Reimbursement;
import models.User;

public interface UserDAO {

    //getUserGivenUsername
    User getUserGivenUsername(String username);

    //create user
    void createUser(User user);
}
