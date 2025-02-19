package com.web.aem.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.apache.sling.api.servlets.HttpConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Servlet that writes some sample content into the response. It is mounted for
 * all resources of a specific Sling resource type. 
 */
@Component(service = { Servlet.class })
@SlingServletResourceTypes(
        resourceTypes="aem-web/components/page",
        methods=HttpConstants.METHOD_GET,
        selectors="hello",
        extensions="html"
)
@ServiceDescription("Simple Demo Servlet")
public class SimpleServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(final SlingHttpServletRequest req,
            final SlingHttpServletResponse resp) throws ServletException, IOException {
        
        // Disable any decoration by setting response headers
        resp.setHeader("X-Content-Decoration", "none");
        
        // Disable cache and further response processing
        resp.setHeader("Cache-Control", "no-store");
        resp.setHeader("Pragma", "no-cache");
        
        // Avoid any default decoration of the response
        resp.setContentType("text/plain");
        resp.getWriter().write("Jyotiswarup Jena");
    }
}
