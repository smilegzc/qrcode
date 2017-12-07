import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class ReadQRCode {
	File file = null;
	
	public ReadQRCode(File file) {
		this.file = file;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String read() {
		
		try {
			//String filePath = null;
			/*
			System.out.println("请输入文件路径");
			Scanner in = new Scanner(System.in);
			filePath = in.nextLine();
			*/
			MultiFormatReader formatReader = new MultiFormatReader();
			BufferedImage image = ImageIO.read(file);
			
			BufferedImageLuminanceSource bils = new BufferedImageLuminanceSource(image);
			BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(bils));
			
			HashMap hints = new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET, "GBK");
			
			Result result = formatReader.decode(binaryBitmap, hints);
			return result.getText();
		} catch (IOException e) {
			return "error";
		} catch(NotFoundException e2) {
			e2.printStackTrace();
			return "error";
		}
	}
}