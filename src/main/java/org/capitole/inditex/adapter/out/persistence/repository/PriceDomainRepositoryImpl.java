package org.capitole.inditex.adapter.out.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.capitole.inditex.adapter.out.persistence.entityrepository.PriceEntityRepository;
import org.capitole.inditex.adapter.out.persistence.model.PriceEntity;
import org.capitole.inditex.adapter.out.persistence.specification.PriceEntitySpecification;
import org.capitole.inditex.application.mapper.PriceMapper;
import org.capitole.inditex.domain.model.PricePage;
import org.capitole.inditex.domain.model.SearchPriceDomainParameters;
import org.capitole.inditex.domain.port.PriceDomainRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PriceDomainRepositoryImpl implements PriceDomainRepository {

    private final PriceEntityRepository priceEntityRepository;
    private final PriceMapper priceMapper;

    @Override
    public PricePage findPricesByParametersAndHighestPriority(
            SearchPriceDomainParameters searchPriceDomainParameters
    ) {
        Page<PriceEntity> priceEntityPage = priceEntityRepository.findAll(
                new PriceEntitySpecification(searchPriceDomainParameters),
                PageRequest.of(
                        searchPriceDomainParameters.getPage(),
                        searchPriceDomainParameters.getSize(),
                        Sort.by(Sort.Order.asc("id"))
                )
        );

        return PricePage.builder()
                .prices(priceMapper.mapToDomain(priceEntityPage.get().toList()))
                .page(priceEntityPage.getNumber())
                .pageSize(priceEntityPage.getSize())
                .totalPages(priceEntityPage.getTotalPages())
                .totalElements(priceEntityPage.getTotalElements())
                .build();
    }
}
