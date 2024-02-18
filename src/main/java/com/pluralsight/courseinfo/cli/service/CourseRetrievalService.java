package com.pluralsight.courseinfo.cli.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static java.net.http.HttpClient.*;

public class CourseRetrievalService {

    private static final String PS_URI = "https://app.pluralsight.com/profile/data/author/%s/all-content";

     // private static final HttpClient CLIENT = HttpClient.newHttpClient(); // Constructor normal, para crear un objeto HttpClient

    private static final HttpClient CLIENT = HttpClient.newBuilder() // contructor para redireccionar siempre , no importa si en la URI se cambia el documento que se esta buscando
            .followRedirects(Redirect.ALWAYS)
            .build();

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    public List<PluralsightCourse> getCoursesFor(String authorId) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.
                newBuilder(URI.create(PS_URI.formatted(authorId))).
                GET().
                build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return switch (response.statusCode()){
            case 200 -> toPluralsightCourses(response);
            case 404 -> List.of();
            default -> throw new IllegalStateException("Unexpected value: " + response.statusCode());
        };

    }

    private static List<PluralsightCourse> toPluralsightCourses(HttpResponse<String> response) throws JsonProcessingException {
        JavaType returnType = OBJECT_MAPPER.
                getTypeFactory().
                constructType(List.class,PluralsightCourse.class);
        return OBJECT_MAPPER.readValue(response.body(), returnType);
    }
}
