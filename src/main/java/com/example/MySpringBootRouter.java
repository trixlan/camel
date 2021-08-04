package com.example;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.Processor;

import org.apache.camel.Exchange;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
public class MySpringBootRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration()
        .component("servlet")
        .bindingMode(RestBindingMode.auto);

        rest("/api")
        // Servicio Post
        .post()
        .type(BeanDatos.class)
        .route()
        .log("The body is ${body.message}!!")
        .transform().simple("The message in body is ${body.saludo()}")
        .to("https://run.mocky.io/v3/3334df41-5550-4527-8a2a-fb682dae4f8f?bridgeEndpoint=true&throwExceptionOnFailure=false")
        .streamCaching()
        .log("Body1 ${body}")
        .convertBodyTo(String.class)
        .process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                String body = (String) exchange.getMessage().getBody();
                body = body.replaceAll("[\\s|\\u00A0]+","");
                Gson gson = new Gson();
                BeanDatos beanDatos = gson.fromJson(body, BeanDatos.class);
                exchange.getMessage().setBody(beanDatos);
            }
        })
        // .log("Body2 ${body}")
        //  .unmarshal()
        // .json(JsonLibrary.XStream)
        // .marshal().json(JsonLibrary.Jackson)
        // .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
        //.unmarshal().json(JsonLibrary.Jackson, BeanDatos.class)
        //---------------------"{\n  \"message\": \"INVALID_SHIPMENT_ORDER_FOLIO\",\n  \"internalCode\": \"PLN100007\"\n}"
        .endRest()
        // Servicio Post2
        .post("/datos")
        .type(BeanDatos.class)
        .route()
        .log("The body is ${body.message}!!")
        .process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                BeanDatos beanDatos = exchange.getMessage().getBody(BeanDatos.class);
                exchange.getMessage().setBody(beanDatos);
            }
        })
        .endRest()
        // Servicio Get
        .get()
        .route()
        .log("The body is ${body}")
        .transform().simple("The body is ${body}")
        .endRest();

//         from("timer:hello?period={{timer.period}}").routeId("hello")
//             .transform().method("myBean", "saySomething")
//             .filter(simple("${body} contains 'foo'"))
//                 .to("log:foo")
//             .end()
//             .to("stream:out")
//             .log("${body}")
//             .to("https://run.mocky.io/v3/3334df41-5550-4527-8a2a-fb682dae4f8f?brigeEndPoint=true&throwExceptionOnFailure=false")
//             // .to("https://run.mocky.io/v3/73666d43-8da3-4b02-b648-8121e80c4232")
// //            .to("https://run.mocky.io/v3/4e1f861e-6a7f-4ca1-85e7-e35bceee5b51")
// //            .to("stream:header")
// 	        .choice()
// 	        .when(simple("${header.CamelHttpResponseCode} == '200'"))
// 	        	.to("stream:out")
//             .when(simple("${header.CamelHttpResponseCode} == '500'"))
//                 .process(new Processor() {
//                     @Override
//                     public void process(Exchange exchange) throws Exception {
//                             String respuesta = exchange.getIn().getBody(String.class);
//                             String transformedText = respuesta.replace('\'', '"');
//                             System.out.println(transformedText);
//                             exchange.getMessage().setBody(transformedText);
//                         }
//                     })
//                 .unmarshal().json(JsonLibrary.Jackson, Respuesta.class)
//                 .log(">>> ${body}")
//                 .bean(Respuesta.class, "respuesta")
//                 .log("${body}")
//                 .log("${body}")
//                 .log("${body}")
// 	        .otherwise()
// 	            .log("No tenia datos la respuesta");
// //        http://10.1.99.27:8080/integrator/receipt-simple-confirm?between={"start": "2018-05-01", "end": "2018-06-12"}
    }

}
