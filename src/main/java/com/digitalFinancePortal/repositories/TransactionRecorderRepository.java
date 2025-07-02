package com.digitalFinancePortal.repositories;

import com.digitalFinancePortal.entities.TransactionRecorder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionRecorderRepository extends CrudRepository<TransactionRecorder, Long> {
    public List<TransactionRecorder> findBySenderAccountOrReceiverAccount(Long senderAccount, Long receiverAmount);
}
