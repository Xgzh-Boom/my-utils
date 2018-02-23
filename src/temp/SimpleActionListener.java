package temp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextArea;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SimpleActionListener implements ActionListener {
	JTextArea i_textA = null;
	JTextArea o_textA = null;

	SimpleActionListener(JTextArea jtf1, JTextArea jtf2) {
		this.i_textA = jtf1;
		this.o_textA = jtf2;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String querysql = this.i_textA.getText();
		List<String> resultFileds = queryFields(querysql);
		try {
			String tableXML_Str = new TableXML().createTableXML_Str(resultFileds);
			this.o_textA.setText(tableXML_Str);
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TransformerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	private List<String> queryFields(String sql) {
		List<String> Fileds = null;
		DBHelper db1 = null;
		ResultSet ret = null;
		try {
			db1 = new DBHelper(sql);// 创建DBHelper对象
			ret = db1.pst.executeQuery();// 执行语句，得到结果集
			ResultSetMetaData meta = ret.getMetaData();
			int ColumnCount = meta.getColumnCount();
			if (ColumnCount > 0) {
				Fileds = new ArrayList<String>(ColumnCount);

				for (int i = 1; i < ColumnCount + 1; i++) {
					Fileds.add(meta.getColumnLabel(i));
					// 测试
					System.out.print(meta.getColumnLabel(i) + "\t" + meta.getColumnName(i) + "\t"
							+ meta.getColumnTypeName(i) + "\t" + meta.getColumnType(i) + Types.VARCHAR);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (db1 != null)
				db1.close();// 关闭连接
			if (ret != null)
				try {
					ret.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}
		if (Fileds == null)
			Fileds = new ArrayList<String>(0);
		return Fileds;
	}

}

class TableXML {
	/**
	 * 表格插件
	 */
	public static final String DATA_TABLE_WIDGET = "datagrid";
	/**
	 * 默认表格样式
	 */
	public static final String DEFAULT_DATA_TABLE_STYLE = "width:700px;height:500px;";

	/**
	 * 默认表格样式
	 */
	public static final String TABLE_TAG = "div";

	/**
	 * 默认列
	 */
	public static final String COLUMN_TAG = "div";

	DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

	Document createTableXML(List<String> data_columns, boolean show_no) throws ParserConfigurationException {

		if (data_columns == null) {
			data_columns = new ArrayList<String>(0);
		}
		String[] columns_arr = data_columns.toArray(new String[data_columns.size()]);

		DocumentBuilder docB = builderFactory.newDocumentBuilder();
		Document doc = docB.newDocument();
		doc.setXmlStandalone(true);

		Element table_tag = createTableElement("", doc);
		appendColumns(doc, table_tag, columns_arr);
		doc.appendChild(table_tag);
		return doc;
	}

	String createTableXML_Str(List<String> data_columns) throws ParserConfigurationException, TransformerException {
		return createTableXML_Str(data_columns, true);
	}

	String createTableXML_Str(List<String> data_columns, boolean show_no)
			throws ParserConfigurationException, TransformerException {
		String tableXML_str = null;
		Document doc_table = createTableXML(data_columns, show_no);

		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transformer = transFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		DOMSource domSource = new DOMSource(doc_table);

		// xml transform String
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		transformer.transform(domSource, new StreamResult(bos));

		tableXML_str = bos.toString();

		tableXML_str = tableXML_str.substring(38);

		return tableXML_str;
	}

	Document createTableXML(List<String> data_column) throws ParserConfigurationException {
		return createTableXML(data_column, false);
	}

	void appendColumns(Document doc, Element table_tag, String[] columns_arr) {
		appendColumns(doc, table_tag, true, columns_arr);
	}

	void appendColumns(Document doc, Element table_tag, boolean show_no, String[] columns_arr) {

		if (show_no == true) {
			appendNOColumnTag(doc, table_tag);
		}
		for (int i = 0; i < columns_arr.length; i++) {
			appendColumnTag(doc, table_tag, columns_arr[i]);
		}
	}

	/**
	 * 添加 行号列
	 * 
	 * @param doc
	 * @param table_tag
	 */
	private void appendNOColumnTag(Document doc, Element table_tag) {
		Element no_column_tag = doc.createElement(TableXML.COLUMN_TAG);
		addAttribute(no_column_tag, "type", "indexcolumn");
		addAttribute(no_column_tag, "header", "序号");
		table_tag.appendChild(no_column_tag);
	}

	/**
	 * 添加 行号列
	 * 
	 * @param doc
	 * @param table_tag
	 */
	private void appendColumnTag(Document doc, Element table_tag, String fieldValue) {
		Element column_tag = doc.createElement(TableXML.COLUMN_TAG);

		addAttribute(column_tag, "field", fieldValue);
		addAttribute(column_tag, "header", fieldValue);

		table_tag.appendChild(column_tag);
	}

	Element createTableElement(String id, Document doc) {
		Element table_tag = doc.createElement(TableXML.TABLE_TAG);
		Map<String, String> Attrs = new HashMap<String, String>(6);
		Attrs.put("id", id);
		Attrs.put("widget", TableXML.DATA_TABLE_WIDGET);
		Attrs.put("style", TableXML.DEFAULT_DATA_TABLE_STYLE);
		Attrs.put("url", "data_url");

		addAttributes(table_tag, Attrs);
		return table_tag;
	}

	/**
	 * 添加多个attribute 到同一个 实体里
	 * 
	 * @param e_tag
	 * @param Attrs
	 */
	void addAttributes(Element e_tag, Map<String, String> Attrs) {
		Set<Map.Entry<String, String>> set_Attrs = Attrs.entrySet();
		for (Map.Entry<String, String> Attr : set_Attrs) {
			String attr_name = Attr.getKey();
			String attr_value = Attr.getValue();
			addAttribute(e_tag, attr_name, attr_value);
		}

	}

	void addAttribute(Element e_tag, String attr_name, String attr_value) {
		e_tag.setAttribute(attr_name, attr_value);
	}
}
