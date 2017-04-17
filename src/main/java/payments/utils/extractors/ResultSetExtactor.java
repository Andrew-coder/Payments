package payments.utils.extractors;

import payments.model.entity.BaseEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetExtactor <E extends BaseEntity> {
    E extract(ResultSet set) throws SQLException;
}
