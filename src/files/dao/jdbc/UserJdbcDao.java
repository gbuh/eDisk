package files.dao.jdbc;

import files.dao.UserDao;
import files.entity.User;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.ParameterDisposer;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserJdbcDao extends JdbcDaoSupport implements UserDao {

    private RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setEnabled(rs.getBoolean("enabled"));
            return user;
        }
        
    };
    
    @Override
    public void create(User user) {
        final KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        
        getJdbcTemplate().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                final PreparedStatement ps = con.prepareStatement("INSERT INTO users (name, password, enabled) VALUES (?, md5(?), ?)", PreparedStatement.RETURN_GENERATED_KEYS);
                final PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(new Object[] {user.getName(), user.getPassword(), user.getEnabled()});
                try {
                    if(pss != null) {
                        pss.setValues(ps);
                    }
                } finally {
                    if (pss instanceof ParameterDisposer) {
                        ((ParameterDisposer) pss).cleanupParameters();
                    }
                }
                return ps;
            }
        }, generatedKeyHolder);
        
        user.setId(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public User read(Long uid) {
        return getJdbcTemplate().queryForObject("SELECT * FROM users WHERE id = ?", rowMapper, uid);
    }

    @Override
    public void update(User user) {
        getJdbcTemplate().update("UPDATE users SET enabled = ? WHERE id = ?", user.getEnabled(), user.getId());
    }

    @Override
    public void delete(Long id) {
        getJdbcTemplate().update("DELETE FROM users WHERE id = ?", id);
    }

    @Override
    public List<User> findAll() {
        return getJdbcTemplate().query("SELECT * FROM users", rowMapper);
    }

    @Override
    public User findByName(String name) {
        return getJdbcTemplate().queryForObject("SELECT * FROM users WHRER name = ?", rowMapper, name);
    }

}
