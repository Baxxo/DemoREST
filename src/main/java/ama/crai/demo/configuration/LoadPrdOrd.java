package ama.crai.demo.configuration;

import ama.crai.demo.entity.Order;
import ama.crai.demo.entity.Product;
import ama.crai.demo.entity.ordPrd.OrderProduct;
import ama.crai.demo.repository.OrderProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LoadPrdOrd {
    private static final Logger log = LoggerFactory.getLogger(LoadPrdOrd.class);
    @Autowired
    private OrderProductRepository orderProductRepository;

    List<OrderProduct> loadPrdOrdInDB(List<Product> products,List<Order> orders) {

        OrderProduct macBookProOrder = new OrderProduct(orders.get(0), products.get(0), 1);
        log.info("Preloading " + macBookProOrder);

        OrderProduct iPhoneXOrder = new OrderProduct(orders.get(1), products.get(1), 1);
        log.info("Preloading " + iPhoneXOrder);

        macBookProOrder = orderProductRepository.save(macBookProOrder);
        iPhoneXOrder = orderProductRepository.save(iPhoneXOrder);
        log.info("Saved " + macBookProOrder);
        log.info("Saved " + iPhoneXOrder);

        return orderProductRepository.findAll();
    }
}
