package webserver.servlet;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ServletContext {
    private final Map<String, Servlet> servlets;

    public ServletContext() {
        this.servlets = new HashMap<>();
    }

    public void addServletIfNotPresent(String servletName, Servlet servlet) {
        if (!servlets.containsKey(servletName)) {
            servlets.put(servletName, servlet);
            servlet.init();
        }
    }

    public void destroy() {
        servlets.forEach((key, value) -> value.destroy());
        servlets.clear();
    }

    public Servlet getServlet(String servletName) {
        return servlets.get(servletName);
    }
}
