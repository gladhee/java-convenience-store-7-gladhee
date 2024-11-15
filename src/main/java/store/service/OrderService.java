package store.service;

import java.util.List;
import java.util.stream.Collectors;
import store.domain.order.Order;
import store.domain.order.OrderLine;
import store.domain.order.OrderRequest;
import store.domain.order.Receipt;
import store.domain.promotion.Promotion;
import store.domain.store.StoreProduct;
import store.exception.OrderException.OrderIsCanceledException;
import store.exception.OrderException.OutOfStockException;
import store.repository.impl.StoreProductsRepositoryImpl;
import store.view.input.InputView;

public class OrderService {

    private final StoreProductsRepositoryImpl storeProductsRepository;

    public OrderService(StoreProductsRepositoryImpl storeProductsRepository) {
        this.storeProductsRepository = storeProductsRepository;
    }

    public Receipt createOrder(List<OrderRequest> orderRequests) {
        List<OrderLine> orderLines = processOrderRequests(orderRequests);
        boolean useMembership = InputView.askToMembershipUse();

        Order order = Order.create(orderLines, useMembership);
        updateStock(order);

        return order.createReceipt();
    }

    private List<OrderLine> processOrderRequests(List<OrderRequest> orderRequests) {
        return orderRequests.stream()
                .map(this::createOrderLine)
                .collect(Collectors.toList());
    }

    private OrderLine createOrderLine(OrderRequest request) {
        StoreProduct product = storeProductsRepository.findByName(request.getProductName());
        validateStock(product, request.getQuantity());
        int finalQuantity = handlePromotions(product, request.getQuantity());
        if (finalQuantity == 0) {
            throw new OrderIsCanceledException();
        }
        return new OrderLine(product, finalQuantity);
    }

    private void validateStock(StoreProduct product, int quantity) {
        if (product.isOutOfStock(quantity)) {
            throw new OutOfStockException();
        }
    }

    private int handlePromotions(StoreProduct product, int requestedQuantity) {
        Promotion promotion = product.getPromotion();
        if (promotion != null && promotion.isApplicable()) {
            return requestedQuantity;
        }
        return handlePromotionQuantity(product, promotion, requestedQuantity);
    }

    private int handlePromotionQuantity(StoreProduct storeProduct, Promotion promotion, int requestedQuantity) {
        int normalPriceQuantity = calculateNormalPriceQuantity(storeProduct, requestedQuantity);
        if (normalPriceQuantity > 0) {
            if (InputView.askToPromotionPurchase(storeProduct.getName(), normalPriceQuantity)) {
                return requestedQuantity;
            }
            return requestedQuantity - normalPriceQuantity;
        }
        return requestedQuantity;
    }

    private int calculateNormalPriceQuantity(StoreProduct product, int requestedQuantity) {
        int promotionStock = product.getPromotionQuantity();
        Promotion promotion = product.getPromotion();
        if (promotion == null) {
            return 0;
        }
        int totalNeeded = promotion.calculateTotalQuantityPerSet();
        int possiblePromotionSets = promotionStock / totalNeeded;
        int coveredByPromotion = possiblePromotionSets * totalNeeded;
        return Math.max(0, requestedQuantity - coveredByPromotion);
    }

    private void updateStock(Order order) {
        order.getOrderLines().forEach(orderLine -> {
            StoreProduct product = orderLine.getProduct();
            product.decreaseStock(orderLine.getQuantity());
            storeProductsRepository.save(product.getName(), product);
        });
    }

}
