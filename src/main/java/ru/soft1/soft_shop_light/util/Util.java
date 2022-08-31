package ru.soft1.soft_shop_light.util;

import org.springframework.web.multipart.MultipartFile;
import ru.soft1.soft_shop_light.model.Article;
import ru.soft1.soft_shop_light.to.ArticlePreview;
import ru.soft1.soft_shop_light.util.exception.ImageConversionException;

import java.io.IOException;
import java.util.List;

public class Util {

    public static byte[] toBytes(MultipartFile image) {
        try {
            return image.getBytes();
        } catch (IOException e) {
            throw new ImageConversionException("Не удалось преобразовать изображение");
        }
    }

    public static List<ArticlePreview> toArticlePreviewList(List<Article> articles) {
        return articles.stream().map(ArticlePreview::new).toList();
    }
}
