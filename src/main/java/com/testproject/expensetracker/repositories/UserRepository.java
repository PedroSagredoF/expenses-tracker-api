package com.testproject.expensetracker.repositories;

import com.testproject.expensetracker.domain.User;
import com.testproject.expensetracker.exceptions.EtAuthException;

public interface UserRepository {

    Integer createUser(String firstName, String lastName, String email, String password) throws EtAuthException;

    User findEmailAndPass(String email, String pass) throws EtAuthException;

    Integer getCountByEmail(String email);

    User findUserById(Integer userId);
}
