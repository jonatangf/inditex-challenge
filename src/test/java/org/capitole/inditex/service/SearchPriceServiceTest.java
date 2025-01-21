package org.capitole.inditex.service;

import org.capitole.inditex.domain.model.PricePage;
import org.capitole.inditex.domain.model.SearchPriceDomainParameters;
import org.capitole.inditex.domain.port.PriceDomainRepository;
import org.capitole.inditex.domain.service.SearchPriceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchPriceServiceTest {

    @Mock
    private PriceDomainRepository priceDomainRepository;
    @InjectMocks
    private SearchPriceService searchPriceService;

    @Test
    void shouldSearchPrices() {
        when(priceDomainRepository.findPricesByParametersAndHighestPriority(any(SearchPriceDomainParameters.class)))
                .thenReturn(PricePage.builder()
                                    .page(0)
                                    .pageSize(10)
                                    .totalPages(1)
                                    .totalElements(1L)
                                    .build());

        var result = searchPriceService.searchPrice(SearchPriceDomainParameters.builder().build());

        assertThat(result).isNotNull();
    }

}