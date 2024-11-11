package store.util.parser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import store.domain.order.OrderRequest;

public class OrderRequestParser {

    private static final String ERROR_INVALID_INPUT = "[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.";
    private static final String ERROR_INVALID_FORMAT = "[ERROR] 올바르지 않은 형식으로 입력했습니다.";

    private static final Pattern ORDER_PATTERN = Pattern.compile("\\[(.*?)]");
    private static final Pattern INPUT_FORMAT = Pattern.compile("\\[([^-]+-\\d+)](?:,\\[([^-]+-\\d+)])*");
    private static final String ORDER_DELIMITER = "-";

    public static List<OrderRequest> parse(String input) {
        validateInput(input);
        return extractOrders(input);
    }

    private static List<OrderRequest> extractOrders(String input) {
        Matcher matcher = ORDER_PATTERN.matcher(input);

        return matcher.results()
                .map(result -> result.group(1))
                .map(OrderRequestParser::createOrderRequest)
                .collect(Collectors.toList());
    }

    private static OrderRequest createOrderRequest(String orderString) {
        String[] orderParts = splitOrderString(orderString);
        validateOrderParts(orderParts);

        String menuName = orderParts[0];
        int quantity = InputParser.convertToInt(orderParts[1]);

        return OrderRequest.of(menuName, quantity);
    }

    private static String[] splitOrderString(String orderString) {
        return orderString.split(ORDER_DELIMITER);
    }

    private static void validateInput(String input) {
        if (isNullOrEmpty(input)) {
            throw new IllegalArgumentException(ERROR_INVALID_INPUT);
        }
        if (!isValidFormat(input)) {
            throw new IllegalArgumentException(ERROR_INVALID_FORMAT);
        }
    }

    private static boolean isNullOrEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    private static boolean isValidFormat(String input) {
        return INPUT_FORMAT.matcher(input).matches();
    }

    private static void validateOrderParts(String[] parts) {
        if (parts.length != 2) {
            throw new IllegalArgumentException(ERROR_INVALID_INPUT);
        }
    }
}