package ru.soft1.soft_shop_light.to;

import lombok.Data;

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

    @Override
    public String toString() {
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
