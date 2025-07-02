package com.digitalFinancePortal.services;

import com.digitalFinancePortal.entities.Dues;
import com.digitalFinancePortal.entities.TransactionRecorder;
import com.digitalFinancePortal.entities.User;
import com.digitalFinancePortal.repositories.DuesRepository;
import com.digitalFinancePortal.repositories.TransactionRecorderRepository;
import com.digitalFinancePortal.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DuesServices {
    @Autowired
    DuesRepository duesRepository;
    @Autowired
    UserServices userServices;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TransactionRecorderRepository transactionRecorderRepository;
    public Optional<Dues> addDues(Dues dues){
        Optional<Dues> due;
        if(userServices.exists(dues.getAccountNumber())){
            due = Optional.of(duesRepository.save(dues));
            return due;
        }
        return Optional.empty();
    }
    public Optional<List<Dues>> getAllDues(Long accountNumber){
        return duesRepository.findAllByAccountNumber(accountNumber);
    }
    @Transactional
    public Boolean settleDue(Long id){
        Optional<Dues> duesOpt = duesRepository.findById(id);
        Optional<User> user = userRepository.findById(duesOpt.get().getAccountNumber());
        Optional<User> college = userRepository.findById(Long.parseLong("1"));
        if(user.get().getBalance()<duesOpt.get().getAmount()){
            return false;
        }
        TransactionRecorder recorder = new TransactionRecorder();
        recorder.setReceiverAccount(1);
        recorder.setSenderAccount(user.get().getAccountNumber());
        recorder.setAmount(duesOpt.get().getAmount());
        recorder.setDescription("Dues settlement");
        recorder.setTimestamp(LocalDateTime.now());
        transactionRecorderRepository.save(recorder);
        user.get().setBalance(user.get().getBalance()-duesOpt.get().getAmount());
        userRepository.save(user.get());
        college.get().setBalance(college.get().getBalance()+duesOpt.get().getAmount());
        userRepository.save(college.get());
        duesRepository.delete(duesOpt.get());
        return true;
    }
}
