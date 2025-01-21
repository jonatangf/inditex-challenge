package org.capitole.inditex.domain.port;

import org.capitole.inditex.domain.model.PricePage;
import org.capitole.inditex.domain.model.SearchPriceDomainParameters;

public interface PriceDomainRepository {

    PricePage findPricesByParametersAndHighestPriority(SearchPriceDomainParameters searchPriceDomainParameters);
}
