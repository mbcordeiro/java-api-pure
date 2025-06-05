import com.sun.net.httpserver.HttpServer;
import handler.ProductHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        start();
    }

    static void start() throws IOException {
        final var server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/product", new ProductHandler());
        server.start();
        System.out.println("Start server");
    }
}
