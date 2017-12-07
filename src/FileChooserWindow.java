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
	
	//�����ʼ��
	void init() {
		//���������
		frame = new FileFrame("FileChooserTest");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation((1366-500)/2,(768-300)/2);
		
		//��ʼ���������
		JPanel savePanel = new JPanel();
		JPanel openPanel = new JPanel();
		Border border = BorderFactory.createLoweredBevelBorder();
		Border saveBorder = BorderFactory.createTitledBorder(border, "���ɶ�ά��", TitledBorder.LEFT, TitledBorder.ABOVE_TOP);
		Border openBorder = BorderFactory.createTitledBorder(border, "��ȡ��ά��", TitledBorder.LEFT, TitledBorder.ABOVE_TOP);
		
		JLabel saveLabel = new JLabel("�ı���Ϣ:", JLabel.RIGHT);
		JLabel openLabel = new JLabel("�ı���Ϣ:", JLabel.RIGHT);
		saveText = new JTextField(50);
		openText = new JTextField(50);
		openText.setEditable(false);
		JButton saveButton = new JButton("Save");
		JButton openButton = new JButton("Open");
		
		//�ڿ���м������
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
		
		//Ϊ��ť����¼�����
		FileListener fileListener = new FileListener();
		saveButton.addActionListener(fileListener);
		openButton.addActionListener(fileListener);
	}
	
	//��������
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
	
	//��ť������
	class FileListener implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			
			String command = event.getActionCommand();
			
			if(command.equals("Save")) {
				
				String sText = saveText.getText();
				if(sText.equals("")) {
					JOptionPane.showMessageDialog(frame, "δ�����ı�", "����", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				Path sFile = saveFile();
				if(sFile != null) {
					WriteQRCode writeFile = new WriteQRCode(sText, sFile);
					writeFile.write();
					
					Image img = new ImageIcon(sFile.toString()).getImage().getScaledInstance(95, 95, Image.SCALE_DEFAULT);
					ImageIcon icon = new ImageIcon();
					icon.setImage(img);
					JOptionPane.showMessageDialog(frame, "��ά�뱣��·��:\n" + sFile.toString(), "ϵͳ��Ϣ", JOptionPane.PLAIN_MESSAGE, icon);
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
					JOptionPane.showMessageDialog(frame, "û�ж�ά����Ϣ", "ϵͳ��Ϣ", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
		
		//����Save��ťʱִ��
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
		
		//����Open��ťʱִ��
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