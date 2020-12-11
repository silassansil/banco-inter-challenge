package br.com.bancointer.challenge.service.impl;

import br.com.bancointer.challenge.domain.Calculation;
import br.com.bancointer.challenge.domain.User;
import br.com.bancointer.challenge.repository.UserRepository;
import br.com.bancointer.challenge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User saveOrUpdate(final User user) {
        return this.userRepository.save(user);
    }

    @Override
    public void delete(final String userId) {
        this.userRepository.deleteById(userId);
    }

    @Override
    public Optional<User> findById(final String userId) {
        return this.userRepository.findById(userId);
    }

    @Override
    public Stream<User> findAll() {
        return StreamSupport.stream(this.userRepository.findAll().spliterator(), false);
    }

    @Override
    public Optional<User> addCalculations(final String userId, final Calculation calculation) {
        final Optional<User> userById = this.findById(userId);
        userById.ifPresent(user -> {
            user.addCalculationsOnUser(calculation);
            this.saveOrUpdate(user);
        });
        return userById;
    }
}
