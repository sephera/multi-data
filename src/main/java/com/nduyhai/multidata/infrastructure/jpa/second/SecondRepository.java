package com.nduyhai.multidata.infrastructure.jpa.second;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
interface SecondRepository extends CrudRepository<SecondEntity, Integer> {

}
