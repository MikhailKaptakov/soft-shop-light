package ru.soft1.soft_shop_light.util;

import org.springframework.web.multipart.MultipartFile;
import ru.soft1.soft_shop_light.util.exception.ImageConversionException;

import java.io.IOException;

public class Util {

    public static byte[] toBytes(MultipartFile image) {
        try {
            return image.getBytes();
        } catch (IOException e) {
            throw new ImageConversionException("Не удалось преобразовать изображение");
        }
    }
}
