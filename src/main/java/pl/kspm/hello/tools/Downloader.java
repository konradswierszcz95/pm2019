package pl.kspm.hello.tools;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Downloader {

    public static void download(String path,String fileName, HttpServletResponse response) {
        response.setHeader("Content-Disposition","attachment: filename="+fileName);
        response.setHeader("Content-Transfer-Encoding","binary");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            FileInputStream fis = new FileInputStream(path);

            int len;
            byte[] buf = new byte[1024];
            while((len=fis.read(buf))>0) {
                bos.write(buf,0,len);
            }
            bos.close();
            response.flushBuffer();
        } catch (Exception ex) {

        }

    }

    public static void upload(String path, String fileName, MultipartFile file) throws IOException {
        File isDir = new File(path);
        if (!isDir.exists()) {
            try {
                isDir.mkdirs();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        Files.write(Paths.get(path + fileName), file.getBytes());
    }
}
