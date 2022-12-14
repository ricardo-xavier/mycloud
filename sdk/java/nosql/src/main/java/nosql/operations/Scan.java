package nosql.operations;

import com.fasterxml.jackson.databind.ObjectMapper;
import nosql.model.Filter;
import nosql.model.ScanRequest;
import nosql.model.ScanResponse;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Scan<T> {
    public PageIterable<T> scan(String name, TableSchema<T> schema, ScanEnhancedRequest request) throws Exception {
        ScanRequest scanRequest = new ScanRequest();
        scanRequest.setTable(name);

        if (request.getFilterExpression() != null) {
            List<Filter> filter = Filter.parse(request.getFilterExpression().getExpression(), request.getFilterExpression().getValues());
            scanRequest.setFilter(filter);
        }

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(scanRequest);

        String url = System.getProperty("URL_NOSQL");
        if (url == null) {
            throw new Exception("URL_NOSQL undefined");
        }

        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create(url + "scan"))
                .setHeader("Content-Type", "application/json")
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        ScanResponse response = mapper.readValue(httpResponse.body(), ScanResponse.class);
        return new PageIterable<>(schema, response.getFields(), response.getItems());
    }
}
