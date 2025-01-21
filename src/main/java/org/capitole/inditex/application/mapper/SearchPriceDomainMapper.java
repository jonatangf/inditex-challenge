package org.capitole.inditex.application.mapper;

import org.capitole.inditex.application.model.SearchPriceApplicationParameters;
import org.capitole.inditex.domain.model.SearchPriceDomainParameters;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SearchPriceDomainMapper {

    SearchPriceDomainParameters mapToDomain(SearchPriceApplicationParameters searchPriceApplicationParameters);
}
