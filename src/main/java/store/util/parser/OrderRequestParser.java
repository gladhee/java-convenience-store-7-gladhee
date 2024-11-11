package store.util.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.domain.order.OrderRequest;

public class OrderRequestParser {

    private static final Pattern ORDER_PATTERN = Pattern.compile("\\[(.*?)]");

    public static List<OrderRequest> parse(String input) {
        validateInput(input);

        List<OrderRequest> requests = new ArrayList<>();
        Matcher matcher = ORDER_PATTERN.matcher(input);

        while (matcher.find()) {
            String[] parts = matcher.group(1).split("-");
            int amount = validateParts(parts);

            requests.add(OrderRequest.of(
                    parts[0],
                    amount
            ));
        }

        return requests;
    }

    private static void validateInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
        if (!input.matches("\\[([^-]+-\\d+)](?:,\\[([^-]+-\\d+)])*")) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다.");
        }
    }

    private static Integer validateParts(String[] parts) {
        if (parts.length != 2) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        }
        return InputParser.convertToInt(parts[1]);
    }

}