package ru.soft1.soft_shop_light.to;

import lombok.Data;
import ru.soft1.soft_shop_light.model.Article;

@Data
public class ArticlePreview {

    private Long id;

    private String header;

    private String preview;

    private byte[] logo;

    public ArticlePreview() {
    }

    public ArticlePreview(Long id, String header, String preview, byte[] logo) {
        this.id = id;
        this.header = header;
        this.preview = preview;
        this.logo = logo;
    }

    public ArticlePreview(Article article) {
        this(article.getId(), article.getHeader(), article.getPreview(), article.getLogo());
    }
}
