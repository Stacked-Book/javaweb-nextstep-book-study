package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import next.model.User;
import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;

public class UserDao {

    private static UserDao userDao = new UserDao();

    private UserDao() {
    }

    public static UserDao getInstance() {
        return userDao;
    }

    public void insert(User user) {
        JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public User findByUserId(String userId) {
        JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";

        RowMapper<User> rm = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs) throws SQLException {
                return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }
        };

        return jdbcTemplate.queryForObject(sql, rm, userId);
    }

    public List<User> findAll() throws SQLException {
        JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
        String sql = "SELECT userId, password, name, email FROM USERS";

        RowMapper<User> rm = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs) throws SQLException {
                return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }
        };

        return jdbcTemplate.query(sql, rm);
    }

    public void update(User user) {
        JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
        String sql = "UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?";
        jdbcTemplate.update(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }
}
