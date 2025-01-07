package Not.Delivered.common.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtConfig {

	private final Long expiration;
	private final SecretKey key;

	public JwtConfig(@Value("%{jwt.secret}") String secret, @Value("%{jwt.expiration}")Long expiration) {
		this.expiration =expiration;
		this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(Long userId) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + expiration) ;

		return Jwts.builder()
				.setSubject(userId.toString())
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(key)
				.compact();
	}

	public Long getUserIdFromToken(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();

		return Long.parseLong(claims.getSubject());
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
