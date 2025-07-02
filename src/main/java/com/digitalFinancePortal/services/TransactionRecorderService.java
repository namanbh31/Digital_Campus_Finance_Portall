package com.digitalFinancePortal.services;

import com.digitalFinancePortal.entities.TransactionRecorder;
import com.digitalFinancePortal.repositories.TransactionRecorderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionRecorderService {
    @Autowired
    TransactionRecorderRepository transactionRecorderRepository;
    public Optional<List<TransactionRecorder>> getAllTransactions(Long accountNumber){
        List<TransactionRecorder> records = transactionRecorderRepository.findBySenderAccountOrReceiverAccount
                (accountNumber, accountNumber);
        return Optional.ofNullable(records);
    }

}
