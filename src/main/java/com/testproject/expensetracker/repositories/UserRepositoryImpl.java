package com.testproject.expensetracker.repositories;

import com.testproject.expensetracker.domain.User;
import com.testproject.expensetracker.exceptions.EtAuthException;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private static final String SQL_CREATE = "INSERT INTO ET_USERS(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES(NEXTVAL('ET-USERS-SEQ', ?,?,?,?)";

    @Override
    public Integer createUser(String firstName, String lastName, String email, String password) throws EtAuthException {
        return null;
    }



    @Override
    public User findEmailAndPass(String email, String pass) throws EtAuthException {
        return null;
    }

    @Override
    public Integer getCountByEmail(String email) {
        return null;
    }

    @Override
    public User findUserById(Integer userId) {
        return null;
    }
}
