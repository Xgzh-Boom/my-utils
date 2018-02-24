package temp.xgzh.dev.dao;

import java.util.Properties;

public class BaseDB {
	private String url = null;
	private String name = null;
	private String user = null;
	private String password = null;

	public BaseDB(Properties con_data) {
		init(con_data);
	}

	protected void init(Properties con_data) {
		setAttribute(con_data);
	}

	void setAttribute(Properties con_data) {
		if (con_data == null) {
			return;
		}
		this.url = con_data.getProperty("url");
		this.name = con_data.getProperty("name");
		this.user = con_data.getProperty("user");
		this.password = con_data.getProperty("password");

	}
}
