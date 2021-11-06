package com.example.app

import com.typesafe.config.ConfigFactory
import org.scalatra.test.scalatest._

class MyScalatraServletTests extends ScalatraFunSuite {

  addServlet(classOf[MyScalatraServlet], "/*")

  test("GET / on MyScalatraServlet should return status 200") {
    get("/") {
      status should equal (200)
    }
  }

  test("Test to check if there is a valid input time to be searched in the log file") {
    val inputTime = ConfigFactory.load().getString("inputTime")
    inputTime should (fullyMatch regex ("19:24:15"))
  }

  test("Test to check if there the correct S3 bucket has been instantiated") {
    val s3bucketName = ConfigFactory.load().getString("bucketName")
    s3bucketName should not be null
  }

  test("Test to check if there the RPC calls are running on the correct port") {
    val Localport = ConfigFactory.load().getString("port")
    Localport should be equals(50051)
  }

  test("Test to check if the API Gateway is valid"){
    val APIGatewayURL = ConfigFactory.load().getString("APIGatewayURL")
    APIGatewayURL should contain ;"https://"
  }

}

