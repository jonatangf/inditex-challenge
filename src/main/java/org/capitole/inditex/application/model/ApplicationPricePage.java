package org.capitole.inditex.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.capitole.inditex.domain.model.Price;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ApplicationPricePage {
    private List<Price> prices;
    private Integer page;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalElements;
}
