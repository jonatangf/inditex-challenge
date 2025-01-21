package org.capitole.inditex.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PricePage {

    private List<Price> prices;
    private Integer page;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalElements;
}
