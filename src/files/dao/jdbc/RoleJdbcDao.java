package files.dao.jdbc;

import files.dao.RoleDao;
import files.entity.Role;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

public class RoleJdbcDao extends JdbcDaoSupport implements RoleDao {

    private RowMapper<Role> rowMapper;

    public void setRowMapper(RowMapper<Role> rowMapper) {
        this.rowMapper = rowMapper;
    }

    @Override
    public List<Role> findAll() {
        return getJdbcTemplate().query("SELECT id AS role_id, name AS role_name FROM roles", rowMapper);
    }

    @Override
    public List<Role> findByUserId(Long uid) {
        return getJdbcTemplate().query("SELECT r.id AS role_id, r.name AS role_name FROM users u JOIN users_roles ur ON u.id = ur.user_id JOIN roles r ON r.id = ur.role_id WHERE u.id = ? ORDER BY r.name", rowMapper, uid);
    }

    @Override
    public Role findById(Long rid) {
        return getJdbcTemplate().queryForObject("SELECT id AS role_id, name AS role_name FROM roles WHERE id = ?", rowMapper, rid);
    }

}
