package com.testproject.expensetracker.repositories;

import com.testproject.expensetracker.constans.Constants;
import com.testproject.expensetracker.domain.Category;
import com.testproject.expensetracker.exceptions.EtBadRequestException;
import com.testproject.expensetracker.exceptions.EtResourceNotFoundException;
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
public class CategoryRepositoryImpl implements CategoryRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Category> findAll(Integer userId) throws EtResourceNotFoundException {
        return jdbcTemplate.query(Constants.SQL_FIND_ALL_CATEGORIES, new Object[]{userId}, categoryRowMapper);
    }

    @Override
    public Category findById(Integer userId, Integer categoryId) throws EtResourceNotFoundException {
        try{
            return jdbcTemplate.queryForObject(Constants.SQL_FIND_CATEGORY_BY_ID, new Object[]{userId, categoryId}, categoryRowMapper);
        }catch (Exception e){
            throw new EtResourceNotFoundException("Category Not Found");
        }

    }

    @Override
    public Integer create(Integer userId, String title, String description) throws EtBadRequestException {
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(Constants.SQL_CREATE_CATEGORY, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.setString(2, title);
                ps.setString(3, description);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("CATEGORY_ID");

        }catch(Exception e){
            throw new EtBadRequestException("Invalid request");
        }
    }

    @Override
    public void update(Integer userId, Integer categoryId, Category category) throws EtBadRequestException {
        try{
            jdbcTemplate.update(Constants.SQL_UPDATE_CATEGORY, new Object[]{category.getTitle(), category.getDescription(),
                    userId, categoryId});
        }catch (Exception e){
            throw new EtBadRequestException("Invalid Request");
        }
    }

    @Override
    public void removeById(Integer userId, Integer categoryId) {
        this.removeAllCatAndTrans(categoryId);
        jdbcTemplate.update(Constants.SQL_DELETE_CATEGORY, new Object[]{userId, categoryId});
    }

    private void removeAllCatAndTrans(Integer categoryId){
        jdbcTemplate.update(Constants.SQL_DELETE_ALL_TRANSACTIONS, new Object[]{categoryId});
    }

    private RowMapper<Category> categoryRowMapper = ((rs, rowNum) ->{
        return  new Category(rs.getInt("CATEGORY_ID"),
                rs.getInt("USER_ID"),
                rs.getString("TITLE"),
                rs.getString("DESCRIPTION"),
                rs.getDouble("TOTAL_EXPENSE"));
    });
}
