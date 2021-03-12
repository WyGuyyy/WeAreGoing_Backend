package com.wearegoing.WeAreGoing;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

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
	public CommandLineRunner schedulingRunner(TaskExecutor executor) {
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
	    				
	    					/*setToNextSaturday(Calendar.SATURDAY, c);
	    					msTillTimeout = (c.getTimeInMillis()-System.currentTimeMillis());
	    					
	    					try {
	    						Thread.sleep(msTillTimeout);
	    						
	    						while(true) {
	    							
	    						}
	    						
	    					} catch (InterruptedException e1) {
	    						// TODO Auto-generated catch block
	    						e1.printStackTrace();
	    					}*/
	    					
	    					c = null;
	    					
	    				}
	    				
	    			}
	    			
	    			});
	        }
	    };
	}
	
}
