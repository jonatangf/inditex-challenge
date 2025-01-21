package org.capitole.inditex.application.mapper;

import org.capitole.inditex.adapter.out.persistence.model.BrandEntity;
import org.capitole.inditex.adapter.out.persistence.model.PriceEntity;
import org.capitole.inditex.adapter.out.persistence.model.ProductEntity;
import org.capitole.inditex.domain.model.Brand;
import org.capitole.inditex.domain.model.Price;
import org.capitole.inditex.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

import static java.util.Objects.nonNull;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    @Mapping(source = "brand", target = "brand", qualifiedByName = "mapBrandToDomain")
    @Mapping(source = "product", target = "product", qualifiedByName = "mapProductToDomain")
    Price mapToDomain(PriceEntity priceEntity);

    List<Price> mapToDomain(List<PriceEntity> priceEntity);

    PriceEntity mapToEntity(Price price);

    @Named("mapBrandToDomain")
    default Brand mapBrandToDomain(BrandEntity brandEntity) {
        if (nonNull(brandEntity)) {
            return Brand.builder()
                    .id(brandEntity.getId())
                    .name(brandEntity.getName())
                    .build();
        } else {
            return null;
        }
    }

    @Named("mapProductToDomain")
    default Product mapProductToDomain(ProductEntity productEntity) {
        if (nonNull(productEntity)) {
            return Product.builder()
                    .id(productEntity.getId())
                    .code(productEntity.getCode())
                    .build();
        } else {
            return null;
        }
    }
}
