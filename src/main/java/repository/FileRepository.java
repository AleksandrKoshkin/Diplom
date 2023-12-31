package repository;

import entiti.File;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends CrudRepository<File, String> {
    @Query(value = "SELECT * FROM files LIMIT :limit", nativeQuery = true)
    List<File> getFiles(int limit);
}
