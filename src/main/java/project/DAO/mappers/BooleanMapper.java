package project.DAO.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BooleanMapper implements RowMapper<Boolean> {

	@Override
	public Boolean mapRow(ResultSet rs, int rowNum) throws SQLException {
		return rs.getBoolean(rs.getMetaData().getColumnName(1));
	}
}
