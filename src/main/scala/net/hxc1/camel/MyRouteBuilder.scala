package net.hxc1.camel

import org.apache.camel.Exchange
import org.apache.camel.scala.dsl.builder.RouteBuilder

/**
 * A Camel Router using the Scala DSL
 */
class MyRouteBuilder extends RouteBuilder {

    // an example of a Processor method
   val myProcessorMethod = (exchange: Exchange) => {
  	 exchange.getIn.setBody("1\n2\n3\n", classOf[String])
     //exchange.getIn.setBody("block test")
   }
   
   val sqlProcessor = (exchange: Exchange) => {
  	 println(exchange.getIn.getBody)
   }
   
   val getEmail = (exchange: Exchange) => {
  	 val body = exchange.getIn.getBody(classOf[java.util.Map[String, String]])
  	 val email = body.get("EMAIL")
  	 val msg = exchange.getIn
  	 msg.setHeader("email", email)
   }
   
   // a route using Scala blocks
	   "timer://foo?period=5s" ==> {
//	      process(myProcessorMethod)
	      to("sql:select * from maillist?dataSourceRef=dataSource")
	      to("log:onsql")
	      to("direct:a")
	   }
	   
	   "direct:a" ==> {
	  	 	split(_.getIn.getBody) {
	  	 		to("log:aftersplit")
	  	 		process(getEmail)
	  	 		to("freemarker:EmailTemplate.ftl")
	  	 		to("log:afterfreemarker")
	  	 	}
	   }
}
