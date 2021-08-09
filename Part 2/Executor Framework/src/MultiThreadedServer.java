import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MultiThreadedServer {
    private static final String DOCUMENT = "" +
            "<html>" +
            "   <head>" +
            "       <title>Single Threaded Server</title>" +
            "   </head>" +
            "   <body>" +
            "       <p>It works!</p>" +
            "   </body>" +
            "</html>";

    private static final String HEADER =
            "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/html; charset = UTF-8\r\n" +
                    "Content-Length: " + DOCUMENT.length() +
                    "\r\n\r\n";

    public static void main(String[] args) throws IOException{
        ServerSocket socket = new ServerSocket(8080);
        Executor executor = Executors.newFixedThreadPool(10);

        while (true) {
            Socket connection = socket.accept();

            executor.execute(() -> serveRequest(connection));
        }
    }

    private static void serveRequest(Socket connection) {
        System.out.println("New connection request from: " + connection.toString());

        try (OutputStream outputStream = connection.getOutputStream();
             PrintWriter writer = new PrintWriter(outputStream)){
            writer.write(HEADER);
            writer.write(DOCUMENT);
        } catch (IOException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError(e);
        }
    }
}
