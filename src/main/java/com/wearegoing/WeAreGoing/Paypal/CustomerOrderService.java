package com.wearegoing.WeAreGoing.Paypal;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.paypal.http.HttpResponse;
import com.paypal.http.exceptions.HttpException;
import com.paypal.http.serializer.Json;
import com.paypal.orders.*;

@Service
public class CustomerOrderService {
	
	
	public int captureOrder(CustomerOrder customerOrder) {
		Order order = null;
		OrdersCaptureRequest request = new OrdersCaptureRequest(customerOrder.getOrderID());

		try {
			// Call API with your client and get a response for your call
			HttpResponse<Order> response = Credentials.client.execute(request);

			// If call returns body in response, you can get the de-serialized version by
			// calling result() on the response
			order = response.result();
			System.out.println("Capture ID: " + order.purchaseUnits().get(0).payments().captures().get(0).id());
			order.purchaseUnits().get(0).payments().captures().get(0).links()
					.forEach(link -> System.out.println(link.rel() + " => " + link.method() + ":" + link.href()));
		} catch (IOException ioe) {
			if (ioe instanceof HttpException) {
				// Something went wrong server-side
				HttpException he = (HttpException) ioe;
				System.out.println(he.getMessage());
				he.headers().forEach(x -> System.out.println(x + " :" + he.headers().header(x)));
				return 0;
			} else {
				// Something went wrong client-side
				System.out.println("ERROR");
				return 0;
			}
		}
		
		return 1;
	}
	
	public Order getOrder(CustomerOrder customerOrder) {
		OrdersGetRequest request = new OrdersGetRequest(customerOrder.getOrderID());
		HttpResponse<Order> response = null;
		
		try {
			response = Credentials.client.execute(request);
			System.out.println("result: " + new JSONObject(new Json().serialize(response.result())));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return response.result();
	}
}
