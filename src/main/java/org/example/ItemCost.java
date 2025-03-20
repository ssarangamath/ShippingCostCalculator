package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
public class ItemCost {
    private String product;
    private Integer cost;
    private List<RangeCost> rangeCosts;
}
