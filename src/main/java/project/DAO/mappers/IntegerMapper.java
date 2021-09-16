package project.DAO.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class IntegerMapper implements RowMapper<Integer> {

	@Override
	public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
		return rs.getInt(rs.getMetaData().getColumnName(1));
	}
}
