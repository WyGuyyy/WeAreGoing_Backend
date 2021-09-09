package com.wearegoing.WeAreGoing.mailchimp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import com.ecwid.maleorang.MailchimpClient;
import com.ecwid.maleorang.MailchimpException;
import com.ecwid.maleorang.method.v3_0.members.EditMemberMethod;

import com.wearegoing.WeAreGoing.API.*;

@CrossOrigin(origins = API.CLIENT_CORS)
@RestController
public class MailchimpController
{
	
	@RequestMapping(method = RequestMethod.POST, value = "/api/mailchimp/subscribe")
	public void subscribe(@RequestBody Subscriber subscriber) throws ClientProtocolException, IOException
		{
			String apiKey = "";
		
			MailchimpClient mcClient = new MailchimpClient(apiKey);
			
			//EditMemberMethod.Create method = new EditMemberMethod.Create("cb703b127c", subscriber.getEmailAddress());
			EditMemberMethod.CreateOrUpdate method = new EditMemberMethod.CreateOrUpdate("c19532cfd5", subscriber.getEmailAddress());
			method.status_if_new = "subscribed";
			
			try {
				mcClient.execute(method);
			} catch (MailchimpException e) {
				e.printStackTrace();
			}
			
		}

}
