package store.view.output;

import java.util.List;
import store.domain.store.StoreProduct;

public class ProductsView {

    public static void print(List<StoreProduct> storeProducts) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("안녕하세요. W편의점입니다.\n")
                .append("현재 보유하고 있는 상품입니다.\n\n")
                .append(createProductsInfo(storeProducts));

        System.out.println(stringBuilder.toString());
    }

    private static String createProductsInfo(List<StoreProduct> storeProducts) {
        StringBuilder stringBuilder = new StringBuilder();

        for (StoreProduct storeProduct : storeProducts) {
            if (storeProduct.getPromotion() != null) {
                stringBuilder.append(storeProduct.toPromotionString());
            }
            stringBuilder.append(storeProduct.toNormalString());
        }

        return stringBuilder.toString();
    }




}
