package ru.soft1.soft_shop_light.to;

import lombok.Data;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.web.json.JsonUtil;

import static java.lang.System.lineSeparator;

@Data
public class ProductDetails {

    private long product_id;
    private String name;
    private String vendor;
    private String country;
    private String licenseTime;
    private int price;
    private int deliveryTimeInDays;
    private boolean ndsInclude;
    private boolean requiredTechnicalSupport;

    public ProductDetails(long product_id,
                          String name,
                          String vendor,
                          String country,
                          String licenseTime,
                          int price,
                          int deliveryTimeInDays,
                          boolean ndsInclude,
                          boolean requiredTechnicalSupport) {
        this.product_id = product_id;
        this.name = name;
        this.vendor = vendor;
        this.country = country;
        this.licenseTime = licenseTime;
        this.price = price;
        this.deliveryTimeInDays = deliveryTimeInDays;
        this.ndsInclude = ndsInclude;
        this.requiredTechnicalSupport = requiredTechnicalSupport;
    }

    public ProductDetails(Product product) {
        this.product_id = product.getId();
        this.name = product.getName();
        this.vendor = product.getVendor();
        this.country = product.getCountry();
        this.licenseTime = product.getLicenseTime();
        this.price = product.getPrice();
        this.deliveryTimeInDays = product.getDeliveryTimeInDays();
        this.ndsInclude = product.isNdsInclude();
        this.requiredTechnicalSupport = product.isRequiredTechnicalSupport();
    }

    public String getMessage() {
        return getSeparatedString("Product identifier: " + product_id) +
                getSeparatedString("Name: " + name) +
                getSeparatedString("Vendor: " + vendor) +
                getSeparatedString("Country: " + country) +
                getSeparatedString("License time: " + licenseTime) +
                getSeparatedString("Expected delivery time: " + deliveryTimeInDays + " days") +
                isNdsIncludedAnswer() +
                isRequiredTechnicalSupport() +
                getSeparatedString("Price: " + price);
    }

    public String toJson() {
        return JsonUtil.writeValue(this);
    }

    @Override
    public String toString() {
        return "ProductDetails{" +
                "product_id=" + product_id +
                ", name='" + name + '\'' +
                ", vendor='" + vendor + '\'' +
                ", country='" + country + '\'' +
                ", licenseTime='" + licenseTime + '\'' +
                ", price=" + price +
                ", deliveryTimeInDays=" + deliveryTimeInDays +
                ", ndsInclude=" + ndsInclude +
                ", requiredTechnicalSupport=" + requiredTechnicalSupport +
                '}';
    }

    private String getSeparatedString(String string) {
        return string + lineSeparator();
    }

    private String isNdsIncludedAnswer() {
        if (ndsInclude) {
            return getSeparatedString("NDS is included");
        } else {
            return getSeparatedString("NDS is not included");
        }
    }

    private String isRequiredTechnicalSupport() {
        if (requiredTechnicalSupport) {
            return getSeparatedString("Technical support included");
        } else {
            return getSeparatedString("Technical support is not provided");
        }
    }



}
