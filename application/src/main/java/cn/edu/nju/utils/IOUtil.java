package cn.edu.nju.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jiayiwu on 17/11/12.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public class IOUtil {

    public static ByteArrayOutputStream toByteArrayOutputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, len);
        }
        result.flush();
        return result;
    }
}
