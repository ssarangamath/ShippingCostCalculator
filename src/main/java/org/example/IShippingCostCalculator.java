package org.example;

import java.util.List;
import java.util.Map;

public interface IShippingCostCalculator
{
    Integer calculateShippingCost(Order order, Map<String , List<ItemCost>> itemCosts);

    Integer calculateRangeShippingCost(Order order, Map<String, List<ItemCost>> itemCosts);
}
