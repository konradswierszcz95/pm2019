package pl.kspm.hello.tools;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class QRcodeGenerator {
    public static void generateQRCode(long machineId) throws WriterException, IOException {
        String absoultePath = new File("").getAbsolutePath();
        String code = Pathes.appHost + "machines/"+machineId;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(code, BarcodeFormat.QR_CODE, 200, 200);

        Path path = FileSystems.getDefault().getPath(absoultePath+File.separator+"uploads"+File.separator+"machines"
                +File.separator+"qr"+File.separator+machineId+".png");
        MatrixToImageWriter.writeToPath(bitMatrix,"PNG",path);
    }
}
