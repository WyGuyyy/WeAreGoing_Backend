package com.wearegoing.WeAreGoing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLDecoder;
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
	
	/*@Bean
	public CommandLineRunner schedulingToken(TaskExecutor executor) {
	    return new CommandLineRunner() {
	    	
	    	@Autowired
	    	InfoService infoService;
	    	
	        public void run(String... args) throws Exception {
	            executor.execute(new Runnable() {
	    			
	    			public void run() {
	    		       
	    		        int amount = 0;
	    		        int paypalBalance = 0;
	    		   
	    				
	    				while(true) {
	    					
	    					try {
	    						
	    						HttpsURLConnection connection = null;
	    						BufferedWriter writer = null;
	    						BufferedReader br = null;
	    						OutputStream os = null;
	    						JSONTokener jsonTokener = null;
	    						JSONObject json = null;
	    						
	    						String data = "grant_type=client_credentials";
	    						String cred = "Aa9IbDS7OS79cp3KX3KHQSL4cnLNXqpJ6Kx1LTNkacmeGWmsx-2x_ukP-rvx97exOXQat0tiJvXy_SId:EOFRODgB6-00AGAGkwuASJ-vEG8WuVBEOgPSwSon1awGUtI1Lq1E0h5RstB-gZx6dKojjIQqYLi-ct4c";
	    						String token = "";
	    						
	    						//Setup comnnection to paypal
								URL url = new URL("https://api.sandbox.paypal.com/v1/oauth2/token");
								connection = (HttpsURLConnection) url.openConnection();
								connection.setRequestMethod("POST");
								connection.setRequestProperty("Authorization", "Basic " + new String(new org.apache.commons.codec.binary.Base64().encode(cred.getBytes())));
								connection.setRequestProperty("Content-Length", Integer.toString(data.length()));
								connection.setRequestProperty("Accept", "application/json");
								connection.setRequestProperty("Accept-Language", "en_US");
								connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
								connection.setUseCaches(false);
								connection.setDoOutput(true);
								
								//Setup output stream to send post request to paypal
								try {
									os = connection.getOutputStream();
								} catch (Exception e) {
									e.printStackTrace();
								}
								
								
								try {
									writer = new BufferedWriter(
									        new OutputStreamWriter(os, "UTF-8"));
								} catch (Exception e) {
									e.printStackTrace();
								}
								
								//Send post request to paypal
								writer.write(data);
								writer.flush();
								writer.close();
								os.close();

								//Call paypal api
								connection.connect();
								
								if(connection.getResponseCode() == 200) {
								
									//Get response back from paypal and parse the current account balance
									//(Need to add check here for response code)
									br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
									jsonTokener = new JSONTokener(br);
									json = new JSONObject(jsonTokener);
									token = json.getString("access_token");
									
									url = new URL("https://api-m.sandbox.paypal.com/v1/reporting/balances");
									connection = (HttpsURLConnection) url.openConnection();
									connection.setRequestMethod("GET");
									connection.setRequestProperty("Authorization", "Bearer " + token);
									connection.setRequestProperty("Content-Type", "application/json");
									connection.setUseCaches(false);
									connection.setDoOutput(true);
									
									//Setup output stream to send post request to paypal
									try {
										os = connection.getOutputStream();
									} catch (Exception e) {
										e.printStackTrace();
									}
																
									//Call paypal api
									connection.connect();
									
									if(connection.getResponseCode() == 200) {
										//Get response back from paypal and parse the current account balance
										//(Need to add check here for response code)
										br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
										jsonTokener = new JSONTokener(br);
										json = new JSONObject(jsonTokener);
										System.out.println(json);
									}else {
										System.out.println("Error calling Paypal API: " + connection);
									}
									
								}else{
									System.out.println("Error calling Paypal API: " + connection.getResponseMessage());
								}
								
							} catch (MalformedURLException e1) {
								e1.printStackTrace();
							} catch (ProtocolException e) {
								e.printStackTrace();
							} catch (IOException e) {
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
	}*/
	
	/*@Bean
	public CommandLineRunner schedulingRefresh(TaskExecutor executor) {
	    return new CommandLineRunner() {
	    	
	    	@Autowired
	    	InfoService infoService;
	    	
	        public void run(String... args) throws Exception {
	            executor.execute(new Runnable() {
	    			
	    			public void run() {
	    		       
	    		        int amount = 0;
	    		        int paypalBalance = 0;
	    		        
	    		        //Setup params for paypal api call
	    		        List<NameValuePair> params = null;
	    		        params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("METHOD", "GetBalance"));
						params.add(new BasicNameValuePair("VERSION", "200"));
						params.add(new BasicNameValuePair("USER", "wearegoingsupp_api1.gmail.com"));
						params.add(new BasicNameValuePair("PWD", "93K3Z6SNL3RX2TU3"));
						params.add(new BasicNameValuePair("SIGNATURE", "A-oCmy9.n4VpIutbhRJI2itwsW.BA2QaOslhZy3vts0tk3jJsEkd69Rw"));
	    				
	    				while(true) {
	    					
	    					try {
	    						
	    						HttpsURLConnection connection = null;
	    						BufferedWriter writer = null;
	    						BufferedReader br = null;
	    						OutputStream os = null;
	    						String response = "";
	    						
	    						//Setup comnnection to paypal
								URL url = new URL("https://api-3t.paypal.com/nvp");
								connection = (HttpsURLConnection) url.openConnection();
								connection.setReadTimeout(10000);
								connection.setConnectTimeout(15000);
								connection.setRequestMethod("POST");
								connection.setDoInput(true);
								connection.setDoOutput(true);
								
								//Setup output stream to send post request to paypal
								try {
									os = connection.getOutputStream();
								} catch (Exception e) {
									e.printStackTrace();
								}
								
								
								try {
									writer = new BufferedWriter(
									        new OutputStreamWriter(os, "UTF-8"));
								} catch (Exception e) {
									e.printStackTrace();
								}
								
								//Send post request to paypal
								writer.write(getQuery(params));
								writer.flush();
								writer.close();
								os.close();

								//Call paypal api
								connection.connect();
								
								if(connection.getResponseCode() == 200) {
								
									//Get response back from paypal and parse the current account balance
									//(Need to add check here for response code)
									br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
									
									response = br.readLine();
									response = URLDecoder.decode(response, "UTF-8");
									response = response.split("&")[0].split("=")[1];
									paypalBalance = (int) Double.parseDouble(response);
									System.out.println(paypalBalance);
								}else{
									System.out.println("Error calling Paypal API: " + connection.getResponseMessage());
								}
								
							} catch (MalformedURLException e1) {
								e1.printStackTrace();
							} catch (ProtocolException e) {
								e.printStackTrace();
							} catch (IOException e) {
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
	
	private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
	{
	    StringBuilder result = new StringBuilder();
	    boolean first = true;

	    for (NameValuePair pair : params)
	    {
	        if (first)
	            first = false;
	        else
	            result.append("&");

	        result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
	        result.append("=");
	        result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
	    }

	    return result.toString();
	}*/
	
}
