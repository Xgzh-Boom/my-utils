package temp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

public class SimpleActionListener implements ActionListener {
	JTextArea i_textA = null;
	JTextArea o_textA = null;

	public static final String LINE_BREAK = System.getProperty("line.separator");
	/**
	 * 列内容存储标记
	 */
	public static final String COLUMNS_CONTENT_MARK = "${columns_content}";
	/**
	 * 列名称存储标记
	 */
	public static final String COLUMNS_NAME_MARK = "${column_filed}";

	StringBuilder strB_table = new StringBuilder(
			"<div id='grid_name' widget='datagrid' url='data_url' style='width:100%;height:95%;'>${columns_content}</div>");
	StringBuilder strB_column = new StringBuilder("<div field='${column_filed}' header='${column_filed}'></div>");

	SimpleActionListener(JTextArea jtf1, JTextArea jtf2) {
		this.i_textA = jtf1;
		this.o_textA = jtf2;
	}

	// @Override
	// public void actionPerformed(ActionEvent e) {
	// String querysql = this.i_textA.getText();
	// StringBuilder strB = query(querysql);
	// this.o_textA.setText(strB.toString());
	// }
	@Override
	public void actionPerformed(ActionEvent e) {
		String querysql = this.i_textA.getText();
		List<String> resultFileds = queryFields(querysql);
		// StringBuilder strB = query(querysql);
		// this.o_textA.setText(strB.toString());
	}

	private List<String> queryFields(String sql) {
		List<String> Fileds = null;
		try {

			DBHelper db1 = new DBHelper(sql);// 创建DBHelper对象
			ResultSet ret = db1.pst.executeQuery();// 执行语句，得到结果集
			ResultSetMetaData meta = ret.getMetaData();
			int ColumnCount = meta.getColumnCount();
			if (ColumnCount > 0) {
				Fileds = new ArrayList<String>(ColumnCount);
				StringBuilder columns_content = new StringBuilder();
				for (int i = 0; i < ColumnCount; i++) {
					StringBuilder t_StrColumn = new StringBuilder(strB_column);
					int mark_start = t_StrColumn.indexOf(COLUMNS_NAME_MARK);
					t_StrColumn.delete(mark_start, COLUMNS_NAME_MARK.length());
					t_StrColumn.insert(mark_start, meta.getColumnName(i));

					// 测试
					System.out.print(meta.getColumnName(i) + "\t" + meta.getColumnTypeName(i) + "\t");
					System.out.println(meta.getColumnType(i) + Types.VARCHAR + "\t");
					columns_content.append(t_StrColumn).append(LINE_BREAK);

				}
				int content_mark = strB_table.indexOf(COLUMNS_CONTENT_MARK);

			}

			ret.close();
			db1.close();// 关闭连接

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (Fileds == null)
			Fileds = new ArrayList<String>(0);
		return Fileds;
	}

	private StringBuilder query(String sql) {
		StringBuilder resStr = new StringBuilder(strB_table);
		try {

			DBHelper db1 = new DBHelper(sql);// 创建DBHelper对象
			ResultSet ret = db1.pst.executeQuery();// 执行语句，得到结果集
			ResultSetMetaData meta = ret.getMetaData();
			int ColumnCount = meta.getColumnCount();
			if (ColumnCount > 0) {
				StringBuilder columns_content = new StringBuilder();
				for (int i = 0; i < ColumnCount; i++) {
					StringBuilder t_StrColumn = new StringBuilder(strB_column);
					int mark_start = t_StrColumn.indexOf(COLUMNS_NAME_MARK);
					t_StrColumn.delete(mark_start, COLUMNS_NAME_MARK.length());
					t_StrColumn.insert(mark_start, meta.getColumnName(i));

					// 测试
					System.out.print(meta.getColumnName(i) + "\t" + meta.getColumnTypeName(i) + "\t");
					System.out.println(meta.getColumnType(i) + Types.VARCHAR + "\t");
					columns_content.append(t_StrColumn).append(LINE_BREAK);

				}
				int content_mark = strB_table.indexOf(COLUMNS_CONTENT_MARK);

				resStr.delete(content_mark, COLUMNS_CONTENT_MARK.length());
				resStr.insert(content_mark, columns_content);

			}

			ret.close();
			db1.close();// 关闭连接

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resStr;
	}
}
