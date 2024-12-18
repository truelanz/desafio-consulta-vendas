package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SaleSellerDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SummaryMinProjection;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT obj " +
            "FROM Sale obj " +
            "WHERE obj.date BETWEEN :minDate AND :maxDate " +
            "AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :sellerName, '%'))")
    List<SaleSellerDTO> searchByName(LocalDate minDate, LocalDate maxDate, String sellerName); //, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT s.name AS sellerName, SUM(sale.amount) AS total "
    + "FROM tb_sales sale "
    + "JOIN tb_seller s ON sale.seller_id = s.id "
    + "WHERE sale.date >=:minDate AND sale.date <=:maxDate "
    + "GROUP BY s.name ",
    countQuery = "SELECT s.name AS sellerName, SUM(sale.amount) AS total "
    + "FROM tb_sales sale "
    + "JOIN tb_seller s ON sale.seller_id = s.id "
    + "WHERE sale.date >=:minDate AND sale.date <=:maxDate "
    + "ORDER BY s.name")
    List<SummaryMinProjection> searchSumary(LocalDate minDate, LocalDate maxDate);
}
