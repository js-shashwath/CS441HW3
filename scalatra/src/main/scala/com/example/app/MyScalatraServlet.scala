package com.example.app

import org.scalatra._
import com.typesafe.config.ConfigFactory
import spray.json._

class MyScalatraServlet extends ScalatraServlet {

  // Input parameter to the AWS Gateway
  val inputTime = ConfigFactory.load().getString("inputTime")
  val inputDifferentialTime = ConfigFactory.load().getString("inputDifferentialTime")

  get("/api") {

    // Sending the input parameters to the Gateways's get call
    val response = scala.io.Source.fromURL("https://sqlzvgvmm8.execute-api.us-east-1.amazonaws.com/prod?inputTime="+inputTime+"&inputDifferentialTime="+inputDifferentialTime)
    val result = response.mkString
    val output = result.parseJson.asJsObject
    output
  }

}
