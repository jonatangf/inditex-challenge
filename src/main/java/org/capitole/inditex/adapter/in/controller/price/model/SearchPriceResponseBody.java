package org.capitole.inditex.adapter.in.controller.price.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SearchPriceResponseBody {
    private List<PriceDTO> prices;
    private Integer page;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalElements;
}
