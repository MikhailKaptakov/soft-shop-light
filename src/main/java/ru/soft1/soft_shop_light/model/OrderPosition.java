package ru.soft1.soft_shop_light.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;
import ru.soft1.soft_shop_light.to.ProductDetails;
import ru.soft1.soft_shop_light.util.converters.ProductDetailsJsonConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "order_position", uniqueConstraints = {@UniqueConstraint(columnNames = {"product_id", "order_id"},
        name = "order_product_id")})
public class OrderPosition extends AbstractEntity{

    @Id
    @SequenceGenerator(name = "order_position_seq", sequenceName = "order_position_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_position_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull
    private Product product;

    @Column(name = "product_value", nullable = false)
    @NotNull
    @Positive
    @Range(max = Byte.MAX_VALUE)
    private int value;

    @Convert(converter = ProductDetailsJsonConverter.class)
    @Column(name = "product_details", nullable = true)
    private ProductDetails productDetails;

    public OrderPosition() {
        this.value = 0;
    }

    public OrderPosition(long id, Product product, int value) {
        super(id);
        this.product = product;
        this.value = value;
        this.productDetails = new ProductDetails(product);
    }

    public OrderPosition(Product product, int value) {
        this.product = product;
        this.value = value;
        this.productDetails = new ProductDetails(product);
    }

    public OrderPosition addOne() {
        value++;
        return this;
    }
}
