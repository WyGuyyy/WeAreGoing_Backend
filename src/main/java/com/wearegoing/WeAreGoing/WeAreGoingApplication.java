package com.wearegoing.WeAreGoing;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import com.wearegoing.WeAreGoing.Info.InfoService;

@SpringBootApplication
public class WeAreGoingApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeAreGoingApplication.class, args);
		startResetThread();
	}
	
	public static void startResetThread(){
		
	}

	@Bean
	public TaskExecutor taskExecutor() {
	    return new SimpleAsyncTaskExecutor(); // Or use another one of your liking
	}
	
	@Bean
	public CommandLineRunner schedulingReset(TaskExecutor executor) {
	    return new CommandLineRunner() {
	    	
	    	@Autowired
	    	InfoService infoService;
	    	
	        public void run(String... args) throws Exception {
	            executor.execute(new Runnable() {
	    			
	    			public void run() {
	    		       
	    		        long msTillTimeout;
	    				
	    				while(true) {
	    					
	    					Calendar c = Calendar.getInstance();

	    					if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && c.get(Calendar.HOUR_OF_DAY) < 12) {

	    						infoService.resetAmount();
	    						
	    						while(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && c.get(Calendar.HOUR_OF_DAY) < 12) {
	    							//Loop until out of inactive period
	    						}
	    					}
	    				
	    					c = null;
	    					
	    				}
	    				
	    			}
	    			
	    			});
	        }
	    };
	}
	
	@Bean
	public CommandLineRunner schedulingRefresh(TaskExecutor executor) {
	    return new CommandLineRunner() {
	    	
	    	@Autowired
	    	InfoService infoService;
	    	
	        public void run(String... args) throws Exception {
	            executor.execute(new Runnable() {
	    			
	    			public void run() {
	    		       
	    		        int amount = 0;
	    		        int paypalBalance = 0;
	    		        String nvp = "USER=wearegoingsupp_api1.gmail.com&PWD=93K3Z6SNL3RX2TU3&SIGNATURE=A-oCmy9.n4VpIutbhRJI2itwsW.BA2QaOslhZy3vts0tk3jJsEkd69Rw&METHOD=GetBalance&VERSION=200";
	    		        try {
							nvp = URLEncoder.encode(nvp, "UTF-8");
						} catch (UnsupportedEncodingException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
	    		        String eURL = "https://api-3t.paypal.com/nvp?" + nvp;
	    		        
	    		       /* try {
							eURL = URLEncoder.encode(eURL, "UTF-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}*/
	    				
	    				while(true) {
	    					
	    					HttpURLConnection connection = null;
	    					
	    					try {
	    						System.out.println(eURL);
								URL url = new URL(eURL);
								
								connection = (HttpURLConnection) url.openConnection();
							    connection.setRequestMethod("POST");
							    connection.setRequestProperty("Content-Type", 
							        "application/x-www-form-urlencoded");

							    //connection.setRequestProperty("Content-Language", "en-US");
							    
							    connection.setUseCaches(false);
							    connection.setDoOutput(true);
							    
							  //Send request
							    DataOutputStream wr = new DataOutputStream (connection.getOutputStream());

							    wr.close();
							    
							  //Get Response  
							    InputStream is = connection.getInputStream();
							    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
							    StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
							    String line;
							    while ((line = rd.readLine()) != null) {
							      response.append(line);
							      response.append('\r');
							    }
							    rd.close();
							    System.out.println(response.toString());
							} catch (MalformedURLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (ProtocolException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

	    					if(amount != paypalBalance) {
	    						infoService.setAmount(paypalBalance);
	    						amount = paypalBalance;
	    					}
	    					
	    					try {
								Thread.sleep(5000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
	    					
	    				}
	    				
	    			}
	    			
	    			});
	        }
	    };
	}
	
}
