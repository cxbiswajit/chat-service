package com.openprice.chatservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.openprice.chatservice.config.model.Product;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class StaticProductInitializer {

    private final ResourceLoader resourceLoader;

    // The static map to store the CSV data
    private static Map<String, Product> productMap = new HashMap<>();

    @Autowired
    public StaticProductInitializer(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void init() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:data.csv");
        InputStream inputStream = resource.getInputStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Product product = new Product(parts[0],parts[1],Double.parseDouble(parts[2]),Double.parseDouble(parts[3]));
                productMap.put(parts[0], product);
            }
        }
    }

    // Getter for accessing the static map from other classes
    public static Map<String, Product> getProductMap() {
        return productMap;
    }
}
