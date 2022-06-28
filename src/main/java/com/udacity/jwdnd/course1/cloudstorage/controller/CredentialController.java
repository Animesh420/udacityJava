package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.modelServices.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.modelServices.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/home")
public class CredentialController {

    private final UserService userService;
    private final CredentialService credentialService;


    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping("/saveCred")
    public RedirectView saveCredential(
                                 @RequestParam("credentialId") Integer credentialid,
                                 @RequestParam("url") String url,
                                 @RequestParam("username") String username,
                                 @RequestParam("password") String password, RedirectAttributes redirectAttrs){
        System.out.println("CREDENTIAL SAVE BREAKPOINT HIT");
        if (credentialid == null){
            int result = this.credentialService.saveCredential(url, username, password, this.userService.getCurrentUserId());
        }
        else {
            int result = this.credentialService.updateCredential(credentialid, url, username,password, this.userService.getCurrentUserId());
        }

        redirectAttrs.addFlashAttribute("show", "cred");
        return  new RedirectView("/home");
    }

    @GetMapping("/delete-cred/{credId}")
    public RedirectView deleteCredential(@PathVariable("credId") Integer credential_id, RedirectAttributes redirectAttrs) {
        System.out.println("CREDENTIAL DELETE BREAKPOINT HIT");
        this.credentialService.deleteCredential(credential_id);
        redirectAttrs.addFlashAttribute("show", "cred");
        return  new RedirectView("/home");
    }


}
