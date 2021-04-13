package com.github.simplesteph.grpc.greeting.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GreetingServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Hello gRPC. I am the server!");

        //Here, we create our server
        Server server = ServerBuilder.forPort(50051)
                .addService(new GreetsServiceImpl()) //this adds our service to the server
                .build();

        //this is how we start the server
        server.start();


        //Every time we request to shut down our application, the server will shut down
        Runtime.getRuntime().addShutdownHook(new Thread( () -> {
            System.out.println("Received Shutdown Request");
            server.shutdown();
            System.out.println("Successfully  stopped  the server");
        } ));

        //if the do not do this, the service starts and the program will finish
        server.awaitTermination();
    }
}
