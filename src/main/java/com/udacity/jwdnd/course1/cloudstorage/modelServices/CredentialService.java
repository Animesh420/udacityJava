package com.udacity.jwdnd.course1.cloudstorage.modelServices;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final HashService hashService;
    private final UserService userService;
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;
    private final String encryptionKey = "SECRET_KEY_CREDENTIAL_ID";

    public CredentialService(HashService hashService, UserService userService, EncryptionService encryptionService, CredentialMapper credentialMapper) {
        this.hashService = hashService;
        this.userService = userService;
        this.encryptionService = encryptionService;
        this.credentialMapper = credentialMapper;
    }
    public Credential getCredential(String url, String username, String password, Integer userId){
        Credential credential = new Credential();
        credential.setUrl(url);
        credential.setUsername(username);
        credential.setPassword(this.encryptionService.encryptValue(password, encryptionKey));
        credential.setUserid(userId);
        return credential;
    }

    public int saveCredential(String url, String username, String password, Integer userId){

        Credential credential =  getCredential(url, username, password, userId);
        return this.credentialMapper.insertCredential(credential);

    }

    public int updateCredential(Integer credential_id, String url, String username,String password, Integer userId) {
        Credential credential =  getCredential(url, username, password, userId);
        credential.setCredentialid(credential_id);
        return this.credentialMapper.updateCredential(credential);
    }

    public void deleteCredential(Integer credentialId){
        this.credentialMapper.deleteCredential(credentialId);
    }

    public ArrayList<Credential> getAllCredential(int userId) {
        List<Credential> results = this.credentialMapper.getCredentialsByUserId(userId);
        ArrayList<Credential> decryptedResults = new ArrayList<>();
        for (Credential cred : results) {
            cred.setDecryptedPassword(this.encryptionService.decryptValue(cred.getPassword(), encryptionKey));
            decryptedResults.add(cred);
        }
        return decryptedResults;
    }
}
