package org.capitole.inditex.adapter.in.controller.price.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PriceDTO {
    private Long brandId;
    private Long productId;
    private Integer priceList;
    private String startDate;
    private String endDate;
    private BigDecimal price;
}
