package org.capitole.inditex.adapter.in.controller.price;

import lombok.RequiredArgsConstructor;
import org.capitole.inditex.adapter.in.controller.price.mapper.PriceAdapterMapper;
import org.capitole.inditex.adapter.in.controller.price.mapper.SearchPriceAdapterMapper;
import org.capitole.inditex.adapter.in.controller.price.model.SearchPriceModelAttribute;
import org.capitole.inditex.adapter.in.controller.price.model.SearchPriceResponseBody;
import org.capitole.inditex.application.model.ApplicationPricePage;
import org.capitole.inditex.application.usecase.SearchPriceUseCase;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PriceControllerImpl implements PriceController {

    private final SearchPriceAdapterMapper searchPriceAdapterMapper;
    private final SearchPriceUseCase searchPriceUseCase;
    private final PriceAdapterMapper priceAdapterMapper;

    public SearchPriceResponseBody searchPrice(
            SearchPriceModelAttribute searchPriceModelAttribute
    ) {
        ApplicationPricePage pricePage = searchPriceUseCase
                .searchPrice(searchPriceAdapterMapper.mapToApplicationParameters(searchPriceModelAttribute));

        return SearchPriceResponseBody.builder()
                .prices(priceAdapterMapper.mapToDTO(pricePage.getPrices()))
                .page(pricePage.getPage())
                .pageSize(pricePage.getPageSize())
                .totalPages(pricePage.getTotalPages())
                .totalElements(pricePage.getTotalElements())
                .build();
    }
}
