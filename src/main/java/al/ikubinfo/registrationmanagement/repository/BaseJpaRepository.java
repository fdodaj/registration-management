package al.ikubinfo.registrationmanagement.repository;

import al.ikubinfo.registrationmanagement.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
@NoRepositoryBean
public interface BaseJpaRepository<E extends BaseEntity> extends JpaSpecificationExecutor<E>, JpaRepository<E, Long> {}
