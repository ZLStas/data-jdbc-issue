package com.example.datajdbcissue;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BRepo extends CrudRepository<B, Long> {
}
