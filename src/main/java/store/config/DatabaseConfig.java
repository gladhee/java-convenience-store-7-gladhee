package store.config;

import java.util.List;
import store.domain.product.Product;
import store.domain.promotion.Promotion;
import store.domain.store.StoreProduct;
import store.repository.impl.ProductRepositoryImpl;
import store.repository.impl.PromotionRepositoryImpl;
import store.repository.impl.StoreProductsRepositoryImpl;
import store.util.reader.FileReader;

public class DatabaseConfig {
    private static DatabaseConfig instance;
    private final StoreProductsRepositoryImpl storeProducts;
    private final ProductRepositoryImpl products;
    private final PromotionRepositoryImpl promotions;

    private DatabaseConfig() {
        storeProducts = StoreProductsRepositoryImpl.getInstance();
        products = ProductRepositoryImpl.getInstance();
        promotions = PromotionRepositoryImpl.getInstance();
        initializeData();
    }

    public static DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }

    private void initializeData() {
        loadFile("promotions.md", this::parsePromotionLine);
        loadFile("products.md", this::parseProductAndStoreProductLine);
    }

    private void loadFile(String fileName, LineParser parser) {
        List<String> lines = FileReader.readLines(fileName);
        lines.removeFirst();
        lines.forEach(parser::parse);
    }

    private void parsePromotionLine(String line) {
        String[] infos = line.split(",");
        Promotion promotion = Promotion.builder()
                .name(infos[0])
                .requiredQuantity(infos[1])
                .freeQuantity(infos[2])
                .startDate(infos[3])
                .endDate(infos[4])
                .build();
        promotions.save(infos[0], promotion);
    }

    private void parseProductAndStoreProductLine(String line) {
        String[] infos = line.split(",");
        createProduct(infos);
        createStoreProduct(infos);
    }

    private void createProduct(String[] infos) {
        Product product = Product.builder()
                .name(infos[0])
                .price(infos[1])
                .build();
        products.save(infos[0], product);
    }

    private void createStoreProduct(String[] infos) {
        Product product = products.findByName(infos[0]);
        try {
            StoreProduct storeProduct = storeProducts.findByName(infos[0]);
            System.out.println(infos[2]);
            storeProduct.updateNormalQuantity(infos[2]);

        } catch (IllegalArgumentException e) {
            if (!infos[3].equals("null")) {
                Promotion promotion = promotions.findByName(infos[3]);
                storeProducts.save(infos[0], StoreProduct.builder()
                        .product(product)
                        .normalQuantity(null)
                        .promotionQuantity(infos[2])
                        .promotion(promotion)
                        .build());
                return;
            }
            storeProducts.save(infos[0], StoreProduct.builder()
                    .product(product)
                    .normalQuantity(infos[2])
                    .promotionQuantity(null)
                    .promotion(null)
                    .build());
        }

    }

    public StoreProductsRepositoryImpl getStoreProducts() {
        return storeProducts;
    }

    @FunctionalInterface
    interface LineParser {
        void parse(String line);
    }
}