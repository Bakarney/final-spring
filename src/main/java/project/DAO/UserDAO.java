package project.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import project.DAO.mappers.BooleanMapper;
import project.entities.User;

/**
 * @author Naberezhniy Artur
 * 
 * Consists methods to manage users in DB.
 */
@Component
public class UserDAO implements UserDetailsService {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public UserDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			final User user = get(username);
			if (user == null)
				throw new UsernameNotFoundException("Can not find user");
			List<GrantedAuthority> roles = new ArrayList<>();
			roles.add(new GrantedAuthority() {
				@Override
				public String getAuthority() {
					if (user.isAdmin())
						return "ROLE_ADMIN";
					return "ROLE_USER";
				}
			});
			org.springframework.security.core.userdetails.User springUser = 
					new org.springframework.security.core.userdetails.User
					(user.getEmail(), user.getPassword(), user.isActive(), true, true, true, roles);
			return springUser;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException("Can not find user", e);
		}
	}

	/**
	 * @param email
	 * @param password
	 * @return User with this email and password or null if it's not found
	 * @throws SQLException
	 */
	public User get(String email, String password) throws SQLException {
		String sql = 
				"SELECT id,name,email,phone,address,card "
				+ "FROM users "
				+ "WHERE email=? AND users.password=?";
		List<User> users = jdbcTemplate.query(sql, new Object[] {email, password}, new BeanPropertyRowMapper<>(User.class));
		if (users.size() == 0)
			return null;
		return users.get(0);
	}
	
	/**
	 * @param email
	 * @return User with this email or null if it's not found
	 * @throws SQLException
	 */
	public User get(String email) throws SQLException {
		String sql = 
				"SELECT id,name,email,password,phone,address,card,admin,active "
				+ "FROM users "
				+ "WHERE email=?";
		List<User> users = jdbcTemplate.query(sql, new Object[] {email}, new BeanPropertyRowMapper<>(User.class));
		if (users.size() == 0)
			return null;
		return users.get(0);
	}
	
	/**
	 * @param id
	 * @return User with this id or null if it's not found
	 * @throws SQLException
	 */
	public User get(int id) throws SQLException {
		String sql = 
				"SELECT id,name,email,phone,address,card,admin,active "
				+ "FROM users "
				+ "WHERE id=?";
		List<User> users = jdbcTemplate.query(sql, new Object[] {id}, new BeanPropertyRowMapper<>(User.class));
		if (users.size() == 0)
			return null;
		return users.get(0);
	}
	
	/**
	 * @return List of all users
	 * @throws SQLException
	 */
	public List<User> getAll() throws SQLException {
		String sql =
				"SELECT id,name,email,phone,address,card,admin,active "
				+ "FROM users";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
	}
	
	/**
	 * @param User id
	 * @return If user admin
	 * @throws SQLException
	 */
	public boolean isAdmin(int id) throws SQLException {
		String sql =
				"SELECT admin "
				+ "FROM users "
				+ "WHERE id=?";
		return jdbcTemplate.query(sql, new Object[] {id}, new BooleanMapper()).get(0);
	}
	
	/**
	 * @param User id
	 * @return If user active
	 * @throws SQLException
	 */
	public boolean isActive(int id) throws SQLException {
		String sql =
				"SELECT active "
				+ "FROM users "
				+ "WHERE id=?";
		return jdbcTemplate.query(sql, new Object[] {id}, new BooleanMapper()).get(0);
	}
	
	/**
	 * @param User
	 * @return If user was created
	 * @throws SQLException
	 */
	public boolean create(User user) throws SQLException {
		String sql =
				"INSERT INTO users (name,users.password,email,phone,address,card) "
				+ "VALUES (?,?,?,?,?,?)";
		int num = jdbcTemplate.update(sql, user.getName(), user.getPassword(), user.getEmail(), user.getPhone(),
				user.getAddress(), user.getCard());
		return num > 0;
	}
	
	/**
	 * @param User id
	 * @param New status
	 * @return If status was set
	 * @throws SQLException
	 */
	public boolean setStatus(int id, boolean status) throws SQLException {
		String sql = 
				"UPDATE users "
				+ "SET active=? "
				+ "WHERE id=?";
		int num = jdbcTemplate.update(sql, status, id);
		return num > 0;
	}
	
	/**
	 * @param User
	 * @param New admin
	 * @return If admin was set
	 * @throws SQLException
	 */
	public boolean setAdmin(int id, boolean admin) throws SQLException {
		String sql = 
				"UPDATE users "
				+ "SET admin=? "
				+ "WHERE id=?";
		int num = jdbcTemplate.update(sql, admin, id);
		return num > 0;
	}
	
	/**
	 * @param User id
	 * @return If user was deleted
	 * @throws SQLException
	 */
	public boolean delete(int id) throws SQLException {
		String sql =
				"DELETE FROM users "
				+ "WHERE id=?";
		int num = jdbcTemplate.update(sql, id);
		return num > 0;
	}
}
