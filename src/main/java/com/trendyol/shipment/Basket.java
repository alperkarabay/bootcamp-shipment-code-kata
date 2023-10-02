package com.trendyol.shipment;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Basket {

    private List<Product> products;

    Map<ShipmentSize, Long> productSizeCountMap;
    public ShipmentSize getShipmentSize() {

        productSizeCountMap = products.stream()
                .collect(Collectors.groupingBy(Product::getSize, Collectors.counting()));

        if (isOneOfTheItemCountIsBiggerThanThree()) {
            return oneUpperShipmentSize();
        }

        return largestProductsSize();
    }

    public boolean isOneOfTheItemCountIsBiggerThanThree(){
        return productSizeCountMap.values().stream().max(Comparator.naturalOrder()).get() >= 3;
    }


    public ShipmentSize largestProductsSize(){
        return productSizeCountMap.entrySet().stream()
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getKey)
                .orElse(ShipmentSize.SMALL);
    }

    public ShipmentSize oneUpperShipmentSize(){
        ShipmentSize productShipmentSize = products.get(0).getSize();
        if(productShipmentSize.equals(ShipmentSize.X_LARGE)) return ShipmentSize.X_LARGE;
        return ShipmentSize.values()[productShipmentSize.ordinal()+1];
    }
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
