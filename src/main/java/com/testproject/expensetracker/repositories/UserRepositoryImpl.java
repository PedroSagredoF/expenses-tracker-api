package com.testproject.expensetracker.repositories;

import com.testproject.expensetracker.constans.Constants;
import com.testproject.expensetracker.domain.User;
import com.testproject.expensetracker.exceptions.EtAuthException;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer createUser(String firstName, String lastName, String email, String password)
            throws EtAuthException {
        String hashedPass = BCrypt.hashpw(password, BCrypt.gensalt(10));
        try{
            logger.info("Inside createUser");
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {PreparedStatement ps = connection.prepareStatement(Constants.SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,firstName);
                ps.setString(2,lastName);
                ps.setString(3,email);
                ps.setString(4,hashedPass);

                return ps;}, keyHolder);
            return (Integer) keyHolder.getKeys().get("USER_ID");
        } catch (Exception e){
            throw new EtAuthException("Invalid details. Failed to create account");
        }

    }



    @Override
    public User findEmailAndPass(String email, String pass) throws EtAuthException {
        logger.info("Inside findEmailAndPass");
        try {
            User user=  jdbcTemplate.queryForObject( Constants.SQL_FIND_BY_EMAIL, new Object[]{email}, userRowMapper);
            if(!BCrypt.checkpw(pass, user.getPassword())) throw new EtAuthException("Invalid email or password");
            return user;

        } catch (EmptyResultDataAccessException e){
            throw new EtAuthException("Invalid email or password");
        }

    }

    @Override
    public Integer getCountByEmail(String email) {
        logger.info("Inside getCountByEmail");
        return jdbcTemplate.queryForObject(Constants.SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
    }

    @Override
    public User findUserById(Integer userId) {
        logger.info("Inside findUserById");
        return jdbcTemplate.queryForObject(Constants.SQL_FIND_BY_ID, new Object[]{userId}, userRowMapper);
    }

    private RowMapper<User> userRowMapper = ((rs, rowNum) ->{
    return new User(rs.getInt("USER_ID"),
            rs.getString("FIRST_NAME"),
            rs.getString("LAST_NAME"),
            rs.getString("EMAIL"),
            rs.getString("PASSWORD"));
    });

}
