package store.domain.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.product.Product;
import store.domain.promotion.Promotion;

import java.util.Arrays;
import java.util.List;
import store.domain.store.StoreProduct;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OrderTest {

    private Product product1;
    private Product product2;
    private Promotion promotion;
    private OrderLine orderLine1;
    private OrderLine orderLine2;
    private StoreProduct storeProduct;
    private StoreProduct storeProduct2;

    @BeforeEach
    void setUp() {
        product1 = Product.builder()
                .name("상품1")
                .price("1000")
                .build();
        product2 = Product.builder()
                .name("상품2")
                .price("2000")
                .build();
        promotion = Promotion.builder()
                .name("프로모션")
                .requiredQuantity("2")
                .freeQuantity("1")
                .startDate("2021-08-01")
                .endDate("2021-08-31")
                .build();

        storeProduct = StoreProduct.builder()
                .product(product1)
                .promotion(promotion)
                .normalQuantity("10")
                .promotionQuantity("5")
                .build();

        storeProduct2 = StoreProduct.builder()
                .product(product2)
                .promotion(promotion)
                .normalQuantity("10")
                .promotionQuantity("5")
                .build();

        orderLine1 = new OrderLine(storeProduct, 3); // 총 가격: 3000 - 프로모션 할인
        orderLine2 = new OrderLine(storeProduct2, 2); // 총 가격: 4000
    }

    @DisplayName("주문 생성 성공")
    @Test
    void 주문_생성_성공() {
        List<OrderLine> orderLines = Arrays.asList(orderLine1, orderLine2);
        Order order = Order.create(orderLines, true);

        assertThat(order.getOrderLines()).hasSize(2);
        assertThat(order.getOrderLines()).contains(orderLine1, orderLine2);
    }

    @DisplayName("주문 항목이 없을 경우 예외 발생")
    @Test
    void 주문항목_없음_예외() {
        assertThatThrownBy(() -> Order.create(null, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 주문 항목은 필수입니다.");

        assertThatThrownBy(() -> Order.create(List.of(), true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 주문 항목은 필수입니다.");
    }

    @DisplayName("멤버십 할인을 적용하여 영수증 생성")
    @Test
    void 멤버십할인_적용_영수증생성() {
        List<OrderLine> orderLines = Arrays.asList(orderLine1, orderLine2);
        Order order = Order.create(orderLines, true);

        Receipt receipt = order.createReceipt();

        int totalPrice = orderLine1.calculatePrice() + orderLine2.calculatePrice();
        int promotionDiscount = orderLine1.calculatePromotionDiscount() + orderLine2.calculatePromotionDiscount();
        int membershipDiscount = Math.min((totalPrice - promotionDiscount) * 30 / 100, 8000);

        assertThat(receipt.getTotalPrice()).isEqualTo(totalPrice);
        assertThat(receipt.getPromotionDiscount()).isEqualTo(promotionDiscount);
        assertThat(receipt.getMembershipDiscount()).isEqualTo(membershipDiscount);
    }

    @DisplayName("멤버십 할인을 적용하지 않고 영수증 생성")
    @Test
    void 멤버십할인_미적용_영수증생성() {
        List<OrderLine> orderLines = Arrays.asList(orderLine1, orderLine2);
        Order order = Order.create(orderLines, false);

        Receipt receipt = order.createReceipt();

        int totalPrice = orderLine1.calculatePrice() + orderLine2.calculatePrice();
        int promotionDiscount = orderLine1.calculatePromotionDiscount() + orderLine2.calculatePromotionDiscount();
        int membershipDiscount = 0;

        assertThat(receipt.getTotalPrice()).isEqualTo(totalPrice);
        assertThat(receipt.getPromotionDiscount()).isEqualTo(promotionDiscount);
        assertThat(receipt.getMembershipDiscount()).isEqualTo(membershipDiscount);
    }

}
