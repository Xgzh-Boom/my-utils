package temp.xgzh.dev.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import temp.xgzh.util.Configuration;

public class BaseDB {
	private String url = null;
	private String driver = null;
	private String username = null;
	private String password = null;

	private Connection conn = null;

	private List<Statement> statList = null;

	public BaseDB(Configuration con_data) {
		init(con_data);
	}

	public BaseDB() {

		Configuration con_data = Configuration.getConfigure();

		init(con_data);
	}

	protected void init(Configuration con_data) {

		setAttribute(con_data);
		try {
			Class.forName(driver);// 指定连接驱动类
			this.conn = DriverManager.getConnection(url, username, password);// 获取连接
		} catch (Exception e) {
			e.printStackTrace();
		}
		statList = new ArrayList<Statement>();

	}

	private void setAttribute(Configuration con_data) {
		if (con_data == null) {
			return;
		}
		this.url = con_data.getUrl();
		this.driver = con_data.getDriver();
		this.username = con_data.getUsername();
		this.password = con_data.getPassword();
	}

	public Connection getConnection() {
		return conn;
	}

	public PreparedStatement createPreStatment(String sql) throws SQLException {
		PreparedStatement Statement = null;
		if (this.conn != null) {
			Statement = this.conn.prepareStatement(sql);
			statList.add(Statement);
		}
		return Statement;
	}

	public Statement createStatment() throws SQLException {
		Statement Statement = null;
		if (this.conn != null) {
			Statement = this.conn.createStatement();
			statList.add(Statement);
		}
		return Statement;
	}

	/**
	 * 关闭连接、回话
	 * 
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		if (statList.size() > 0) {
			closeStatements();
		}
		if (conn != null) {
			conn.close();
		}
	}

	/**
	 * 关闭当前DB连接的会话集
	 * 
	 * @throws SQLException
	 */
	protected void closeStatements() throws SQLException {
		Statement[] stats = statList.toArray(new Statement[statList.size()]);
		for (int i = 0; i < stats.length; i++) {
			Statement stat = stats[i];
			closeStatement(stat);
		}
		statList.clear();

	}

	/**
	 * 关闭会话
	 * 
	 * @param stat
	 * @throws SQLException
	 */
	private void closeStatement(Statement stat) throws SQLException {
		if (stat != null) {
			stat.close();
		}
	}

	public DatabaseMetaData getDatabaseMetaData() throws SQLException {
		DatabaseMetaData dbMetaData = null;
		if (conn != null) {
			dbMetaData = conn.getMetaData();
		}
		return dbMetaData;
	}

}
