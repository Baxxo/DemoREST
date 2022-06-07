package ama.crai.demo.configuration;

import ama.crai.demo.entity.Product;
import ama.crai.demo.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LoadProduct {
    private static final Logger log = LoggerFactory.getLogger(LoadProduct.class);
    @Autowired
    private ProductRepository productRepository;

    List<Product> loadProductInDB() {

        Product macBookPro = new Product("MacBook Pro", "Apple's latest laptop");
        log.info("Preloading " + macBookPro);

        Product iPhoneX = new Product("iPhone X", "Apple's latest smartphone");
        log.info("Preloading " + iPhoneX);

        macBookPro = productRepository.save(macBookPro);
        iPhoneX = productRepository.save(iPhoneX);
        log.info("Saved " + macBookPro);
        log.info("Saved " + iPhoneX);

        return productRepository.findAll();
    }
}
