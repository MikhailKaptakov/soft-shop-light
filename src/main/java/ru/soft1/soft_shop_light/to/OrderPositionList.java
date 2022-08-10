package ru.soft1.soft_shop_light.to;

import lombok.Data;
import ru.soft1.soft_shop_light.model.OrderPosition;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.util.exception.NotFoundException;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class OrderPositionList {
    public static final OrderPosition mockOrderPosition = new OrderPosition();

    private List<OrderPosition> positions = new ArrayList<>();

    public boolean isIncluded(long productId) {
        return getById(productId) == null;
    }

    public Optional<OrderPosition> getById(long productId) {
        return positions.stream()
                .filter((p)-> p.getProduct().getId().equals(productId))
                .findFirst();
    }

    public int getValue(long productId) {
        return getById(productId).orElse(mockOrderPosition).getValue();
    }

    public void setValue(@NotNull Integer value, long productId) {
        if (value <= 0) {
             removePosition(productId);
             return;
        }
        getById(productId)
                .orElseThrow(()->{throw new NotFoundException("Order Position not found");})
                .setValue(value);
    }

    public boolean addProduct(Product product) {
        if (product == null) {
            return false;
        }
        getById(product.getId()).
                orElse(addNewPosition(product))
                .addOne();
        return true;
    }

    public void removePosition(long productId) {
        OrderPosition position= getById(productId)
                .orElseThrow(() -> {throw new NotFoundException("Order Position not found");});
        positions.remove(position);
    }

    public int takeTotalPrice() {
        return positions.stream().map((p)->{return p.getValue()*p.getProductDetails().getPrice();})
                .toList().stream().reduce(0, Integer::sum);
    }

    private OrderPosition addNewPosition(Product product) {
        OrderPosition position = new OrderPosition(product, 0);
        positions.add(position);
        return position;
    }


}
