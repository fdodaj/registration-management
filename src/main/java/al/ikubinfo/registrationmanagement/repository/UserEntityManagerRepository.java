package al.ikubinfo.registrationmanagement.repository;

import al.ikubinfo.registrationmanagement.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserEntityManagerRepository {

    @Autowired
    private EntityManager entityManager;

    public List<UserEntity> getAllStudentsWithPaidCurses() {
        TypedQuery<UserEntity> query = entityManager
                .createQuery("select U from UserEntity U where U.status = 'PAID'", UserEntity.class);
        return query.getResultList();
    }
}
