package com.github.saleco.dao;


public interface UrlDAO {
  String generateShortCode();
  void storeUrl(String shortcode, String url);

  String getUrl(String shortcode);
}
