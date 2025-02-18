package org.zerock.mallapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zerock.mallapi.controller.formatter.LocalDateFormatter;

@Configuration
public class CustomServletConfig implements WebMvcConfigurer {

  //시간을 넘겨 받을때 포멧 설정.
  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addFormatter(new LocalDateFormatter());
  }
  // @Override
  // public void addCorsMappings(CorsRegistry registry) {
  //   registry.addMapping("/**")
  //     .allowedOrigins("*")
  //     .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS")
  //     .maxAge(300)
  //     .allowedHeaders("Authorization", "Cache-Control", "Content-Type");
  // }
}
