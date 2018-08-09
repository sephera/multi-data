package com.nduyhai.multidata.infrastructure.jpa.first;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
interface FirstRepository extends CrudRepository<FirstEntity, Integer> {

}
