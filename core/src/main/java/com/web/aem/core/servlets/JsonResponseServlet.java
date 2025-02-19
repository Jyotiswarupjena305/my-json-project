package com.web.aem.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class)
@SlingServletResourceTypes(
        resourceTypes = "aem-web/components/page",
        methods = "GET",
        selectors = "jsonresponse",
        extensions = "json"
)
public class JsonResponseServlet extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        JSONObject jsonResponse = new JSONObject();
        try {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        jsonResponse.put("message", "This is a JSON response from Sling Servlet");
        jsonResponse.put("status", "success");
        jsonResponse.put("name","Jyotiswarup");
        jsonResponse.put("tus", "success");
        jsonResponse.put("title","Jyotiswarup");
        } catch (org.json.JSONException e) {
            response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"JSON processing error\"}");
        }
        response.getWriter().write(jsonResponse.toString());
    }
}
