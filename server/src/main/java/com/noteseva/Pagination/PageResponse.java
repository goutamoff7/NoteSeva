package com.noteseva.Pagination;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageResponse<T> {

    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean lastPage;

    // T - Notes, Organizer, PYQ
    // U - NotesDTO, OrganizerDTO, PYQDTO
    public static <T, U> PageResponse<U> getPageResponseDTO(Page<T> page, List<U> dtoList){
        PageResponse<U> pageResponse = new PageResponse<>();
        pageResponse.setContent(dtoList);
        pageResponse.setPageNumber(page.getNumber());
        pageResponse.setPageSize(page.getSize());
        pageResponse.setTotalElements(page.getTotalElements());
        pageResponse.setTotalPages(page.getTotalPages());
        pageResponse.setLastPage(page.isLast());
        return pageResponse;
    }

    public static <T> PageResponse<T> getPageResponse(Page<T> page){
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.setContent(page.getContent());
        pageResponse.setPageNumber(page.getNumber());
        pageResponse.setPageSize(page.getSize());
        pageResponse.setTotalElements(page.getTotalElements());
        pageResponse.setTotalPages(page.getTotalPages());
        pageResponse.setLastPage(page.isLast());
        return pageResponse;
    }
}

