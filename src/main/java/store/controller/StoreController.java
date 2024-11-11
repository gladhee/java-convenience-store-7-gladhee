package store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.domain.order.OrderRequest;
import store.domain.order.Receipt;
import store.service.OrderService;
import store.service.StoreProductsService;
import store.util.parser.InputParser;
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
    }

    private void processOrder() {
        ProductsView.print(storeProductsService.getStoreProducts());
        List<OrderRequest> orderRequests = parseOrderInput();

        Receipt receipt = orderService.createOrder(orderRequests);
        ReceiptView.print(receipt);
    }

    private List<OrderRequest> parseOrderInput() {
        try {
            String orderInput = InputView.inputProducts();
            List<OrderRequest> orderRequests = OrderRequestParser.parse(orderInput);
            storeProductsService.validateOrderRequests(orderRequests);
            return orderRequests;
        } catch (IllegalArgumentException | IllegalStateException e) {
            OutputView.printError(e.getMessage());
            return parseOrderInput();
        }
    }

}
