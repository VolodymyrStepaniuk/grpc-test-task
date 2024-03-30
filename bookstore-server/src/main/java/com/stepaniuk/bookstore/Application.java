package com.stepaniuk.bookstore;

import com.stepaniuk.bookstore.book.exception.ExceptionHandler;
import com.stepaniuk.bookstore.grpc.BookGrpcService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws IOException, InterruptedException {
		Server server = ServerBuilder.forPort(8080)
				.addService(SpringApplication.run(Application.class).getBean(BookGrpcService.class))
				.intercept(new ExceptionHandler())
				.build();

		server.start();
		server.awaitTermination();
	}

}
