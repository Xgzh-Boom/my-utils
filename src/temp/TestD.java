package temp;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import temp.xgzh.dev.dao.BaseDB;
import temp.xgzh.util.Configuration;
import temp.xgzh.util.db.DBUtil;

public class TestD {

	public static void main(String[] args) throws SQLException {
		// temp01();
		ResultSet rs = DBUtil.getColumns("aos_rms_user");
		while (rs.next()) {

			System.out.println(
					rs.getString("COLUMN_NAME") + "--" + rs.getInt("DATA_TYPE") + "--" + rs.getString("REMARKS"));

		}
		rs.close();
	}

	private static void temp01() {
		Configuration config = Configuration.getConfigure();
		BaseDB db = new BaseDB(config);
		ResultSet rest = null;
		try {
			Connection conn = db.getConnection();
			DatabaseMetaData dbMetaData = conn.getMetaData();
			PreparedStatement stat = db.createPreStatment("select * from aos_rms_user  limit 0,0");
			rest = stat.executeQuery();
			ResultSetMetaData metaData = rest.getMetaData();
			int ColumnCount = metaData.getColumnCount();
			for (int Column_index = 1; Column_index <= ColumnCount; Column_index++) {
				System.out.print(metaData.getColumnName(Column_index));
				System.out.print("\t");
				System.out.print(metaData.getPrecision(Column_index));
				System.out.println();
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {

				if (rest != null) {
					rest.close();
				}

				db.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
