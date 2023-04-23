package com.fanqie.commonutils.utils;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface Permission {

	/**
	 * 权限
	 */
	@AliasFor("value")
	PermissionEnum[] name() default {};

	/**
	 * 权限
	 */
	@AliasFor("name")
	PermissionEnum[] value() default {};

}