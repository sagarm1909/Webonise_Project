/**
 * 
 */
package com.wb.API;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.wb.requestBody.LoginRequestBody;
import com.wb.requestBody.SignupRequestBody;
import com.wb.services.AuthorizationServices;

/**
 * @author DELL
 *
 */
@RestController
@RequestMapping("/")
public class restAPIs {	
	
	AuthorizationServices authorizationServices = new AuthorizationServices();
	
	@PostMapping(value = "login", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> doLogin(@RequestBody LoginRequestBody requestLoginData) {		
		boolean isLogin = authorizationServices.userLoginAuthorization(requestLoginData.getEmailID(), requestLoginData.getPassword());
		if (isLogin) {
			return new ResponseEntity<String>("Login Successfully.....", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Login Failed.....", HttpStatus.UNAUTHORIZED);
		}
		
	}
	
	@PostMapping(value = "signup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> doSignUp(@RequestBody SignupRequestBody requestSignupData) {
		Map<String, Object> userInfoMap = authorizationServices.userSignupAuthorization(requestSignupData.getFirstName(), requestSignupData.getLastName(), requestSignupData.getEmailID(), requestSignupData.getPassword(), requestSignupData.getCompany(), requestSignupData.getCountry());		
		return new ResponseEntity<Map<String, Object>>(userInfoMap, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "listing/{FilterField}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Object>> doListing(@RequestHeader("APP-Token") String authenticateKey, @PathVariable String FilterField) {
		List<Object> usersList = authorizationServices.userListAuthorization(authenticateKey, FilterField);
		if (usersList.size() > 0) {
			new ResponseEntity<List<Object>>(usersList, HttpStatus.OK);
		} else {
			usersList.add("APP-Token or Field Name doesn't match......");
			return new ResponseEntity<List<Object>>(usersList, HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<List<Object>>(usersList, HttpStatus.OK);
		
	}

}
