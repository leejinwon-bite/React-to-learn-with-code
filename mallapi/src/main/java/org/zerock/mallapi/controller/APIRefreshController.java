package org.zerock.mallapi.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.mallapi.util.CustomJWTException;
import org.zerock.mallapi.util.JWTUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
public class APIRefreshController {
  @RequestMapping("/api/member/refresh")
  public Map<String, Object> refresh(@RequestHeader("Authorization") String
  authHeader, String refreshToken){
    if(refreshToken == null){
      throw new CustomJWTException("NULL_REFRESH");
    }
    if(authHeader == null || authHeader.length() <= 7){
      throw new CustomJWTException("INVALID_STRING");
    }
    String accessToken = authHeader.substring(7);
    if(checkExpiredToken(accessToken) == false){
      return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
    }
    Map<String, Object> claims = JWTUtil.validateToken(refreshToken);
    log.info("refresh ... claims: " + claims);
    String newAccessToken = JWTUtil.generateToken(claims, 10);
    String newRefreshToken = checkTime((Long)claims.get("exp")) ?
      JWTUtil.generateToken(claims, 60*24) : refreshToken;
    return Map.of("accessToken", newAccessToken, "refreshToken",
      newRefreshToken);
  }
  private boolean checkExpiredToken(String token){
    try{
      JWTUtil.validateToken(token);
    }catch(CustomJWTException e){
      if(e.getMessage().equals("Expired")){
        return true;
      }
    }
    return false;
  }
  private boolean checkTime(Long exp){
    java.util.Date expDate = new java.util.Date(exp * 1000);
    long gap = expDate.getTime() - System.currentTimeMillis();
    long leftMin = gap/(1000 * 60);
    return leftMin < 60;
  }
}
