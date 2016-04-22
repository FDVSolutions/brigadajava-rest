package com.fdvsolutions.brigada.rest.security;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
import org.jboss.resteasy.util.Base64;

/**
 * This interceptor verify the access permissions for a user based on username and passowrd provided in request
 */
@Provider
@ServerInterceptor
public class SecurityInterceptor implements PreProcessInterceptor {

	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Basic";
	private static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this resource", 401, new Headers<Object>());;
	private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("Nobody can access this resource", 403, new Headers<Object>());;
	private static final ServerResponse SERVER_ERROR = new ServerResponse("INTERNAL SERVER ERROR", 500, new Headers<Object>());;
	
	@Override
	public ServerResponse preProcess(HttpRequest request, ResourceMethod methodInvoked) throws Failure, WebApplicationException {
		Method method = methodInvoked.getMethod();
		
		//verify generic access
		if(method.isAnnotationPresent(PermitAll.class)) {
			return null;
		}
		
		if(method.isAnnotationPresent(DenyAll.class)) {
			return ACCESS_FORBIDDEN;
		}
		
		//Get request headers and parse authorization
		final HttpHeaders headers = request.getHttpHeaders();
	    final List<String> authorization = headers.getRequestHeader(AUTHORIZATION_PROPERTY);
	    
	    if(authorization == null || authorization.isEmpty()) {
	    	return ACCESS_DENIED;
	    }
	    
	    final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

	    String usernameAndPassword;
		try {
			usernameAndPassword = new String(Base64.decode(encodedUserPassword));
		} 
		catch (IOException e) {
			return SERVER_ERROR;
		}

	    final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
	    final String username = tokenizer.nextToken();
	    final String password = tokenizer.nextToken();
	    
	    //verify user access
		if(method.isAnnotationPresent(RolesAllowed.class)) {
			
			RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
			Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

			String userRole = "ADMIN";  //TODO load user and fetch permission instead of ADMIN harcode

			if(!rolesSet.contains(userRole))
				return ACCESS_DENIED;
		}

		return null;
	}
	
}
