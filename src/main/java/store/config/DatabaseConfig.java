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
    private static final String PROMOTIONS_FILE = "promotions.md";
    private static final String PRODUCTS_FILE = "products.md";
    private static final String NULL_VALUE = "null";
    private static final String DEFAULT_QUANTITY = "0";

    private static DatabaseConfig instance;
    private final StoreProductsRepositoryImpl storeProducts;
    private final ProductRepositoryImpl products;
    private final PromotionRepositoryImpl promotions;

    private DatabaseConfig() {
        this.storeProducts = StoreProductsRepositoryImpl.getInstance();
        this.products = ProductRepositoryImpl.getInstance();
        this.promotions = PromotionRepositoryImpl.getInstance();
        initializeData();
    }

    public static DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }

    private void initializeData() {
        loadFile(PROMOTIONS_FILE, this::parsePromotionLine);
        loadFile(PRODUCTS_FILE, this::parseProductAndStoreProductLine);
    }

    private void loadFile(String fileName, LineParser parser) {
        List<String> lines = FileReader.readLines(fileName);
        lines.removeFirst();
        lines.forEach(parser::parse);
    }

    private void parsePromotionLine(String line) {
        String[] infos = line.split(",");
        Promotion promotion = createPromotion(infos);
        promotions.save(infos[0], promotion);
    }

    private Promotion createPromotion(String[] infos) {
        return Promotion.builder()
                .name(infos[0])
                .requiredQuantity(infos[1])
                .freeQuantity(infos[2])
                .startDate(infos[3])
                .endDate(infos[4])
                .build();
    }

    private void parseProductAndStoreProductLine(String line) {
        String[] infos = line.split(",");
        saveProduct(infos);
        saveStoreProduct(infos);
    }

    private void saveProduct(String[] infos) {
        Product product = createProduct(infos);
        products.save(infos[0], product);
    }

    private Product createProduct(String[] infos) {
        return Product.builder()
                .name(infos[0])
                .price(infos[1])
                .build();
    }

    private void saveStoreProduct(String[] infos) {
        Product product = products.findByName(infos[0]);
        StoreProduct existingProduct = storeProducts.findByName(infos[0]);

        if (existingProduct != null) {
            updateExistingProduct(existingProduct, infos);
            return;
        }
        createNewStoreProduct(product, infos);
    }

    private void updateExistingProduct(StoreProduct storeProduct, String[] infos) {
        storeProduct.updateNormalQuantity(infos[2]);
    }

    private void createNewStoreProduct(Product product, String[] infos) {
        StoreProduct newProduct = selectStoreProductType(product, infos);
        storeProducts.save(infos[0], newProduct);
    }

    private StoreProduct selectStoreProductType(Product product, String[] infos) {
        if (isPromotionalProduct(infos[3])) {
            return createPromotionalStoreProduct(product, infos);
        }
        return createNormalStoreProduct(product, infos);
    }

    private boolean isPromotionalProduct(String promotionInfo) {
        return !NULL_VALUE.equals(promotionInfo);
    }

    private StoreProduct createPromotionalStoreProduct(Product product, String[] infos) {
        Promotion promotion = promotions.findByName(infos[3]);
        return StoreProduct.builder()
                .product(product)
                .normalQuantity(DEFAULT_QUANTITY)
                .promotionQuantity(infos[2])
                .promotion(promotion)
                .build();
    }

    private StoreProduct createNormalStoreProduct(Product product, String[] infos) {
        return StoreProduct.builder()
                .product(product)
                .normalQuantity(infos[2])
                .promotionQuantity(null)
                .promotion(null)
                .build();
    }

    public StoreProductsRepositoryImpl getStoreProducts() {
        return storeProducts;
    }

    @FunctionalInterface
    interface LineParser {
        void parse(String line);
    }
}