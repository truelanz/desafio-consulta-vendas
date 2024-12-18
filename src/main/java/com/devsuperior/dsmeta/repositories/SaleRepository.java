package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devsuperior.dsmeta.dto.SaleSellerDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT obj " +
            "FROM Sale obj " +
            "WHERE obj.date BETWEEN :minDate AND :maxDate " +
            "AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :sellerName, '%'))")
    Page<SaleSellerDTO> searchByName(LocalDate minDate, LocalDate maxDate, String sellerName, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT s.name AS sellerName, SUM(sales.amount) AS TOTAL "
    + "FROM tb_sales sales "
    + "JOIN tb_seller s ON sales.seller_id = s.id "
    + "WHERE sales.date BETWEEN :minDate AND :maxDate "
    + "GROUP BY s.name ",
    countQuery = "SELECT s.name AS sellerName, SUM(sales.amount) AS totalAmount "
    + "FROM tb_sales sales "
    + "JOIN tb_seller s ON sales.seller_id = s.id "
    + "WHERE sales.date BETWEEN minDate AND sales.date "
    + "ORDER BY s.name")
    List<SaleSellerDTO> searchSumary(@Param("minDate")LocalDate minDate, @Param("maxDate") LocalDate maxDate); 
}
