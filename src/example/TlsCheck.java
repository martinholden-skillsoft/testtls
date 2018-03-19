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
import org.json.JSONException;
import org.json.JSONObject;

public class TlsCheck {

	/**
	 * Tests whether this client can make an HTTP connection with TLS 1.2.
	 *
	 * @return true if connection is successful. false otherwise.
	 */
	public static boolean isSuccessfulTLS12Connection() {
		try {
			JSONObject jObj = null;
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, null, null);
			
			
			//Determine based on SSLContext WHAT protocols are enabled.
			SSLSocketFactory sf=sslContext.getSocketFactory();
			SSLSocket ssl=(SSLSocket)sf.createSocket();
			String[] protocols = ssl.getEnabledProtocols();
			System.out.println("Enabled protocol versions: " + Arrays.asList(protocols));
			
			
			HttpsURLConnection.setDefaultSSLSocketFactory(sf);
			
			System.out.println("Testing Connection using https://www.howsmyssl.com/");
			URL url = new URL("https://www.howsmyssl.com/a/check");
			HttpsURLConnection httpsConnection = (HttpsURLConnection) url.openConnection();

			httpsConnection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpsConnection.getInputStream()));
			StringBuilder body = new StringBuilder();
			while (reader.ready()) {
				body.append(reader.readLine());
			}
			httpsConnection.disconnect();
			
			// try parse the string to a JSON object
			try {
				jObj = new JSONObject(body.toString());
				String tlsVersion = jObj.getString("tls_version");
				System.out.println("TLS Version Reported by API: "+tlsVersion);
				if (tlsVersion.equals("TLS 1.2")) {
					return true;
				}
			} catch (JSONException e) {
				System.out.println("Error parsing data " + e.toString());
			}

		} catch (NoSuchAlgorithmException e) {
		} catch (UnknownHostException e) {
		} catch (IOException e) {
		} catch (KeyManagementException e) {
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

		if (isSuccessfulTLS12Connection()) {
			System.out.println("Successfully connected to TLS 1.2 endpoint.");
		} else {
			System.out.println("Failed to connect to TLS 1.2 endpoint.");
		}
	}
}