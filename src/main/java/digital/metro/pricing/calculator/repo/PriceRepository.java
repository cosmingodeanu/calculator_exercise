package digital.metro.pricing.calculator.repo;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A dummy implementation for testing purposes. In production, we would get real prices from a database.
 */
@Component
public class PriceRepository {

    private final Map<String, BigDecimal> prices = new HashMap<>();
    private static final Random random = new Random();
    private static final int SCALE = 2;
    private static final double ARTICLE_LOWER_LIMIT_RANGE = 0.5d;
    private static final double ARTICLE_UPPER_LIMIT_RANGE = 29.50d;
    private static final String CUSTOMER1 = "customer-1";
    private static final String CUSTOMER2 = "customer-2";
    private static final BigDecimal CUSTOMER1_FACTOR = new BigDecimal("0.90");
    private static final BigDecimal CUSTOMER2_FACTOR = new BigDecimal("0.85");

    public BigDecimal getPriceByArticleId(String articleId) {
        return prices.computeIfAbsent(articleId, key -> computeArticleRandomPrice());
    }

    public BigDecimal getPriceByArticleIdAndCustomerId(String articleId, String customerId) {
        switch (customerId) {
            case CUSTOMER1:
                return getPriceByArticleId(articleId).multiply(CUSTOMER1_FACTOR).setScale(SCALE, RoundingMode.HALF_UP);
            case CUSTOMER2:
                return getPriceByArticleId(articleId).multiply(CUSTOMER2_FACTOR).setScale(SCALE, RoundingMode.HALF_UP);
        }

        return null;
    }

    private static BigDecimal computeArticleRandomPrice() {
        return BigDecimal.valueOf(ARTICLE_LOWER_LIMIT_RANGE + random.nextDouble() * ARTICLE_UPPER_LIMIT_RANGE)
                .setScale(SCALE, RoundingMode.HALF_UP);
    }
}
