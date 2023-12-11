package com.testproject.expensetracker.repositories;

import com.testproject.expensetracker.domain.User;
import com.testproject.expensetracker.exceptions.EtAuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    private static final String SQL_CREATE = "INSERT INTO ET_USERS(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES(NEXTVAL('ET-USERS-SEQ', ?,?,?,?)";
    private static final String SQL_COUNT_BY_EMAIL = "SSELECT COUNT(*) FROM ET_USERS WHERE EMAIL = ?";
    private static final String SQL_FIND_BY_ID = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD"+
            "FROM ET_USERS WHERE USER_ID = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer createUser(String firstName, String lastName, String email, String password)
            throws EtAuthException {
        try{
            logger.info("Inside createUser");
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,firstName);
                ps.setString(1,lastName);
                ps.setString(1,email);
                ps.setString(1,password);

                return ps;}, keyHolder);
            return (Integer) keyHolder.getKeys().get("USER_ID");
        } catch (Exception e){
            throw new EtAuthException("Invalid details. Failed to create account");
        }

    }



    @Override
    public User findEmailAndPass(String email, String pass) throws EtAuthException {
        logger.info("Inside findEmailAndPass");

        return null;
    }

    @Override
    public Integer getCountByEmail(String email) {
        logger.info("Inside getCountByEmail");
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
    }

    @Override
    public User findUserById(Integer userId) {
        logger.info("Inside findUserById");
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId}, userRowMapper);
    }

    private RowMapper<User> userRowMapper = ((rs, rowNum) ->{
    return new User(rs.getInt("USER_ID"),
            rs.getString("FIRST_NAME"),
            rs.getString("LAST_NAME"),
            rs.getString("EMAIL"),
            rs.getString("PASSWORD"));
    });

}
