package ru.soft1.soft_shop_light.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelephoneValidator implements ConstraintValidator<TelephoneRu, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        return value == null || value.isBlank() || isValid(value);
    }

    //https://www.regextester.com/99415
    private boolean isValid(String telephoneNumber) {
        String patterns = "^(\\+7|7|8)?" +
                "[\\s\\-]?\\(?[489][0-9]{2}\\)?" +
                "[\\s\\-]?[0-9]{3}" +
                "[\\s\\-]?[0-9]{2}" +
                "[\\s\\-]?[0-9]{2}$";
        Pattern pattern = Pattern.compile(patterns);
        Matcher matcher = pattern.matcher(telephoneNumber);
        return matcher.matches();
    }

}