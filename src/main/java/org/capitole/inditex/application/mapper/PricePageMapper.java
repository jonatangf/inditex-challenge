package org.capitole.inditex.application.mapper;

import org.capitole.inditex.application.model.ApplicationPricePage;
import org.capitole.inditex.domain.model.PricePage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PriceMapper.class})
public interface PricePageMapper {

    ApplicationPricePage mapToApplicationPricePage(PricePage pricePage);
}
