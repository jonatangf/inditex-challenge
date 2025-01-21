package org.capitole.inditex.adapter.in.controller.price;

import org.capitole.inditex.adapter.in.controller.price.mapper.PriceAdapterMapperImpl;
import org.capitole.inditex.adapter.in.controller.price.mapper.SearchPriceAdapterMapperImpl;
import org.capitole.inditex.adapter.in.controller.price.model.SearchPriceModelAttribute;
import org.capitole.inditex.application.model.ApplicationPricePage;
import org.capitole.inditex.application.model.SearchPriceApplicationParameters;
import org.capitole.inditex.application.usecase.SearchPriceUseCase;
import org.capitole.inditex.domain.model.Price;
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
class PriceControllerImplTest {

    @Mock
    private SearchPriceUseCase searchPriceUseCase;
    @InjectMocks
    private PriceControllerImpl priceController;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(priceController, "searchPriceAdapterMapper", new SearchPriceAdapterMapperImpl());
        ReflectionTestUtils.setField(priceController, "priceAdapterMapper", new PriceAdapterMapperImpl());

    }

    @Test
    void shouldSearchPrices() {
        when(searchPriceUseCase.searchPrice(any(SearchPriceApplicationParameters.class)))
                .thenReturn(ApplicationPricePage.builder()
                                    .prices(List.of(Price.builder().build()))
                                    .page(0)
                                    .pageSize(20)
                                    .totalPages(1)
                                    .totalElements(1L)
                                    .build());

        var searchPriceResponseBody = priceController.searchPrice(SearchPriceModelAttribute.builder().build());

        verify(searchPriceUseCase).searchPrice(any(SearchPriceApplicationParameters.class));
        assertThat(searchPriceResponseBody).isNotNull();
        assertThat(searchPriceResponseBody.getPrices()).isNotEmpty();
        assertThat(searchPriceResponseBody.getPage()).isZero();
        assertThat(searchPriceResponseBody.getPageSize()).isEqualTo(20);
        assertThat(searchPriceResponseBody.getTotalPages()).isEqualTo(1);
        assertThat(searchPriceResponseBody.getTotalElements()).isEqualTo(1);
    }

}