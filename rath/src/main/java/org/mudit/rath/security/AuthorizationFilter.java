package org.mudit.rath.security;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.mudit.rath.configuration.MySessionFactory;
import org.mudit.rath.configuration.Role;
import org.mudit.rath.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Secured
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {
	
	@Context
    private ResourceInfo resourceInfo;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// TODO Auto-generated method stub
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

	    // Check if the HTTP Authorisation header is present and formatted correctly 
	    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
	        throw new NotAuthorizedException("Authorization header must be provided");
	    }
	
	    // Extract the token from the HTTP Authorisation header
	    String token = authorizationHeader.substring("Bearer".length()).trim();
	    Claims claims = Jwts.parser()         
	    		   .setSigningKey(DatatypeConverter.parseBase64Binary("MysecretKey"))
	    		   .parseClaimsJws(token).getBody();
	    
	    // Get the resource class which matches with the requested URL
        // Extract the roles declared by it
        Class<?> resourceClass = resourceInfo.getResourceClass();
        List<Role> classRoles = extractRoles(resourceClass);

        // Get the resource method which matches with the requested URL
        // Extract the roles declared by it
        Method resourceMethod = resourceInfo.getResourceMethod();
        List<Role> methodRoles = extractRoles(resourceMethod);
        User current_user;
        try {

            // Check if the user is allowed to execute the method
            // The method annotations override the class annotations
            if (methodRoles.isEmpty()) {
                current_user =checkPermissions(classRoles,token);
            } else {
               current_user = checkPermissions(methodRoles,token);
            }
            if (current_user == null){
            	requestContext.abortWith(
                        Response.status(Response.Status.FORBIDDEN).build());
            }
            requestContext.setProperty("user", current_user);
        } catch (Exception e) {
            requestContext.abortWith(
                Response.status(Response.Status.FORBIDDEN).build());
        }
        
    }

    // Extract the roles from the annotated element
    private List<Role> extractRoles(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return new ArrayList<Role>();
        } else {
            Secured secured = annotatedElement.getAnnotation(Secured.class);
            if (secured == null) {
                return new ArrayList<Role>();
            } else {
                Role[] allowedRoles = secured.value();
                return Arrays.asList(allowedRoles);
            }
        }
    }

    private User checkPermissions(List<Role> allowedRoles,String token) throws Exception {
        // Check if the user contains one of the allowed roles
        // Throw an Exception if the user has not permission to execute the method
    	Session new_session = MySessionFactory.getMy_factory().openSession();
		new_session.beginTransaction();
		Criteria criteria = new_session.createCriteria(org.mudit.rath.model.UserSession.class);
    	org.mudit.rath.model.UserSession current_session = (org.mudit.rath.model.UserSession) criteria.add(Restrictions.eq("access_token", token)).uniqueResult();
    	User current_user = current_session.getUser();
    	System.out.println(current_user.getRole());
    	try {
    	for (Role role:allowedRoles){
    		if (role.toString().contains(current_user.getRole().toString())){
    			return current_user;
    		}
    	}
    	}catch (NullPointerException e){
    		 
    	}
    	return null;
    }

}
