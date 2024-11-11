package lk.ijse.crop_monitoring_backend.util;

import java.util.Base64;

public class AppUtil {

    public static String toBase64Img(byte[] bytesImg) {
        return Base64.getEncoder().encodeToString(bytesImg);
    }
}
