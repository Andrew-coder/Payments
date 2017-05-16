package payments.utils.extractors;

import payments.model.entity.BaseEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This interface defines class to extract entity from result set
 * @param <E>
 */
public interface ResultSetExtactor <E extends BaseEntity> {
    E extract(ResultSet set) throws SQLException;
}
