package store.view.input;

import camp.nextstep.edu.missionutils.Console;
import store.domain.product.Product;

public class InputView {

    public static String inputProducts() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");

        return Console.readLine();
    }

    public static boolean askToMembershipUse() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");

        return inputYesOrNo();
    }

    public static boolean askToPromotionPurchase(String productName, int quantity) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("현재 ").append(productName)
                .append("은(는) ")
                .append(quantity)
                .append("개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");

        System.out.println(stringBuilder.toString());

        return inputYesOrNo();
    }

    public static boolean askToPromotionNotApplicablePurchase(Product product, int quantity) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("현재 ").append(product.getName()).append(" ")
                .append(quantity).append("개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");

        System.out.println(stringBuilder.toString());

        return inputYesOrNo();
    }

    public static boolean askToContinuePurchase() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");

        return inputYesOrNo();
    }

    private static boolean inputYesOrNo() {
        try {
            String input = Console.readLine().toUpperCase();
            validateYesOrNo(input);

            return input.equals("Y");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return inputYesOrNo();
        }
    }

    private static void validateYesOrNo(String input) {
        if (!input.equals("Y") && !input.equals("N")) {
            throw new IllegalArgumentException("[ERROR] Y 또는 N을 입력해 주세요.");
        }
    }

    public static void closeStream() {
        Console.close();
    }

}
