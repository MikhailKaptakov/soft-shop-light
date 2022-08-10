package ru.soft1.soft_shop_light.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import ru.soft1.soft_shop_light.util.validation.TelephoneRu;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "product_order_no_authority")
public class ProductOrder extends AbstractEntity {

    @Id
    @SequenceGenerator(name = "product_order_seq", sequenceName = "product_order_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_order_seq")
    private Long id;

    @Column(name = "email", nullable = false)
    @Email
    @NotBlank
    @Size(max = 128)
    private String email;

    @Column(name = "telephone_number", nullable = false)
    @TelephoneRu
    @NotBlank
    private String telephoneNumber;

    @Column(name = "firstname", nullable = false)
    @NotBlank
    @Size(min=3, max=50)
    private String firstname;

    @Column(name = "surname", nullable = false)
    @NotBlank
    @Size(min=3, max=50)
    private String surname;

    @Column(name = "second_name", nullable = false)
    @NotBlank
    @Size(min=3, max=50)
    private String secondName;

    @Column(name = "company_name")
    @Size(min=3, max=100)
    private String companyName;

    @Column(name = "address", nullable = false)
    @Size(min=3, max=100)
    private String address;

    @Column(name = "comment")
    @Size(min=3, max=256)
    private String comment;

    @Column(name = "order_date_time", nullable = false, columnDefinition = "timestamp default current_timestamp", updatable = false)
    @NotNull
    private LocalDateTime orderDateTime;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    private List<OrderPosition> positions = new ArrayList<>();

    public ProductOrder() {
    }

    public ProductOrder(long id,
                        String email,
                        String telephoneNumber,
                        String firstname,
                        String surname,
                        String secondName,
                        String companyName,
                        String address,
                        String comment,
                        LocalDateTime orderDateTime,
                        List<OrderPosition> positions) {
        super(id);
        this.email = email;
        this.telephoneNumber = telephoneNumber;
        this.firstname = firstname;
        this.surname = surname;
        this.secondName = secondName;
        this.companyName = companyName;
        this.address = address;
        this.comment = comment;
        this.orderDateTime = orderDateTime;
        this.positions = positions;
    }

    public int takeTotalPrice() {
        return positions.stream().map((p)->{return p.getValue()*p.getProductDetails().getPrice();})
                .toList().stream().reduce(0, Integer::sum);
    }
}
