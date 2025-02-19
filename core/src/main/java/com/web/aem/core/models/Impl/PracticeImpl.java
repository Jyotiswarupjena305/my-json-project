package com.web.aem.core.models.Impl;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import com.web.aem.core.models.Practice;

@Model(adaptables = Resource.class, adapters = Practice.class)
public class PracticeImpl implements Practice {
    

    @Inject
    private String title;

    @Inject
    private String description;

    @Inject
    private String imagevalue;


    @Override
    public String getTitle() {
        // TODO Auto-generated method stub
        return title;
    }

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub

        return description;}

    @Override
    public String getImagevalue() {
        // TODO Auto-generated method stub
       return imagevalue;
    }


        
}
