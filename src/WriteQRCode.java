import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class WriteQRCode {
	
	Path file = null;
	String text = null;
	public WriteQRCode(String text) {
		this.text = text;
		file = new File("C:/Users/Lenovo/Desktop/TwoDimensionCode.jpg").toPath();
	}
	
	public WriteQRCode(String text, Path file) {
		this.text = text;
		this.file = file;
	}
	@SuppressWarnings({ "unchecked",})
	
	//���ı�����д��ָ���ļ�
	public void write() {
		int width = 400;
		int height = 400;
		String format = "jpg";
		/*
		System.out.println("�������ı����ݣ�");
		Scanner in = new Scanner(System.in);
		content = in.nextLine();
		*/
		//�����ά�����
		@SuppressWarnings("rawtypes")
		HashMap hints = new HashMap();
		hints.put(EncodeHintType.CHARACTER_SET, "GBK");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		hints.put(EncodeHintType.MARGIN, 1);
		
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
			//Path file = new File("C:/Users/Lenovo/Desktop/TwoDimensionCode.jpg").toPath();
			MatrixToImageWriter.writeToPath(bitMatrix, format, file);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println("�ѳɹ����ɶ�ά��");
	}
}