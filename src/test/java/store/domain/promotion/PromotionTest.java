package store.domain.promotion;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class PromotionTest {

    @DisplayName("정상적인 값으로 생성할 수 있다")
    @Test
    void 프로모션_상품_생성_테스트() {
        Promotion product = Promotion.builder()
                .name("탄산2+1")
                .requiredQuantity("2")
                .freeQuantity("1")
                .startDate("2021-06-01")
                .endDate("2021-06-30")
                .build();

        Assertions.assertThat(product).isInstanceOf(Promotion.class);
    }

    @DisplayName("프로모션명이 올바르지 않으면 예외를 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @NullSource
    void 프로모션명이_올바르지_않으면_예외를_발생한다(String name) {
        Assertions.assertThatThrownBy(() -> Promotion.builder()
                        .name(name)
                        .requiredQuantity("2")
                        .freeQuantity("1")
                        .startDate("2021-06-01")
                        .endDate("2021-06-30")
                        .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("증정 및 필수 수량은 0보다 커야 한다")
    @ParameterizedTest
    @CsvSource(value = {"0,0", "0,1", "1,0"})
    void 증정_및_필수_수량은_0보다_커야_한다(String requiredQuantity, String freeQuantity) {
        Assertions.assertThatThrownBy(() -> Promotion.builder()
                        .name("탄산2+1")
                        .requiredQuantity(requiredQuantity)
                        .freeQuantity(freeQuantity)
                        .startDate("2021-06-01")
                        .endDate("2021-06-30")
                        .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("종료일이 시작일보다 빠르면 예외를 발생한다")
    @ParameterizedTest
    @CsvSource(value = {"2021-06-01,2021-05-31", "2021-06-02,2021-06-01"})
    void 종료일이_시작일보다_빠르면_예외를_발생한다(String startDate, String endDate) {
        Assertions.assertThatThrownBy(() -> Promotion.builder()
                        .name("탄산2+1")
                        .requiredQuantity("2")
                        .freeQuantity("1")
                        .startDate(startDate)
                        .endDate(endDate)
                        .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("현재 시간이 프로모션 기간에 포함되면 true를 반환한다")
    @Test
    void 현재_시간이_프로모션_기간에_포함되면_true를_반환한다() {
        Promotion product = Promotion.builder()
                .name("탄산2+1")
                .requiredQuantity("2")
                .freeQuantity("1")
                .startDate("2024-11-01")
                .endDate("2025-12-30")
                .build();

        boolean result = product.isApplicable();

        Assertions.assertThat(result).isTrue();
    }

}
