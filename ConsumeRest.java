import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class ConsumeRest {
    private static String header_name = "Authorization";
    private static String api_key = "";

    public static void main(String[] args) {
        //post_request();
        get_request();
    }

    public static void post_request() {
        // build a client to handle the request/response
        HttpClient httpClient = HttpClient.newHttpClient();

        Transcript transcript = new Transcript();
        transcript.setAudio_url("https://github.com/johnmarty3/JavaAPITutorial/blob/main/Thirsty.mp4?raw=true");

        //use GSON to translate the above object into JSON
        // it does this automatically from a standardised java class
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(transcript);

        // build our request
        try {
            HttpRequest postRequest = HttpRequest.newBuilder() // uses the builder pattern
                    .uri(new URI("https://api.assemblyai.com/v2/transcript"))
                    .header(header_name, api_key)
                    .POST(BodyPublishers.ofString(jsonRequest))
                    .build();

            // retrieve the response in String format
            HttpResponse<String> postResponse = httpClient.send(postRequest, BodyHandlers.ofString());

            // now convert the response into a java object
            // we can do this automatically with gson because the data we send and receive always corresponds to the class
            // structure
            transcript = gson.fromJson(postResponse.body(), Transcript.class);
        System.out.println(transcript.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void get_request() {
        try {
            // build an HTTP request to send
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(new URI("https://www.google.com"))
                    .header(header_name, api_key)
                    .GET()
                    .build();

            // we need an HTTP Client to handle the response
            HttpClient client = HttpClient.newHttpClient();

            // send the request, fetch the response
            HttpResponse<String> res = client.send(req, BodyHandlers.ofString());   // with our client, send our built request and ask for a string back
            System.out.println(res.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
