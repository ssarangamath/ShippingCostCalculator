package org.example;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

import org.apache.commons.collections4.CollectionUtils;

public class ShippingCostCalculator implements IShippingCostCalculator{

    @Override
    public Integer calculateShippingCost(Order order, Map<String, List<ItemCost>> itemCosts) {
       if(Objects.isNull(order) || Objects.isNull(itemCosts) || itemCosts.size() == 0 ||
               StringUtils.isEmpty(order.getCountry()) || CollectionUtils.isEmpty(order.getItems()))
           return -1;

        String country = order.getCountry();

        int totalCost = 0;

        List<ItemCost> costs = itemCosts.get(country);

        for(LineItem lineItem: order.getItems()){
            Optional<Integer> itemCost = costs.stream().filter(cost ->
                    cost.getProduct().equals(lineItem.getProduct())).map(ItemCost::getCost).findFirst();

            totalCost+= itemCost.get() * lineItem.getQuantity();
        }
        return totalCost;
    }

    @Override
    public Integer calculateRangeShippingCost(Order order, Map<String, List<ItemCost>> itemCosts) {
        if(Objects.isNull(order) || Objects.isNull(itemCosts) || itemCosts.size() == 0 ||
                StringUtils.isEmpty(order.getCountry()) || CollectionUtils.isEmpty(order.getItems()))
            return -1;

        String country = order.getCountry();

        int totalCost = 0;
        List<ItemCost> costs = itemCosts.get(country);

        for(LineItem lineItem: order.getItems()){
            List<RangeCost> rangeCosts = costs.stream().filter(cost -> cost.getProduct().equals(lineItem.getProduct())).
                    map(cost -> cost.getRangeCosts()).findFirst().get();

            Collections.sort(rangeCosts, Comparator.comparingInt(rangeCost -> rangeCost.getMinQuantity()));

            int quantity = lineItem.getQuantity();

            int i = 0;
            while(quantity>0){
                RangeCost rangeCost = rangeCosts.get(i++);
                if(rangeCost.getMaxQuantity() == null){
                    totalCost += quantity* rangeCost.getCost();
                    quantity = 0;
                }else {
                    int rangeQuantity = rangeCost.getMaxQuantity()-rangeCost.getMinQuantity() +1;
                    if(quantity >= rangeQuantity){
                        totalCost += rangeQuantity * rangeCost.getCost();
                        quantity = quantity - rangeQuantity;
                    }else{
                        totalCost += quantity * rangeCost.getCost();
                        quantity = 0;
                    }
                }
            }
        }
        return totalCost;
    }
}
