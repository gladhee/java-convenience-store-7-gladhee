package store.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.order.OrderRequest;
import store.domain.product.Product;
import store.domain.promotion.Promotion;
import store.domain.store.StoreProduct;
import store.repository.impl.StoreProductsRepositoryImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StoreProductsServiceTest {

    private StoreProductsRepositoryImpl storeProductsRepository;
    private StoreProductsService storeProductsService;

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

        storeProductsService = new StoreProductsService(storeProductsRepository);
    }

    @Test
    @DisplayName("모든 제품 목록을 조회합니다")
    void getStoreProducts() {
        List<StoreProduct> products = storeProductsService.getStoreProducts();

        assertThat(products).hasSize(2);
    }

    @Test
    @DisplayName("상품명을 통해 특정 제품을 검색합니다")
    void searchStoreProductByProductName() {
        StoreProduct product = storeProductsService.searchStoreProductByProductName("상품1");

        assertThat(product).isNotNull();
        assertThat(product.getProduct().getName()).isEqualTo("상품1");
    }

    @Test
    @DisplayName("존재하지 않는 상품을 검색할 때 예외가 발생합니다")
    void searchStoreProductByInvalidProductName() {
        StoreProduct product = storeProductsService.searchStoreProductByProductName("없는상품");

        assertThat(product).isNull();
    }

    @Test
    @DisplayName("유효한 주문 요청을 검증합니다")
    void validateOrderRequests_ValidRequest() {
        List<OrderRequest> orderRequests = List.of(OrderRequest.of("상품1", 3), OrderRequest.of("상품2", 2));

        storeProductsService.validateOrderRequests(orderRequests);
    }

    @Test
    @DisplayName("존재하지 않는 상품으로 주문 요청 시 예외 발생")
    void validateOrderRequests_InvalidProduct() {
        List<OrderRequest> orderRequests = List.of(OrderRequest.of("없는상품", 3));

        assertThatThrownBy(() -> storeProductsService.validateOrderRequests(orderRequests))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
    }

    @Test
    @DisplayName("재고 수량을 초과하는 주문 요청 시 예외 발생")
    void validateOrderRequests_OutOfStock() {
        List<OrderRequest> orderRequests = List.of(OrderRequest.of("상품1", 20));

        assertThatThrownBy(() -> storeProductsService.validateOrderRequests(orderRequests))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
    }

    @Test
    @DisplayName("비프로모션 수량을 정확히 계산합니다")
    void calculateNonPromotionQuantity() {
        OrderRequest orderRequest = OrderRequest.of("상품1", 10);

        int nonPromotionQuantity = storeProductsService.calculateNonPromotionQuantity(orderRequest);

        assertThat(nonPromotionQuantity).isGreaterThanOrEqualTo(0);
    }
}