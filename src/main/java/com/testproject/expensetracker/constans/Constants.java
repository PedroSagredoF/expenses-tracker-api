package com.testproject.expensetracker.constans;

public class Constants {
    public static final String API_SECRET_KEY = "expensetrackerapikey";
    public static final long TOKEN_VALIDITY = 2 * 60 * 60 * 1000;
    public static final String SQL_CREATE_USER = "INSERT INTO ET_USERS(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) " +
            "VALUES(NEXTVAL('ET_USERS_SEQ'), ?,?,?,?)";
    public static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM ET_USERS WHERE EMAIL = ?";
    public static final String SQL_FIND_BY_ID = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD " +
            "FROM ET_USERS WHERE USER_ID = ?";
    public static final String SQL_FIND_BY_EMAIL = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD " +
            "FROM ET_USERS WHERE EMAIL = ?";

    public static final String SQL_CREATE_CATEGORY = "INSERT INTO ET_CATEGORIES (CATEGORY_ID, USER_ID, TITLE, DESCRIPTION) " +
            "VALUES(NEXTVAL('ET_CATEGORIES_SEQ'), ?,?,?)";
    public static final String SQL_FIND_CATEGORY_BY_ID = "SELECT C.CATEGORY_ID, C.USER_ID, C.TITLE, C.DESCRIPTION, " +
            "COALESCE(SUM(T.AMOUNT), 0) TOTAL_EXPENSE " +
            "FROM ET_TRANSACTIONS T RIGHT OUTER JOIN ET_CATEGORIES C ON C.CATEGORY_ID = T.CATEGORY_ID " +
            "WHERE C.USER_ID = ? AND C.CATEGORY_ID = ? GROUP BY C.CATEGORY_ID";

    public static final String SQL_FIND_ALL_CATEGORIES = "SELECT C.CATEGORY_ID, C.USER_ID, C.TITLE, C.DESCRIPTION, " +
            "COALESCE(SUM(T.AMOUNT), 0) TOTAL_EXPENSE " +
            "FROM ET_TRANSACTIONS T RIGHT OUTER JOIN ET_CATEGORIES C ON C.CATEGORY_ID = T.CATEGORY_ID " +
            "WHERE C.USER_ID = ? GROUP BY C.CATEGORY_ID";

    public static final String SQL_UPDATE_CATEGORY = "UPDATE ET_CATEGORIES SET TITLE = ?, DESCRIPTION = ? " +
            "WHERE USER_ID = ? AND CATEGORY_ID = ?";

}
