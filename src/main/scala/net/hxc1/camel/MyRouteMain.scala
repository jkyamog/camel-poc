package net.hxc1.camel

import org.apache.camel.main.Main
import org.apache.camel.scala.dsl.builder.RouteBuilderSupport
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.apache.camel.builder.RouteBuilder

/**
 * A Main to run Camel with MyRouteBuilder
 */
object MyRouteMain extends RouteBuilderSupport {

  def main(args: Array[String]) {
    val springContext = new ClassPathXmlApplicationContext("applicationContext.xml")
    springContext.start
    
    val routeBuilder = springContext.getBean(classOf[MyRouteBuilder])
//    routeBuilder.init
//    springContext.getBean("dataSource")

    val main = new Main()
    // enable hangup support so you need to use ctrl + c to stop the running app
    main.enableHangupSupport();
//    main.addRouteBuilder(new MyRouteBuilder())
    // must use run to start the main application
    main.run();
    
  }
}

