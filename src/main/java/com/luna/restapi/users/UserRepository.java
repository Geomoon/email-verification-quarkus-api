package com.luna.restapi.users;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<User, Long> {

    public Optional<User> findByEmail(String email) {
        return find("email = ?1", email).singleResultOptional();
    }

    public boolean existsById(Long id) {
        return findById(id) != null;
    }

}
