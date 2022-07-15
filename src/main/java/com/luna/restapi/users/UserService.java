package com.luna.restapi.users;

import com.luna.restapi.auth.UserResponse;
import com.luna.restapi.exceptions.NotFoundException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository repository;

    @Transactional
    public List<UserResponse> findAll() {
        return repository.findAll().list()
                .stream().map(u -> {
                    var response = new UserResponse();
                    response.setName(u.getName());
                    response.setLastname(u.getLastname());
                    response.setEmail(u.getEmail());
                    response.setVerified(u.isVerified());
                    return response;
                }).collect(Collectors.toList());
    }

    @Transactional
    public User save(User user) {
        repository.persist(user);
        return user;
    }

    @Transactional
    public User findById(long id) {
        return repository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    @Transactional
    public User findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + email));
    }

    @Transactional
    public void deleteById(long id) {
        var exists = repository.existsById(id);
        if (exists) repository.deleteById(id);
    }
}
