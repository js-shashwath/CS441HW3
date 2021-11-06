# CS441 Homework 3 : Jawaharlal Sathyanarayan, Shashwath (sjawah2@uic.edu)

## Introduction
This is the third homework assignment of CS 441. 

1. The homework assignment consists of two interlocked parts: first, create a client program that uses gRPC to invoke a lambda function deployed on AWS to determine if the desired timestamp is in the log file.
   Second, to a client program and the corresponding lambda function that use the REST methods (e.g., GET or POST) to interact.
2. The GRPC program also has a server which on request in-turn requests the central server for processing the data and replies to the GRPC client.
3. The GRPC client and Server communicate using protobuffs which are configured in the 'protobuf' repository. The central server is actually a AWS lambda that serves to the request given via a REST protocol.
4. The AWS lambda is configured to listen to a 'GET' request which is being invoked in the GRPC server and the AKKA client.
5. The AWS lambda function in turn has to be given a privilege to access S3 bucket where the logs are stored.
6. The logs are stored into the S3 bucket by running an EC2 instance of the project 'LogFileGenerator' and having the S3 bucket as the target storage.

## Prerequisites

* Install SBT to execute the project : CS-441-HW3[AwsLambdaUsinggRPC]
* AWS account to run EC2 instance of LogFileGenerator, S3 bucket to store logs and AWS lambda to execute the code

## How to run the project

* Clone the GIT repository by using git clone https://github.com/js-shashwath/CS-441-HW3.git
* The scala version should be set in the Global Libraries under Project Structure in Files .
* The SBT configuration should be added by using Edit Configurations and then simulations can be ran in the IDE .

1] Steps to run the gRPC implementation :
* Run the following command in the console

- sbt run

Choose to execute the gRPCServer class so that the server is listening on the port mentioned in the config file. 
Then run the client class gRPCClient to get back the response from the lambda hosted on the API Gateway.

* One Additional step is required to ensure smooth execution. In File -> Project Structure-> Modules -> AwsLambdaUsingRPC-> Sources, Make sure that the path target\..\main is removed. Else an error will occur while execution

Output:
```
21:04:54.182 [grpc-nio-worker-ELG-1-2] DEBUG io.grpc.netty.NettyClientHandler - [id: 0x872dea6d, L:/127.0.0.1:52284 - R:localhost/127.0.0.1:50051] INBOUND HEADERS: streamId=3 headers=GrpcHttp2ResponseHeaders[grpc-status: 0] padding=0 endStream=true
21:04:54.186 [grpc-nio-worker-ELG-1-2] DEBUG io.grpc.netty.NettyClientHandler - [id: 0x872dea6d, L:/127.0.0.1:52284 - R:localhost/127.0.0.1:50051] INBOUND PING: ack=true bytes=1234
Nov 05, 2021 9:04:54 PM gRPC.gRPCClient response
INFO: Greeting: {"hash": "<md5 HASH object @ 0x7f330d57bfd0>", "isPresent": "True"}
21:04:54.199 [grpc-nio-worker-ELG-1-2] DEBUG io.grpc.netty.NettyClientHandler - [id: 0x872dea6d, L:/127.0.0.1:52284 - R:localhost/127.0.0.1:50051] OUTBOUND GO_AWAY: lastStreamId=0 errorCode=0 length=0 bytes=
```

2] Steps to run the Scalatra implementation :
* Run the following command in the console

- sbt (This command starts the sbt server on the default local port 8080)
- jetty:start to ping the server with the input request
- open a browser and check the URL : http://localhost:8080/api for the response from the API Gateway

Output:
```
{"hash":"","isPresent":"True"}
```

### Test cases crested

Test cases have been written for the following scenarios. Run the following command in the console

- sbt test

1. MyScalatraServlet should return status 200
2. Test to check if there is a valid input time to be searched in the log file
3. Test to check if there the correct S3 bucket has been instantiated
4. Test to check if there the RPC calls are running on the correct port
5. Test to check if the API Gateway is valid

### To run the AWS environment
* Create a account in aws.amazon.com and create a IAM user.
* Create a S3 bucket.
* Create a EC2 instance to deploy the project
* Use the commands below to install required stack for the EC2 instance

* chmod 400 LogFileGenerator.pem
* ssh -I C:\Users\Downloads\LogFileGenerator.pem ec2-3-91-200-46.compute-1.amazonaws.com
* sudo yum install git
* sudo yum install sbt
* sudo amazon-linux-extras enable corretto8
* sudo yum clean metadata
* sudo yum install -y java-1.8.0-amazon-corretto

* Deploy 'LogFileGenerator' in the EC2 instance using the git clone command
* Set Output path to S3 bucket created to store the logs and use sbt compile to generate the log files
* Create a AWS lambda with python function.
* Create an API gateway as the Endpoint with a 'GET' request.
* Deploy the Lambda function
* Now the lambda function is ready for deployment.

### My video submission :
https://youtu.be/QKsHj0ycdio