package ru.soft1.soft_shop_light.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;
import ru.soft1.soft_shop_light.util.validation.NoHtml;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "product")
public class Product extends AbstractEntity {

    @Id
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank
    @NoHtml
    @Size(min=3, max=50)
    private String name;

    @Column(name = "vendor", nullable = false)
    @NotBlank
    @NoHtml
    @Size(min=3, max=50)
    private String vendor;

    @Column(name = "country")
    @Size(min=3, max=50)
    @NoHtml
    private String country;

    @Column(name = "license_time", nullable = false)
    @NotBlank
    @Size(min=3, max=50)
    @NoHtml
    private String licenseTime;

    @Column(name = "description")
    @Size(max=3000)
    private String description;

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 0, max = Integer.MAX_VALUE)
    private int price;

    @Column(name = "delivery_time_in_days", nullable = false)
    @NotNull
    @Range(min = 0, max = Integer.MAX_VALUE)
    private int deliveryTimeInDays;

    @Column(name = "nds_include", nullable = false /*columnDefinition = "bool default true"*/)
    private boolean ndsInclude;

    @Column(name = "req_tech_support", nullable = false/*, columnDefinition = "bool default false"*/)
    private boolean requiredTechnicalSupport;

    @Column(name="available", nullable = false, columnDefinition = "boolean default false")
    private boolean available;

    @Column(name="favorite", nullable = false, columnDefinition = "boolean default false")
    private boolean favorite;

    @Column(name="image", columnDefinition = "BLOB(1M)")
    @Lob
    private byte[] image;

    public Product() {
        this.ndsInclude = true;
        this.requiredTechnicalSupport = false;
        this.available = false;
    }

    public Product(Long id,
                   String name,
                   String vendor,
                   String country,
                   String licenseTime,
                   String description,
                   int price,
                   int deliveryTimeInDays,
                   boolean ndsInclude,
                   boolean requiredTechnicalSupport,
                   boolean available,
                   boolean favorite) {
        this(name, vendor, country, licenseTime, description, price,
                deliveryTimeInDays, ndsInclude, requiredTechnicalSupport, available, favorite);
        this.id = id;
    }

    public Product(String name,
                   String vendor,
                   String country,
                   String licenseTime,
                   String description,
                   int price,
                   int deliveryTimeInDays,
                   boolean ndsInclude,
                   boolean requiredTechnicalSupport,
                   boolean available,
                   boolean favorite) {
        this.name = name;
        this.vendor = vendor;
        this.country = country;
        this.licenseTime = licenseTime;
        this.description = description;
        this.price = price;
        this.deliveryTimeInDays = deliveryTimeInDays;
        this.ndsInclude = ndsInclude;
        this.requiredTechnicalSupport = requiredTechnicalSupport;
        this.available = available;
        this.favorite = favorite;
    }

    public Product(String name,
                   String vendor,
                   String country,
                   String licenseTime,
                   String description,
                   int price,
                   int deliveryTimeInDays,
                   boolean ndsInclude,
                   boolean requiredTechnicalSupport) {
        this(name, vendor, country, licenseTime, description, price,
                deliveryTimeInDays, ndsInclude, requiredTechnicalSupport, true, false);
    }

    public Product(Product product) {
        this(product.id,
                product.name,
                product.vendor,
                product.country,
                product.licenseTime,
                product.description,
                product.price,
                product.deliveryTimeInDays,
                product.ndsInclude,
                product.requiredTechnicalSupport,
                product.available,
                product.favorite);
        this.setImage(product.image);
    }
}