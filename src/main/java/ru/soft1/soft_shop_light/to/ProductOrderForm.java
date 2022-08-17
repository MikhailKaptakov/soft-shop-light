package ru.soft1.soft_shop_light.to;

import lombok.Data;
import ru.soft1.soft_shop_light.util.validation.TelephoneRu;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class ProductOrderForm {

    @Email
    @NotBlank
    @Size(max = 128)
    private String email ="";

    @TelephoneRu
    @NotBlank
    private String telephoneNumber = "";

    @NotBlank
    @Size(min=3, max=50)
    private String firstname = "";

    @NotBlank
    @Size(min=3, max=50)
    private String surname = "";

    @NotBlank
    @Size(min=3, max=50)
    private String secondName = "";

    @Size(max=100)
    private String companyName = "";

    @Size(max=100)
    private String address = "";

    @Size(max=256)
    private String comment = "";

    public void setValues(ProductOrderForm productOrderForm) {
        this.email = productOrderForm.email;
        this.firstname = productOrderForm.firstname;
        this.secondName = productOrderForm.secondName;
        this.surname = productOrderForm.surname;
        this.telephoneNumber = productOrderForm.telephoneNumber;
        this.comment = productOrderForm.comment;
        this.address = productOrderForm.address;
        this.companyName = productOrderForm.companyName;
    }
}
