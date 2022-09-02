package core.jdbc;

import next.Exception.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    public void update(String sql, PreparedStatementSetter pss) throws DataAccessException {
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pss.setValues(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void update(String sql, Object... parameters) {
        update(sql, createPreparedStatementSetter(parameters));
    }

    public <T> List<T> query(String sql, PreparedStatementSetter pss,
                      RowMapper<T> rowMapper) throws DataAccessException {
        ResultSet rs = null;
        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pss.setValues(pstmt);
            rs = pstmt.executeQuery();

            List<T> result = new ArrayList<>();
            while (rs.next()) {
                result.add(rowMapper.mapRow(rs));
            }

            return result;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper,
                             Object... parameters) {
        return query(sql, rowMapper, createPreparedStatementSetter(parameters));
    }

    public <T> T queryForObject(String sql, PreparedStatementSetter pss, RowMapper rowMapper)
        throws DataAccessException {
        List<T> result = query(sql, pss, rowMapper);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    public <T> T queryForObject(String sql, RowMapper rowMapper, Object... parameters) {
        return queryForObject(sql, rowMapper, parameters);
    }

    private PreparedStatementSetter createPreparedStatementSetter(Object... parameters) {
        return new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                for (int i = 0; i < parameters.length; i++) {
                    pstmt.setObject(i + 1, parameters[i]);
                }
            }
        };
    }

}
