package com.testproject.expensetracker.constans;

import com.testproject.expensetracker.repositories.UserRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
    public static final String API_SECRET_KEY = "expensetrackerapikey";
    public static final long TOKEN_VALIDITY = 2 * 60 * 60 * 1000;
    public static final String SQL_CREATE = "INSERT INTO ET_USERS(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES(NEXTVAL('ET_USERS_SEQ'), ?,?,?,?)";
    public static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM ET_USERS WHERE EMAIL = ?";
    public static final String SQL_FIND_BY_ID = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD "+ "FROM ET_USERS WHERE USER_ID = ?";
    public static final String SQL_FIND_BY_EMAIL = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD "+ "FROM ET_USERS WHERE EMAIL = ?";
}
