package com.github.simplesteph.grpc.calculator.client;

import com.proto.calculator.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CalculatorClient {

    public static void main(String[] args) {
        System.out.println("I am the Client!!");

        // establish the channel
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50052)
                .usePlaintext()
                .build();

        //create the stub
        System.out.println("Creating stub...");
        // we crated an Calculator service client (blocking - synchronous)
        CalculatorServiceGrpc.CalculatorServiceBlockingStub calculatorClient = CalculatorServiceGrpc.newBlockingStub(channel);

        // -- UNARY --
        //create the protocol buffer message
        Numbers numbers  = Numbers.newBuilder()
                .setFirstNumber(20)
                .setSecondNumber(8)
                .build();

        //do the same for the request
        CalculateRequest calculateRequest = CalculateRequest.newBuilder()
                .setNumbers(numbers)
                .build();

        //call the rpc and get back the GreetResponse (protocol buffer)
        CalculateResponse calculateResponse = calculatorClient.sum(calculateRequest);

        //print the result
        System.out.println(numbers.getFirstNumber() + " + " +numbers.getSecondNumber() + " = " + calculateResponse.getResult());

        //shutdown the channel
        System.out.println("Shutting down the channel");
        //channel.shutdown();


        // -- SERVER STREAMING --
        System.out.println("---Server Steaming---");
        System.out.println("Prime Number Decomposition Service");

        // Prepare the request
        long number = 567895488153L;

        // message for the user
        System.out.println("The number to decompose is: " + number);

        // Stream the response in a blocking manner
        // the line below is the same as the example above but with fewer lines of code, we are setting the request within the response call
        calculatorClient.primeNumberDecomposition(PrimeNumberDecompositionRequest.newBuilder()
                .setNumber(number).build())
                .forEachRemaining(primeNumberDecompositionResponse -> {
                    System.out.println(primeNumberDecompositionResponse.getResult());
                });

        // shut down the channel
        System.out.println("Shutting down the channel");
        channel.shutdown();
    }


}
