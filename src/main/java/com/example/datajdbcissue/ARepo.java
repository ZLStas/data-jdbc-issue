package com.example.datajdbcissue;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ARepo extends CrudRepository<A, Long> {
}
