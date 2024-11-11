package store.util.parser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import store.domain.order.OrderRequest;
import store.exception.InputException.InvalidFormatException;
import store.exception.InputException.InvalidInputException;

public class OrderRequestParser {

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
            throw new InvalidInputException();
        }
        if (!isValidFormat(input)) {
            throw new InvalidFormatException();
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
            throw new InvalidInputException();
        }
    }
}