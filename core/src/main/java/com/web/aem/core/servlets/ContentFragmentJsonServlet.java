package com.web.aem.core.servlets;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.ContentElement;
import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component(service = {Servlet.class})
@SlingServletResourceTypes(
        resourceTypes = "aem-web/components/page",
        selectors = "model",
        extensions = "json"
)
public class ContentFragmentJsonServlet extends SlingAllMethodsServlet {

                
 

   JsonObject jsonPath= new JsonObject();

    private List<String> fragmentPaths ;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        ResourceResolver resolver = request.getResourceResolver();

                JsonObject jsonResponse = new JsonObject();
                JsonObject items = new JsonObject();

                fragmentPaths = new ArrayList<String>();

                Resource pageResource = request.getResource();                
                // Collect fragment paths
                collectFragmentPaths(pageResource);
                    // String[] fragmentPath = {"/content/dam/aem-web/contentmodel/jyoti","/content/dam/aem-web/contentmodel/munu"};

                    for (String child : fragmentPaths) {
                        
                            if (child != null) {
                                Resource fragmentResource = resolver.getResource(child);
                                if (fragmentResource != null) {
                                    ContentFragment fragment = fragmentResource.adaptTo(ContentFragment.class);
                                    if (fragment != null) {
                                        JsonObject fragmentJson = new JsonObject();
                                        fragmentJson.addProperty("title", fragment.getTitle());
                                        JsonObject elements = new JsonObject();

                                        Iterator<ContentElement> elementIterator = fragment.getElements();
                                        while (elementIterator.hasNext()) {
                                        ContentElement element = elementIterator.next();
                                            elements.addProperty(element.getName(), element.getContent()); // Use getContent() for value
                                        }
                                        elements.addProperty("fragmentPath", fragmentResource.getPath());
                                        
                                        fragmentJson.add("elements", elements);
                                        items.add(fragment.getName(), fragmentJson);
                                    }
                                }
                            }
                        
                    }
                

                jsonResponse.add(":items", items);
                jsonResponse.add(":path", jsonPath);
                response.setContentType("application/json");
                response.getWriter().write(jsonResponse.toString());
    }

    private void collectFragmentPaths(Resource resource) {

        if (resource.getValueMap().containsKey("fragmentPath")) {
            jsonPath.addProperty(resource.getName(), resource.getPath());
            fragmentPaths.add(resource.getValueMap().get("fragmentPath", String.class));
        }
        for (Resource child : resource.getChildren()) {
            collectFragmentPaths(child);  // Recursive call for all nested children
        }
    }



   





}
