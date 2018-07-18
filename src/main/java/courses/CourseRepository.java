package courses;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {

	Collection<Course> findByTopicsContains(Topic java);
	
	Collection<Course> findByTopicsId(long topicId);

	Collection<Course> findByTextbooksContains(Textbook textbook);

	Course findByName(String courseName);

	Collection<Course> findAllByOrderByNameAsc();


}
