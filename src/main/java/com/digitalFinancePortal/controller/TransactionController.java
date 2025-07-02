package com.digitalFinancePortal.controller;

import com.digitalFinancePortal.entities.TransactionRecorder;
import com.digitalFinancePortal.entities.User;
import com.digitalFinancePortal.repositories.TransactionRecorderRepository;
import com.digitalFinancePortal.services.TransactionRecorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller

public class TransactionController {
    @Autowired
    TransactionRecorderService transactionRecorderService;

    @GetMapping("/statements")
    public String getStatements(Principal principal, Model model){

        Optional<List<TransactionRecorder>> records = transactionRecorderService.getAllTransactions(Long.parseLong(principal.getName()));
        model.addAttribute("transactions", records.get());
        model.addAttribute("accountNumber", principal.getName());
        return "statementTable";
    }
}
