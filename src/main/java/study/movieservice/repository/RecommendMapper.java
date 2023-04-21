package study.movieservice.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import study.movieservice.domain.movie.Recommend;

import java.util.Optional;

/**
 * DB와 Recommend 관련 객체들을 연결해주는 인터페이스
 */
@Mapper
public interface RecommendMapper {
    /**
     * recommend 객체를 DB에 저장하기 위한 메소드
     *
     * @param recommend 입력받은 recommend 객체
     */
    void recommendSave(Recommend recommend);

    /**
     * reviewId와 memberId를 입력받아 recommend 객체의 존재여부를 리턴하는 메소드
     *
     * @param reviewId recommend가 어떤 리뷰에 있는지 알려주는 reviewId
     * @param memberId 현재 로그인 중인 id
     * @return 해당 review에 이미 작성된 recommend 객체가 있다면 1 없다면 0
     */
    boolean findByReviewIdAndMemberId(Long reviewId, Long memberId);

    /**
     * recommend와 memberId 를 입력받아 해당 recommend의 상태를 DB에 업데이트하는 메소드
     *
     * @param recommend 바꿀상태를 가지고있는 recommend 객체
     * @param memberId 현재 로그인 중인 id
     */
    void recommendUpdate(@Param("recommend") Recommend recommend,@Param("memberId") Long memberId);

    /**
     * memberId와 reviewId를 입력받아 DB에서 삭제하는 메소드
     *
     * @param memberId 현재 로그인 중인 id
     * @param reviewId 삭제하고자 하는 recommend가 있는 review의 id
     */
    void recommendDelete(Long memberId, Long reviewId);
}
