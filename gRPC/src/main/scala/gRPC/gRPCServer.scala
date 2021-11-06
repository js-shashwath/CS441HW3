package gRPC

import java.util.logging.Logger
import io.grpc.{Server, ServerBuilder}
import spray.json._

import java.util.concurrent.TimeUnit
import java.util.logging.{Level, Logger}
import com.logfile.proto.logfile.{GreeterGrpc, logReply, logRequest}
import com.logfile.proto.logfile.GreeterGrpc.GreeterBlockingStub
import com.typesafe.config.ConfigFactory
import io.grpc.{ManagedChannel, ManagedChannelBuilder, StatusRuntimeException}
import scalapb.options.ScalapbProto.message

import scala.concurrent.{ExecutionContext, Future}

/*
gRPC implementation referenced from the below repo:
https://github.com/xuwei-k/grpc-scala-sample/tree/master/grpc-scala/src/main/scala/io/grpc/examples/helloworld
*/

object gRPCServer {

  // Configuring the port for the gRPC server
  private val logger = Logger.getLogger(classOf[gRPCServer].getName)
  val port = ConfigFactory.load().getInt("port")

  // Starting the server of the gRPC server
  def main(args: Array[String]): Unit = {
    val server = new gRPCServer(ExecutionContext.global)
    server.start()
    server.blockUntilShutdown()
  }
}

class gRPCServer(executionContext: ExecutionContext) { self =>

  private[this] var server: Server = null

  private def start(): Unit = {
    server = ServerBuilder.forPort(gRPCServer.port).addService(GreeterGrpc.bindService(new GreeterImpl, executionContext)).build.start
    gRPCServer.logger.info("Server started on " + gRPCServer.port)
    sys.addShutdownHook {
      gRPCServer.logger.info("Shutting down gRPC server since JVM is shutting down")
      self.stop()
    }
  }

  // Stopping the server when the instance is still running
  private def stop(): Unit = {
    if (server != null) {
      server.shutdown()
    }
  }

  private def blockUntilShutdown(): Unit = {
    if (server != null) {
      server.awaitTermination()
    }
  }

  private class GreeterImpl extends GreeterGrpc.Greeter {

    override def sayHello(req: logRequest) = {

      val input = req.name.split(",")
      val inputTime = input(0)
      val inputDifferentialTime = input(1)

      //Call Lambda API Gateway
      val responseAWS = scala.io.Source.fromURL("https://sqlzvgvmm8.execute-api.us-east-1.amazonaws.com/prod?inputTime="+inputTime+"&inputDifferentialTime="+inputDifferentialTime)
      val result = responseAWS.mkString
      val json = result.parseJson.asJsObject
      val reply = logReply(message = result)
      responseAWS.close()
      Future.successful(reply)
    }
  }

}
