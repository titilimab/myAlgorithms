package myorg.javacode.javaDotNetPkg;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

public class Whois_SocketProgramming{
	
	public static void main(String args[]) throws Exception{
		
		//Open a Socket
		//Create a socket connected to domain name cisco.com, port 80.
		Socket socketObj = new Socket("xxx.com", 80);
		
		//Open input and output streams to the Socket
		InputStream in = socketObj.getInputStream();
		OutputStream out = socketObj.getOutputStream();
		
		//Construct the request String to be sent to the server using output stream object of the socket
		String reqStr = (args.length == 0 ? "xxx.com" : args[0])+"\n";
		//Convert the Request string to Bytes
		byte reqByte[] = reqStr.getBytes();
		
		//Send request in byte format using output stream object
		out.write(reqByte);
		
		//Read from the response using read() method
		int res;
		while((res = in.read()) != -1){
			
			System.out.print((char)res);
		}
		
		//Close the input and output stream objects
		in.close();
		out.close();
		
		//Close the Socket Object once communication is over
		socketObj.close();
	}
}

/*
Sample Response 1:
 --------------------
HTTP/1.1 400 Bad Request
Date: Wed, 19 Feb 2020 19:49:56 GMT
Server: Apache
Content-Length: 226
Connection: close
Content-Type: text/html; charset=iso-8859-1

<!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
<html><head>
<title>400 Bad Request</title>
</head><body>
<h1>Bad Request</h1>
<p>Your browser sent a request that this server could not understand.<br />
</p>
</body></html>

-----------------
Sample Response 2:
-----------------

Whois Server Version 1.3
Domain names in the .com, .net, and .org domains can now be registered
with many different competing registrars. Go to http://www.internic.net
for detailed information.
Domain Name: OSBORNE.COM
Registrar: NETWORK SOLUTIONS, INC.
Whois Server: whois.networksolutions.com
Referral URL: http://www.networksolutions.com
Name Server: NS1.EPPG.COM
Name Server: NS2.EPPG.COM
.
.
.

 * */
