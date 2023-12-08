package com.testproject.expensetracker.Service;

import com.testproject.expensetracker.domain.User;
import com.testproject.expensetracker.exceptions.EtAuthException;

public interface UserService {

    User validateUser(String email, String password) throws EtAuthException;

    User registerUser(String firstName, String lastName, String email, String password) throws EtAuthException;

}
