import java.io.*;
import java.nio.file.Path;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooserWindow {
	JFrame frame = null;
	JTextField saveText = null;
	JTextField openText = null;
	public static void main(String[] args) {
		FileChooserWindow fileChooser = new FileChooserWindow();
		fileChooser.init();
	}
	
	//界面初始化
	void init() {
		//绘出界面框架
		frame = new FileFrame("FileChooserTest");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation((1366-500)/2,(768-300)/2);
		
		//初始化各个组件
		JPanel savePanel = new JPanel();
		JPanel openPanel = new JPanel();
		Border border = BorderFactory.createLoweredBevelBorder();
		Border saveBorder = BorderFactory.createTitledBorder(border, "生成二维码", TitledBorder.LEFT, TitledBorder.ABOVE_TOP);
		Border openBorder = BorderFactory.createTitledBorder(border, "读取二维码", TitledBorder.LEFT, TitledBorder.ABOVE_TOP);
		
		JLabel saveLabel = new JLabel("文本信息:", JLabel.RIGHT);
		JLabel openLabel = new JLabel("文本信息:", JLabel.RIGHT);
		saveText = new JTextField(50);
		openText = new JTextField(50);
		openText.setEditable(false);
		JButton saveButton = new JButton("Save");
		JButton openButton = new JButton("Open");
		
		//在框架中加入组件
		savePanel.setBorder(saveBorder);
		savePanel.add(saveLabel);
		savePanel.add(saveText);
		savePanel.add(saveButton);
		openPanel.setBorder(openBorder);
		openPanel.add(openLabel);
		openPanel.add(openText);
		openPanel.add(openButton);
		frame.add(savePanel,BorderLayout.NORTH);
		frame.add(openPanel, BorderLayout.SOUTH);
		frame.pack();
		
		//为按钮添加事件监听
		FileListener fileListener = new FileListener();
		saveButton.addActionListener(fileListener);
		openButton.addActionListener(fileListener);
	}
	
	//界面框架类
	class FileFrame extends JFrame {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6778860030780421258L;

		public FileFrame(String title) {
			setTitle(title);
			setVisible(true);
		}
	}
	
	//按钮监听类
	class FileListener implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			
			String command = event.getActionCommand();
			
			if(command.equals("Save")) {
				
				String sText = saveText.getText();
				if(sText.equals("")) {
					JOptionPane.showMessageDialog(frame, "未输入文本", "错误", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				Path sFile = saveFile();
				if(sFile != null) {
					WriteQRCode writeFile = new WriteQRCode(sText, sFile);
					writeFile.write();
					
					Image img = new ImageIcon(sFile.toString()).getImage().getScaledInstance(95, 95, Image.SCALE_DEFAULT);
					ImageIcon icon = new ImageIcon();
					icon.setImage(img);
					JOptionPane.showMessageDialog(frame, "二维码保存路径:\n" + sFile.toString(), "系统消息", JOptionPane.PLAIN_MESSAGE, icon);
				}
				
			} else if(command.equals("Open")) {
				String oText = null;
				
				File oFile = openFile();
				if(oFile == null) return;
				
				ReadQRCode read = new ReadQRCode(oFile);
				oText = read.read();
				
				if(!oText.equals("error")) {
					openText.setText(oText);
				} else {
					JOptionPane.showMessageDialog(frame, "没有二维码信息", "系统消息", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
		
		//按下Save按钮时执行
		Path saveFile() {
			String filePath = null;
			JFileChooser saveChooser = new JFileChooser();
			saveChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = saveChooser.showSaveDialog(frame);
			if(result == JFileChooser.APPROVE_OPTION) {
				filePath = saveChooser.getSelectedFile().getPath();
				return new File(filePath + "\\QRCode.jpg").toPath();
			} else {
				return null;
			}
		}
		
		//按下Open按钮时执行
		File openFile() {
			File filePath = null;
			JFileChooser openChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg gif png Images", "jpg", "gif", "png");
			    openChooser.setFileFilter(filter);

			int result = openChooser.showOpenDialog(frame);
			if(result == JFileChooser.APPROVE_OPTION) {
				filePath = openChooser.getSelectedFile();
				return filePath;
			} else {
				return null;
			}
			
		}
	}
}