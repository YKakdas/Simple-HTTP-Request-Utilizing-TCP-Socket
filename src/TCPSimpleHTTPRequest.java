// Yasar Kakdas

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPSimpleHTTPRequest {

    public static void main(String[] args) throws IOException {
        new TCPSimpleHTTPRequest();
    }

    public TCPSimpleHTTPRequest() throws IOException {
        Socket socket = new Socket("example.com", 80); // server is example.com with default http port number 80

        /*
        Request: GET /
        HTTP Version: HTTP/1.1
        It is mandatory to add Host header in HTTP version 1.1: Host: example.com
        Each line ends with \r\n
         */
        String request = """
                GET / HTTP/1.1\r
                Host: example.com\r
                \r
                """;
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        output.write(request.getBytes());
        output.flush();

        byte[] receiveBuffer = new byte[1024];
        int numberOfReadChars;
        while ((numberOfReadChars = input.read(receiveBuffer)) != 0) {
            if (numberOfReadChars == -1) break;
            String receivedMessage = new String(receiveBuffer, 0, numberOfReadChars);
            System.out.println(receivedMessage);
            if (input.available() == 0) break;
        }

        input.close();
        output.close();
        socket.close();
    }
}
