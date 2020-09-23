package com.yara.fieldservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.yara.fieldservice.model.Field;

/**
 * The Interface FieldRepository.
 *
 */
public interface FieldRepository extends MongoRepository<Field, String>{

}
