package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // Create US Order
        LineItem lineItem1 = new LineItem("mobile", 4);
        LineItem lineItem2 = new LineItem("laptop", 12);
        List<LineItem> lineItems = new ArrayList<>(List.of(lineItem1, lineItem2));
        Order order = new Order(lineItems, "US");

        // Create CA Order
        LineItem caLineItem1 = new LineItem("mobile", 5);
        LineItem caLineItem2 = new LineItem("laptop", 5);
        List<LineItem> caLineItems = new ArrayList<>(List.of(caLineItem1, caLineItem2));
        Order caOrder = new Order(caLineItems, "CA");

        // Create Shipping Cost Matrix for US
        Map<String, List<ItemCost>> shippingCostMatrix = new HashMap<>();
        ItemCost mobileCost = new ItemCost("mobile", 100, null);
        ItemCost laptopCost = new ItemCost("laptop", 200, null);

        List<ItemCost> itemCosts = new ArrayList<>(List.of(mobileCost, laptopCost));
        shippingCostMatrix.put("US", itemCosts);

        // Create Shipping Cost Matrix for CA
        ItemCost caMobileCost = new ItemCost("mobile", 200, null);
        ItemCost caLaptopCost = new ItemCost("laptop", 200, null);
        List<ItemCost> caItemCosts = new ArrayList<>(List.of(caLaptopCost, caMobileCost));

        shippingCostMatrix.put("CA", caItemCosts);

        IShippingCostCalculator shippingCostCalculator = new ShippingCostCalculator();

        // Calculate Shipping Cost for US
        int shippingCost = shippingCostCalculator.calculateShippingCost(order, shippingCostMatrix);
        System.out.println("Shipping Cost :: "+ shippingCost);

        // Calculate Shipping Cost for CA
        int caShippingCost = shippingCostCalculator.calculateShippingCost(caOrder, shippingCostMatrix);
        System.out.println("CA Shipping Cost :: "+ caShippingCost);


        // Create Shipping Range Cost Matrix for US
        RangeCost rangeCost0 = new RangeCost(1, 3 , 10); // 30
        RangeCost rangeCost4 = new RangeCost(6, 10, 20); // 20
        List<RangeCost> rangeCosts0 = new ArrayList<>(List.of(rangeCost0, rangeCost4));

        ItemCost rangeItemCost0 = new ItemCost("mobile", null, rangeCosts0);

        RangeCost rangeCost1 = new RangeCost(1, 5, 100); // 500
        RangeCost rangeCost2 = new RangeCost(6, 10, 200); // 1000
        RangeCost rangeCost3 = new RangeCost(11, null, 300); // 600

        List<RangeCost> rangeCosts = new ArrayList<>(List.of(rangeCost1, rangeCost2, rangeCost3));

        ItemCost rangeItemCost1 = new ItemCost("laptop", null, rangeCosts);

        List<ItemCost> rangeItemCosts = new ArrayList<>(List.of(rangeItemCost0, rangeItemCost1));

        Map<String, List<ItemCost>> rangeShippingCostMatrix = new HashMap<>();
        rangeShippingCostMatrix.put("US", rangeItemCosts);

        int rangeShippingcost = shippingCostCalculator.calculateRangeShippingCost(order, rangeShippingCostMatrix);

        System.out.println("Range Shipping Cost is "+ rangeShippingcost);
    }
}