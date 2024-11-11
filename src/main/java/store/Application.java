package store;

import store.config.DatabaseConfig;
import store.controller.StoreController;
import store.service.OrderService;
import store.service.StoreProductsService;

public class Application {
    public static void main(String[] args) {
        DatabaseConfig databaseConfig = DatabaseConfig.getInstance();
        OrderService orderService = new OrderService(databaseConfig.getStoreProducts());
        StoreProductsService storeProductsService = new StoreProductsService(databaseConfig.getStoreProducts());
        StoreController storeController = new StoreController(orderService, storeProductsService);

        System.out.println(databaseConfig.getStoreProducts().findAll());
        storeController.run();
    }
}
