package store.util.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import store.domain.order.OrderRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OrderRequestParserTest {

    @DisplayName("올바른 형식의 입력을 파싱하여 OrderRequest 리스트 생성")
    @ParameterizedTest
    @CsvSource({
            "'[상품1-3]', '상품1', 3",
            "'[상품2-2],[상품3-5]', '상품2', 2",
            "'[상품4-10],[상품5-1]', '상품4', 10"
    })
    void 올바른_형식의_입력_파싱(String input, String expectedProductName, int expectedQuantity) {
        List<OrderRequest> orderRequests = OrderRequestParser.parse(input);

        assertThat(orderRequests).isNotEmpty();
        assertThat(orderRequests.getFirst().getProductName()).isEqualTo(expectedProductName);
        assertThat(orderRequests.getFirst().getQuantity()).isEqualTo(expectedQuantity);
    }

    @DisplayName("입력이 null 또는 빈 문자열일 경우 예외 발생")
    @ParameterizedTest
    @NullAndEmptySource
    void 입력_null_또는_빈문자열(String input) {
        assertThatThrownBy(() -> OrderRequestParser.parse(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("올바르지 않은 형식의 입력일 경우 예외 발생")
    @ParameterizedTest
    @CsvSource({
            "'상품1-3, 상품2-2'",
            "'[상품3-3] [상품4-1]'",
            "'상품5-2'",
            "'[상품6-]'",
            "'[상품7-abc]'"
    })
    void 올바르지_않은_형식의_입력(String input) {
        assertThatThrownBy(() -> OrderRequestParser.parse(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("상품명과 수량이 제대로 구분되지 않을 경우 예외 발생")
    @ParameterizedTest
    @CsvSource({
            "'[상품1-]', '[ERROR]'",
            "'[상품2]', '[ERROR]'"
    })
    void 잘못된_상품명_수량_구분(String input, String expectedErrorMessage) {
        assertThatThrownBy(() -> OrderRequestParser.parse(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(expectedErrorMessage);
    }

}
