package store.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OrderRequestTest {

    @DisplayName("OrderRequest 생성 성공")
    @Test
    void 주문요청_생성_성공() {
        OrderRequest orderRequest = OrderRequest.of("상품명", 5);

        assertThat(orderRequest.getProductName()).isEqualTo("상품명");
        assertThat(orderRequest.getQuantity()).isEqualTo(5);
    }

    @DisplayName("상품명이 null이거나 비어있을 경우 예외 발생")
    @Test
    void 상품명_유효성검사() {
        assertThatThrownBy(() -> OrderRequest.of(null, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");

        assertThatThrownBy(() -> OrderRequest.of(" ", 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("수량이 0 이하일 경우 예외 발생")
    @Test
    void 수량_유효성검사() {
        assertThatThrownBy(() -> OrderRequest.of("상품명", 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");

        assertThatThrownBy(() -> OrderRequest.of("상품명", -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

}
