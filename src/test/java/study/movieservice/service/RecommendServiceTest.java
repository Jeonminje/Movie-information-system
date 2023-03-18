package study.movieservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.movieservice.domain.SessionConst;
import study.movieservice.domain.movie.Recommend;
import study.movieservice.repository.RecommendMapper;

import javax.servlet.http.HttpSession;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RecommendServiceTest {

    @Autowired
    RecommendService recommendService;
    @Autowired
    RecommendMapper recommendMapper;
    @Autowired
    ReviewService reviewService;
    @Autowired
    HttpSession httpSession;

    private Recommend createRecommend(){
        return Recommend.builder()
                .reviewId(1L)
                .memberId(1L)
                .recommendState(true)
                .build();
    }

    @Test
    void test_concurrency() throws InterruptedException {
        //given
        Recommend recommend=createRecommend();
        httpSession.setAttribute(SessionConst.MEMBER_ID, 1L);

        //when
        ExecutorService executorService= Executors.newFixedThreadPool(100);
        CountDownLatch countDownLatch=new CountDownLatch(100);

        for(int i=1;i<=100;i++){
            executorService.submit(()->{
                try{
                    recommendService.recommendSave(recommend);

                }catch(Exception e){
                    System.out.println("e = " + e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();

        assertThat(reviewService.getLikeCount(1L)).isEqualTo(100);
    }
}