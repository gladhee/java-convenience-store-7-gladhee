package store.service;

import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.order.Order;
import store.domain.order.OrderLine;
import store.domain.order.OrderRequest;
import store.domain.order.Receipt;
import store.domain.product.Product;
import store.domain.promotion.Promotion;
import store.domain.store.StoreProduct;
import store.repository.impl.StoreProductsRepositoryImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderServiceTest {

    private StoreProductsRepositoryImpl storeProductsRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        storeProductsRepository = new StoreProductsRepositoryImpl();
        Product product1 = Product.builder()
                .name("상품1")
                .price("1000")
                .build();
        Promotion promotion = Promotion.builder()
                .name("프로모션")
                .requiredQuantity("2")
                .freeQuantity("1")
                .startDate("2021-08-01")
                .endDate("2021-08-31")
                .build();
        StoreProduct storeProductWithPromotion = StoreProduct.builder()
                .product(product1)
                .promotion(promotion)
                .normalQuantity("10")
                .promotionQuantity("5")
                .build();
        storeProductsRepository.save("상품1", storeProductWithPromotion);

        Product product2 = Product.builder()
                .name("상품2")
                .price("2000")
                .build();
        StoreProduct storeProductWithoutPromotion = StoreProduct.builder()
                .product(product2)
                .normalQuantity("10")
                .build();
        storeProductsRepository.save("상품2", storeProductWithoutPromotion);

        orderService = new OrderService(storeProductsRepository);
    }

    @Test
    @DisplayName("유효한 주문 요청을 통해 영수증을 생성합니다")
    void 유효한_주문_생성() {
        List<OrderRequest> orderRequests = List.of(OrderRequest.of("상품1", 3));

        Receipt receipt = createOrderWithoutMembership(orderRequests);

        assertThat(receipt).isNotNull();
        assertThat(receipt.getTotalPrice()).isGreaterThan(0);
    }

    @Test
    @DisplayName("프로모션이 적용되지 않은 경우에도 정상적으로 주문이 생성됩니다")
    void 프로모션_미적용_주문() {
        List<OrderRequest> orderRequests = List.of(OrderRequest.of("상품2", 2));

        Receipt receipt = createOrderWithoutMembership(orderRequests);

        assertThat(receipt).isNotNull();
        assertThat(receipt.getTotalPrice()).isEqualTo(4000); // 상품2 가격 2000 * 2
    }

    // 회원 할인을 사용하지 않는 주문 생성 메서드
    private Receipt createOrderWithoutMembership(List<OrderRequest> orderRequests) {
        List<OrderLine> orderLines = orderRequests.stream()
                .map(orderRequest -> {
                    StoreProduct product = storeProductsRepository.findByName(orderRequest.getProductName());
                    validateStock(product, orderRequest.getQuantity());
                    return new OrderLine(product, orderRequest.getQuantity());
                })
                .collect(Collectors.toList());

        Order order = Order.create(orderLines, false); // useMembership을 false로 설정
        updateStock(order);

        return order.createReceipt();
    }

    private void validateStock(StoreProduct product, int quantity) {
        if (product.isOutOfStock(quantity)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %s의 재고가 부족합니다.", product.getName()));
        }
    }

    private void updateStock(Order order) {
        order.getOrderLines().forEach(orderLine -> {
            StoreProduct product = orderLine.getProduct();
            product.decreaseStock(orderLine.getQuantity());
            storeProductsRepository.save(product.getName(), product);
        });
    }

}
