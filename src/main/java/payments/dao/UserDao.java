package payments.dao;

import payments.model.entity.user.User;

import java.util.Optional;

/**
 * DAO for user
 */
public interface UserDao extends CommonDao<User>{
    /**
     * find one user by cellphone which is unique
     * @param cellphone cellphone of the user
     * @return
     */
    Optional<User> findUserByCellphone(String cellphone);

    /**
     * update database if new user cards were found
     * @param id user's id
     */
    void updateUserCards(long id);
}