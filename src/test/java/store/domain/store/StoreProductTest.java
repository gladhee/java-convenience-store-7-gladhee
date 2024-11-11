package store.domain.store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.product.Product;
import store.domain.promotion.Promotion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class StoreProductTest {

    private StoreProduct storeProduct;
    private Promotion promotion;
    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .name("상품명")
                .price("1000")
                .build();
        promotion = Promotion.builder()
                .name("프로모션명")
                .requiredQuantity("2")
                .freeQuantity("1")
                .startDate("2021-08-01")
                .endDate("2021-08-31")
                .build();
        storeProduct = StoreProduct.builder()
                .product(product)
                .promotion(promotion)
                .normalQuantity("10")
                .promotionQuantity("5")
                .build();
    }

    @DisplayName("재고를 정상적으로 감소시킵니다.")
    @Test
    void 재고감소_정상적인_케이스() {
        storeProduct.decreaseStock(3);

        assertThat(storeProduct.getNormalQuantity()).as("일반 재고는 7이어야 합니다.").isEqualTo(7);
        assertThat(storeProduct.getPromotionQuantity()).as("프로모션 재고는 변경되지 않아야 합니다.").isEqualTo(5);
    }

    @DisplayName("재고가 부족한 경우 예외를 발생시킵니다.")
    @Test
    void 재고부족시_예외발생() {
        assertThatThrownBy(() -> storeProduct.decreaseStock(20))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("[ERROR] 재고가 부족합니다.");
    }

    @DisplayName("일반 재고만 감소하는 경우")
    @Test
    void 일반재고만_감소() {
        storeProduct.decreaseStock(2);

        assertThat(storeProduct.getNormalQuantity()).as("일반 재고는 8이어야 합니다.").isEqualTo(8);
        assertThat(storeProduct.getPromotionQuantity()).as("프로모션 재고는 변경되지 않아야 합니다.").isEqualTo(5);
    }

}
