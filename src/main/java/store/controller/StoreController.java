package store.controller;

import java.util.List;
import store.domain.order.OrderRequest;
import store.domain.order.Receipt;
import store.domain.store.StoreProduct;
import store.service.OrderService;
import store.service.StoreProductsService;
import store.util.parser.OrderRequestParser;
import store.view.input.InputView;
import store.view.output.OutputView;
import store.view.output.ProductsView;
import store.view.output.ReceiptView;


public class StoreController {

    private final OrderService orderService;
    private final StoreProductsService storeProductsService;

    public StoreController(OrderService orderService, StoreProductsService storeProductsService) {
        this.orderService = orderService;
        this.storeProductsService = storeProductsService;
    }

    public void run() {
        do {
            try {
                processOrder();
            } catch (IllegalArgumentException | IllegalStateException e) {
                OutputView.printError(e.getMessage());
            }
        } while (InputView.askToContinuePurchase());
        InputView.closeStream();
    }

    private void processOrder() {
        ProductsView.print(storeProductsService.getStoreProducts());
        List<OrderRequest> orderRequests = parseOrderInput();
        Receipt receipt = orderService.createOrder(orderRequests);

        ReceiptView.print(receipt);
    }

    public List<OrderRequest> parseOrderInput() {
        try {
            String orderInput = InputView.inputProducts();
            List<OrderRequest> orderRequests = OrderRequestParser.parse(orderInput);
            validateOrders(orderRequests);
            return orderRequests;
        } catch (IllegalArgumentException | IllegalStateException e) {
            OutputView.printError(e.getMessage());
            return parseOrderInput();
        }
    }

    private void validateOrders(List<OrderRequest> orderRequests) {
        storeProductsService.validateOrderRequests(orderRequests);
        validateNonPromotionalOrders(orderRequests);
    }

    private void validateNonPromotionalOrders(List<OrderRequest> orderRequests) {
        orderRequests.forEach(request -> {
            StoreProduct product = storeProductsService.searchStoreProductByProductName(request.getProductName());
            if (!product.isPromotionApplicable()) {
                validateNonPromotionalQuantity(request, product);
            }
        });
    }

    private void validateNonPromotionalQuantity(OrderRequest request, StoreProduct product) {
        int nonPromotionQuantity = storeProductsService.calculateNonPromotionQuantity(request);
        if (nonPromotionQuantity > 0 &&
                !InputView.askToPromotionNotApplicablePurchase(product.getProduct(), nonPromotionQuantity)) {
            throw new IllegalStateException("상품을 다시 입력해주세요");
        }
    }

}
