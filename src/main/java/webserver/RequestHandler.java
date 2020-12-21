package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exception.NotFoundController;
import http.request.HttpRequest;
import http.request.Request;
import http.response.HttpResponse;
import http.response.Response;
import webserver.servlet.DispatcherServlet;
import webserver.servlet.ResourceHttpHandler;
import webserver.servlet.Servlet;
import webserver.servlet.ServletContext;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final ServletContext servletContext;

    public RequestHandler(Socket connectionSocket, ServletContext servletContext) {
        this.connection = connectionSocket;
        this.servletContext = servletContext;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            DataOutputStream dos = new DataOutputStream(out);

            Request request = HttpRequest.from(bufferedReader);
            Response response = new HttpResponse();
            servletContext.addServletIfNotPresent("dispatcherServlet", new DispatcherServlet());

            handleRequest(request, response);
            response.send(dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void handleRequest(Request request, Response response) {
        final Servlet dispatcherServlet = servletContext.getServlet("dispatcherServlet");

        try {
            dispatcherServlet.service(request, response);
        } catch (NotFoundController e) {
            ResourceHttpHandler.handleResource(request, response);
        }
    }
}
