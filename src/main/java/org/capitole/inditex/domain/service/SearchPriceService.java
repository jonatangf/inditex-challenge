package org.capitole.inditex.domain.service;

import lombok.RequiredArgsConstructor;
import org.capitole.inditex.domain.model.PricePage;
import org.capitole.inditex.domain.model.SearchPriceDomainParameters;
import org.capitole.inditex.domain.port.PriceDomainRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchPriceService {

    private final PriceDomainRepository priceDomainRepository;

    public PricePage searchPrice(SearchPriceDomainParameters searchPriceDomainParameters) {
        return priceDomainRepository.findPricesByParametersAndHighestPriority(searchPriceDomainParameters);
    }
}
