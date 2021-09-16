package project.DAO;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import project.DAO.mappers.BooleanMapper;
import project.entities.User;

@Component
public class UserDAO {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public UserDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public User get(String email, String password) throws SQLException {
		String sql = 
				"SELECT id,name,email,phone,addres,card "
				+ "FROM users "
				+ "WHERE email=? AND users.password=?";
		return jdbcTemplate.query(sql, new Object[] {email, password}, new BeanPropertyRowMapper<>(User.class)).get(0);
	}
	
	public User get(String email) throws SQLException {
		String sql = 
				"SELECT id,name,email,phone,addres,card,admin,status "
				+ "FROM users "
				+ "WHERE email=?";
		return jdbcTemplate.query(sql, new Object[] {email}, new BeanPropertyRowMapper<>(User.class)).get(0);
	}
	
	public User get(int id) throws SQLException {
		String sql = 
				"SELECT id,name,email,phone,addres,card,admin,status "
				+ "FROM users "
				+ "WHERE id=?";
		return jdbcTemplate.query(sql, new Object[] {id}, new BeanPropertyRowMapper<>(User.class)).get(0);
	}
	
	public List<User> getAll() throws SQLException {
		String sql =
				"SELECT id,name,email,phone,addres,card,admin,status "
				+ "FROM users";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
	}
	
	public boolean isAdmin(int id) throws SQLException {
		String sql =
				"SELECT admin "
				+ "FROM users "
				+ "WHERE id=?";
		return jdbcTemplate.query(sql, new Object[] {id}, new BooleanMapper()).get(0);
	}
	
	public boolean isActive(int id) throws SQLException {
		String sql =
				"SELECT status "
				+ "FROM users "
				+ "WHERE id=?";
		return jdbcTemplate.query(sql, new Object[] {id}, new BooleanMapper()).get(0);
	}
	
	public boolean create(User user) throws SQLException {
		String sql =
				"INSERT INTO users (name,users.password,email,phone,addres,card) "
				+ "VALUES (?,?,?,?,?,?)";
		int num = jdbcTemplate.update(sql, user.getName(), user.getPassword(), user.getEmail(), user.getPhone(),
				user.getAddress(), user.getCard());
		return num > 0;
	}
	
	public boolean setStatus(int id, boolean status) throws SQLException {
		String sql = 
				"UPDATE users "
				+ "SET status=? "
				+ "WHERE id=?";
		int num = jdbcTemplate.update(sql, status, id);
		return num > 0;
	}
	
	public boolean setAdmin(int id, boolean admin) throws SQLException {
		String sql = 
				"UPDATE users "
				+ "SET admin=? "
				+ "WHERE id=?";
		int num = jdbcTemplate.update(sql, admin, id);
		return num > 0;
	}
	
	public boolean delete(int id) throws SQLException {
		String sql =
				"DELETE FROM users "
				+ "WHERE id=?";
		int num = jdbcTemplate.update(sql, id);
		return num > 0;
	}
}
