package org.capitole.inditex.impl;

import org.capitole.inditex.application.mapper.PricePageMapperImpl;
import org.capitole.inditex.application.mapper.SearchPriceDomainMapperImpl;
import org.capitole.inditex.application.model.SearchPriceApplicationParameters;
import org.capitole.inditex.application.usecase.impl.SearchPriceUseCaseImpl;
import org.capitole.inditex.domain.model.Price;
import org.capitole.inditex.domain.model.PricePage;
import org.capitole.inditex.domain.service.SearchPriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchPriceUseCaseImplTest {

    @Mock
    private SearchPriceService searchPriceService;
    @InjectMocks
    private SearchPriceUseCaseImpl searchPriceUseCase;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(searchPriceUseCase, "searchPriceDomainMapper", new SearchPriceDomainMapperImpl());
        ReflectionTestUtils.setField(searchPriceUseCase, "pricePageMapper", new PricePageMapperImpl());
    }

    @Test
    void shouldSearchPrices() {
        when(searchPriceService.searchPrice(any())).thenReturn(PricePage.builder()
                                                                       .page(0)
                                                                       .pageSize(20)
                                                                       .totalPages(1)
                                                                       .totalElements(1L)
                                                                       .prices(List.of(Price.builder().build()))
                                                                       .build());

        var result = searchPriceUseCase.searchPrice(SearchPriceApplicationParameters.builder().build());

        verify(searchPriceService).searchPrice(any());

        assertThat(result).isNotNull();
        assertThat(result.getPrices()).isNotEmpty();
        assertThat(result.getPage()).isZero();
        assertThat(result.getPageSize()).isEqualTo(20);
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getTotalElements()).isEqualTo(1);
    }

}