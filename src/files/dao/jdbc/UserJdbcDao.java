package files.dao.jdbc;

import files.dao.UserDao;
import files.entity.Role;
import files.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.ParameterDisposer;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserJdbcDao extends JdbcDaoSupport implements UserDao {

    private RowMapper<Role> roleRowMapper;

    private RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final User user = new User();
            user.setId(rs.getLong("user_id"));
            user.setName(rs.getString("user_name"));
            user.setPassword(rs.getString("user_password"));
            user.setEnabled(rs.getBoolean("user_enabled"));
            return user;
        }
        
    };

    private ResultSetExtractor<List<User>> extractor = new ResultSetExtractor<List<User>>() {
        @Override
        public List<User> extractData(final ResultSet rs) throws SQLException, DataAccessException {
            final List<User> users = new ArrayList<>();
            int count = 0;
            User user = null;
            while (rs.next()) {
                if (user == null || !Long.valueOf(rs.getLong("user_id")).equals(user.getId())) {
                    user = rowMapper.mapRow(rs, count);
                    user.setRoles(new ArrayList<>());
                    users.add(user);
                }
                user.getRoles().add(roleRowMapper.mapRow(rs, count));
                count++;
            };
            return users;
        }
    };

    public void setRoleRowMapper(final RowMapper<Role> roleRowMapper) {
        this.roleRowMapper = roleRowMapper;
    }

    @Override
    public void create(final User user) {
        final KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        getJdbcTemplate().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                final PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO users (name, password, enabled) VALUES (?, md5(?), ?)",
                        PreparedStatement.RETURN_GENERATED_KEYS);
                final PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(
                        new Object[] {user.getName(), user.getPassword(), user.getEnabled()});
                try {
                    if (pss != null) {
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
    public User read(final Long uid) {
        final List<User> users = getJdbcTemplate().query("SELECT"
                + " u.id AS user_id, u.name AS user_name, u.password AS user_password, u.enabled AS user_enabled,"
                + " r.id AS role_id, r.name AS role_name"
                + " FROM users u"
                + " LEFT JOIN users_roles ur ON ur.user_id = u.id"
                + " LEFT JOIN roles r ON r.id = ur.role_id"
                + " WHERE u.id = ?",
                extractor, uid);
        return users.get(0);
    }

    @Override
    public void update(final User user) {
        getJdbcTemplate().update("UPDATE users SET enabled = ? WHERE id = ?", user.getEnabled(),
                user.getId());
    }

    @Override
    public void delete(final Long id) {
        getJdbcTemplate().update("DELETE FROM users WHERE id = ?", id);
    }

    @Override
    public List<User> findAll() {
        return getJdbcTemplate().query("SELECT"
                + " u.id AS user_id, u.name AS user_name, u.password AS user_password, u.enabled AS user_enabled,"
                + " r.id AS role_id, r.name AS role_name"
                + " FROM users u"
                + " LEFT JOIN users_roles ur ON ur.user_id = u.id"
                + " LEFT JOIN roles r ON r.id = ur.role_id",
                extractor);
    }

    @Override
    public User findByName(final String name) {
        final List<User> users = getJdbcTemplate().query("SELECT"
                + " u.id AS user_id, u.name AS user_name, u.password AS user_password, u.enabled AS user_enabled,"
                + " r.id AS role_id, r.name AS role_name"
                + " FROM users u"
                + " LEFT JOIN users_roles ur ON ur.user_id = u.id"
                + " LEFT JOIN roles r ON r.id = ur.role_id"
                + " WHERE u.name = ?",
                extractor, name);
        return users.get(0);
    }
}
