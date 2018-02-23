package temp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ToolsFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void frameInit() {
		super.frameInit();
		plusInit();
	}

	void plusInit() {
		JPanel jp = new JPanel();
		jp.setLayout(new FlowLayout());
		JTextArea jtf1 = new JTextArea(15, 50);
		jtf1.setLineWrap(true);
		JTextArea jtf2 = new JTextArea(15, 50);
		jtf2.setLineWrap(true);
		newFrame(this, jp, jtf1, jtf2);
		this.setResizable(false);
	}

	private static int newFrame(JFrame jf, JPanel jp, JTextArea jtf1, JTextArea jtf2) {
		Container contentPane = jf.getContentPane();
		contentPane.setLayout(new BorderLayout());
		jtf1.setFont(new Font("谐体", Font.BOLD, 21));
		jtf2.setFont(new Font("谐体", Font.BOLD, 22));
		// JScrollPane为滚动条组件
		jp.add(new JScrollPane(jtf1));
		JButton button = new JButton("获取");
		ActionListener buttonAction = new SimpleActionListener(jtf1, jtf2);
		button.addActionListener(buttonAction);
		// 按钮调整大小
		button.setPreferredSize(new Dimension(400, 60));
		Font f = new Font("谐体", Font.BOLD, 30);
		button.setFont(f);
		jp.add(button);
		jp.add(new JScrollPane(jtf2));

		contentPane.add(jp);

		jf.pack();
		jf.setLocation(0, 0);
		jf.setSize(1000, 1000);
		// jf.setVisible(true);

		jf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		return 0;
	}

	public static void main(String[] args) {
		ToolsFrame f = new ToolsFrame();
		f.setVisible(true);
	}
}
