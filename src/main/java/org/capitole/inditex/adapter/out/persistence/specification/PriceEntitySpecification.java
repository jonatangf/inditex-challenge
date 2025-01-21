package org.capitole.inditex.adapter.out.persistence.specification;

import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.capitole.inditex.adapter.out.persistence.model.PriceEntity;
import org.capitole.inditex.domain.model.SearchPriceDomainParameters;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PriceEntitySpecification implements Specification<PriceEntity> {

    private static final String PRIORITY = "priority";
    private static final String BRAND = "brand";
    private static final String PRODUCT = "product";
    private static final String START_DATE = "startDate";
    private static final String END_DATE = "endDate";
    private static final String ID = "id";

    private final SearchPriceDomainParameters searchPriceDomainParameters;

    /**
     * Builds the main predicate for the PriceEntity query.
     *
     * @param root            the root type in the query
     * @param query           the query instance
     * @param criteriaBuilder criteria builder for creating query predicates
     * @return the combined predicate for the query
     */
    @Override
    public Predicate toPredicate(Root<PriceEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        getBrandIdPredicate(root, criteriaBuilder).ifPresent(predicates::add);
        getProductIdPredicate(root, criteriaBuilder).ifPresent(predicates::add);
        getStartDatePredicate(root, criteriaBuilder).ifPresent(predicates::add);
        getEndDatePredicate(root, criteriaBuilder).ifPresent(predicates::add);

        predicates.add(
                criteriaBuilder.equal(root.get(PRIORITY),
                                      getMaximumPrioritySubQuery(root, query, criteriaBuilder)
                )
        );
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Optional<Predicate> getStartDatePredicate(Root<PriceEntity> root, CriteriaBuilder criteriaBuilder) {
        return Optional.ofNullable(searchPriceDomainParameters.getEffectiveDate())
                .map(effectiveDate -> criteriaBuilder.lessThanOrEqualTo(root.get(START_DATE), effectiveDate));
    }

    private Optional<Predicate> getEndDatePredicate(Root<PriceEntity> root, CriteriaBuilder criteriaBuilder) {
        return Optional.ofNullable(searchPriceDomainParameters.getEffectiveDate())
                .map(effectiveDate -> criteriaBuilder.greaterThanOrEqualTo(root.get(END_DATE), effectiveDate));
    }

    private Optional<Predicate> getProductIdPredicate(Root<PriceEntity> root, CriteriaBuilder criteriaBuilder) {
        return Optional.ofNullable(searchPriceDomainParameters.getProductId())
                .map(productId -> criteriaBuilder.equal(root.get(PRODUCT).get(ID), productId));
    }

    private Optional<Predicate> getBrandIdPredicate(Root<PriceEntity> root, CriteriaBuilder criteriaBuilder) {
        return Optional.ofNullable(searchPriceDomainParameters.getBrandId())
                .map(brandId -> criteriaBuilder.equal(root.get(BRAND).get(ID), brandId));
    }

    /**
     * Constructs a subquery to find the maximum priority for prices matching the criteria.
     * The subquery dynamically adapts based on the presence of an effective date.
     *
     * @param root            the root type in the main query
     * @param query           the main query instance
     * @param criteriaBuilder criteria builder for creating predicates
     * @return a subquery selecting the maximum priority
     */
    private Subquery<Integer> getMaximumPrioritySubQuery(
            Root<PriceEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder
    ) {
        Subquery<Integer> subquery = query.subquery(Integer.class);
        Root<PriceEntity> subRoot = subquery.from(PriceEntity.class);

        Instant effectiveDate = searchPriceDomainParameters.getEffectiveDate();

        List<Predicate> subqueryPredicates = new ArrayList<>();
        subqueryPredicates.add(criteriaBuilder.equal(subRoot.get(BRAND).get(ID), root.get(BRAND).get(ID)));
        subqueryPredicates.add(criteriaBuilder.equal(subRoot.get(PRODUCT).get(ID), root.get(PRODUCT).get(ID)));

        if (effectiveDate != null) {
            subqueryPredicates.add(criteriaBuilder.lessThanOrEqualTo(subRoot.get(START_DATE), effectiveDate));
            subqueryPredicates.add(criteriaBuilder.greaterThanOrEqualTo(subRoot.get(END_DATE), effectiveDate));
        } else {
            subqueryPredicates.add(criteriaBuilder.lessThanOrEqualTo(subRoot.get(START_DATE), root.get(START_DATE)));
            subqueryPredicates.add(criteriaBuilder.greaterThanOrEqualTo(subRoot.get(END_DATE), root.get(END_DATE)));
        }

        subquery.select(criteriaBuilder.max(subRoot.get(PRIORITY))).where(subqueryPredicates.toArray(new Predicate[0]));

        return subquery;
    }
}
