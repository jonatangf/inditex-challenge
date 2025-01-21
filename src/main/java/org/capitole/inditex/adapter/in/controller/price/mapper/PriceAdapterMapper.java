package org.capitole.inditex.adapter.in.controller.price.mapper;

import org.capitole.inditex.adapter.in.controller.price.model.PriceDTO;
import org.capitole.inditex.domain.model.Brand;
import org.capitole.inditex.domain.model.Price;
import org.capitole.inditex.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

import static java.util.Objects.isNull;

@Mapper(componentModel = "spring")
public interface PriceAdapterMapper {

    @Mapping(source = "brand", target = "brandId", qualifiedByName = "mapBrandToId")
    @Mapping(source = "product", target = "productId", qualifiedByName = "mapProductToId")
    PriceDTO mapToDTO(Price price);
    List<PriceDTO> mapToDTO(List<Price> price);

    @Named("mapBrandToId")
    default Long mapBrandToId(Brand brand) {
        return isNull(brand) ? null : brand.getId();
    }

    @Named("mapProductToId")
    default Long mapProductToId(Product product) {
        return isNull(product) ? null : product.getId();
    }
}
