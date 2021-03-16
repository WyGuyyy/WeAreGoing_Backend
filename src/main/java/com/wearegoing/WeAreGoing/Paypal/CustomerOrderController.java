package com.wearegoing.WeAreGoing.Paypal;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.http.HttpResponse;
import com.paypal.http.exceptions.HttpException;
import com.paypal.orders.*;
import com.wearegoing.WeAreGoing.API.API;
import com.wearegoing.WeAreGoing.Info.InfoService;

@CrossOrigin(origins = API.CLIENT_CORS)
@RestController
public class CustomerOrderController {
	
	@Autowired
	CustomerOrderService customerOrderService;
	
	@Autowired
	InfoService infoService;
	
	/*@RequestMapping(method = RequestMethod.POST, value = "/api/order/capture")
	public int captureOrder(@RequestBody CustomerOrder customerOrder) {

		int status = -1;
		
		status = 
		
		status = customerOrderService.captureOrder(customerOrder);
		
		if(status == 0) {
			return 0;
		}
		
		return status;
		
	}*/
	
	//@RequestMapping(method = RequestMethod.POST, value = "/api/order/capture", consumes = { "multipart/form-data" })
	@PostMapping(value = "/api/order/capture")
	public int captureOrder(@RequestBody CustomerOrder customerOrder) {
		
		try {
			int flag = customerOrderService.captureOrder(customerOrder);
			
			if(flag == 0) {
				System.out.println("Error capturing order - Capture Order endpoint");
				return 0;
			}
			
		}catch(Exception ex) {
			System.out.println("Error capturing order: " + ex.getMessage());
			ex.printStackTrace();
			return 0;
		}
		
		infoService.incrementAmount();
		
		return 1;
		
	}
	
}