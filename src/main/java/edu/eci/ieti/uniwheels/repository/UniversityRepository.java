package edu.eci.ieti.uniwheels.repository;

import edu.eci.ieti.uniwheels.model.Universidad;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UniversityRepository extends MongoRepository<Universidad,String> {
}
