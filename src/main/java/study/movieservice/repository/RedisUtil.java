package study.movieservice.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RedisUtil {

    private final StringRedisTemplate redisTemplate;

    public Optional<String> getData(String email){
        ValueOperations<String,String > store = redisTemplate.opsForValue();
        return Optional.ofNullable(store.get(email));
    }

    public void setDataExpire(String email, String emailCode, long sec){
        ValueOperations<String, String> store = redisTemplate.opsForValue();
        Duration duration = Duration.ofSeconds(sec);
        store.set(email, emailCode, duration);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }
}
