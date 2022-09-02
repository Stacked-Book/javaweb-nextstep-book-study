package next.dao;

import java.sql.ResultSet;
import java.util.List;

import core.jdbc.JdbcTemplate;
import next.model.User;

public class UserDao {

    public void insert(User user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUserId(), user.getPassword(),
                user.getName(), user.getEmail());
    }

    public void update(User user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "UPDATE USERS SET password=?, name=?, email=? WHERE userid=?";
        jdbcTemplate.update(sql, user.getPassword(), user.getName(),
                user.getEmail(), user.getUserId());

    }

    public List<User> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT userId, password, name, email FROM USERS";
        return jdbcTemplate.query(sql, (ResultSet rs) ->
                new User(rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")));

    }

    public User findByUserId(String userId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        return jdbcTemplate.queryForObject(sql, (ResultSet rs) ->
            new User(rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email")), userId);
    }
}
