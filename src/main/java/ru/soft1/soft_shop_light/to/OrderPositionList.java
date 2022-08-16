package ru.soft1.soft_shop_light.to;

import lombok.Data;
import ru.soft1.soft_shop_light.model.OrderPosition;
import ru.soft1.soft_shop_light.model.Product;
import ru.soft1.soft_shop_light.util.exception.NotFoundException;

import javax.validation.constraints.NotNull;
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

    public void addProduct(Product product) {
        if (product == null) {
        }
        OrderPosition orderPosition = getById(product.getId()).
                orElse(getNewPosition(product)).addOne();
        if (orderPosition.getValue() == 1) {
            positions.add(orderPosition);
        }
    }

    public void deleteOne(long productId) {
        OrderPosition orderPosition = getById(productId).
                orElseThrow(() -> {throw new NotFoundException("Order Position not found");});
        orderPosition.setValue(orderPosition.getValue() - 1);
        if (orderPosition.getValue() <= 0) {
            removePosition(productId);
        }
    }

    public void removePosition(long productId) {
        OrderPosition position = getById(productId)
                .orElseThrow(() -> {throw new NotFoundException("Order Position not found");});
        positions.remove(position);
    }

    public int takeTotalPrice() {
        return positions.stream().map((p)->{return p.getValue()*p.getProductDetails().getPrice();})
                .toList().stream().reduce(0, Integer::sum);
    }

    private OrderPosition getNewPosition(Product product) {
        OrderPosition position = new OrderPosition(product, 0);
        return position;
    }

    public void removeAll() {
        positions = new ArrayList<>();
    }

}
