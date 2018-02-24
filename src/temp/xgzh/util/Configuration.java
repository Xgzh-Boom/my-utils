package temp.xgzh.util;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Configuration {

	public static Configuration db_config = null;
	private String url;
	private String driver;
	private String username;
	private String password;

	private Configuration(String url, String driver, String username, String password) {
		super();
		this.url = url;
		this.driver = driver;
		this.username = username;
		this.password = password;
	}

	/**
	 * 获取 对象 Configuration
	 * 
	 * @return
	 */
	public static Configuration getConfigure() {

		if (Configuration.db_config == null) {
			try {
				InputStream in = Configuration.class.getResourceAsStream("db.xml");
				if (in != null) {

					Configuration.db_config = load(in);
				}
			} catch (DocumentException e) {
				e.printStackTrace();
				return null;
			}
		}
		return Configuration.db_config;

	}

	private static Configuration load(InputStream in) throws DocumentException {

		SAXReader reader = new SAXReader();
		Document doc = reader.read(in);
		Element datas = doc.getRootElement();
		String url = datas.element(IDS.URL).getText();
		String driver = datas.element(IDS.DRIVER).getText();
		String username = datas.element(IDS.USERNAME).getText();
		String password = datas.element(IDS.PASSWORD).getText();
		System.out.println("------------------------");
		System.out.println(url);
		System.out.println(driver);
		System.out.println(username);
		System.out.println(password);
		System.out.println("------------------------");
		Configuration.db_config = new Configuration(url, driver, username, password);
		return Configuration.db_config;

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

interface IDS {
	public static final String URL = "url";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String DRIVER = "driver";

}