package com.github.simplesteph.grpc.calculator.server;

import com.proto.calculator.*;
import io.grpc.stub.StreamObserver;

public class CalculatorServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {

    // Unary API
    @Override
    public void sum(CalculateRequest request, StreamObserver<CalculateResponse> responseObserver) {
        //extract all the files we need
        Numbers numbers = request.getNumbers();
        int firstNumber = numbers.getFirstNumber();
        int secondNumber = numbers.getSecondNumber();


        // create the response
        int result = firstNumber + secondNumber;
        CalculateResponse response = CalculateResponse.newBuilder()
                .setResult(result) //we set the result to be the value of the var
                .build();

        //send the response to the client
        responseObserver.onNext(response);

        //complete the RPC call
        responseObserver.onCompleted();

    }

    // Unary API
    @Override
    public void div(CalculateRequest request, StreamObserver<CalculateResponse> responseObserver) {
        //extract all the files we need
        Numbers numbers = request.getNumbers();
        int firstNumber = numbers.getFirstNumber();
        int secondNumber = numbers.getSecondNumber();


        // create the response
        int result = firstNumber / secondNumber;
        CalculateResponse response = CalculateResponse.newBuilder()
                .setResult(result) //we set the result to be the value of the var
                .build();

        //send the response to the client
        responseObserver.onNext(response);

        //complete the RPC call
        responseObserver.onCompleted();

    }

    // server streaming API
    @Override
    public void primeNumberDecomposition(PrimeNumberDecompositionRequest request, StreamObserver<PrimeNumberDecompositionResponse> responseObserver) {
        // extract the data - we need the number
        long number = request.getNumber();
        // now we can transform the number and send multiple messages to the client for every time we decompose a number
        long k = 2L; // we need this variable to increment it by one if number % k == 0

        while (number > 1) {
            if (number % k == 0) {
                number = number / k; // example 120 / 2 = 60 --> number to evaluate for the next iteration

                // send the result to the client
                PrimeNumberDecompositionResponse response = PrimeNumberDecompositionResponse.newBuilder()
                        .setResult(k)
                        .build();

                responseObserver.onNext(response);
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            } else {
                k++; // if the remainder is not 0, increase the value of k by 1
            }
        }
        responseObserver.onCompleted();
    }
}
