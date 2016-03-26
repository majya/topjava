package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserMealRepositoryImpl implements UserMealRepository {

    private static final BeanPropertyRowMapper<UserMeal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(UserMeal.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUser;


    @Autowired
    public JdbcUserMealRepositoryImpl(DataSource dataSource) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("MEALS")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("id", userMeal.getId())
                .addValue("datetime", Timestamp.valueOf(userMeal.getDateTime()))
                .addValue("description", userMeal.getDescription())
                .addValue("calories", userMeal.getCalories());
        if (userMeal.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(map);
            userMeal.setId(newKey.intValue());
        } else {
            SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT user_id FROM meals WHERE id=?", userMeal.getId());
            int testUserId = 0;

            if (sqlRowSet.next()) testUserId = sqlRowSet.getInt("user_id");
            if(testUserId==userId) {
                namedParameterJdbcTemplate.update("" +
                        "UPDATE meals SET user_id=:user_id, datetime=:datetime, description=:description, " +
                        "calories=:calories WHERE id=:id", map);
            }
            else return null;
        }
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT user_id FROM meals WHERE id=?", id);
        int testUserId = 0;

        if (sqlRowSet.next()) testUserId = sqlRowSet.getInt("user_id");

        return testUserId == userId && jdbcTemplate.update("DELETE FROM meals WHERE id=?", id) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT user_id FROM meals WHERE id=?", id);
        int testUserId = 0;

        if (sqlRowSet.next()) testUserId = sqlRowSet.getInt("user_id");
        if(testUserId==userId) {
            List<UserMeal> meals = jdbcTemplate.query("SELECT * FROM meals WHERE id=?", ROW_MAPPER, id);
            return DataAccessUtils.singleResult(meals);
        }
       else return null;
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        List<UserMeal> query = jdbcTemplate.query("SELECT * FROM meals WHERE user_id=?", ROW_MAPPER, userId);
        Collections.sort(query, (o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
        return query;
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return getAll(userId).stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime(), startDate, endDate))
                .collect(Collectors.toList());
    }
}
