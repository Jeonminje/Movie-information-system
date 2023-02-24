package study.movieservice.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PagingVO {
    private Integer total;
    private Integer totalPageNum;
    private Integer currentPage;
    private Object data;
}
