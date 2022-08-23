package ru.soft1.soft_shop_light.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.soft1.soft_shop_light.util.validation.NoHtml;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "article")
public class Article extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "article_seq", sequenceName = "article_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_seq")
    private Long id;

    @Column(name = "header", nullable = false)
    @NotBlank
    @NoHtml
    @Size(min=3, max=50)
    private String header;

    @Column(name = "preview", nullable = false)
    @NotBlank
    @NoHtml
    @Size(min=3, max=150)
    private String preview;

    @Column(name = "text", nullable = false)
    @NotBlank
    @Size(min=3, max=3500)
    private String text;

    @Column(name="logo", columnDefinition = "BLOB(30k)")
    @Lob
    private byte[] logo;

    @Column(name="image", columnDefinition = "BLOB(1M)")
    @Lob
    private byte[] image;

    @Column(name="available", nullable = false, columnDefinition = "boolean default true")
    private boolean available;

    public Article() {
    }

    public Article(Long id,
                   String header,
                   String preview,
                   String text,
                   boolean available) {
        this.id = id;
        this.header = header;
        this.preview = preview;
        this.text = text;
        this.available = available;
    }
    public Article(String header,
                   String preview,
                   String text,
                   boolean available) {
        this.header = header;
        this.preview = preview;
        this.text = text;
        this.available = available;
    }
    public Article(Long id,
                   String header,
                   String preview,
                   String text,
                   byte[] logo,
                   byte[] image,
                   boolean available) {
        this(id, header, preview, text, available);
        setLogo(logo);
        setImage(image);
    }

    public Article(Article article) {
        this(article.id,
                article.header,
                article.preview,
                article.text,
                article.logo,
                article.image,
                article.available);
    }


}



