package com.testproject.expensetracker.repositories;

import com.testproject.expensetracker.constans.Constants;
import com.testproject.expensetracker.domain.Category;
import com.testproject.expensetracker.domain.Transaction;
import com.testproject.expensetracker.exceptions.EtBadRequestException;
import com.testproject.expensetracker.exceptions.EtResourceNotFoundException;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Transaction> findAll(Integer userId, Integer categoryId) {
        return jdbcTemplate.query(Constants.SQL_FIND_ALL_TRANSACTIONS, new Object[]{userId, categoryId}, transactionRowMapper);
    }

    @Override
    public Transaction findById(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException {
        try{
            return jdbcTemplate.queryForObject(Constants.SQL_FIND_TRANSACTION_BY_ID, new Object[]{userId, categoryId, transactionId}, transactionRowMapper);
        } catch (Exception e){
            throw new EtResourceNotFoundException("Transaction Not Found");
        }
    }

    @Override
    public Integer create(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestException {
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection->{
                PreparedStatement ps = connection.prepareStatement(Constants.SQL_CREATE_TRANSACTION, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setInt(1,categoryId);
                ps.setInt(2,userId);
                ps.setDouble(3, amount);
                ps.setString(4, note);
                ps.setLong(5, transactionDate);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("TRANSACTION_ID");
        }catch (Exception e){
            throw new EtBadRequestException("Invalid Request");
        }
    }

    @Override
    public void update(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction) throws EtBadRequestException {
        try {
            jdbcTemplate.update(Constants.SQL_UPDATE_TRANSACTIONS, new Object[]{transaction.getAmount(), transaction.getNote(),
            transaction.getTransactionDate(), userId, categoryId, transactionId});
        }catch (Exception e){
            throw new EtBadRequestException("Invalid Request");
        }
    }

    @Override
    public void removeById(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException {
        int count = jdbcTemplate.update(Constants.SQL_DELETE_TRANSACTION, new Object[]{userId, categoryId, transactionId});
        if(count == 0)
            throw new EtResourceNotFoundException("Transaction Not Found");
    }

    private RowMapper<Transaction> transactionRowMapper  = ((rs, rowNum) ->{
        return  new Transaction(
                rs.getInt("TRANSACTION_ID"),
                rs.getInt("CATEGORY_ID"),
                rs.getInt("USER_ID"),
                rs.getDouble("AMOUNT"),
                rs.getString("NOTE"),
                rs.getLong("TRANSACTION_DATE")
        );
    });
}
