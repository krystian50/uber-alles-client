package com.example.tomek.uberallescustomer.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Krystian on 2017-04-16.
 */

public class AuthenticationInterceptor implements Interceptor {

    private String authToken;

    public AuthenticationInterceptor(String token) {
        this.authToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .header("Authorization", authToken);
        System.out.println("intercept");

        Request request = builder.build();
        Response response = chain.proceed(request);
//        System.out.println(String.format("Received response for %s in %.1fms%n%s" +
//                response.request().url() + response.headers()));
        return response;
}
}