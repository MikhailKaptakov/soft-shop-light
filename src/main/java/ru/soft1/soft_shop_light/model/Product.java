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
    @NoHtml
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

    @Column(name="available", nullable = false, columnDefinition = "boolean default true")
    private boolean available;

    public Product() {
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
                   boolean available) {
        this(name, vendor, country, licenseTime, description, price,
                deliveryTimeInDays, ndsInclude, requiredTechnicalSupport, available);
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
                   boolean available) {
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
                deliveryTimeInDays, ndsInclude, requiredTechnicalSupport, true);
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
                product.available);
    }
}
