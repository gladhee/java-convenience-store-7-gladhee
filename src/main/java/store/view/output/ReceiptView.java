package store.view.output;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import store.domain.order.OrderLine;
import store.domain.order.Receipt;

public class ReceiptView {

    private static final String STORE_NAME = "W 편의점";
    private static final String HEADER_BORDER = "==============";
    private static final String RECEIPT_BORDER = "====================================";
    private static final String PROMOTION_SECTION = "=============증\t정===============";

    public static void print(Receipt receipt) {
        StringBuilder builder = new StringBuilder();
        appendHeader(builder);
        appendOrderLines(builder, receipt.getOrderLines());
        appendPromotionSection(builder, receipt.getOrderLines());
        appendSummary(builder, receipt);
        System.out.println(builder.toString());
    }

    private static void appendHeader(StringBuilder builder) {
        builder.append(HEADER_BORDER)
                .append(STORE_NAME)
                .append(HEADER_BORDER)
                .append("\n")
                .append("상품명\t\t수량\t금액\n");
    }

    private static void appendOrderLines(StringBuilder builder, List<OrderLine> orderLines) {
        for (OrderLine line : orderLines) {
            builder.append(String.format("%s\t\t%d\t%s\n",
                    line.getProduct().getName(),
                    line.getQuantity(),
                    formatNumber(line.calculatePrice())));
        }
    }

    private static void appendPromotionSection(StringBuilder builder, List<OrderLine> orderLines) {
        builder.append(PROMOTION_SECTION).append("\n");
        for (OrderLine line : orderLines) {
            int promotionDiscount = line.calculatePromotionDiscount();
            if (promotionDiscount > 0) {
                builder.append(String.format("%s\t\t%d\n",
                        line.getProduct().getName(),
                        promotionDiscount / line.getProduct().getProduct().getPrice()));
            }
        }
    }

    private static void appendSummary(StringBuilder builder, Receipt receipt) {
        builder.append(RECEIPT_BORDER).append("\n")
                .append(String.format("총구매액\t\t%d\t%s\n",
                        getTotalQuantity(receipt.getOrderLines()), formatNumber(receipt.getTotalPrice())))
                .append(String.format("행사할인\t\t\t-%s\n",
                        formatNumber(receipt.getPromotionDiscount())))
                .append(String.format("멤버십할인\t\t\t-%s\n",
                        formatNumber(receipt.getMembershipDiscount())))
                .append(String.format("내실돈\t\t\t%s\n",
                        formatNumber(receipt.calculateFinalPrice())));
    }

    private static int getTotalQuantity(List<OrderLine> orderLines) {
        return orderLines.stream()
                .mapToInt(OrderLine::getQuantity)
                .sum();
    }

    private static String formatNumber(int number) {
        return NumberFormat.getNumberInstance(Locale.KOREA)
                .format(number);
    }

}
