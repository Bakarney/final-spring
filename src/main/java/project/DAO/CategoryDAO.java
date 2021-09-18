package project.DAO;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import project.DAO.mappers.StringMapper;

@Component
public class CategoryDAO {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public CategoryDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<String> getAll() throws SQLException {
		String sql =
				"SELECT name "
				+ "FROM categories";
		return jdbcTemplate.query(sql, new StringMapper());
	}
	
	public boolean create(String name) throws SQLException {
		String sql =
				"INSERT INTO categories (name) "
				+ "VALUES (?)";
		int num = jdbcTemplate.update(sql, name);
		return num > 0;
	}
	
	public boolean delete(String name) throws SQLException {
		String sql =
				"DELETE FROM producer "
				+ "WHERE name=?";
		int num = jdbcTemplate.update(sql, name);
		return num > 0;
	}
}
