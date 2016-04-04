package com.blandon.test.http;

import java.io.IOException;

import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Lookup;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;

public class HttpAuth {

	
	/*
	 * User credentials
		Any process of user authentication requires a set of credentials that can be used to establish user identity. 
		In the simplest form user credentials can be just a user name / password pair. UsernamePasswordCredentials represents a set of credentials consisting of a security principal and a password in clear text. 
		This implementation is sufficient for standard authentication schemes defined by the HTTP standard specification.
	 * */
	private static void basicAuth(){
		UsernamePasswordCredentials creds = new UsernamePasswordCredentials("user", "pwd");
		System.out.println(creds.getUserPrincipal().getName());
		System.out.println(creds.getPassword());
		        
	}
	
	
	/*
	 * 4.3. Credentials provider
		Credentials providers are intended to maintain a set of user credentials and to be able to produce user credentials for a particular authentication scope. 
		Authentication scope consists of a host name, a port number, a realm name and an authentication scheme name. 
		When registering credentials with the credentials provider one can provide a wild card (any host, any port, any realm, any scheme) instead of a concrete attribute value. 
		The credentials provider is then expected to be able to find the closest match for a particular scope if the direct match cannot be found.
		HttpClient can work with any physical representation of a credentials provider that implements the CredentialsProvider interface. 
		The default CredentialsProvider implementation called BasicCredentialsProvider is a simple implementation backed by a java.util.HashMap.
	 * */
	private static void credentialProvider(){
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(
		    new AuthScope("somehost", AuthScope.ANY_PORT), 
		    new UsernamePasswordCredentials("u1", "p1"));
		credsProvider.setCredentials(
		    new AuthScope("somehost", 8080), 
		    new UsernamePasswordCredentials("u2", "p2"));
		credsProvider.setCredentials(
		    new AuthScope("otherhost", 8080, AuthScope.ANY_REALM, "ntlm"), 
		    new UsernamePasswordCredentials("u3", "p3"));

		System.out.println(credsProvider.getCredentials(
		    new AuthScope("somehost", 80, "realm", "basic")));
		System.out.println(credsProvider.getCredentials(
		    new AuthScope("somehost", 8080, "realm", "basic")));
		System.out.println(credsProvider.getCredentials(
		    new AuthScope("otherhost", 8080, "realm", "basic")));
		System.out.println(credsProvider.getCredentials(
		    new AuthScope("otherhost", 8080, null, "ntlm")));
	}
	
	/*
	 * 4.4. HTTP authentication and execution context
		HttpClient relies on the AuthState class to keep track of detailed information about the state of the authentication process.
		HttpClient creates two instances of AuthState in the course of HTTP request execution: one for target host authentication and another one for proxy authentication. 
		In case the target server or the proxy require user authentication the respective AuthScope instance will be populated with the AuthScope, AuthScheme and Crednetials used during the authentication process. 
		The AuthState can be examined in order to find out what kind of authentication was requested, whether a matching AuthScheme implementation was found and whether the credentials provider managed to find user credentials for the given authentication scope.
		In the course of HTTP request execution HttpClient adds the following authentication related objects to the execution context:
		Lookup instance representing the actual authentication scheme registry. The value of this attribute set in the local context takes precedence over the default one.
		CredentialsProvider instance representing the actual credentials provider. The value of this attribute set in the local context takes precedence over the default one.
		AuthState instance representing the actual target authentication state. The value of this attribute set in the local context takes precedence over the default one.
		AuthState instance representing the actual proxy authentication state. The value of this attribute set in the local context takes precedence over the default one.
		AuthCache instance representing the actual authentication data cache. The value of this attribute set in the local context takes precedence over the default one.
		The local HttpContext object can be used to customize the HTTP authentication context prior to request execution, or to examine its state after the request has been executed:
	 * */
	private static void authContext() throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = null;

		CredentialsProvider credsProvider = null;
		Lookup<AuthSchemeProvider> authRegistry = null;
		AuthCache authCache = null;

		HttpClientContext context = HttpClientContext.create();
		context.setCredentialsProvider(credsProvider);
		context.setAuthSchemeRegistry(authRegistry);
		context.setAuthCache(authCache);
		HttpGet httpget = new HttpGet("http://somehost/");
		CloseableHttpResponse response1 = httpclient.execute(httpget, context);

		AuthState proxyAuthState = context.getProxyAuthState();
		System.out.println("Proxy auth state: " + proxyAuthState.getState());
		System.out.println("Proxy auth scheme: " + proxyAuthState.getAuthScheme());
		System.out.println("Proxy auth credentials: " + proxyAuthState.getCredentials());
		AuthState targetAuthState = context.getTargetAuthState();
		System.out.println("Target auth state: " + targetAuthState.getState());
		System.out.println("Target auth scheme: " + targetAuthState.getAuthScheme());
		System.out.println("Target auth credentials: " + targetAuthState.getCredentials());
	}
}
