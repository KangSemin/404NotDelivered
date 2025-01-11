package Not.Delivered.auth.service;

import Not.Delivered.common.config.JwtConfig;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {

  private final StringRedisTemplate redisTemplate;
  private final JwtConfig jwtConfig;

  public void addToBlacklist(String token) {
    long expiration = jwtConfig.getExpiration(token);
    redisTemplate.opsForValue().set(token, "logout", expiration, TimeUnit.MILLISECONDS);
  }

  public boolean isBlacklisted(String token) {
    return false;
//   return Boolean.TRUE.equals(redisTemplate.hasKey(token));
  }
}
