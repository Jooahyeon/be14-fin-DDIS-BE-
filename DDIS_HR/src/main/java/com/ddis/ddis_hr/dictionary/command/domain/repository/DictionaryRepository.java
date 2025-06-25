package com.ddis.ddis_hr.dictionary.command.domain.repository;

import com.ddis.ddis_hr.dictionary.command.domain.aggregate.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {

}


