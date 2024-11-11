package store.domain.order;

import java.util.ArrayList;
import java.util.List;

public class Receipt {
    private final List<OrderLine> orderLines;
    private final int totalPrice;
    private final int promotionDiscount;
    private final int membershipDiscount;

    private Receipt(List<OrderLine> orderLines, int totalPrice, int promotionDiscount, int membershipDiscount) {
        this.orderLines = new ArrayList<>(orderLines);
        this.totalPrice = totalPrice;
        this.promotionDiscount = promotionDiscount;
        this.membershipDiscount = membershipDiscount;
    }

    public static Receipt create(List<OrderLine> orderLines, int totalPrice,
                                 int promotionDiscount, int membershipDiscount) {
        return new Receipt(orderLines, totalPrice, promotionDiscount, membershipDiscount);
    }

    public int calculateFinalPrice() {
        return totalPrice - promotionDiscount - membershipDiscount;
    }

    public List<OrderLine> getOrderLines() {
        return new ArrayList<>(orderLines);
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }
}