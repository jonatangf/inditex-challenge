package org.capitole.inditex.application.usecase.impl;

import lombok.RequiredArgsConstructor;
import org.capitole.inditex.application.mapper.PricePageMapper;
import org.capitole.inditex.application.mapper.SearchPriceDomainMapper;
import org.capitole.inditex.application.model.ApplicationPricePage;
import org.capitole.inditex.application.model.SearchPriceApplicationParameters;
import org.capitole.inditex.application.usecase.SearchPriceUseCase;
import org.capitole.inditex.domain.service.SearchPriceService;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SearchPriceUseCaseImpl implements SearchPriceUseCase {

    private final SearchPriceService searchPriceService;
    private final SearchPriceDomainMapper searchPriceDomainMapper;
    private final PricePageMapper pricePageMapper;

    @Override
    public ApplicationPricePage searchPrice(SearchPriceApplicationParameters searchPriceApplicationParameters) {
        return pricePageMapper.mapToApplicationPricePage(
                searchPriceService
                        .searchPrice(searchPriceDomainMapper.mapToDomain(searchPriceApplicationParameters))
        );
    }
}
