package store.service;

import java.util.List;
import store.domain.order.OrderRequest;
import store.domain.store.StoreProduct;
import store.exception.InputException.InvalidOrderQuantityException;
import store.exception.InputException.InvalidProductNameException;
import store.repository.impl.StoreProductsRepositoryImpl;

public class StoreProductsService {

    private final StoreProductsRepositoryImpl storeProductsRepository;

    public StoreProductsService(StoreProductsRepositoryImpl storeProductsRepository) {
        this.storeProductsRepository = storeProductsRepository;
    }

    public List<StoreProduct> getStoreProducts() {
        return storeProductsRepository.findAll();
    }

    public StoreProduct searchStoreProductByProductName(String productName) {
        return storeProductsRepository.findByName(productName);
    }

    public void validateOrderRequests(List<OrderRequest> orderRequests) {
        for (OrderRequest orderRequest : orderRequests) {
            StoreProduct storeProduct = storeProductsRepository.findByName(orderRequest.getProductName());
            if (storeProduct == null) {
                throw new InvalidProductNameException();
            }
            if (storeProduct.isOutOfStock(orderRequest.getQuantity())) {
                throw new InvalidOrderQuantityException();
            }
        }
    }

    public int calculateNonPromotionQuantity(OrderRequest orderRequest) {
        StoreProduct storeProduct = storeProductsRepository.findByName(orderRequest.getProductName());
        return storeProduct.calculateNonPromotionQuantity(orderRequest.getQuantity());
    }

}
