package files.dao.jdbc;

import files.dao.RoleDao;
import files.entity.Role;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RoleJdbcDao extends JdbcDaoSupport implements RoleDao {

    private RowMapper<Role> rowMapper = new RowMapper<Role>() {
        @Override
        public Role mapRow(final ResultSet rs,final int rowNum) throws SQLException {
            final Role role = new Role();
            role.setId(rs.getLong("id"));
            role.setName(rs.getString("name"));
            return role;
        }
    };
    
    @Override
    public List<Role> findAll() {
        return getJdbcTemplate().query("SELECT * FROM roles", rowMapper);
    }

    @Override
    public List<Role> findByUserId(Long uid) {
        return getJdbcTemplate().query("SELECT r.id, r.name FROM users u JOIN users_roles ur ON u.id = ur.user_id JOIN roles r ON r.id = ur.role_id WHERE u.id = ? ORDER BY r.name", rowMapper, uid);
    }

    @Override
    public Role findById(Long rid) {
        return getJdbcTemplate().queryForObject("SELECT * FROM roles WHERE id = ?", rowMapper, rid);
    }

}
