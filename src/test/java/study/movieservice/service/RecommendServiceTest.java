package study.movieservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import study.movieservice.domain.movie.Recommend;
import study.movieservice.domain.movie.Review;
import study.movieservice.repository.RecommendMapper;
import study.movieservice.repository.ReviewMapper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RecommendServiceTest {

    @Autowired
    private ReviewMapper reviewMapper;



    @Test
    void testA() throws InterruptedException {
        //given
        /*
        Review review = Review.builder()
                .movieId(1L)
                .memberId(1L)
                .content("재")
                .rating(1.5).build();
        reviewMapper.save(review);
        */

        //when
        ExecutorService executorService= Executors.newFixedThreadPool(100);
        CountDownLatch countDownLatch=new CountDownLatch(100);

        for(int i=1;i<=100;i++){
            executorService.submit(()->{
                try{/*
                    Recommend recommend = Recommend.builder()
                            .memberId(1L)
                            .reviewId(1L)
                            .recommendState(true)
                            .build();
                    recommendService.recommendSave(recommend);
                    */

                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        //recommendService에서 세션의 memberId로 들어가는거라 null나옴 바로 mapper적용
        //아니면 service에서 memberId받아서 하는걸로 교체
        countDownLatch.await();
        //then

    }


}