package com.web.aem.core.servlets;

import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.osgi.service.component.annotations.Component;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class,
           property = {
               "sling.servlet.resourceTypes=aem-web/components/page",
               "sling.servlet.selectors=data",
               "sling.servlet.extensions=json",
               "sling.servlet.methods=GET"
           })
public class DataServlet extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.getWriter().write("{\"message\": \"This is data servlet\"}");
    }
}
