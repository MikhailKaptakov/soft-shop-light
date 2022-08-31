package ru.soft1.soft_shop_light.support;
import ru.soft1.soft_shop_light.model.Article;
import ru.soft1.soft_shop_light.to.ArticlePreview;

import java.util.List;

public class ArticleTestData {
    public static final MatcherFactory.Matcher<Article> ARTICLE_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Article.class);

    public static final MatcherFactory.Matcher<ArticlePreview> ARTICLE_PREVIEW_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(ArticlePreview.class);

    public static final long FIRST_ID = 1;

    private static final Article first = new Article(FIRST_ID,
            "Header", "Preview", "Text", true);
    private static final Article second = new Article(FIRST_ID+1,
            "Header2", "Preview2", "Text2", true);
    private static final Article third = new Article(FIRST_ID+2,
            "Header3", "Preview3", "Text3", false);
    private static final Article newArticle = new Article("NewHeader", "NewPreview",
            "NewText", true);

    public static Article getFirst() {
        return new Article(first);
    }
    public static Article getSecond() {
        return new Article(second);
    }
    public static Article getThird() {
        return new Article(third);
    }
    public static Article getNew() {
        return new Article(newArticle);
    }

    public static List<Article> getAll() {
        return List.of(getFirst(), getSecond(), getThird());
    }

    public static List<Article> getAllAvailable() {
        return List.of(getFirst(), getSecond());
    }
}
