package store.domain.promotion;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import store.util.parser.DateParser;

public class PromotionProductTest {

    private PromotionRule rule;

    @BeforeEach
    void setUp() {
        rule = PromotionRule.builder()
                .requiredQuantity(2)
                .freeQuantity(1)
                .startDate("2021-01-01")
                .endDate("2021-12-31")
                .build();
    }

    @DisplayName("정상적인 값으로 생성할 수 있다")
    @ParameterizedTest
    @CsvSource({"콜라, 2", "사이다, 3"})
    void 프로모션_상품_생성_테스트(String name, int quantity) {
        PromotionProduct product = PromotionProduct.builder()
                .name(name)
                .quantity(quantity)
                .rule(rule)
                .build();

        Assertions.assertThat(product).isInstanceOf(PromotionProduct.class);
    }

    @DisplayName("수량 감소 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void 수량_감소_테스트(int quantity) {
        PromotionProduct product = PromotionProduct.builder()
                .name("콜라")
                .quantity(5)
                .rule(rule)
                .build();

        int result = product.decrease(quantity);

        Assertions.assertThat(result).isEqualTo(5 - quantity);
    }

    @DisplayName("감소할 수량보다 현재 수량이 적으면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {6, 7, 8})
    void 감소할_수량보다_현재_수량이_적으면_예외가_발생한다(int quantity) {
        PromotionProduct product = PromotionProduct.builder()
                .name("콜라")
                .quantity(5)
                .rule(rule)
                .build();

        Assertions.assertThatThrownBy(() -> product.decrease(quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("감소할 수량이 음수이면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3})
    void 감소할_수량이_음수이면_예외가_발생한다(int quantity) {
        PromotionProduct product = PromotionProduct.builder()
                .name("콜라")
                .quantity(5)
                .rule(rule)
                .build();

        Assertions.assertThatThrownBy(() -> product.decrease(quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("상품 이름이 null이면 예외가 발생한다")
    @ParameterizedTest
    @CsvSource({", 2", " , 3"})
    void 상품_이름_null_예외가_발생한다(String name, int quantity) {
        Assertions.assertThatThrownBy(() -> PromotionProduct.builder()
                        .name(name)
                        .quantity(quantity)
                        .rule(rule)
                        .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("2+1 행사 상품의 수량이 2개면 구매가 안 된다")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void 행사_상품의_수량이_2개면_구매가_안_된다(int quantity) {
        PromotionProduct product = PromotionProduct.builder()
                .name("콜라")
                .quantity(quantity)
                .rule(rule)
                .build();

        boolean result = product.attemptPromotionPurchase(2, DateParser.parse("2021-01-01"));

        Assertions.assertThat(result).isFalse();
    }

}
