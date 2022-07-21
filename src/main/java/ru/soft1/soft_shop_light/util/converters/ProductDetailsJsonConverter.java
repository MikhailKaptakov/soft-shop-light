package ru.soft1.soft_shop_light.util.converters;

import ru.soft1.soft_shop_light.to.ProductDetails;
import ru.soft1.soft_shop_light.web.json.JsonUtil;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ProductDetailsJsonConverter implements AttributeConverter<ProductDetails, String> {
    @Override
    public String convertToDatabaseColumn(ProductDetails productDetails) {
        return productDetails.toJson();
    }

    @Override
    public ProductDetails convertToEntityAttribute(String s) {
        return JsonUtil.readValue(s, ProductDetails.class);
    }
}
