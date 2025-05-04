package com.noteseva.service;

import com.noteseva.constants.Status;
import com.noteseva.model.Query;
import com.noteseva.repository.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class QueryService {

    @Autowired
    QueryRepository queryRepository;

    public Query save(Query query) {
        query.setStatus(Status.PENDING);
        query.setRemarks("Query created successfully");
        query.setRaisedAt(LocalDateTime.now());
        return queryRepository.save(query);
    }
}
