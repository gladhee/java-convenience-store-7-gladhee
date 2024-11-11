package store.domain.order;

import store.exception.ProductException.EmptyProductNameException;
import store.exception.PromotionException.QuantityShouldBePositiveException;

public class OrderRequest {

    private final String productName;
    private final int quantity;

    private OrderRequest(String productName, int quantity) {
        validateRequest(productName, quantity);
        this.productName = productName;
        this.quantity = quantity;
    }

    public static OrderRequest of(String productName, int quantity) {
        return new OrderRequest(productName, quantity);
    }

    private void validateRequest(String productName, int quantity) {
        if (productName == null || productName.trim().isEmpty()) {
            throw new EmptyProductNameException();
        }
        if (quantity <= 0) {
        throw new QuantityShouldBePositiveException();
        }
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

}