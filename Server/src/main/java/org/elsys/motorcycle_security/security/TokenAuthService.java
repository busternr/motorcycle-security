package org.elsys.motorcycle_security.security;

import static java.util.Collections.emptyList;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthService {
  static final long VALIDITY = 31456952000l; //1 y
  static final String KEY = "Motorcycle Security";
  static final String TOKEN_PREFIX = "User";
  static final String HEADER_STRING = "Authorization";
  static void addAuthentication(HttpServletResponse res, String username) throws UnsupportedEncodingException {
    String JWT = Jwts.builder().setSubject(username).
            setExpiration(new Date(System.currentTimeMillis() + VALIDITY))
            .signWith(SignatureAlgorithm.HS512, KEY.getBytes("UTF-8")).compact();
    res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
}

  static Authentication getAuthenticationFromReq(HttpServletRequest request) throws UnsupportedEncodingException {
    String token = request.getHeader(HEADER_STRING);
    if (token != null) {
      try {
        String user = Jwts.parser().setSigningKey(KEY.getBytes("UTF-8")).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody().getSubject();
        return user != null ? new UsernamePasswordAuthenticationToken(user, null, emptyList()) : null;
      }
      catch (io.jsonwebtoken.MalformedJwtException ex) {}
    }
    return null;
  }
}
