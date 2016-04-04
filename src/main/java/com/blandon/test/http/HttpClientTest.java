package com.blandon.test.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.FutureRequestExecutionService;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.HttpRequestFutureTask;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blandon.test.bean.MyJsonObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class HttpClientTest {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpClientTest.class);
	
	
	public static void main(String[] args) throws ClientProtocolException, IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		//client1();
		//closeResponse();
		//consumeEntityConetent();
		//protocalInterceptor();
		testConsumeResponse();
		//testSubmitForm();
	}
	
	
	//get response and convert the html to string to print on console
	private static void testConsumeResponse() throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException{
		
		String httpUrl = "http://localhost:8080/blandon-test/user.do?name=blandon";
		
		String httpsUrl = "https://localhost:8443/blandon-test/user.do?name=blandon";
		
		HttpGet get = new HttpGet(httpsUrl);
		
//		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		CloseableHttpClient httpClient2 = HttpClients.custom()
				.setSSLSocketFactory(new SSLSocketFactory(SSLContexts.custom()
						.loadTrustMaterial(null, new TrustSelfSignedStrategy()).build())).build();
				
		HttpResponse response = null;
		
		//String encoding = Base64.encodeBase64String("tomcat:tomcat".getBytes());
		
		//get.setHeader("Authorization", "Basic " + encoding);
		
		try{
			//username and password are specified in tomcat-users.xml file.
			UsernamePasswordCredentials npc = new UsernamePasswordCredentials("tomcat", "tomcat");
			
			AuthScope authScope = new AuthScope("localhost", AuthScope.ANY_PORT);
			
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
			credsProvider.setCredentials(authScope, npc);
			
			HttpClientContext context = HttpClientContext.create();
			context.setCredentialsProvider(credsProvider);
			
			response = httpClient2.execute(get, context);
			
			if(response != null){
				StatusLine statusLine = response.getStatusLine();
				
				int statusCode = statusLine.getStatusCode();
				String version = statusLine.getProtocolVersion().toString();
				String phrase = statusLine.getReasonPhrase();
				
				
				logger.debug("response status: "+statusCode+", version: "+version+", phrase: "+phrase);
				
				if(statusCode != 200){
					throw new RuntimeException("Request is not successful, please verify your request.");
				}else{
					HttpEntity entity = response.getEntity();
					String type = entity.getContentType().toString();
					long length = entity.getContentLength();
					
					logger.debug("content type: {}, length: {}", type, length);
					
					BufferedReader br = null;
					
					try{
						InputStream is = entity.getContent();
						
						br = new BufferedReader(new InputStreamReader(is));
						
						String line =null;
						
						StringBuilder sb = new StringBuilder();
						
						while ((line =br.readLine()) != null){
							sb.append(line).append("\n");
						}
						System.out.println("response content is: \n"+sb.toString());
						
					}finally{
						if(br != null){
							br.close();
						}
					}
					
				}
			}
			
			
		}catch(IOException e){
			throw new RuntimeException("Fail to excute this request: "+get.getURI(), e);
		}finally{
			httpClient2.close();
		}
	
		
	}
	
	
	//test post data
	private static void testSubmitForm() throws IOException{
		
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("name", "Gary"));
		
		//The UrlEncodedFormEntity instance will use the so called URL encoding to encode parameters and produce the following content:
		//param1=value1&param2=value2
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
		
		HttpPost post = new HttpPost("http://localhost:8080/blandon-test/user.do?");
		post.setEntity(entity);
		
		CloseableHttpClient client = HttpClients.createDefault();
		
		CloseableHttpResponse resp = null;
		
		try{
			resp = client.execute(post);
			
			logger.debug("status code: {}, status phrase: {}", resp.getStatusLine().getStatusCode(), resp.getStatusLine().getReasonPhrase());
			
			HttpEntity respEntity = resp.getEntity();
			
			logger.debug("type: {}, length: {}", respEntity.getContentType(), respEntity.getContentLength());
			
			
			
		}catch(IOException e){
			throw new RuntimeException("Fail to execute http post", e);
		}finally{
			if(resp != null){
				resp.close();
			}
			
		}
		
		
	}
	
	
	private static void client1(){
		CloseableHttpResponse response = null;
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet("http://localhost:8080");
			 response = httpclient.execute(httpget);
		} catch(IOException e){
			throw new RuntimeException("failed to execute http get method.", e);
		}finally {
			if(response != null){
				try {
					response.close();
				} catch (IOException e) {
					throw new RuntimeException("failed to close the response.", e);
				}
			}
		}
		
	}
	
	
	//All HTTP requests have a request line consisting a method name, a request URI and an HTTP protocol version.
	private static void httpGet() throws URISyntaxException{
		
		HttpGet httpget = new HttpGet("http://www.google.com/search?hl=en&q=httpclient&btnG=Google+Search&aq=f&oq=");
		
		
		//HTTP request URIs consist of a protocol scheme, host name, optional port, resource path, optional query, and optional fragment.
		URI uri = new URIBuilder()
        .setScheme("http")
        .setHost("www.google.com")
        .setPath("/search")
        .setParameter("q", "httpclient")
        .setParameter("btnG", "Google Search")
        .setParameter("aq", "f")
        .setParameter("oq", "")
        .build();
		
		HttpGet httpget2 = new HttpGet(uri);
		System.out.println(httpget.getURI());
	}
	
	
	private static void httpResponse(){
		//The first line of response message consists of the protocol version followed by a numeric status code and its associated textual phrase
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");

		System.out.println(response.getProtocolVersion());
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(response.getStatusLine().getReasonPhrase());
		System.out.println(response.getStatusLine().toString());
		
		//output is like following:
		
		/**
		 * HTTP/1.1
		   200
		   OK
		   HTTP/1.1 200 OK
		 * */
	}
	
	
	
	private static void httpHeaders(){
		
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
			response.addHeader("Set-Cookie",  "c1=a; path=/; domain=localhost");
			response.addHeader("Set-Cookie",  "c2=b; path=\"/\", c3=c; domain=\"localhost\"");
			Header h1 = response.getFirstHeader("Set-Cookie");
			System.out.println(h1);
			Header h2 = response.getLastHeader("Set-Cookie");
			System.out.println(h2);
			Header[] hs = response.getHeaders("Set-Cookie");
			System.out.println(hs.length);
		
			//output:
			
			/*
			 * Set-Cookie: c1=a; path=/; domain=localhost
			   Set-Cookie: c2=b; path="/", c3=c; domain="localhost"
			   2
			 * 
			 * */
	}
	
	
	private static void parseHttpHeaders(){
		
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
			response.addHeader("Set-Cookie",  "c1=a; path=/; domain=localhost");
			response.addHeader("Set-Cookie", "c2=b; path=\"/\", c3=c; domain=\"localhost\"");

			HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator("Set-Cookie"));

			while (it.hasNext()) {
			    HeaderElement elem = it.nextElement(); 
			    System.out.println(elem.getName() + " = " + elem.getValue());
			    NameValuePair[] params = elem.getParameters();
			    for (int i = 0; i < params.length; i++) {
			        System.out.println(" " + params[i]);
			    }
			}
			
			//output:
			
			/*
			 *  c1 = a
				path=/
				domain=localhost
				c2 = b
				path=/
				c3 = c
				domain=localhost
			 * */
	}
	
	
	//When creating an entity for a outgoing message, this meta data has to be supplied by the creator of the entity.
	private static void entityMetadata() throws ParseException, IOException{
		
		StringEntity myEntity = new StringEntity("important message", ContentType.create("text/plain", "UTF-8"));

		System.out.println(myEntity.getContentType());
		System.out.println(myEntity.getContentLength());
		System.out.println(EntityUtils.toString(myEntity));
		System.out.println(EntityUtils.toByteArray(myEntity).length);
		//output:
		
		/*
		 *  Content-Type: text/plain; charset=utf-8
		17
		important message
		17
		 * */
	}
	
	
	//In order to ensure proper release of system resources one must close the content stream associated with the entity 
	private static void releaseResource() throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("http://localhost/");
		CloseableHttpResponse response = httpclient.execute(httpget);
		try {
		    HttpEntity entity = response.getEntity();
		    if (entity != null) {
		        InputStream instream = entity.getContent();
		        try {
		            // do something useful
		        } finally {
		            instream.close();
		        }
		    }
		} finally {
		    response.close();
		}
	}
	
	
	//There can be situations, however, when only a small portion of the entire response content needs to be retrieved and the performance penalty for consuming the remaining content and making the connection reusable is too high, in which case one can terminate the content stream by closing the response.
	private static void closeResponse() throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("http://localhost:8080");
		CloseableHttpResponse response = httpclient.execute(httpget);
		try {
		    HttpEntity entity = response.getEntity();
		    if (entity != null) {
		        InputStream instream = entity.getContent();
		        int byteOne = instream.read();
		        int byteTwo = instream.read();
		        // Do not need the rest
		    }
		} finally {
		    response.close();
		}
	}
	
	//consume entity  content
	private static void consumeEntityConetent() throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("http://localhost:8080/");
		CloseableHttpResponse response = httpclient.execute(httpget);
		try {
		    HttpEntity entity = response.getEntity();
		    if (entity != null) {
		        long len = entity.getContentLength();
		        if (len != -1 && len < 2048) {
		            System.out.println(EntityUtils.toString(entity));
		        } else {
		            // Stream content out
		        }
		    }
		} finally {
		    response.close();
		}
	}
	
	
	/*
	 * In some situations it may be necessary to be able to read entity content more than once. In this case entity content must be buffered in some way, 
	 * either in memory or on disk. The simplest way to accomplish that is by wrapping the original entity with the BufferedHttpEntity class. 
	 * This will cause the content of the original entity to be read into a in-memory buffer. In all other ways the entity wrapper will be have the original one.
	 * */
	private static void  bufferEntityContent() throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("http://localhost/");
		CloseableHttpResponse response = httpclient.execute(httpget);
		try {
		    HttpEntity entity = response.getEntity();
		    if (entity != null) {
		        entity = new BufferedHttpEntity(entity);
		    }
		} finally {
		    response.close();
		}
	}
	
	
	
	/*
	 * 1.1.7. Producing entity content
	  HttpClient provides several classes that can be used to efficiently stream out content through HTTP connections.
	  Instances of those classes can be associated with entity enclosing requests such as POST and PUT in order to enclose entity content into outgoing HTTP requests.
	  HttpClient provides several classes for most common data containers such as string, byte array, input stream, and file: StringEntity, ByteArrayEntity, InputStreamEntity, and FileEntity.
	 * */
	
	private static void produceEntity(){
		File file = new File("somefile.txt");
		
		FileEntity entity = new FileEntity(file, ContentType.create("text/plain", "UTF-8"));        

		HttpPost httppost = new HttpPost("http://localhost/action.do");
		httppost.setEntity(entity);
	}
	
	/*
	 * 1.1.7.1. HTML forms
		Many applications need to simulate the process of submitting an HTML form, for instance, in order to log in to a web application or submit input data. 
		HttpClient provides the entity class UrlEncodedFormEntity to facilitate the process.
	 * */
	private static void submitForm(){
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("param1", "value1"));
		formparams.add(new BasicNameValuePair("param2", "value2"));
		
		//The UrlEncodedFormEntity instance will use the so called URL encoding to encode parameters and produce the following content:
		//param1=value1&param2=value2
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
		
		HttpPost httppost = new HttpPost("http://localhost/handler.do");
		httppost.setEntity(entity);
	}
	
	
	/*
	 * 1.1.7.2. Content chunking
		Generally it is recommended to let HttpClient choose the most appropriate transfer encoding based on the properties of the HTTP message being transferred. 
		It is possible, however, to inform HttpClient that chunk coding is preferred by setting HttpEntity#setChunked() to true. Please note that HttpClient will use this flag as a hint only.
	 	This value will be ignored when using HTTP protocol versions that do not support chunk coding, such as HTTP/1.0.
	 	
	 	The content can be broken up into a number of chunks; each of which is prefixed by its size in bytes. 
	 	A zero size chunk indicates the end of the response message. 
	 	If a server is using chunked encoding it must set the Transfer-Encoding header to "chunked".
	 * */
	private static void chunkedTransfer(){
		
		StringEntity entity = new StringEntity("important message",ContentType.create("plain/text", Consts.UTF_8));
		entity.setChunked(true);
		HttpPost httppost = new HttpPost("http://localhost/acrtion.do");
		httppost.setEntity(entity);
	}
	
	
	/*
	 * 1.1.8. Response handlers
		The simplest and the most convenient way to handle responses is by using the ResponseHandler interface, 
		which includes the handleResponse(HttpResponse response) method. This method completely relieves the user from having to worry about connection management. 
		When using a ResponseHandler, HttpClient will automatically take care of ensuring release of the connection back to the connection manager regardless whether the request execution succeeds or causes an exception.
	 * */
	
	private static void responseHandler() throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("http://localhost/json");

		ResponseHandler<MyJsonObject> rh = new ResponseHandler<MyJsonObject>() {

		    public MyJsonObject handleResponse(
		            final HttpResponse response) throws IOException {
		        StatusLine statusLine = response.getStatusLine();
		        HttpEntity entity = response.getEntity();
		        if (statusLine.getStatusCode() >= 300) {
		            throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
		        }
		        if (entity == null) {
		            throw new ClientProtocolException("Response contains no content");
		        }
		        Gson gson = new GsonBuilder().create();
		        ContentType contentType = ContentType.getOrDefault(entity);
		        Charset charset = contentType.getCharset();
		        Reader reader = new InputStreamReader(entity.getContent(), charset);
		        return gson.fromJson(reader, MyJsonObject.class);
		    }
		};
		MyJsonObject myjson = httpclient.execute(httpget, rh);
	}
	
	
	
	/*
	 * 1.2. HttpClient interface

	HttpClient interface represents the most essential contract for HTTP request execution. 
	It imposes no restrictions or particular details on the request execution process and leaves the specifics of connection management, state management, authentication and redirect handling up to individual implementations. 
	This should make it easier to decorate the interface with additional functionality such as response content caching.
	Generally HttpClient implementations act as a facade to a number of special purpose handler or strategy interface implementations 
	responsible for handling of a particular aspect of the HTTP protocol such as redirect or authentication handling or making decision about connection persistence and keep alive duration. This enables the users to selectively replace default implementation of those aspects with custom, application specific ones.
	 * */
	private static void httpClientInterface(){
		
		ConnectionKeepAliveStrategy keepAliveStrat = new DefaultConnectionKeepAliveStrategy() {

		    @Override
		    public long getKeepAliveDuration(
		            HttpResponse response,
		            HttpContext context) {
		        long keepAlive = super.getKeepAliveDuration(response, context);
		        if (keepAlive == -1) {
		            // Keep connections alive 5 seconds if a keep-alive value
		            // has not be explicitly set by the server
		            keepAlive = 5000;
		        }
		        return keepAlive;
		    }

		};
		CloseableHttpClient httpclient = HttpClients.custom().setKeepAliveStrategy(keepAliveStrat).build();
	}
	
	
	/*
	 * 1.2.2. HttpClient resource deallocation
		When an instance CloseableHttpClient is no longer needed and is about to go out of scope the connection manager associated with it must be shut down by calling the CloseableHttpClient#close() method.
	 * */
	private static void closeHttpClient() throws IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
		    //do something
		} finally {
		    httpclient.close();
		}
	}
	
	
	/*
	 * 1.3. HTTP execution context

	Originally HTTP has been designed as a stateless, response-request oriented protocol. However, real world applications often need to be able to persist state information through several logically related request-response exchanges. In order to enable applications to maintain a processing state HttpClient allows HTTP requests to be executed within a particular execution context, referred to as HTTP context. Multiple logically related requests can participate in a logical session if the same context is reused between consecutive requests. HTTP context functions similarly to a java.util.Map<String, Object>. It is simply a collection of arbitrary named values. An application can populate context attributes prior to request execution or examine the context after the execution has been completed.

	HttpContext can contain arbitrary objects and therefore may be unsafe to share between multiple threads. It is recommended that each thread of execution maintains its own context.

	In the course of HTTP request execution HttpClient adds the following attributes to the execution context:

    HttpConnection instance representing the actual connection to the target server.

    HttpHost instance representing the connection target.

    HttpRoute instance representing the complete connection route

    HttpRequest instance representing the actual HTTP request. The final HttpRequest object in the execution context always represents the state of the message exactly as it was sent to the target server. Per default HTTP/1.0 and HTTP/1.1 use relative request URIs. However if the request is sent via a proxy in a non-tunneling mode then the URI will be absolute.

    HttpResponse instance representing the actual HTTP response.

    java.lang.Boolean object representing the flag indicating whether the actual request has been fully transmitted to the connection target.

    RequestConfig object representing the actual request configuation.

    java.util.List<URI> object representing a collection of all redirect locations received in the process of request execution.

	One can use HttpClientContext adaptor class to simplify interractions with the context state.
	 * */
	private static void httpContext() throws IOException{
		HttpContext context = new BasicHttpContext();
		HttpClientContext clientContext = HttpClientContext.adapt(context);
		HttpHost target = clientContext.getTargetHost();
		HttpRequest request = clientContext.getRequest();
		HttpResponse response = clientContext.getResponse();
		RequestConfig config = clientContext.getRequestConfig();
		
		
		
		//Multiple request sequences that represent a logically related session should be executed with the same HttpContext instance to ensure automatic propagation of conversation context and state information between requests.
		//In the following example the request configuration set by the initial request will be kept in the execution context and get propagated to the consecutive requests sharing the same context.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000).setConnectTimeout(1000).build();

		HttpGet httpget1 = new HttpGet("http://localhost/1");
		httpget1.setConfig(requestConfig);
		CloseableHttpResponse response1 = httpclient.execute(httpget1, context);
		try {
		    HttpEntity entity1 = response1.getEntity();
		} finally {
		    response1.close();
		}
		HttpGet httpget2 = new HttpGet("http://localhost/2");
		CloseableHttpResponse response2 = httpclient.execute(httpget2, context);
		try {
		    HttpEntity entity2 = response2.getEntity();
		} finally {
		    response2.close();
		}
	}
	
	
	
	/*
	 * 1.4. HTTP protocol interceptors

		The HTTP protocol interceptor is a routine that implements a specific aspect of the HTTP protocol. Usually protocol interceptors are expected to act upon one specific header or a group of related headers of the incoming message, or populate the outgoing message with one specific header or a group of related headers. Protocol interceptors can also manipulate content entities enclosed with messages - transparent content compression / decompression being a good example. Usually this is accomplished by using the 'Decorator' pattern where a wrapper entity class is used to decorate the original entity. Several protocol interceptors can be combined to form one logical unit.
		
		Protocol interceptors can collaborate by sharing information - such as a processing state - through the HTTP execution context. Protocol interceptors can use HTTP context to store a processing state for one request or several consecutive requests.
		
		Usually the order in which interceptors are executed should not matter as long as they do not depend on a particular state of the execution context. If protocol interceptors have interdependencies and therefore must be executed in a particular order, they should be added to the protocol processor in the same sequence as their expected execution order.
		
		Protocol interceptors must be implemented as thread-safe. Similarly to servlets, protocol interceptors should not use instance variables unless access to those variables is synchronized.
		
		This is an example of how local context can be used to persist a processing state between consecutive requests:
	 * */
	private static void protocolInterceptor() throws ClientProtocolException, IOException{
		
		CloseableHttpClient httpclient = HttpClients.custom()
		        .addInterceptorLast(new HttpRequestInterceptor() {

		            public void process(
		                    final HttpRequest request,
		                    final HttpContext context) throws HttpException, IOException {
		                AtomicInteger count = (AtomicInteger) context.getAttribute("count");
		                request.addHeader("Count", Integer.toString(count.getAndIncrement()));
		            }

		        })
		        .build();

		AtomicInteger count = new AtomicInteger(1);
		HttpClientContext localContext = HttpClientContext.create();
		localContext.setAttribute("count", count);

		HttpGet httpget = new HttpGet("http://localhost:8080/");
		for (int i = 0; i < 10; i++) {
		    CloseableHttpResponse response = httpclient.execute(httpget, localContext);
		    try {
		        HttpEntity entity = response.getEntity();
		    } finally {
		        response.close();
		    }
		}
	}
	
	
	/*
	 * 7.3. Using the FutureRequestExecutionService
		Using the FutureRequestExecutionService, you can schedule http calls and treat the response as a Future. 
		This is useful when e.g. making multiple calls to a web service. The advantage of using the FutureRequestExecutionService is that you can use multiple threads to schedule requests concurrently, set timeouts on the tasks, or cancel them when a response is no longer necessary.
		FutureRequestExecutionService wraps the request with a HttpRequestFutureTask, which extends FutureTask. 
		This class allows you to cancel the task as well as keep track of various metrics such as request duration.
		7.3.1. Creating the FutureRequestExecutionService
		The constructor for the futureRequestExecutionService takes any existing httpClient instance and an ExecutorService instance. When configuring both, it is important to align the maximum number of connections with the number of threads you are going to use. 
		When there are more threads than connections, the connections may start timing out because there are no available connections. When there are more connections than threads, the futureRequestExecutionService will not use all of them
	 * */
	private static void futureTask() throws InterruptedException, ExecutionException{
		HttpClient httpClient = HttpClientBuilder.create().setMaxConnPerRoute(5).build();
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		FutureRequestExecutionService futureRequestExecutionService = new FutureRequestExecutionService(httpClient, executorService);
		

		HttpRequestFutureTask<Boolean> task = futureRequestExecutionService.execute(
		    new HttpGet("http://www.google.com"), HttpClientContext.create(),
		    new OkidokiHandler(){
		    	
		    });
		// blocks until the request complete and then returns true if you can connect to Google
		boolean ok=task.get();
	}
	
	
	/*
	 * 2.8. HttpClient proxy configuration
		Even though HttpClient is aware of complex routing schemes and proxy chaining, it supports only simple direct or one hop proxy connections out of the box.
		The simplest way to tell HttpClient to connect to the target host via a proxy is by setting the default proxy parameter:
	 * */
	private static void httpProxy(){
		HttpHost proxy = new HttpHost("someproxy", 8080);
		DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
		CloseableHttpClient httpclient = HttpClients.custom()
		        .setRoutePlanner(routePlanner)
		        .build();
		
		// can also instruct HttpClient to use the standard JRE proxy selector to obtain proxy information:
		
		SystemDefaultRoutePlanner routePlanner2 = new SystemDefaultRoutePlanner(
		        ProxySelector.getDefault());
		CloseableHttpClient httpclient2 = HttpClients.custom()
		        .setRoutePlanner(routePlanner2)
		        .build();
		
		
	}
	
	
	private static void proxyAuth() throws ClientProtocolException, IOException{
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope("localhost", 8888),
                new UsernamePasswordCredentials("squid", "squid"));
        credsProvider.setCredentials(
                new AuthScope("httpbin.org", 80),
                new UsernamePasswordCredentials("user", "passwd"));
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider).build();
        try {
            HttpHost target = new HttpHost("httpbin.org", 80, "http");
            HttpHost proxy = new HttpHost("localhost", 8888);

            RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();
            HttpGet httpget = new HttpGet("/basic-auth/user/passwd");
            httpget.setConfig(config);

            System.out.println("Executing request " + httpget.getRequestLine() + " to " + target + " via " + proxy);

            CloseableHttpResponse response = httpclient.execute(target, httpget);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                System.out.println(EntityUtils.toString(response.getEntity()));
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
	}
	
	
	
	
}

  class OkidokiHandler implements ResponseHandler<Boolean> {
	public Boolean handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
		return response.getStatusLine().getStatusCode() == 200;
	}
}