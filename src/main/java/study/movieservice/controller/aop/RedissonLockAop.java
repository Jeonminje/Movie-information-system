package study.movieservice.controller.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import study.movieservice.controller.aop.annotation.RedissonLock;

import java.lang.reflect.Method;

import static study.movieservice.domain.ExceptionMessageConst.FAILED_GET_LOCK;

/**
 * RedissonLock 적용을 위한 어노테이션
 * 분산락을 담당
 */
@Component
@Aspect
@RequiredArgsConstructor
public class RedissonLockAop {

    private final RedissonClient redissonClient;

    @Around("@annotation(study.movieservice.controller.aop.annotation.RedissonLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        RedissonLock redissonLock = method.getAnnotation(RedissonLock.class);

        String key = this.createKey(signature.getParameterNames(), joinPoint.getArgs(), redissonLock.key());

        RLock rLock = redissonClient.getLock(key);

        try {
            boolean isPossible = rLock.tryLock(redissonLock.waitTime(), redissonLock.leaseTime(), redissonLock.timeUnit());

            if (!isPossible) {
                throw new IllegalArgumentException(FAILED_GET_LOCK.getMessage());
            }

            return joinPoint.proceed();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            rLock.unlock();
        }

    }

    /**
     * redisson 클라이언트에 저장할 key값을 생성하는 메소드
     *
     * @param parameterNames 호출되는 메소드의 파라미터 이름목록
     * @param args joinPoint가 호출되는 객체의 파라미터 목록
     * @param key 메소드별로 지정해놓은 default key값
     * @return redisson 클라이언트에 저장할 key값
     */
    private String createKey(String[] parameterNames, Object[] args, String key) {
        String resultKey = key;

        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals(key)) {
                resultKey += args[i];
                break;
            }
        }

        return resultKey;
    }

}
