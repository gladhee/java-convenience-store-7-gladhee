package store.domain.order;

import java.util.ArrayList;
import java.util.List;
import store.exception.OrderException.OrderItemShouldBeRequiredException;

public class Order {

    private static final int MEMBERSHIP_DISCOUNT_RATE = 30;
    private static final int MAX_MEMBERSHIP_DISCOUNT = 8000;

    private final List<OrderLine> orderLines;
    private final boolean useMembership;

    private Order(List<OrderLine> orderLines, boolean useMembership) {
        validateOrderLines(orderLines);
        this.orderLines = new ArrayList<>(orderLines);
        this.useMembership = useMembership;
    }

    private void validateOrderLines(List<OrderLine> orderLines) {
        if (orderLines == null || orderLines.isEmpty()) {
            throw new OrderItemShouldBeRequiredException();
        }
    }

    public static Order create(List<OrderLine> orderLines, boolean useMembership) {
        return new Order(orderLines, useMembership);
    }

    public Receipt createReceipt() {
        int totalPrice = calculateTotalPrice();
        int promotionDiscount = calculatePromotionDiscount();
        int membershipDiscount = calculateMembershipDiscount(totalPrice - promotionDiscount);
        return Receipt.create(
                orderLines,
                totalPrice,
                promotionDiscount,
                membershipDiscount
        );
    }

    private int calculateTotalPrice() {
        return orderLines.stream()
                .mapToInt(OrderLine::calculatePrice)
                .sum();
    }

    private int calculatePromotionDiscount() {
        return orderLines.stream()
                .mapToInt(OrderLine::calculatePromotionDiscount)
                .sum();
    }

    private int calculateMembershipDiscount(int priceAfterPromotion) {
        if (!useMembership) {
            return 0;
        }
        int discountAmount = (priceAfterPromotion * MEMBERSHIP_DISCOUNT_RATE) / 100;
        return Math.min(discountAmount, MAX_MEMBERSHIP_DISCOUNT);
    }

    public List<OrderLine> getOrderLines() {
        return new ArrayList<>(orderLines);
    }

}