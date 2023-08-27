package org.bawker.dev;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Scanner;

public class App {

    public static final Logger logger = Logger.getInstance();
    public static final Voting voting = Voting.getInstance();


    public static void main(String[] args) throws IOException {
        final int port = 8000;
        String message;

        message = "Initializing all interfaces";
        logger.logInfo(message);
        System.out.println("[*] : ");
        final HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new RootHandler());
        server.createContext("/vote", new SubmitHandler());
        server.createContext("/candidate", new CandidateHandler());
        server.createContext("/delete", new DeleteCandidateHandler());
        server.setExecutor(null); // To use the default
        server.start();

        message = "Starting server";
        logger.logInfo(message);
        System.out.println("[*] " + message + "...");
        logger.logInfo("Started on: ");
        logger.logInfo("[*] " + InetAddress.getByName("localhost") + ":" + port);
        logger.logInfo(printAddresses(port));
        System.out.println("[*] Started on:");
        System.out.println("[*] " + InetAddress.getByName("localhost") + ":" + port);
        System.out.println(printAddresses(port));

        logger.logWarning("Multiple Interfaces detected");

    }

    //rewrite and optimize the function bellow
    public static String printAddresses(int port) {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (!address.isLinkLocalAddress() && !ni.getName().startsWith("lo")) {
                        return "[*] " + address.getHostAddress() + ":" + port;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }


    static class RootHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            File file = new File("index.html");
            Scanner reader = new Scanner(file);
            reader.useDelimiter("\\Z");

            String response = reader.next();
            response = response.replaceAll("NAME_TOPVOTED", voting.getTopCandidate().getName());
            response = response.replaceAll("VOTES_TOPVOTED", voting.getTopCandidate().getVotes() + "");

            StringBuilder rowComplete = new StringBuilder();
            String rowTemplate =
                    """
                            <tr>
                                <td>01</td>
                                <td>02</td>
                                <td>03</td>
                                <td>04</td>
                            </tr>""";

            for (Candidate c : voting.getCandidates()) {
                rowComplete.append(rowTemplate.replaceAll("01", c.getName())
                        .replaceAll("02", c.getVotes() + "")
                        .replaceAll("03", c.getAffiliation())
                        .replaceAll("04", c.getId() + ""));
            }
            response = response.replaceAll("TROWS_VOTED", rowComplete.toString());

            httpExchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        }
    }

    static class CandidateHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            if (httpExchange.getRequestMethod().equalsIgnoreCase("POST")) {
                InputStream inputStream = httpExchange.getRequestBody();
                byte[] data = inputStream.readAllBytes();

                String formData = new String(data, StandardCharsets.UTF_8);

                String[] data1 = formData.split("&");
                data1[0] = data1[0].replaceAll("name=", "");
                data1[1] = data1[1].replaceAll("age=", "");
                data1[2] = data1[2].replaceAll("affiliation=", "");
                data1[0] = data1[0].replace((char) 43, ' ');

                voting.createCandidate(new Candidate(data1[0], Integer.parseInt(data1[1]), data1[2]));


            }

            File file = new File("candidate.html");
            Scanner reader = new Scanner(file);
            reader.useDelimiter("\\Z");

            String response = reader.next();
            response = response.replaceAll("NAME_TOPVOTED", voting.getTopCandidate().getName());
            response = response.replaceAll("VOTES_TOPVOTED", voting.getTopCandidate().getVotes() + "");


            httpExchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        }
    }

    static class SubmitHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

            if (httpExchange.getRequestMethod().equalsIgnoreCase("POST")) {
                InputStream inputStream = httpExchange.getRequestBody();
                byte[] data = inputStream.readAllBytes();

                String formData = new String(data, StandardCharsets.UTF_8);

                formData = formData.replaceAll("id=", "");
                System.out.println(formData);
                int id = Integer.parseInt(formData);
                voting.addVote(id);
            }


            File file = new File("vote.html");
            Scanner reader = new Scanner(file);
            reader.useDelimiter("\\Z");

            String response = reader.next();

            httpExchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        }
    }

    static class DeleteCandidateHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

            if (httpExchange.getRequestMethod().equalsIgnoreCase("POST")) {
                InputStream inputStream = httpExchange.getRequestBody();
                byte[] data = inputStream.readAllBytes();

                String formData = new String(data, StandardCharsets.UTF_8);

                formData = formData.replaceAll("id=", "");
                System.out.println(formData);
                int id = Integer.parseInt(formData);
                voting.deleteCandidate(id);
            }


            File file = new File("delete.html");
            Scanner reader = new Scanner(file);
            reader.useDelimiter("\\Z");

            String response = reader.next();
            response = response.replaceAll("NAME_TOPVOTED", voting.getTopCandidate().getName());
            response = response.replaceAll("VOTES_TOPVOTED", voting.getTopCandidate().getVotes() + "");

            httpExchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        }
    }
}