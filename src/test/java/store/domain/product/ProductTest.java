package store.domain.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    @DisplayName("상품을 생성한다")
    void 상품_생성_테스트() {
        Product product = Product.builder()
                .name("콜라")
                .price(1000)
                .quantity(10)
                .build();

        Assertions.assertThat(product.getName()).isEqualTo("콜라");
        Assertions.assertThat(product.getPrice()).isEqualTo(1000);
    }

    @Test
    @DisplayName("상품 이름이 null이면 예외가 발생한다")
    void 상품_이름_null_예외가_발생한다() {
        Assertions.assertThatThrownBy(() -> Product.builder()
                        .name(null)
                        .price(1000)
                        .quantity(10)
                        .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("상품 이름이 빈 문자열이면 예외가 발생한다")
    void 상품_이름_빈_문자열_예외가_발생한다() {
        Assertions.assertThatThrownBy(() -> Product.builder()
                        .name("")
                        .price(1000)
                        .quantity(10)
                        .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("상품 가격이 음수면 예외가 발생한다")
    void 상품_가격_음수면_예외가_발생한다() {
        Assertions.assertThatThrownBy(() -> Product.builder()
                        .name("cola")
                        .price(-1000)
                        .quantity(10)
                        .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

}
