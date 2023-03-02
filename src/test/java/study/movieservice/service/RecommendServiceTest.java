package study.movieservice.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import org.mockito.junit.jupiter.MockitoExtension;

import study.movieservice.domain.movie.Recommend;
import study.movieservice.repository.RecommendMapper;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecommendServiceTest {

    @InjectMocks
    RecommendService recommendService;
    @Mock
    RecommendMapper recommendMapper;
    @Mock
    ReviewService reviewService;
    @Mock
    SessionManager sessionManager;

    private Recommend createRecommend(){
        return Recommend.builder()
                .reviewId(1L)
                .recommendState(true)
                .build();
    }
    @Test
    void test_concurrency() throws InterruptedException {
        //given
        doReturn(1L).when(sessionManager).getMemberId();
        doReturn(false).when(recommendMapper).findByReviewIdAndMemberId(1L,1L);
        doNothing().when(reviewService).increaseLikeCount(1L);
        doNothing().when(recommendMapper).recommendSave(any(Recommend.class));

        Recommend recommend=createRecommend();

        //when
        ExecutorService executorService= Executors.newFixedThreadPool(150);
        CountDownLatch countDownLatch=new CountDownLatch(150);

        for(int i=1;i<=150;i++){
            executorService.submit(()->{
                try{
                    recommendService.recommendSave(recommend);
                }finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        //then
        verify(reviewService,times(150)).increaseLikeCount(1L);
        verify(recommendMapper,times(150)).recommendSave(any(Recommend.class));
    }


}