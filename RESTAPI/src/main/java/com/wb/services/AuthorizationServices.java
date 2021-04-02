/**
 * 
 */
package com.wb.services;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.wb.requestBody.SignupRequestBody;

/**
 * @author DELL
 *
 */
public class AuthorizationServices {
	
	
	static Map<String, Object> userDataMap = new LinkedHashMap<String, Object>();
	
	public boolean userLoginAuthorization(String emailID, String password) {		
		// TODO Auto-generated method stub
		try {
			String concatString = emailID + ":" + password;
			if (!userDataMap.isEmpty() && userDataMap.containsKey(Base64.getEncoder().encodeToString(concatString.getBytes("utf-8")))) {
				return true;
			} else {
				return false;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public Map<String, Object> userSignupAuthorization(String first_Name, String last_Name, String emailID,
			String password, String company, String country) {
		// TODO Auto-generated method stub
		Map<String, Object> userMap = new LinkedHashMap<String, Object>();
		
		try {
			userMap.put("First_Name", first_Name);
			userMap.put("Last_Name", last_Name);
			String concatString = emailID + ":" + password;
			userMap.put("APP-Token", Base64.getEncoder().encodeToString(concatString.getBytes("utf-8")));
			userMap.put("Company", company);
			userMap.put("Country", country);
			userDataMap.put(Base64.getEncoder().encodeToString(concatString.getBytes("utf-8")), userMap);
			return userMap;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userMap;
	}

	public List<Object> userListAuthorization(String authenticateKey, String FilterField) {
		// TODO Auto-generated method stub
		
		if (!userDataMap.isEmpty() && userDataMap.containsKey(authenticateKey)) {
			Map<String, Object> authenticateUserData = (Map<String, Object>) userDataMap.get(authenticateKey);
			if (FilterField.toLowerCase().equals("all")) {
				List<Object> allUserList = new ArrayList<>();
				for (Map.Entry<String, Object> userDataMapEntry : userDataMap.entrySet()) {
					Map<String, Object> userData = (Map<String, Object>) userDataMapEntry.getValue();
					allUserList.add(userData);
				}
				return allUserList;
				
			} else {
				String typeValue = (String) authenticateUserData.get(FilterField);
				List<Object> userNamesList = new ArrayList<>();
				for (Map.Entry<String, Object> userDataMapEntry : userDataMap.entrySet()) {
					Map<String, Object> userData = (Map<String, Object>) userDataMapEntry.getValue();
					if (userData.containsKey(FilterField)) {
						if (userData.get(FilterField).equals(typeValue)) {
							userNamesList.add(userData.get("First_Name") + " " + userData.get("Last_Name"));
						}
					} else {
						return new ArrayList<Object>();
					}
					
				}
				return userNamesList;
			}
			
		} else {
			return new ArrayList<>();
		}
	}
}
