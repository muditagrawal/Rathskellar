package org.mudit.rath.security;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.ws.rs.NameBinding;

import org.mudit.rath.configuration.Role;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
@NameBinding
public @interface Secured {
	Role[] value() default {};
}
