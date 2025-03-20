package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class RangeCost {
    private Integer minQuantity;
    private Integer maxQuantity;
    private Integer cost;
}
