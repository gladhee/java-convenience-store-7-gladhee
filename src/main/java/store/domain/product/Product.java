package store.domain.product;

public class Product {

    private final String name;
    private final int price;

    private Product(String name, int price) {
        validateProduct(name, price);
        this.name = name;
        this.price = price;
    }

    public static Product of(String name, int price) {
        return new Product(name, price);
    }

    // @todo refactor
    private void validateProduct(String name, int price) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 상품명은 빈 값일 수 없습니다.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("[ERROR] 상품 가격은 0보다 커야 합니다.");
        }
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

}
