package br.com.bancointer.challenge.repository;

import br.com.bancointer.challenge.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
