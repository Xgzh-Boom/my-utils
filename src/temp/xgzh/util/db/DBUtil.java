package temp.xgzh.util.db;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import temp.xgzh.dev.dao.BaseDB;

public class DBUtil {
	public static ResultSet getTable(DatabaseMetaData DBMetaData, String tableName) throws SQLException {
		ResultSet set = DBMetaData.getTables(null, null, tableName, null);
		return set;
	}

	public static ResultSet getTable(String tableName) throws SQLException {
		BaseDB db = new BaseDB();
		DatabaseMetaData DBMetaData = db.getDatabaseMetaData();
		return getTable(DBMetaData, tableName);
	}

	public static ResultSet getColumns(String tableName) throws SQLException {

		ResultSet resSet = null;
		BaseDB db = new BaseDB();
		DatabaseMetaData DBMetaData = db.getDatabaseMetaData();
		resSet = DBMetaData.getColumns(null, null, tableName, null);

		return resSet;
	}
}
