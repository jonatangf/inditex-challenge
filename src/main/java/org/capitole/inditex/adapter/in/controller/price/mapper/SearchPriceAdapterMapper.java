package org.capitole.inditex.adapter.in.controller.price.mapper;

import org.capitole.inditex.adapter.in.controller.price.model.SearchPriceModelAttribute;
import org.capitole.inditex.application.model.SearchPriceApplicationParameters;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.util.StringUtils;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface SearchPriceAdapterMapper {

    @Mapping(target = "effectiveDate", source = "effectiveDate", qualifiedByName = "mapToInstant")
    SearchPriceApplicationParameters mapToApplicationParameters(SearchPriceModelAttribute searchPriceModelAttribute);

    @Named("mapToInstant")
    default Instant mapToInstant(String effectiveDate) {
        return StringUtils.hasText(effectiveDate) ? Instant.parse(effectiveDate) : null;
    }
}
