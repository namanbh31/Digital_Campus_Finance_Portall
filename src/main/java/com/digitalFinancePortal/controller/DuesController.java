package com.digitalFinancePortal.controller;

import com.digitalFinancePortal.entities.Dues;
import com.digitalFinancePortal.entities.User;
import com.digitalFinancePortal.entities.UserType;
import com.digitalFinancePortal.repositories.DuesRepository;
import com.digitalFinancePortal.repositories.UserRepository;
import com.digitalFinancePortal.services.DuesServices;
import com.digitalFinancePortal.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class DuesController {
    @Autowired
    DuesRepository duesRepository;
    @Autowired
    DuesServices duesServices;
    @Autowired
    UserRepository userRepository;

   @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add-dues")
    public String getDuesForm(Principal principal, Model model){
        User user = userRepository.findById(Long.parseLong(principal.getName())).get();
        if(user.getUserType()!= UserType.Admin){
            return "redirect:/unauthorized";
        }
        model.addAttribute("dues", new Dues());
        return "addDuesForm";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all-dues")
    public String getAdminDues(Principal principal, Model model){
        Iterable<Dues> duesList = duesRepository.findAll();
        model.addAttribute("duesList", duesList);
        model.addAttribute("accountNumber", principal.getName());
        return "duesTable";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-dues")
    public String addDues(Principal principal, @ModelAttribute("dues") Dues dues, Model model){
        Optional<User> user = userRepository.findById(dues.getAccountNumber());
        if(user.isEmpty()){
            return "error";
        }
        duesServices.addDues(dues);

        return "redirect:/dashboard";

    }
//    @PreAuthorize("hasRole('USER')")
    @GetMapping("/dues")
    public String getDues(Authentication authentication, Model model){
        User user = userRepository.findById(Long.parseLong(authentication.getName())).get();
        Optional<List<Dues>> duesList = duesServices.getAllDues(user.getAccountNumber());
        model.addAttribute("duesList", duesList.get());
        model.addAttribute("accountNumber", authentication.getName());
        return "duesTable";
    }
    @PostMapping("settle-dues/{id}")
    public String settleDues(Principal principal, @PathVariable("id") Long id, Model model){
        User user = userRepository.findById(Long.parseLong(principal.getName())).get();
        if(user == null){
            return "redirect:/login";
        }
        duesServices.settleDue(id);
        Optional<User> updatedUser = userRepository.findById(user.getAccountNumber());
        model.addAttribute("balance", updatedUser.get().getBalance());
        model.addAttribute("userType", user.getUserType());
        return "redirect:/dashboard";
    }

}
