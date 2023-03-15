package study.movieservice.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.movieservice.domain.SessionConst;
import study.movieservice.domain.movie.Recommend;
import study.movieservice.domain.movie.Review;
import study.movieservice.repository.RecommendMapper;


import javax.servlet.http.HttpSession;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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


    static Long a=0L;
    String aa="1";


    private Recommend createRecommend(){
        return Recommend.builder()
                .reviewId(1L)
                .memberId(1L)
                .recommendState(true)
                .build();
    }
    private Review createReview() {
        return Review.builder()
                .movieId(1L)
                .content("d")
                .likeCount(0L)
                .rating(1.0).build();
    }
    private Review increaseReview(Review review){
        return Review.builder()
                .movieId(1L)
                .memberId(1L)
                .content("d")
                .likeCount(review.getLikeCount()+1L)
                .rating(1.0).build();
    }
    @Test
    void test_concurrency() throws InterruptedException {
        //given
        Recommend recommend=createRecommend();
        Review review=createReview();
        httpSession.setAttribute(SessionConst.MEMBER_ID, 1L);

        //when
        ExecutorService executorService= Executors.newFixedThreadPool(120);
        CountDownLatch countDownLatch=new CountDownLatch(120);

        for(int i=1;i<=120;i++){
            executorService.submit(()->{
                try{
                    a++;
                    aa=aa+"1";
                    reviewService.decreaseDislikeCount(1L);
                    //recommendService.recommendSave(recommend);

                }catch(Exception e){
                    System.out.println("e = " + e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();

        //then
        //verify(reviewService,times(100)).increaseLikeCount(1L);
        //verify(recommendMapper,times(100)).recommendSave(any(Recommend.class));
        //assertThat(reviewService.getLikeCount(1L)).isEqualTo(201L);
        assertThat(a).isEqualTo(120L);
    }
}