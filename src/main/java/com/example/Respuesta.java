package com.example;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * A bean that returns a message when you call the {@link #saySomething()} method.
 * <p/>
 * Uses <tt>@Component("myBean")</tt> to register this bean with the name <tt>myBean</tt>
 * that we use in the Camel route to lookup this bean.
 */
@Component("respuesta")
public class Respuesta {

    private String message;
    private String internalCode;

    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return this.message;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }
    
    public String getInternalCode() {
        return this.internalCode;
    }
    
    public String respuesta() {
        return this.message + " " + this.internalCode;
    }

}
