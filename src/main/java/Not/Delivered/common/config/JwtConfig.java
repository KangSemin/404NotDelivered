package Not.Delivered.common.config;

import Not.Delivered.user.domain.UserStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {


	private final Long expiration;
	private final SecretKey key;

	public JwtConfig(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}")Long expiration) {
		this.expiration = expiration;
		this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(Long userId, UserStatus userStatus) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + expiration) ;

		Claims claims = Jwts.claims().setSubject(userId.toString());
		claims.put("userStatus",userStatus);

		return Jwts.builder()
				.setClaims(claims)
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

	public UserStatus getUserStatusFromToken(String token) {

		Claims claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();

		String userStatus = claims.get("userStatus", String.class);
		return UserStatus.valueOf(userStatus);
	}

	public long getExpiration(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getExpiration().getTime() - new Date().getTime();
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
