package store.service;

import java.util.List;
import store.domain.order.OrderRequest;
import store.domain.store.StoreProduct;
import store.repository.impl.StoreProductsRepositoryImpl;

public class StoreProductsService {

    private final StoreProductsRepositoryImpl storeProductsRepository;

    public StoreProductsService(StoreProductsRepositoryImpl storeProductsRepository) {
        this.storeProductsRepository = storeProductsRepository;
    }

    public List<StoreProduct> getStoreProducts() {
        return storeProductsRepository.findAll();
    }

    public void validateOrderRequests(List<OrderRequest> orderRequests) {
        for (OrderRequest orderRequest : orderRequests) {
            StoreProduct storeProduct = storeProductsRepository.findByName(orderRequest.getProductName());
            if (storeProduct == null) {
                throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
            }

            if (storeProduct.isOutOfStock(orderRequest.getQuantity())) {
                throw new IllegalStateException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
            }
        }
    }

}
