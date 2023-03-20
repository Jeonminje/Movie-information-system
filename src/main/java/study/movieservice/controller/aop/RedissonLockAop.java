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
