# testtls
Tool to test TLS connections with JAVA, there are two classes:

**TlsCheck.java**

This uses the great https://www.howsmyssl.com/ API

The test will display:
* What protocols are supported
* What protocols are enabled for this connection
* The TLS version reported by the API
* Whether the connection was successful for TLS 1.2

An Example Output is:
~~~~
Supported protocol versions: [SSLv2Hello, SSLv3, TLSv1, TLSv1.1, TLSv1.2]
Enabled protocol versions: [TLSv1.2]
Testing Connection using https://www.howsmyssl.com/
TLS Version Reported by API: TLS 1.2
Successfully connected to TLS 1.2 endpoint.
~~~~

Run
~~~~ 
ant alltests
~~~~
To run three tests to illustrate the differences:

* Default JAVA Configuration
* Explicitly specifying TLS 1.0 via -D command line paramters
* Explicitly specifying TLS 1.2 via -D command line paramters

**OlsaTlsCheck.java**

This attempts a connection to the specified URL, either in the build.properties file or on the ANT command line.

The test will display:
* What protocols are supported
* What protocols are enabled for this connection
* Whether the connection was successful for TLS 1.2

An Example Output is:
~~~~
Supported protocol versions: [SSLv2Hello, SSLv3, TLSv1, TLSv1.1, TLSv1.2]
Enabled protocol versions: [TLSv1]
Testing Connection using https://{customername}.skillwsa.com
Error connecting this exception usually indicates the TLS Version is unsupported javax.net.ssl.SSLHandshakeException: Received fatal alert: handshake_failure
Failed to connect to TLS 1.2 endpoint.
~~~~

Run
~~~~ 
ant allolsatests -Dproject.olsaUrl=https://{customername}.skillwsa.com
~~~~
To run three tests to illustrate the differences:

* Default JAVA Configuration
* Explicitly specifying TLS 1.0 via -D command line paramters
* Explicitly specifying TLS 1.2 via -D command line paramters
