package com.testproject.expensetracker.Service;

import com.testproject.expensetracker.domain.User;

public interface UserService {

    User validateUser(String email, String password) throws EtAuthException;

    User registerUser(String firstName, String lastName, String email, String password) throws EtAuthException;

}
