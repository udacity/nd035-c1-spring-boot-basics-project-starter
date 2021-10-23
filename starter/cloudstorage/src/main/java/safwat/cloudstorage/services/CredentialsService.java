package safwat.cloudstorage.services;

import java.util.List;


import org.springframework.stereotype.Service;

import safwat.cloudstorage.mappers.CredentialsMapper;
import safwat.cloudstorage.model.Credentials;

@Service
public class CredentialsService {
	CredentialsMapper credentialsMapper;
	EncryptionService encryptionService;
	
	public CredentialsService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
		this.credentialsMapper = credentialsMapper;
		this.encryptionService = encryptionService;
	}
	
	
	public int createCredentials(Credentials credentials){
		
		String key = encryptionService.getAlphaNumericString(16);
	    
		credentials.setKey(key);
		
		String password = credentials.getPassword();
		String encryptedPass = encryptionService.encryptValue(password, key);
		credentials.setPassword(encryptedPass);
		
		//System.out.println("test original pass : " + encryptionService.decryptValue(encryptedPass, key));
		
		return credentialsMapper.insertCredentials(credentials);
	}
	public int updateCredentials(Credentials credentials) {
		System.out.println("test update pass : " + credentials.getPassword());
		System.out.println("test update key : " + credentials.getKey());
		System.out.println("test update id : " + credentials.getCredentialId());
		
		System.out.println("test update : " + credentialsMapper.findCredentialsById(credentials.getCredentialId()));
		
		
		String key = credentialsMapper.findCredentialsById(credentials.getCredentialId()).getKey();
		
		

		String encryptedPass = encryptionService.encryptValue(credentials.getPassword(), key);
		credentials.setPassword(encryptedPass);
		
		return credentialsMapper.updateCredentials(credentials);
	}
	
	public List<Credentials> getAllCredentials(){
		return credentialsMapper.findAllCredentials();
	}
	
	public String getOriginalPass(Credentials credentials) {
		
		String key = credentialsMapper.findCredentialsById(credentials.getCredentialId()).getKey();
		String encryptedPass = credentials.getPassword();
		String pass = encryptionService.decryptValue(encryptedPass, key);
		return pass;
	}
	
	public void deleteCredential(int id) {
		credentialsMapper.deleteCredential(id);
	}
	
}
