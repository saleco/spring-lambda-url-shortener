package com.github.saleco.functions;

import com.github.saleco.dao.UrlDAO;
import com.github.saleco.exceptions.InvalidUrlFormatException;
import com.github.saleco.exceptions.RedirectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Function;

@Component("getURL")
public class GetURLFunction implements Function<String, String> {

  @Autowired
  private UrlDAO urlDAO;

  @Override
  public String apply(String shortcode) {
    String url = urlDAO.getUrl(shortcode);

    throw new RedirectException(url);
  }
}
