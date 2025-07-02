package com.digitalFinancePortal.services;

import com.digitalFinancePortal.entities.TransactionRecorder;
import com.digitalFinancePortal.entities.User;
import com.digitalFinancePortal.repositories.TransactionRecorderRepository;
import com.digitalFinancePortal.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServices {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TransactionRecorderRepository transactionRecorderRepository;

    @Transactional
    public Optional<User> addBalanceUser(Long accountNumber, Long amount){
        Optional<User> user = userRepository.findById(accountNumber);
        if(user.isEmpty()){
            return Optional.empty();
        }
        user.get().setBalance(user.get().getBalance()+amount);
        User updatedUser = userRepository.save(user.get());
        TransactionRecorder recorder = new TransactionRecorder();
        recorder.setAmount(amount);
        recorder.setDescription("adding balance online");
        recorder.setTimestamp(LocalDateTime.now());
        recorder.setSenderAccount(1);
        recorder.setReceiverAccount(accountNumber);
        transactionRecorderRepository.save(recorder);
        return Optional.ofNullable(updatedUser);
    }
    @Transactional
    public Optional<User> addBalanceAdmin(Long accountNumber, Long amount){
        Optional<User> user = userRepository.findById(accountNumber);
        if(user.isEmpty()){
            return Optional.empty();
        }
        user.get().setBalance(user.get().getBalance()+amount);
        User updatedUser = userRepository.save(user.get());
        TransactionRecorder recorder = new TransactionRecorder();
        recorder.setAmount(amount);
        recorder.setDescription("adding balance through cash");
        recorder.setTimestamp(LocalDateTime.now());
        recorder.setSenderAccount(1);
        recorder.setReceiverAccount(accountNumber);
        transactionRecorderRepository.save(recorder);
        return Optional.of(updatedUser);
    }
    @Transactional
    public boolean tranasferBalance(Long senderAccount, Long receiverAccount, Long amount){
        Optional<User> sender = userRepository.findById(senderAccount);
        Optional<User> receiver = userRepository.findById(receiverAccount);
        if(sender.isEmpty() || receiver.isEmpty()){
            return false;
        }
        if(sender.get().getBalance() < amount){
            return false;
        }
        sender.get().setBalance(sender.get().getBalance()-amount);
        receiver.get().setBalance(receiver.get().getBalance()+amount);
        userRepository.save(sender.get());
        userRepository.save(receiver.get());
        TransactionRecorder recorder = new TransactionRecorder();
        recorder.setAmount(amount);
        recorder.setDescription("transfer balance");
        recorder.setTimestamp(LocalDateTime.now());
        recorder.setSenderAccount(senderAccount);
        recorder.setReceiverAccount(receiverAccount);
        transactionRecorderRepository.save(recorder);
        return true;
    }
    public Boolean exists(Long accountNumber){
        Optional<User> user = userRepository.findById(accountNumber);
        if(user.isEmpty()){
            return false;
        }
        return true;
    }
}
