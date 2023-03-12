package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import study.movieservice.domain.movie.Recommend;
import java.util.concurrent.TimeUnit;

import static study.movieservice.domain.ExceptionMessageConst.FAILED_GET_LOCK;


@Service
@RequiredArgsConstructor
public class RecommendLock {

    private final RecommendService recommendService;
    private final RedissonClient redissonClient;

    public void recommendSave(Recommend inputRecommend) {

        RLock lock = redissonClient.getLock("saveRecommend");

        try {
            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);
            if (!available)
                throw new IllegalArgumentException(FAILED_GET_LOCK.getMessage());
            recommendService.recommendSave(inputRecommend);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void recommendUpdate(Recommend inputRecommend) {

        RLock lock = redissonClient.getLock("updateRecommend");

        try {
            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);
            if (!available)
                throw new IllegalArgumentException(FAILED_GET_LOCK.getMessage());
            recommendService.recommendUpdate(inputRecommend);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void recommendDelete(Recommend inputRecommend) {

        RLock lock = redissonClient.getLock("deleteRecommend");

        try {
            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);
            if (!available)
                throw new IllegalArgumentException(FAILED_GET_LOCK.getMessage());
            recommendService.recommendDelete(inputRecommend);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
