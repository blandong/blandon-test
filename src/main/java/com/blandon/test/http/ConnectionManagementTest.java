package com.blandon.test.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ConnectionRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

public class ConnectionManagementTest {
	public static void main(String[] args) {
		
		
		
		
		
	}
	
	
	
	
	private static void getConnection() throws InterruptedException, ExecutionException, IOException{
		HttpClientContext context = HttpClientContext.create();
		HttpClientConnectionManager connMrg = new BasicHttpClientConnectionManager();
		HttpRoute route = new HttpRoute(new HttpHost("localhost", 80));
		// Request new connection. This can be a long process
		ConnectionRequest connRequest = connMrg.requestConnection(route, null);
		// Wait for connection up to 10 sec
		HttpClientConnection conn = connRequest.get(10, TimeUnit.SECONDS);
		try {
		    // If not open
		    if (!conn.isOpen()) {
		        // establish connection based on its route info
		        connMrg.connect(conn, route, 1000, context);
		        // and mark it as route complete
		        connMrg.routeComplete(conn, route, context);
		    }
		    // Do useful things with the connection.
		} finally {
		    connMrg.releaseConnection(conn, null, 1, TimeUnit.MINUTES);
		}
	}
	
	
	
	
	private static void basicConnectionManager(){
		BasicHttpClientConnectionManager basicHttpClientConnectionManager = new BasicHttpClientConnectionManager();
	}
	
	
	
	
	private static void poolingConnectionManager(){
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		// Increase max total connection to 200
		cm.setMaxTotal(200);
		// Increase default max connection per route to 20
		cm.setDefaultMaxPerRoute(20);
		// Increase max connections for localhost:80 to 50
		HttpHost localhost = new HttpHost("locahost", 80);
		cm.setMaxPerRoute(new HttpRoute(localhost), 50);

		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
	}
	
	/*
	 * The PoolingClientConnectionManager will allocate connections based on its configuration. 
	 * If all connections for a given route have already been leased, a request for a connection will block until a connection is released back to the pool. 
	 * One can ensure the connection manager does not block indefinitely in the connection request operation by setting 'http.conn-manager.timeout' to a positive value.
	 * If the connection request cannot be serviced within the given time period ConnectionPoolTimeoutException will be thrown. 
	 * */
	private static void multiThreadRequest() throws InterruptedException{
		
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		
		CloseableHttpClient httpClient = HttpClients.custom()
		        .setConnectionManager(cm)
		        .build();

		// URIs to perform GETs on
		String[] urisToGet = {
		    "http://www.domain1.com/",
		    "http://www.domain2.com/",
		    "http://www.domain3.com/",
		    "http://www.domain4.com/"
		};

		// create a thread for each URI
		GetThread[] threads = new GetThread[urisToGet.length];
		for (int i = 0; i < threads.length; i++) {
		    HttpGet httpget = new HttpGet(urisToGet[i]);
		    threads[i] = new GetThread(httpClient, httpget);
		}

		// start the threads
		for (int j = 0; j < threads.length; j++) {
		    threads[j].start();
		}

		// join the threads
		for (int j = 0; j < threads.length; j++) {
		    threads[j].join();
		}
	}
	
	
	
	
	
	static class GetThread extends Thread {

	    private final CloseableHttpClient httpClient;
	    private final HttpContext context;
	    private final HttpGet httpget;

	    public GetThread(CloseableHttpClient httpClient, HttpGet httpget) {
	        this.httpClient = httpClient;
	        this.context = HttpClientContext.create();
	        this.httpget = httpget;
	    }

	    @Override
	    public void run() {
	        try {
	            CloseableHttpResponse response = httpClient.execute(
	                    httpget, context);
	            try {
	                HttpEntity entity = response.getEntity();
	            } finally {
	                response.close();
	            }
	        } catch (ClientProtocolException ex) {
	            // Handle protocol errors
	        } catch (IOException ex) {
	            // Handle I/O errors
	        }
	    }

	}
	
	
	
	
	/*
	 * One of the major shortcomings of the classic blocking I/O model is that the network socket can react to I/O events only when blocked in an I/O operation. When a connection is released back to the manager, 
	 * it can be kept alive however it is unable to monitor the status of the socket and react to any I/O events. 
	 * If the connection gets closed on the server side, the client side connection is unable to detect the change in the connection state (and react appropriately by closing the socket on its end).
		HttpClient tries to mitigate the problem by testing whether the connection is 'stale', 
		that is no longer valid because it was closed on the server side, 
		prior to using the connection for executing an HTTP request. 
		The stale connection check is not 100% reliable. The only feasible solution that does not involve a one thread per socket model for idle connections is a dedicated monitor thread used to evict connections that are considered expired due to a long period of inactivity. 
		The monitor thread can periodically call ClientConnectionManager#closeExpiredConnections() method to close all expired connections and evict closed connections from the pool. It can also optionally call ClientConnectionManager#closeIdleConnections() method to close all connections that have been idle over a given period of time.
	 * 
	 * */
	
		 static class IdleConnectionMonitorThread extends Thread {
		    
		    private final HttpClientConnectionManager connMgr;
		    private volatile boolean shutdown;
		    
		    public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
		        super();
		        this.connMgr = connMgr;
		    }

		    @Override
		    public void run() {
		        try {
		            while (!shutdown) {
		                synchronized (this) {
		                    wait(5000);
		                    // Close expired connections
		                    connMgr.closeExpiredConnections();
		                    // Optionally, close connections
		                    // that have been idle longer than 30 sec
		                    connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
		                }
		            }
		        } catch (InterruptedException ex) {
		            // terminate
		        }
		    }
		    
		    public void shutdown() {
		        shutdown = true;
		        synchronized (this) {
		            notifyAll();
		        }
		    }
		    
		}
	
	
		 /*
		  * 2.6. Connection keep alive strategy
			The HTTP specification does not specify how long a persistent connection may be and should be kept alive. 
			Some HTTP servers use a non-standard Keep-Alive header to communicate to the client the period of time in seconds they intend to keep the connection alive on the server side. 
			HttpClient makes use of this information if available.
			 If the Keep-Alive header is not present in the response, HttpClient assumes the connection can be kept alive indefinitely. 
			 However, many HTTP servers in general use are configured to drop persistent connections after a certain period of inactivity in order to conserve system resources, quite often without informing the client. In case the default strategy turns out to be too optimistic, one may want to provide a custom keep-alive strategy.
		  * */
	
		 private static void keepAlive(){
			 ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {

				    public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
				        // Honor 'keep-alive' header
				        HeaderElementIterator it = new BasicHeaderElementIterator(
				                response.headerIterator(HTTP.CONN_KEEP_ALIVE));
				        while (it.hasNext()) {
				            HeaderElement he = it.nextElement();
				            String param = he.getName();
				            String value = he.getValue();
				            if (value != null && param.equalsIgnoreCase("timeout")) {
				                try {
				                    return Long.parseLong(value) * 1000;
				                } catch(NumberFormatException ignore) {
				                }
				            }
				        }
				        HttpHost target = (HttpHost) context.getAttribute(
				                HttpClientContext.HTTP_TARGET_HOST);
				        if ("www.naughty-server.com".equalsIgnoreCase(target.getHostName())) {
				            // Keep alive for 5 seconds only
				            return 5 * 1000;
				        } else {
				            // otherwise keep alive for 30 seconds
				            return 30 * 1000;
				        }
				    }

				};
				CloseableHttpClient client = HttpClients.custom()
				        .setKeepAliveStrategy(myStrategy)
				        .build();
		 }
		 
		 
		 /*
		  * 2.7. Connection socket factories
			HTTP connections make use of a java.net.Socket object internally to handle transmission of data across the wire. 
			However they rely on the ConnectionSocketFactory interface to create, initialize and connect sockets. 
			This enables the users of HttpClient to provide application specific socket initialization code at runtime. 
			PlainConnectionSocketFactory is the default factory for creating and initializing plain (unencrypted) sockets.
			The process of creating a socket and that of connecting it to a host are decoupled, 
			so that the socket could be closed while being blocked in the connect operation.
		  * */
		 private static void socket() throws IOException{
			 HttpClientContext clientContext = HttpClientContext.create();
			 PlainConnectionSocketFactory sf = PlainConnectionSocketFactory.getSocketFactory();
			 Socket socket = sf.createSocket(clientContext);
			 int timeout = 1000; //ms
			 HttpHost target = new HttpHost("localhost");
			 InetSocketAddress remoteAddress = new InetSocketAddress(
			         InetAddress.getByAddress(new byte[] {127,0,0,1}), 80);
			 sf.connectSocket(timeout, socket, target, remoteAddress, null, clientContext);
		 }
	
		 
		 /*
		  * 2.7.1. Secure socket layering
			LayeredConnectionSocketFactory is an extension of the ConnectionSocketFactory interface. Layered socket factories are capable of creating sockets layered over an existing plain socket. 
			Socket layering is used primarily for creating secure sockets through proxies. HttpClient ships with SSLSocketFactory that implements SSL/TLS layering. Please note HttpClient does not use any custom encryption functionality. 
			It is fully reliant on standard Java Cryptography (JCE) and Secure Sockets (JSEE) extensions.
		  * */
	
	
		 
		 
		 
		 /*
		  * 2.7.2. Integration with connection manager
			Custom connection socket factories can be associated with a particular protocol scheme as as HTTP or HTTPS and then used to create a custom connection manager.
		  * */
		 
		 private static void customConnectionManager(){
			 ConnectionSocketFactory plainsf = null;
			 LayeredConnectionSocketFactory sslsf = null;
			 Registry<ConnectionSocketFactory> r = RegistryBuilder.<ConnectionSocketFactory>create()
			         .register("http", plainsf)
			         .register("https", sslsf)
			         .build();

			 HttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(r);
			 HttpClients.custom()
			         .setConnectionManager(cm)
			         .build();
		 }
		 
		 
		 
		 
		 
		 
		 
		 
		 /*	
		  * 2.8. HttpClient proxy configuration
			Even though HttpClient is aware of complex routing schemes and proxy chaining, 
			it supports only simple direct or one hop proxy connections out of the box.
			The simplest way to tell HttpClient to connect to the target host via a proxy is by setting the default proxy parameter:
		  * */
		 
		 private static void proxySetting(){
			 HttpHost proxy = new HttpHost("someproxy", 8080);
			 DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
			 CloseableHttpClient httpclient = HttpClients.custom()
			         .setRoutePlanner(routePlanner)
			         .build();
			 
			 
			 //One can also instruct HttpClient to use the standard JRE proxy selector to obtain proxy information:
			 SystemDefaultRoutePlanner routePlanner2 = new SystemDefaultRoutePlanner(ProxySelector.getDefault());
				CloseableHttpClient httpclient2 = HttpClients.custom()
				        .setRoutePlanner(routePlanner2)
				        .build();
				
				
				
				//Alternatively, one can provide a custom RoutePlanner implementation in order to have a complete control over the process of HTTP route computation:
				HttpRoutePlanner routePlanner3 = new HttpRoutePlanner() {

				    public HttpRoute determineRoute(
				            HttpHost target,
				            HttpRequest request,
				            HttpContext context) throws HttpException {
				        return new HttpRoute(target, null,  new HttpHost("someproxy", 8080),
				                "https".equalsIgnoreCase(target.getSchemeName()));
				    }

				};
				CloseableHttpClient httpclient3 = HttpClients.custom()
				        .setRoutePlanner(routePlanner3)
				        .build();
				    }
				
}


