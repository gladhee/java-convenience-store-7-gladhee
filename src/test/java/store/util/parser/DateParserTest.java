package store.util.parser;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

class DateParserTest {

    @DisplayName("올바른 형식의 날짜 문자열을 LocalDate로 변환한다")
    @ParameterizedTest
    @ValueSource(strings = {
            "2024-11-29",
            "2024-01-01",
            "2024-12-31"
    })
    void 날짜_파싱_테스트(String dateStr) {
        LocalDate result = DateParser.parse(dateStr);

        Assertions.assertThat(result).isEqualTo(LocalDate.parse(dateStr));
    }

    @DisplayName("null 값이 입력되면 예외가 발생한다")
    @Test
    void Null_입력시_예외가_발생한다() {
        Assertions.assertThatThrownBy(() -> DateParser.parse(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("빈 문자열이 입력되면 예외가 발생한다")
    @Test
    void 빈_문자열_예외가_발생한다() {
        Assertions.assertThatThrownBy(() -> DateParser.parse("  "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("잘못된 형식의 날짜가 입력되면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {
            "2024/11/29",
            "2024-13-29",
            "2024-11-32",
            "20241129",
            "2024-11",
            "invalid"
    })
    void 잘못된_날짜_형식_예외가_발생한다(String invalidDate) {
        Assertions.assertThatThrownBy(() -> DateParser.parse(invalidDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

}