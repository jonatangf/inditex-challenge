package org.capitole.inditex.adapter.out.persistence.repository;

import org.capitole.inditex.adapter.out.persistence.entityrepository.PriceEntityRepository;
import org.capitole.inditex.adapter.out.persistence.model.PriceEntity;
import org.capitole.inditex.adapter.out.persistence.specification.PriceEntitySpecification;
import org.capitole.inditex.application.mapper.PriceMapperImpl;
import org.capitole.inditex.domain.model.PricePage;
import org.capitole.inditex.domain.model.SearchPriceDomainParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceDomainRepositoryImplTest {

    @Mock
    private PriceEntityRepository priceEntityRepository;

    @InjectMocks
    private PriceDomainRepositoryImpl priceDomainRepository;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(priceDomainRepository, "priceMapper", new PriceMapperImpl());
    }

    @Test
    void shouldFindPricesByParametersAndHighestPriority() {
        Page<PriceEntity> priceEntityPage = new PageImpl<>(
                List.of(PriceEntity.builder().build(), PriceEntity.builder().build()),
                PageRequest.of(0, 10, Sort.by(Sort.Order.asc("id"))), 2);
        when(priceEntityRepository.findAll(any(PriceEntitySpecification.class), any(PageRequest.class)))
                .thenReturn(priceEntityPage);

        PricePage result = priceDomainRepository
                .findPricesByParametersAndHighestPriority(SearchPriceDomainParameters.builder().build());

        assertThat(result).isNotNull();
        assertThat(result.getPrices()).isNotEmpty().hasSize(2);
        assertThat(result.getPage()).isZero();
        assertThat(result.getPageSize()).isEqualTo(10);
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getTotalElements()).isEqualTo(2L);
    }
}