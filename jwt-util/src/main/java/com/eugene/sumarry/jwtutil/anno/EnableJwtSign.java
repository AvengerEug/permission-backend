package com.eugene.sumarry.jwtutil.anno;

import com.eugene.sumarry.jwtutil.processor.ImportJWTRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ImportJWTRegistrar.class)
public @interface EnableJwtSign {
}
