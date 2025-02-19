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
                selectors = {"model"},

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

    fragmentPaths = new ArrayList<>();

    Resource pageResource = request.getResource();
    collectFragmentPaths(pageResource);

    for (String child : fragmentPaths) {
        if (child != null) {
            Resource fragmentResource = resolver.getResource(child);
            if (fragmentResource != null) {
                ContentFragment fragment = fragmentResource.adaptTo(ContentFragment.class);
                if (fragment != null) {
                    JsonObject fragmentJson = processFragment(fragment, resolver);
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
private JsonObject processFragment(ContentFragment fragment, ResourceResolver resolver) {
    JsonObject fragmentJson = new JsonObject();
    fragmentJson.addProperty("title", fragment.getTitle());
    JsonObject elements = new JsonObject();

    Iterator<ContentElement> elementIterator = fragment.getElements();
    while (elementIterator.hasNext()) {
        ContentElement element = elementIterator.next();
        String elementValue = element.getContent();

        if (elementValue != null && !elementValue.trim().isEmpty()) {
            // If cfField contains a nested content fragment, process it recursively
            if (element.getName().equals("cfField") && elementValue.startsWith("/content/dam/")) {
                Resource nestedFragmentResource = resolver.getResource(elementValue);
                if (nestedFragmentResource != null) {
                    ContentFragment nestedFragment = nestedFragmentResource.adaptTo(ContentFragment.class);
                    if (nestedFragment != null) {
                        JsonObject nestedJson = processFragment(nestedFragment, resolver);
                        if (nestedJson.size() > 0) { // Only add if it contains valid data
                            elements.add("Field", nestedJson);
                        }
                    }
                }
            } else {
                // Only add the key if it has a non-empty value
                elements.addProperty(element.getName(), elementValue);
            }
        }
    }

    // Only add elements if there are valid fields inside
    if (elements.size() > 0) {
        elements.addProperty("fragmentPath", fragment.adaptTo(Resource.class).getPath());
        fragmentJson.add("elements", elements);
    }

    return fragmentJson;
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
