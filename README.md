# testtls
Tool to test TLS connections with JAVA, it uses the great https://www.howsmyssl.com/ API

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
Run ANT to run three tests to illustrate the differences:

* Default JAVA Configuration
* Explicitly specifying TLS 1.0 via -D command line paramters
* Explicitly specifying TLS 1.2 via -D command line paramters
