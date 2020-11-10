package hieu;

import java.io.Serializable;
import java.util.List;

public class PageResult<T> implements Serializable {

    public static final int DEFAULT_FIRST_RESULT = 0;
    public static final int DEFAULT_PAGE_SIZE = 100;

    private int pageNumber;
    private int pageSize;
    private int totalElements;
    private int totalPages;

    private List<T> elements;

    public PageResult(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean hasMore() {
        return totalElements > calculateOffset() + pageSize;
    }

    public boolean hasPrevious() {
        return pageNumber > 1 && totalElements > 0;
    }

    public int getLastPageNumber(Long count) {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        if (pageSize > count) {
            pageSize = Math.toIntExact(count);
        }
        return (int) (Math.ceil((double) count / pageSize));
    }

    public int calculateOffset() {
        if (pageNumber < 1 || pageSize < 1) {
            pageNumber = 1;
            pageSize = DEFAULT_PAGE_SIZE;
            return DEFAULT_FIRST_RESULT;
        }
        return (pageNumber - 1) * pageSize;
    }

}
