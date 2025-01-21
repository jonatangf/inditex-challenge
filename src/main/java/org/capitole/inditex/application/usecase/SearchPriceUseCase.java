package org.capitole.inditex.application.usecase;

import org.capitole.inditex.application.model.ApplicationPricePage;
import org.capitole.inditex.application.model.SearchPriceApplicationParameters;

public interface SearchPriceUseCase {

    ApplicationPricePage searchPrice(SearchPriceApplicationParameters searchPriceApplicationParameters);
}
