package ru.soft1.soft_shop_light.web.controllers;

import com.sun.istack.FinalArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.soft1.soft_shop_light.model.OrderPosition;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.model.ProductOrder;
import ru.soft1.soft_shop_light.repository.ProductRepository;
import ru.soft1.soft_shop_light.util.validation.ValidationUtil;

import javax.swing.text.Position;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


@Slf4j
@Controller
@RequestMapping("/products")
@SessionAttributes(ProductController.currentOrderAttribute)
public class ProductController {

    public static final String currentOrderAttribute = "currentOrder";

    @Autowired
    private ProductRepository productRepository;

    @ModelAttribute("currentOrder")
    public ProductOrder createNewOrder() {
        return new ProductOrder();
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("products", productRepository.getAllOrderById());
        return "products";
    }

    @PostMapping("/{id}")
    public String addToOrder(Model model, @PathVariable("id") long id) {
        ProductOrder order = (ProductOrder) model.getAttribute(ProductController.currentOrderAttribute);
        //check not null order
        Product product = ValidationUtil.checkNotFoundWithId(productRepository.get(id), id);
        order.addProduct(product);
        return "forward:/products";
    }



    /*@PostMapping("/register")
    public String saveRegister(@Validated(View.Web.class) UserTo userTo, BindingResult result, SessionStatus status, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("register", true);
            return "profile";
        }
        super.create(userTo);
        status.setComplete();
        return "redirect:/login?message=app.registered&username=" + userTo.getEmail();
    }*/

}
