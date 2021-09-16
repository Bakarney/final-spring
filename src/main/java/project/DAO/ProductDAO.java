package project.DAO;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import project.entities.Product;

@Component
public class ProductDAO {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public ProductDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public Product get(int id) throws SQLException {
		String sql = 
				"SELECT products.id AS id,products.name AS name,description,producer.name AS producer,categories.name AS category,gender,number,price,photo "
				+ "FROM products "
				+ "INNER JOIN categories ON categories.id=products.category_id "
				+ "INNER JOIN producer ON products.producer_id=producer.id "
				+ "WHERE products.id=?";
		return jdbcTemplate.query(sql, new Object[] {id}, new BeanPropertyRowMapper<>(Product.class)).get(0);
	}
	
	public List<Product> getAll() {
		String sql =
				"SELECT products.id AS id,products.name AS name,description,producer.name AS producer,categories.name AS category,gender,number,price,photo "
				+ "FROM products "
				+ "INNER JOIN categories ON categories.id=products.category_id "
				+ "INNER JOIN producer ON products.producer_id=producer.id";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
	}
	
	public List<Product> getOrders(int id) throws SQLException {
		String sql = 
				"SELECT products.id AS id,products.name AS name,producer.name AS producer,categories.name AS category,gender,number,price,photo "
				+ "FROM orders_products "
				+ "INNER JOIN products ON products.id=orders_products.product_id "
				+ "INNER JOIN orders ON orders.id=orders_products.order_id "
				+ "INNER JOIN categories ON categories.id=products.category_id "
				+ "INNER JOIN producer ON products.producer_id=producer.id "
				+ "WHERE orders.id=?";
		return jdbcTemplate.query(sql, new Object[] {id}, new BeanPropertyRowMapper<>(Product.class));
	}
	
	public List<Product> getFiltered(String[] categories, String[] producers, String gender, String bot, String top, String order, String start, String number) throws SQLException {
		String sql = 
				"SELECT products.id AS id,products.name AS name,categories.name AS category,number,price,photo,producer.name AS producer,gender "
				+ "FROM products "
				+ "INNER JOIN categories ON products.category_id=categories.id "
				+ "INNER JOIN producer ON products.producer_id=producer.id ";
		sql = buildQuery(sql, categories, producers, gender, bot, top, order, start, number);
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
	}
	
	public int count(String[] categories, String[] producers, String gender, String bot, String top) throws SQLException {
		String sql =
				"SELECT COUNT(products.id) "
				+ "FROM products "
				+ "INNER JOIN categories ON products.category_id=categories.id "
				+ "INNER JOIN producer ON products.producer_id=producer.id \n";
		sql = buildQuery(sql, categories, producers, gender, bot, top, null, null, null);
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Integer.class)).get(0);
	}
	
	public boolean create(Product product) throws SQLException {
		String sql =
				"INSERT INTO products (name,category_id,description,number,price,photo,producer_id,gender) "
				+ "VALUES (?,(SELECT id FROM categories WHERE categories.name=?),?,?,?,?,(SELECT id FROM producer WHERE producer.name=?),?)";
		int num = jdbcTemplate.update(sql, product.getName(), product.getCategory(), product.getDescription(), 
				product.getNumber(), product.getPrice(), product.getPhoto(), product.getProducer(),
				product.getGender());
		return num > 0;
	}
	
	public boolean update(Product product) throws SQLException {
		String sql =
				"UPDATE products "
				+ "SET name=?, "
				+ "category_id=(SELECT id FROM categories WHERE name=?), "
				+ "description=?, "
				+ "number=?, "
				+ "price=?, "
				+ "photo=?, "
				+ "producer_id=(SELECT id FROM producer WHERE name=?), "
				+ "gender=? "
				+ "WHERE id=?";
		int num = jdbcTemplate.update(sql, product.getName(), product.getCategory(), product.getDescription(), 
				product.getNumber(), product.getPrice(), product.getPhoto(), product.getProducer(),
				product.getGender(), product.getId());
		return num > 0;
	}
	
	public boolean delete(int id) throws SQLException {
		String sql =
				"DELETE FROM products "
				+ "WHERE id=?";
		int num = jdbcTemplate.update(sql, id);
		return num > 0;
	}
	
	private static String buildQuery(String query, String[] categories, String[] producers, String gender, String bot, String top, String order, String start, String number) {
        return query + buildWhere(categories, producers, gender, bot, top) + buildOrder(order) + buildLimit(start, number);
    }

    private static String buildWhere(String[] categories, String[] producers, String gender, String bot, String top) {
        StringBuilder query = new StringBuilder();
        boolean category = categories != null && categories.length != 0;
        boolean producer = producers != null && producers.length != 0;
        boolean sex = gender != null && !gender.isEmpty() && !gender.equals("unisex");
        boolean price = bot != null && top != null;

        if (!category && !producer && !sex && !price)
            return "";

        query.append("WHERE ");

        if (category) {
            query.append("(");
            for (int i = 0; i < categories.length; i++) {
                if (i != 0)
                    query.append(" OR ");
                query.append("categories.name=").append("'").append(categories[i]).append("'");
            }
            query.append(")");
        }

        if (producer) {
            if (category)
                query.append(" AND ");
            query.append("(");
            for (int i = 0; i < producers.length; i++) {
                if (i != 0)
                    query.append(" OR ");
                query.append("producer.name='").append(producers[i]).append("'");
            }
            query.append(")");
        }

        if (sex) {
            if (category || producer)
                query.append(" AND ");
            switch (gender) {
                case "male":
                    query.append("(gender='male' OR gender='unisex')");
                    break;
                case "female":
                    query.append("(gender='female' OR gender='unisex')");
                    break;
                default:
                    break;
            }
        }

        if (price) {
            if (category || producer || sex)
                query.append(" AND ");
            query.append("price>=").append(bot);
            query.append(" AND price<=").append(top);
        }

        query.append(" \n");
        return query.toString();
    }

    private static String buildOrder(String order) {
        StringBuilder query = new StringBuilder();

        if (order != null && !order.isEmpty()) {
            query.append("ORDER BY ");
            switch (order) {
                case "a-z":
                    query.append("name ASC");
                    break;
                case "z-a":
                    query.append("name DESC");
                    break;
                case "l-h":
                    query.append("price ASC");
                    break;
                case "h-l":
                    query.append("price DESC");
                    break;
                case "new":
                    query.append("id ASC");
                    break;
                case "old":
                    query.append("id DESC");
                    break;
                default:
                    break;
            }
            query.append("\n");
        }
        return query.toString();
    }
    
    private static String buildLimit(String start, String number) {
    	if (start == null || number == null)
    		return "";
    	return "LIMIT " + start + "," + number;
    }
}