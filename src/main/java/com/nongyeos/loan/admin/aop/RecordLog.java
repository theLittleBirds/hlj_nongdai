package com.nongyeos.loan.admin.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;  

@Target(ElementType.METHOD)     
@Retention(RetentionPolicy.RUNTIME)     
@Documented    
@Inherited    
public @interface RecordLog {  
    /** 
     * 方法描述 
     * @return 
     */  
    public String description() default "no description";   
}