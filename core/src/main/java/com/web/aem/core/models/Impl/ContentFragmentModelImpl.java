package com.web.aem.core.models.Impl;
import com.web.aem.core.models.ContentFragmentModel;
import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.FragmentData;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Model(adaptables = Resource.class, adapters = ContentFragmentModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContentFragmentModelImpl implements ContentFragmentModel {

  @Self
  private Resource resource;
   
    @Override
    public Map<String, Object> getContentFragmentData() {
     
        //  Map<String, Object> dataMap = new HashMap<>();
        // ContentFragment contentFragment = resource.adaptTo(ContentFragment.class);
        // if (contentFragment != null) {
        //     for (String elementName : contentFragment.getElementNames()) {
        //         FragmentData data = contentFragment.getElement(elementName).getValue();
        //         dataMap.put(elementName, data.getValue());
        //     }
        // }
        // return dataMap;
        Map<String, Object> dataMap = new HashMap<>();
        ContentFragment contentFragment = resource.adaptTo(ContentFragment.class);
        if (contentFragment != null) {
            Iterator<ContentElement> elements = contentFragment.getElements();
            while (elements.hasNext()) {
                ContentElement element = elements.next();
                FragmentData data = element.getValue();
                dataMap.put(element.getName(), data.getValue());
            }
        }
        return dataMap;
    }
	
}
