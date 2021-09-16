package project.DAO;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import project.DAO.mappers.IntegerMapper;
import project.entities.*;

@Component
public class OrderDAO {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public OrderDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	private Order getOrdersProducts(Order order) throws SQLException {
		String sql = 
				"SELECT product_id "
				+ "FROM orders_products "
				+ "WHERE order_id=?";
		order.setCart(jdbcTemplate.query(sql, new Object[] {order.getId()}, new IntegerMapper()));
		return order;
	}
	
	public Order get(int id) throws SQLException {
		String sql =
				"SELECT orders.id,orders.state,user_id "
				+ "FROM orders "
				+ "WHERE orders.id=?";
		Order order = jdbcTemplate.query(sql, new Object[] {id}, new BeanPropertyRowMapper<>(Order.class)).get(0);
		return getOrdersProducts(order);
	}
	
	public Order get(User user) throws SQLException {
		String sql =
				"SELECT orders.id "
				+ "FROM orders "
				+ "WHERE user_id=? AND orders.state=\"preparing\"";
		Order order = jdbcTemplate.query(sql, new Object[] {user.getId()}, new BeanPropertyRowMapper<>(Order.class)).get(0);
		return getOrdersProducts(order);
	}
	
	public List<Order> getAll() throws SQLException {
		String sql =
				"SELECT orders.id,orders.state,user_id "
				+ "FROM orders "
				+ "WHERE state!='preparing'";
		List<Order> orders = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Order.class));
		for (Order order : orders) 
			order = getOrdersProducts(order);
		return orders;
	}
	
	public Order create(int userId) throws SQLException {
		String sql =
				"INSERT INTO orders (user_id) "
				+ "VALUES (?)";
		jdbcTemplate.update(sql, userId);
		sql =	"SELECT id "
				+ "FROM orders "
				+ "WHERE user_id=? AND orders.state=\"preparing\"";
		return jdbcTemplate.query(sql, new Object[] {userId}, new BeanPropertyRowMapper<>(Order.class)).get(0);
	}
	
	public boolean addProduct(int id, int product_id) throws SQLException {
		String sql =
				"INSERT INTO orders_products (order_id,product_id) "
				+ "VALUES (?,?)";
		int num = jdbcTemplate.update(sql, id, product_id);
		return num > 0;
	}
	
	public boolean removeProduct(int id, int product_id) throws SQLException {
		String sql =
				"DELETE FROM orders_products "
				+ "WHERE order_id=? AND product_id=?";
		int num = jdbcTemplate.update(sql, id, product_id);
		return num > 0;
	}
	
	public boolean setState(int id, String state) throws SQLException {
		String sql =
				"UPDATE orders "
				+ "SET state=?"
				+ "WHERE id=?";
		int num = jdbcTemplate.update(sql, id, state);
		return num > 0;
	}
	
	public boolean delete(int id) throws SQLException {
		String sql =
				"DELETE FROM orders "
				+ "WHERE id=?";
		int num = jdbcTemplate.update(sql, id);
		return num > 0;
	}
}
