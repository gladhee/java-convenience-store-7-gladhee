package store.domain.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ProductTest {

    @DisplayName("상품을 생성한다")
    @Test
    void 상품_생성_테스트() {
        Product product = Product.builder()
                .name("콜라")
                .price(1000)
                .quantity(10)
                .build();

        Assertions.assertThat(product.getName()).isEqualTo("콜라");
        Assertions.assertThat(product.getPrice()).isEqualTo(1000);
    }

    @DisplayName("상품 이름이 null이면 예외가 발생한다")
    @Test
    void 상품_이름_null_예외가_발생한다() {
        Assertions.assertThatThrownBy(() -> Product.builder()
                        .name(null)
                        .price(1000)
                        .quantity(10)
                        .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("상품 이름이 빈 문자열이면 예외가 발생한다")
    @Test
    void 상품_이름_빈_문자열_예외가_발생한다() {
        Assertions.assertThatThrownBy(() -> Product.builder()
                        .name("")
                        .price(1000)
                        .quantity(10)
                        .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("상품 가격이 음수면 예외가 발생한다")
    @Test
    void 상품_가격_음수면_예외가_발생한다() {
        Assertions.assertThatThrownBy(() -> Product.builder()
                        .name("cola")
                        .price(-1000)
                        .quantity(10)
                        .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("수량 감소 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void 수량_감소_테스트(int quantity) {
        Product product = Product.builder()
                .name("콜라")
                .price(1000)
                .quantity(5)
                .build();

        int result = product.decrease(quantity);

        Assertions.assertThat(result).isEqualTo(5 - quantity);
    }

    @DisplayName("감소할 수량보다 현재 수량이 적으면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {6, 7, 8})
    void 감소할_수량보다_현재_수량이_적으면_예외가_발생한다(int quantity) {
        Product product = Product.builder()
                .name("콜라")
                .price(1000)
                .quantity(5)
                .build();

        Assertions.assertThatThrownBy(() -> product.decrease(quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("감소할 수량이 음수이면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3})
    void 감소할_수량이_음수이면_예외가_발생한다(int quantity) {
        Product product = Product.builder()
                .name("콜라")
                .price(1000)
                .quantity(5)
                .build();

        Assertions.assertThatThrownBy(() -> product.decrease(quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

}
