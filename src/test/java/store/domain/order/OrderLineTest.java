package store.domain.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.product.Product;
import store.domain.promotion.Promotion;
import store.domain.store.StoreProduct;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OrderLineTest {

    private StoreProduct storeProductWithPromotion;
    private StoreProduct storeProductWithoutPromotion;

    @BeforeEach
    void setUp() {
        Product product = Product.builder()
                .name("상품명")
                .price("1000")
                .build();
        Promotion promotion = Promotion.builder()
                .name("프로모션명")
                .requiredQuantity("2")
                .freeQuantity("1")
                .startDate("2021-08-01")
                .endDate("2021-08-31")
                .build();
        storeProductWithPromotion = StoreProduct.builder()
                .product(product)
                .promotion(promotion)
                .normalQuantity("10")
                .promotionQuantity("5")
                .build();

        storeProductWithoutPromotion = StoreProduct.builder()
                .product(product)
                .normalQuantity("10")
                .build();
    }

    @DisplayName("OrderLine 생성 성공")
    @Test
    void 주문항목_생성_성공() {
        OrderLine orderLine = new OrderLine(storeProductWithPromotion, 3);

        assertThat(orderLine.getProduct()).isEqualTo(storeProductWithPromotion);
        assertThat(orderLine.getQuantity()).isEqualTo(3);
    }

    @DisplayName("수량이 0 이하일 경우 예외 발생")
    @Test
    void 수량_유효성검사() {
        assertThatThrownBy(() -> new OrderLine(storeProductWithPromotion, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 주문 수량은 0보다 커야 합니다.");

        assertThatThrownBy(() -> new OrderLine(storeProductWithPromotion, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 주문 수량은 0보다 커야 합니다.");
    }

    @DisplayName("프로모션이 적용된 제품의 가격 계산")
    @Test
    void 프로모션_적용된_가격_계산() {
        OrderLine orderLine = new OrderLine(storeProductWithPromotion, 3);
        int expectedPrice = storeProductWithPromotion.calculatePrice(3);

        assertThat(orderLine.calculatePrice()).isEqualTo(expectedPrice);
    }

    @DisplayName("프로모션이 적용되지 않은 제품의 가격 계산")
    @Test
    void 프로모션_미적용_가격_계산() {
        OrderLine orderLine = new OrderLine(storeProductWithoutPromotion, 3);
        int expectedPrice = storeProductWithoutPromotion.calculatePrice(3);

        assertThat(orderLine.calculatePrice()).isEqualTo(expectedPrice);
    }

    @DisplayName("프로모션 할인 계산")
    @Test
    void 프로모션_할인_계산() {
        OrderLine orderLine = new OrderLine(storeProductWithPromotion, 3);
        int expectedDiscount = storeProductWithPromotion.calculatePromotionDiscount(3);

        assertThat(orderLine.calculatePromotionDiscount()).isEqualTo(expectedDiscount);
    }

    @DisplayName("프로모션이 없는 제품의 할인 계산")
    @Test
    void 프로모션_없는_할인_계산() {
        OrderLine orderLine = new OrderLine(storeProductWithoutPromotion, 3);
        int expectedDiscount = storeProductWithoutPromotion.calculatePromotionDiscount(3);

        assertThat(orderLine.calculatePromotionDiscount()).isEqualTo(expectedDiscount);
    }

}
