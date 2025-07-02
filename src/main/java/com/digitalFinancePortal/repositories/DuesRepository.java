package com.digitalFinancePortal.repositories;

import com.digitalFinancePortal.entities.Dues;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DuesRepository extends CrudRepository<Dues, Long> {
    public Optional<List<Dues>> findAllByAccountNumber(Long accountNumber);
}
