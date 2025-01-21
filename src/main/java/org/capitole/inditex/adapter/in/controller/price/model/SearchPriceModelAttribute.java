package org.capitole.inditex.adapter.in.controller.price.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SearchPriceModelAttribute {
    private Long brandId;
    private Long productId;
    private String effectiveDate;
    @Builder.Default
    private Integer page = 0;
    @Builder.Default
    private Integer size = 20;
}
