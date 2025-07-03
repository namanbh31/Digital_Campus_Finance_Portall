package com.digitalFinancePortal.controller;

import com.digitalFinancePortal.DTO.AddBalanceRequest;
import com.digitalFinancePortal.DTO.PaymentRequest;
import com.digitalFinancePortal.entities.User;
import com.digitalFinancePortal.entities.UserType;
import com.digitalFinancePortal.repositories.TransactionRecorderRepository;
import com.digitalFinancePortal.repositories.UserRepository;
import com.digitalFinancePortal.services.DuesServices;
import com.digitalFinancePortal.services.UserServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.Optional;

@Controller
//@SessionAttributes("user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TransactionRecorderRepository transactionRecorderRepository;
    @Autowired
    UserServices userServices;
    @Autowired
    DuesServices duesServices;
    @GetMapping("/")
    public String getHome(Principal principal) {
        if (principal == null) return "redirect:/login";
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String getDashboard(Principal principal, Model model) {
        User user = userRepository.findById(Long.parseLong(principal.getName()))
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("balance", user.getBalance());
        model.addAttribute("userType", user.getUserType());
        return "dashboard";
    }
    @GetMapping("/login")
    public String getLogin() {
        return "home";
    }
    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/register")
    public String getRegistrationForm(Model model){
       model.addAttribute("user", new User());
       model.addAttribute("userType", UserType.values());
       return "registrationForm";
   }
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public String postRegistrationForm(@ModelAttribute("user") User newuser, BindingResult result, Model model){
        Optional<User> user = userRepository.findById(Long.parseLong(newuser.getAccountNumber().toString()));
        if(user.isPresent()){
            return "redirect:/register";
        }
        User newUser = userRepository.save(newuser);
        model.addAttribute("name", newUser.getAccountNumber());
        return "registrationSuccess";
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'VENDOR')")
   @GetMapping("/make-payment")
   public String getPaymentForm(Model model){
       model.addAttribute("paymentRequest", new PaymentRequest());
       return "paymentRequestForm";
   }
   @Transactional
   @PreAuthorize("hasAnyRole('ADMIN', 'VENDOR')")
   @PostMapping("/make-payment")
   public String processPayment(@ModelAttribute PaymentRequest paymentRequest, Model model, Principal principal) {
        Optional<User> user = userRepository.findById(paymentRequest.getAccountNumber());
        if(user.isEmpty()|| !user.get().getPassword().equals(paymentRequest.getPassword())){ 
            
            return "paymentFailed";
        }
        Boolean paymentSuccess = userServices.tranasferBalance(paymentRequest.getAccountNumber(), Long.parseLong(principal.getName()), paymentRequest.getAmount());
        if(!paymentSuccess){
            return "paymentFailed";
        }
        model.addAttribute("amount", paymentRequest.getAmount());

        return "success";
   }

   @GetMapping("/add-balance")
   public String updateBalance(Principal principal, Model model){
       User user = userRepository.findById(Long.parseLong(principal.getName())).get();
       if(user==null){
           return "redirect:/login";
       }
       AddBalanceRequest req = new AddBalanceRequest();
       Optional<User> userOpt = userRepository.findById(user.getAccountNumber());
       if(userOpt.get().getUserType() !=UserType.Admin){
           req.setAccountNumber(user.getAccountNumber());
           req.setReadOnly(true);
       }
       else{
           req.setReadOnly(false);
       }
       model.addAttribute("addBalanceRequest", req);
       return "addBalanceForm";
   }

    @PostMapping("/add-balance")
    public String addBalance(Principal principal, @ModelAttribute AddBalanceRequest addBalanceRequest, Model model){
       User user = userRepository.findById(Long.parseLong(principal.getName())).get();
       Optional<User> userToUpdate = userRepository.findById(addBalanceRequest.getAccountNumber());
       if(userToUpdate.isEmpty() || addBalanceRequest.getBalance()<0){
           return "redirect:/add-balance";
       }
       Optional<User> updatedUser;
       if(user.getUserType() == UserType.Admin){
           updatedUser = userServices.addBalanceAdmin(addBalanceRequest.getAccountNumber(), addBalanceRequest.getBalance());
       }
       else{
           updatedUser = userServices.addBalanceUser(addBalanceRequest.getAccountNumber(), addBalanceRequest.getBalance());
       }
       user = userRepository.findById(Long.parseLong(principal.getName())).get();
       model.addAttribute("balance", user.getBalance());
       model.addAttribute("userType", user.getUserType());
       return "dashboard";
   }
    @GetMapping("/error")
    public String getError(){
       return "error";
    }
    @GetMapping("/unauthorized")
    public String getUnauthorized(){
       return "unauthorized";
    }

}








