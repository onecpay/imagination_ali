package com.example.index.dao;

import com.example.index.entity.RoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * @author ONEC
 */
@RepositoryDefinition(domainClass = RoleInfo.class, idClass = Long.class)
public interface RoleRepository extends JpaRepository<RoleInfo, Long> {

}
