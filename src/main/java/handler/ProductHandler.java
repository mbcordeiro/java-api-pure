package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import domain.Product;
import repository.ProductRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ProductHandler implements HttpHandler {
    private final ProductRepository repository = new ProductRepository();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        switch (method) {
            case "GET":
                handleGet(exchange);
                break;
            case "POST":
                handlePost(exchange);
                break;
            default:
                exchange.sendResponseHeaders(405, -1);
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        try {
            List<Product> products = repository.findAll();

            StringBuilder response = new StringBuilder("[");
            for (Product p : products) {
                response.append("{")
                        .append("\"id\":").append(p.getId()).append(",")
                        .append("\"name\":\"").append(p.getName()).append("\",")
                        .append("\"value\":").append(p.getValue()).append(",")
                        .append("\"cod\":\"").append(p.getCod()).append("\",")
                        .append("\"sku\":\"").append(p.getSku()).append("\"")
                        .append("},");
            }
            if (response.charAt(response.length() - 1) == ',') {
                response.setLength(response.length() - 1);
            }
            response.append("]");

            byte[] bytes = response.toString().getBytes();
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
            String error = "{\"error\":\"Internal Server Error\"}";
            exchange.sendResponseHeaders(500, error.length());
            OutputStream os = exchange.getResponseBody();
            os.write(error.getBytes());
            os.close();
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(isr);
        StringBuilder body = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            body.append(line);
        }

        String json = body.toString();

        try {
            String name = extractJsonValue(json, "name");
            BigDecimal value = new BigDecimal(extractJsonValue(json, "value"));
            String cod = extractJsonValue(json, "cod");
            String sku = extractJsonValue(json, "sku");

            Product product = new Product(name, value, cod, sku);
            repository.save(product);

            String response = "{\"message\":\"Product insert with success\"}";
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(201, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
            String error = "{\"error\":\"Error processing data\"}";
            exchange.sendResponseHeaders(400, error.length());
            OutputStream os = exchange.getResponseBody();
            os.write(error.getBytes());
            os.close();
        }
    }

    private String extractJsonValue(String json, String key) {
        String pattern = "\"" + key + "\"\\s*:\\s*\"?([^\"]+?)\"?(,|})";
        java.util.regex.Pattern regex = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher matcher = regex.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new IllegalArgumentException("Field '" + key + "' Not found");
    }

}
