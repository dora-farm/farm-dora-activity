package com.farmdora.farmdoraactivity.admin.repository;

import com.farmdora.farmdoraactivity.entity.Sale;
import com.farmdora.farmdoraactivity.entity.SaleFile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleFileRepository extends JpaRepository<SaleFile, Integer> {
    Optional<SaleFile> findBySaleAndIsMainIsFalse(Sale sale);
}
