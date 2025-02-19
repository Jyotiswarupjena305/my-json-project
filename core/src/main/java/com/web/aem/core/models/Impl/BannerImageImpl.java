package com.web.aem.core.models.Impl;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import com.web.aem.core.models.BannerImage;

@Model(adaptables = Resource.class, adapters = BannerImage.class)
public class BannerImageImpl implements BannerImage {

  @Inject
  String imagevalue;

  @Override
  public String getImage() {

    return imagevalue;
  }

}
