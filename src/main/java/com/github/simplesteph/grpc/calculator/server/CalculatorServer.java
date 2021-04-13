package com.github.simplesteph.grpc.calculator.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class CalculatorServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("I am a gRPC server!\n" +
                "I will be listening on port 50052");

        //create the server
        Server server = ServerBuilder.forPort(50052)
                .addService(new CalculatorServiceImpl())
                .build();

        // start the server
        server.start();

        //Every time we request to shut down our application, the server will shut down
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Received Shutdown Request");
            server.shutdown();
            System.out.println("Successfully  stopped  the server");
        }));

        //if the do not do this, the service starts and the program will finish
        server.awaitTermination();
    }

}

