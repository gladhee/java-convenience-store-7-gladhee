package store.domain.order;

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
            throw new IllegalArgumentException("[ERROR] 상품명은 필수입니다.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("[ERROR] 수량은 0보다 커야 합니다.");
        }
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }
}