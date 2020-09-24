package edu.eci.ieti.uniwheels.repository;

import edu.eci.ieti.uniwheels.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<Usuario,String> {
}
