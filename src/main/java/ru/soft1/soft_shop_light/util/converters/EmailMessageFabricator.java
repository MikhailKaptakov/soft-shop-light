package ru.soft1.soft_shop_light.util.converters;

import ru.soft1.soft_shop_light.model.OrderPosition;
import ru.soft1.soft_shop_light.model.ProductOrder;
import ru.soft1.soft_shop_light.to.ProductDetails;

import static java.lang.System.lineSeparator;

public class EmailMessageFabricator {

    private final static String separator = lineSeparator();
    private final static String whiteSpace = "  ";
    private final static String emptyString = "";

    public static String getFullOrder(ProductOrder productOrder) {
        StringBuilder builder = new StringBuilder();
        builder.append(getOrderHeader(productOrder));
        for (OrderPosition orderPosition : productOrder.getPositions()) {
            builder.append(lineSeparator());
            builder.append(getOrderPositionMessage(orderPosition));
        }
        return builder.toString();
    }

    public static String getOrderHeader(ProductOrder productOrder) {
        return getSeparatedString("Order identifier:", "" + productOrder.getId()) +
                getSeparatedString("Email:", productOrder.getEmail()) +
                getSeparatedString("Tel.:", productOrder.getTelephoneNumber()) +
                getSeparatedString("Firstname:", productOrder.getFirstname()) +
                getSeparatedString("Surname:", productOrder.getSurname()) +
                getSeparatedString("Second name:", productOrder.getSecondName()) +
                getSeparatedString("Company:", productOrder.getCompanyName()) +
                getSeparatedString("Address:", productOrder.getAddress()) +
                getSeparatedString("Comment:", productOrder.getComment()) +
                getSeparatedString("Order date:", productOrder.getOrderDateTime().toString()) +
                getSeparatedString("Total Price:", productOrder.takeTotalPrice() +"");
    }

    private static String getOrderPositionMessage(OrderPosition orderPosition) {
        ProductDetails productDetails = orderPosition.getProductDetails();
        return getSeparatedString("Product identifier:", "" + productDetails.getProduct_id()) +
                getSeparatedString("Name:", productDetails.getName()) +
                getSeparatedString("Vendor:", productDetails.getVendor()) +
                getSeparatedString("Country:", productDetails.getCountry()) +
                getSeparatedString("License time:", productDetails.getLicenseTime()) +
                getSeparatedString("Expected delivery time:",productDetails.getDeliveryTimeInDays() + " days") +
                getSeparatedStringFromCondition("NDS is included", "NDS is not included",
                        productDetails.isNdsInclude()) +
                getSeparatedStringFromCondition("Technical support included",
                        "Technical support is not provided",
                        productDetails.isRequiredTechnicalSupport()) +
                getSeparatedString("Price:", "" + productDetails.getPrice()) +
                getSeparatedString("Count:", orderPosition.getValue() + "") +
                getSeparatedString("Total price:", orderPosition.getValue()+productDetails.getPrice() + "");
    }

    private static String getSeparatedString(String title, String fieldValue) {
        if (fieldValue == null || fieldValue.isBlank()) {
            return emptyString;
        }
        return title + whiteSpace + fieldValue + separator;
    }

    private static String getSeparatedStringFromCondition(String trueString, String falseString, boolean condition) {
        String out = condition?trueString:falseString;
        return getSeparatedString(emptyString, out);
    }
}