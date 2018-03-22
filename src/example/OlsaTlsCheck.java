package example;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import java.net.Socket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class OlsaTlsCheck {

	/**
	 * Tests whether this client can make an HTTP connection with TLS 1.2.
	 *
	 * @return true if connection is successful. false otherwise.
	 */
	public static boolean isSuccessfulTLS12Connection(String olsaURL) {
		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, null, null);
			
			
			//Determine based on SSLContext WHAT protocols are enabled.
			SSLSocketFactory sf=sslContext.getSocketFactory();
			SSLSocket ssl=(SSLSocket)sf.createSocket();
			String[] protocols = ssl.getEnabledProtocols();
			System.out.println("Enabled protocol versions: " + Arrays.asList(protocols));
			
			
			HttpsURLConnection.setDefaultSSLSocketFactory(sf);
			
			System.out.println("Testing Connection using " + olsaURL);
			URL url = new URL(olsaURL);
			HttpsURLConnection httpsConnection = (HttpsURLConnection) url.openConnection();

			httpsConnection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpsConnection.getInputStream()));
			StringBuilder body = new StringBuilder();
			while (reader.ready()) {
				body.append(reader.readLine());
			}
			httpsConnection.disconnect();
			
			return true;

		} catch (javax.net.ssl.SSLHandshakeException e) {
			System.out.println("Error connecting this exception usually indicates the TLS Version is unsupported " + e.toString());
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error connecting " + e.toString());
		} catch (UnknownHostException e) {
			System.out.println("Error connecting " + e.toString());
		} catch (IOException e) {
			System.out.println("Error connecting " + e.toString());
		} catch (KeyManagementException e) {
			System.out.println("Error connecting " + e.toString());
		}
		return false;
	}

	public static void main(String[] args) {
		try {
			SSLParameters sslParams = SSLContext.getDefault().getSupportedSSLParameters();
			String[] protocols = sslParams.getProtocols();
			System.out.println("Supported protocol versions: " + Arrays.asList(protocols));
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		if (args.length > 0) {
				if (isSuccessfulTLS12Connection(args[0])) {
					System.out.println("Successfully connected to TLS 1.2 endpoint.");
				} else {
					System.out.println("Failed to connect to TLS 1.2 endpoint.");
				}
		} else {
			System.out.println("You must supply a URL");
		}
	}
}