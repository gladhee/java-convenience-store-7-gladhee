package store.domain.order;

import store.domain.store.StoreProduct;
import store.exception.OrderException.OrderQuantityShouldBePositiveException;

public class OrderLine {

    private final StoreProduct product;
    private final int quantity;

    public OrderLine(StoreProduct product, int quantity) {
        validateQuantity(quantity);
        this.product = product;
        this.quantity = quantity;
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new OrderQuantityShouldBePositiveException();
        }
    }

    public int calculatePrice() {
        return product.calculatePrice(quantity);
    }

    public int calculatePromotionDiscount() {
        return product.calculatePromotionDiscount(quantity);
    }

    public StoreProduct getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

}