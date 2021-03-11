package com.wearegoing.WeAreGoing;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wearegoing.WeAreGoing.Info.InfoService;

@SpringBootApplication
public class WeAreGoingApplication {
	
	@Autowired
	static InfoService infoService;

	public static void main(String[] args) {
		SpringApplication.run(WeAreGoingApplication.class, args);
		//startResetThread(); - this isnt working, autowire not working in different thread?
	}

	//START HERE NEXT TIME AND RUN RESET EVERY SATURDAY AT MIDNIGHT
		public static void startResetThread() {
			
			Thread resetThread = new Thread(new Runnable() {
			
			public void run() {
		       
		        long msTillTimeout;
				
				while(true) {
					
					Calendar c = Calendar.getInstance();
					
					if(c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY && (c.get(Calendar.HOUR_OF_DAY) == 18 && c.get(Calendar.MINUTE) > 10)) {
						System.out.println(c.get(Calendar.MINUTE));
						System.out.println(c.get(Calendar.HOUR_OF_DAY));
						infoService.resetAmount();
						
						while(c.get(Calendar.HOUR_OF_DAY) < 12) {
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
		
			resetThread.start();
		}
		
		/*public static void setToNextSaturday(int dayOfWeekToSet, Calendar c) {
				int currentDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
				System.out.println(c.DAY_OF_MONTH);
				System.out.println(currentDayOfWeek);
				
				while(currentDayOfWeek != dayOfWeekToSet) {
					c.add(Calendar.DAY_OF_MONTH, 1);
					currentDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
				}
				
				System.out.println(c.DAY_OF_WEEK);
		}*/
		
	}
