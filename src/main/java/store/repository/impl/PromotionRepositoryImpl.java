package store.repository.impl;

import java.util.List;
import store.domain.promotion.Promotion;
import store.repository.interfaces.PromotionRepository;

public class PromotionRepositoryImpl implements PromotionRepository {

    private static PromotionRepositoryImpl instance;

    public static PromotionRepositoryImpl getInstance() {
        if (instance == null) {
            return new PromotionRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void save(Promotion promotion) {

    }

    @Override
    public Promotion findByName(String promotionName) {
        return null;
    }

    @Override
    public List<Promotion> findAll() {
        return null;
    }

}
