package org.capitole.inditex.adapter.out.persistence.entityrepository;

import org.capitole.inditex.adapter.out.persistence.model.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceEntityRepository extends JpaRepository<PriceEntity, Long>, JpaSpecificationExecutor<PriceEntity> {

}
