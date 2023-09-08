package org.example;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        simpleConsole();
    }

    @SuppressWarnings("squid:S106")
    private static void simpleConsole() {
        OkHttpClient client = new OkHttpClient();
        String apiUrl = "http://localhost:8080/university/departments";

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the text: ");
        String userInput = scanner.nextLine();

        if (userInput.startsWith("Global search by")) {
            apiUrl = "http://localhost:8080/university/employees";
        }

        Request request = new Request.Builder()
                .url(apiUrl)
                .post(RequestBody.create(userInput,
                        MediaType.parse("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                if (body != null) {
                    System.out.println(body.string());
                }
            } else {
                System.out.println("Error: " + response.code());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}