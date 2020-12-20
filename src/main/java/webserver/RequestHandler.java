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
import http.response.HttpResponse;
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

            HttpRequest httpRequest = HttpRequest.from(bufferedReader);
            HttpResponse httpResponse = new HttpResponse();
            servletContext.addServletIfNotPresent("dispatcherServlet", new DispatcherServlet());

            handleRequest(httpRequest, httpResponse);
            httpResponse.send(dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
        final Servlet dispatcherServlet = servletContext.getServlet("dispatcherServlet");

        try {
            dispatcherServlet.service(httpRequest, httpResponse);
        } catch (NotFoundController e) {
            ResourceHttpHandler.handleResource(httpRequest, httpResponse);
        }
    }
}
