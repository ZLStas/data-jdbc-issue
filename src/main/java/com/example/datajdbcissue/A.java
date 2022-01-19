package com.example.datajdbcissue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class A {

    @Id
    private Long id;


    @MappedCollection(keyColumn = "id", idColumn = "a_id")
    private List<B> bies;

}
