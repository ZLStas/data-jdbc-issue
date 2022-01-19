package com.example.datajdbcissue;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AService {

    private final ARepo aRepo;

    public A save(A a) {
        return aRepo.save(a);
    }

}
