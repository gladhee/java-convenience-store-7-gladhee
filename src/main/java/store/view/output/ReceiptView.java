package store.view.output;

import store.domain.order.Receipt;

public class ReceiptView {

    public static void print(Receipt receipt) {
        System.out.println("주문 내역을 출력합니다.");
        System.out.println("==================================");
        receipt.getOrderLines().forEach(orderLine -> {
            System.out.println(orderLine.getProduct().getName() + " : " + orderLine.getQuantity() + "개");
        });
        System.out.println("==================================");
        System.out.println("총구매액  " + receipt.getTotalPrice() + "원");
        System.out.println("행사할인  " + receipt.getPromotionDiscount() + "원");
        System.out.println("멤버십할인  " + receipt.getMembershipDiscount() + "원");
        System.out.println("내실돈  " + String.format("%,d", (receipt.calculateFinalPrice())) + "원");
        System.out.println("==================================");

    }

}
