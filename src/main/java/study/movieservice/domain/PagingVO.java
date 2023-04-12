package study.movieservice.domain;

import lombok.Builder;
import lombok.Getter;

/**
 * 리뷰 목록을 페이징하기 위해 만든 VO
 */
@Getter
@Builder
public class PagingVO {
    private Integer total;
    private Integer totalPageNum;
    private Integer currentPage;
    private Object data;
}
