package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSellerDTO;
import com.devsuperior.dsmeta.dto.SummaryMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SummaryMinProjection;
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

	//findReport
	public List<SaleSellerDTO> findReport(String minDate, String maxDate, String name, Pageable pageable) {

		//Sefinindo data atual do sistema
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		//Caso não houver passado a data maxima, retornar a data atual, caso contrário, retornar a data que foi passada.
        LocalDate finalDate = (maxDate == null || maxDate.equals("")) ? today : LocalDate.parse(maxDate);
		//Caso não houver passado a data minima, retornar a data de 1 anos anterior à data maxima, caso contrário, retornar a data que foi passada.
        LocalDate initialDate = (minDate == null || minDate.equals("")) ? finalDate.minusYears(1L) : LocalDate.parse(minDate);

        List<SaleSellerDTO> result = repository.searchByName(initialDate, finalDate, name); //, pageable);
        return result;
    }

	//SearchBySymmary
	public List<SummaryMinDTO> findSummary(String minDate, String maxDate){

		// Determinando as datas inicial e final
        LocalDate initialDate;
        LocalDate finalDate;
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		if ((minDate == null || minDate.equals("")) && (maxDate == null || maxDate.equals(""))) {
            // Se nenhuma data for fornecida, usar período de 1 ano antes até hoje
            initialDate = today.minusYears(1L);
            finalDate = today;
        } else {
            // Se datas forem fornecidas, parseá-las ou usar os padrões
            initialDate = LocalDate.parse(minDate);
            finalDate = LocalDate.parse(maxDate);
        }

        List<SummaryMinProjection> list = repository.searchSumary(initialDate, finalDate);
        List<SummaryMinDTO> dto = list.stream().map(x -> new SummaryMinDTO(x)).collect(Collectors.toList());
		return dto;
	}

}
