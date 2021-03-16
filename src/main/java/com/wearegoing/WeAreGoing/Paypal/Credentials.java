package com.wearegoing.WeAreGoing.Paypal;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;

public class Credentials {
	
	//(NOTE: Currently sandbox credentials)
	//static String clientId = "Aa9IbDS7OS79cp3KX3KHQSL4cnLNXqpJ6Kx1LTNkacmeGWmsx-2x_ukP-rvx97exOXQat0tiJvXy_SId"; //Sandbox
    //static String secret = "EOFRODgB6-00AGAGkwuASJ-vEG8WuVBEOgPSwSon1awGUtI1Lq1E0h5RstB-gZx6dKojjIQqYLi-ct4c"; //Sandbox
	
	//Live Credentials
	static String clientId = "AdI2x6gGA-HRug3XKCYqiwk-MVDMmBbU1wbf03GJq2S9qaaGwI8XBppUIu_z7edajkVHr16cGojDBOCq";
	static String secret = "EH79LMQmNuc6IMu_NwKhOqzidRe9iZRucERTakyJbBQMjIxVv3J-67GaAlzIb_9YI5jsKzlYBhxTv13G";
    
    // Creating a sandbox environment
    //private static PayPalEnvironment environment = new PayPalEnvironment.Sandbox(clientId, secret);
	private static PayPalEnvironment environment = new PayPalEnvironment.Live(clientId, secret);
    
    // Creating a client for the environment
    static PayPalHttpClient client = new PayPalHttpClient(environment);

}
