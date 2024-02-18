package com.pluralsight.courseinfo.cli.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.net.http.HttpClient.*;

public class CourseRetrievalService {

    private static final String PS_URI = "https://app.pluralsight.com/profile/data/author/%s/all-content";

     // private static final HttpClient CLIENT = HttpClient.newHttpClient(); // Constructor normal, para crear un objeto HttpClient

    private static final HttpClient CLIENT = HttpClient.newBuilder() // contructor para redireccionar siempre , no importa si en la URI se cambia el documento que se esta buscando
            .followRedirects(Redirect.ALWAYS)
            .build();


    public String getCoursesFor (String authorId) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.
                newBuilder(URI.create(PS_URI.formatted(authorId))).
                GET().
                build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return switch (response.statusCode()){
            case 200 -> response.body();
            case 404 -> "";
            default -> throw new IllegalStateException("Unexpected value: " + response.statusCode());
        };

    }
}
