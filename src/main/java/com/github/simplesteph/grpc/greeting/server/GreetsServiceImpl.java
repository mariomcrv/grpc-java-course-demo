package com.github.simplesteph.grpc.greeting.server;

import com.proto.greet.*;
import io.grpc.stub.StreamObserver;

public class GreetsServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {

    // THIS IS A UNARY API
    @Override
    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        // extract the fields we need
        Greeting greeting = request.getGreeting();
        String firstName = greeting.getFirstName();
        String lastName = greeting.getLastName();

        // create the response
        String result = "Hello " + firstName + " hope you are having a nice day!\n" +
                "By the way, I know your last name is " + lastName;
        GreetResponse response = GreetResponse.newBuilder()
                .setResult(result)
                .build();

        // Send the response
        responseObserver.onNext(response);

        // complete the RPC call
        responseObserver.onCompleted();
    }

    // THE METHOD BELOW IS A SERVER-STREAMING API
    @Override
    public void greetManyTimes(GreetManyTimesRequest request, StreamObserver<GreetManyTimesResponse> responseObserver) {
        String firstName = request.getGreeting().getFirstName();

        try {
            for (int i = 0; i < 10; i++) {
                String result = "Hello " + firstName + " response number: " + i;
                GreetManyTimesResponse response = GreetManyTimesResponse.newBuilder()
                        .setResult(result)
                        .build();

                //send response inside the loop
                responseObserver.onNext(response);

                // wait i second to execute the next loop again
                Thread.sleep(1000L);
            }
            // below means that when the thread is interrupted
            // print the stacktrace and FINALLY! call onCompleted
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //complete the rpc call
            responseObserver.onCompleted();

        }
    }

    // THE METHOD BELOW IS A CLIENT-STREAMING API
    @Override
    public StreamObserver<LongGreetRequest> longGreet(StreamObserver<LongGreetResponse> responseObserver) {
        // this implementation is particularly tricky because we have to return a value as opposed
        // to the other APIs. we return a StreamObserver object of type LongGreetRequest

        StreamObserver<LongGreetRequest> requestObserver = new StreamObserver<LongGreetRequest>() {
            String result = "";

            @Override
            public void onNext(LongGreetRequest value) { // How to react to a new message
                // Client sends a message
                result += "Hello " + value.getGreeting().getFirstName() + "! ";
            }

            @Override
            public void onError(Throwable t) { // how to react to an error
                // Client sends an error
            }

            @Override
            public void onCompleted() { // how to react on completion
                // Client is done
                responseObserver.onNext(
                     LongGreetResponse.newBuilder()
                     .setResult(result)
                     .build()
                );
                responseObserver.onCompleted();
            }
        };
        return requestObserver;
    }
}
