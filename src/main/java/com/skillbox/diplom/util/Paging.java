package com.skillbox.diplom.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Paging {

    public static Pageable getPaging(int offset, int limit, Sort sort) {
        int pageNumber = offset / limit;
        return PageRequest.of(pageNumber, limit, sort);
    }
}
