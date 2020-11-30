package com.github.saleco.functions;

import com.github.saleco.dao.UrlDAO;
import com.github.saleco.exceptions.InvalidUrlFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Function;

@Component("generateShortcode")
public class GenerateShortcodeFunction implements Function<String, String> {

  @Autowired
  private UrlDAO urlDAO;

  @Override
  public String apply(String url) {
    try {
      new URL(url);
    } catch (MalformedURLException e) {
      throw new InvalidUrlFormatException();
    }

    String shortCode = urlDAO.generateShortCode();
    urlDAO.storeUrl(shortCode, url);

    return shortCode;

  }
}
