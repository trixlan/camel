package com.example;

import org.springframework.stereotype.Component;

/**
 * A bean that returns a message when you call the {@link #saySomething()} method.
 * <p/>
 * Uses <tt>@Component("myBean")</tt> to register this bean with the message <tt>myBean</tt>
 * that we use in the Camel route to lookup this bean.
 */

@Component("beanDatos")
public class BeanDatos {

    //@Column(name="message", nullable=true)
    private String message;

    //@Column(name="message", nullable=true)
    private String internalCode;

    public void beanDatos(String message, String internalCode) {
        this.message = message;
        this.internalCode = internalCode;
    }

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
    
    public String saludo() {
        return "Hola " +  this.getMessage() + " InternalCode " + this.getInternalCode();
    }

}