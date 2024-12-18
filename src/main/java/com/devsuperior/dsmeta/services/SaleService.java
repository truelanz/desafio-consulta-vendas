package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSellerDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleSellerDTO> findReport(String minDate, String maxDate, String name, Pageable pageable) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
        LocalDate finalDate = (maxDate == null || maxDate.equals("")) ? today : LocalDate.parse(maxDate);
        LocalDate initialDate = (minDate == null || minDate.equals("")) ? finalDate.minusYears(1L) : LocalDate.parse(minDate);

        Page<SaleSellerDTO> result = repository.searchByName(initialDate, finalDate, name, pageable);
        return result;
    }

	/* //SearchBySymmary
	public List<SaleSellerDTO> searchSumary(String minDate, String maxDate){
		LocalDate min = this.validateMinDate(minDate);
		LocalDate max = this.validateMaxDate(maxDate);

		return repository.searchSumary(min, max).stream().map(x -> new SaleSellerDTO(x)).toList();
	} */

}
