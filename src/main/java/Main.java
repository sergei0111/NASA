import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
       // String url = "https://api.nasa.gov/planetary/apod?api_key=gpoxySiPqVz12UxbaZbN1rI05JMEdNZHSX7g1MOx";
        String url = "https://api.nasa.gov/planetary/apod?"+
                "api_key=gpoxySiPqVz12UxbaZbN1rI05JMEdNZHSX7g1MOx"+
                "&date=2024-01-23";

        CloseableHttpClient client = HttpClients.createDefault();

        CloseableHttpResponse response = client.execute(new HttpGet(url));

        //Scanner scanner = new Scanner(response.getEntity().getContent());
        //System.out.println(scanner.nextLine());

        ObjectMapper mapper = new ObjectMapper();
        NasaAnswer answer = mapper.readValue(response.getEntity().getContent(),NasaAnswer.class);

        String[] separatedAnswer = answer.url.split("/");
        String fileName = separatedAnswer[separatedAnswer.length - 1];
        CloseableHttpResponse image = client.execute(new HttpGet(answer.url));
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        image.getEntity().writeTo(fileOutputStream);

    }
}
