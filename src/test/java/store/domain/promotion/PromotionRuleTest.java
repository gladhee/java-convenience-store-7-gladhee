package store.domain.promotion;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PromotionRuleTest {

    @Test
    @DisplayName("정상적인 값으로 생성할 수 있다")
    void 프로모션_생성_테스트() {
        PromotionRule rule = PromotionRule.builder()
                .requiredQuantity(3)
                .freeQuantity(1)
                .startDate("2024-01-01")
                .endDate("2024-12-31")
                .build();

        Assertions.assertThat(rule).isInstanceOf(PromotionRule.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    @DisplayName("필요 수량이 0 이하면 예외가 발생한다")
    void 필요_수량_0_이하면_예외가_발생한다(int invalidQuantity) {
        Assertions.assertThatThrownBy(() -> PromotionRule.builder()
                        .requiredQuantity(invalidQuantity)
                        .freeQuantity(1)
                        .startDate("2024-01-01")
                        .endDate("2024-12-31")
                        .build()
                )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 필요 수량은 0보다 커야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    @DisplayName("증정 수량이 0 이하면 예외가 발생한다")
    void 증정_수량_0_이하면_예외가_발생한다(int invalidQuantity) {
        Assertions.assertThatThrownBy(() -> PromotionRule.builder()
                        .requiredQuantity(3)
                        .freeQuantity(invalidQuantity)
                        .startDate("2024-01-01")
                        .endDate("2024-12-31")
                        .build()
                )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 증정 수량은 0보다 커야 합니다.");
    }

    @Test
    @DisplayName("시작일이 종료일보다 늦으면 예외가 발생한다")
    void 시작일이_종료일보다_늦으면_예외가_발생한다() {
        Assertions.assertThatThrownBy(() -> PromotionRule.builder()
                        .requiredQuantity(3)
                        .freeQuantity(1)
                        .startDate("2024-12-31")
                        .endDate("2024-01-01")
                        .build()
                )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 시작일이 종료일보다 늦을 수 없습니다.");
    }

    @Test
    @DisplayName("프로모션 기간 내의 날짜이면 true를 반환한다")
    void 프로모션_기간_내의_날짜면_true_반환_테스트() {
        PromotionRule promotion = PromotionRule.builder()
                .requiredQuantity(3)
                .freeQuantity(1)
                .startDate("2024-01-01")
                .endDate("2024-12-31")
                .build();

        Assertions.assertThat(promotion.isApplicable(LocalDate.parse("2024-06-15"))).isTrue();
    }

    @Test
    @DisplayName("프로모션 시작일 이전이면 false를 반환한다")
    void 프로모션_시작일_이전이면_false_반환_테스트() {
        PromotionRule promotion = PromotionRule.builder()
                .requiredQuantity(3)
                .freeQuantity(1)
                .startDate("2024-01-01")
                .endDate("2024-12-31")
                .build();

        Assertions.assertThat(promotion.isApplicable(LocalDate.parse("2023-12-31"))).isFalse();
    }

    @Test
    @DisplayName("프로모션 종료일 이후면 false를 반환한다")
    void 프로모션_종료일_이후면_false_반환_테스트() {
        PromotionRule promotion = PromotionRule.builder()
                .requiredQuantity(3)
                .freeQuantity(1)
                .startDate("2024-01-01")
                .endDate("2024-12-31")
                .build();

        Assertions.assertThat(promotion.isApplicable(LocalDate.parse("2025-01-01"))).isFalse();
    }

}
