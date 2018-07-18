package courses;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CourseController {
	
	@Resource
	CourseRepository courseRepo;
	
	@Resource
	TopicRepository topicRepo;
	
	@Resource
	TextbookRepository textbookRepo;

	
	@RequestMapping("/course")
	public String findOneCourse(@RequestParam(value="id")long id, Model model) throws CourseNotFoundException{
		Optional<Course> course = courseRepo.findById(id);
		
		if(course.isPresent()) {
			model.addAttribute("courses", course.get());
			return "course";
		}
		throw new CourseNotFoundException();
		
		
	}

	@RequestMapping("/show-courses")
	public String findAllCourses(Model model) {
		model.addAttribute("courses", courseRepo.findAll());
		return("courses");
		
		
	}
	
	@RequestMapping("/topic")
	public String findOneTopic(@RequestParam(value="id")long id, Model model) throws TopicNotFoundException {
		Optional<Topic> topic = topicRepo.findById(id);
		
		if(topic.isPresent()) {
			model.addAttribute("topics", topic.get());
			model.addAttribute("courses", courseRepo.findByTopicsContains(topic.get()));
			return "topic";
	}
		throw new TopicNotFoundException();
		
		
	}
	@RequestMapping("/topics")
	public String findAllTopics(Model model) {
		model.addAttribute("topics", topicRepo.findAll());
		return("topics");
		
		
	}
	@RequestMapping("/textbook")
	public String findOneTextbook(@RequestParam(value="id")long id, Model model) throws TextbookNotFoundException {
		Optional<Textbook> textbook = textbookRepo.findById(id);
			
		if(textbook.isPresent()) {
			model.addAttribute("textbooks", textbook.get());
			model.addAttribute("courses", courseRepo.findByTextbooksContains(textbook.get()));
			return "textbook";
		}
			throw new TextbookNotFoundException();
			
		
	}
	
	@RequestMapping("/show-textbooks")
	public String findAllTextbooks(Model model) {
		model.addAttribute("textbooks", textbookRepo.findAll());
		return("textbook");
	}
	
	@RequestMapping("/delete-course")
	public String deleteCourseByName(String courseName) {
		if (courseRepo.findByName(courseName) != null) {
			Course deletedCourse = courseRepo.findByName(courseName);
			courseRepo.delete(deletedCourse);
		}
		return"redirect:/show-courses";
		}
	
	@RequestMapping("/add-course")
	public String addCourse(String courseName, String courseDescription, String topicName) {
		Topic topic = topicRepo.findByName(topicName);
		if(topic == null){
			topic = new Topic(topicName);
			topicRepo.save(topic);
		}
		
		
		Course newCourse = courseRepo.findByName(courseName);
		if (newCourse == null) {
			newCourse = new Course(courseName, courseDescription, topic);
			courseRepo.save(newCourse);
		}
		return"redirect:/show-courses";	
		}
	
	@RequestMapping("/del-course")
	public String deleteCourseById(Long courseId) {
		courseRepo.deleteById(courseId);	
		
		return"redirect:/show-courses";	
	}
	
	
	@RequestMapping("/find-by-topic")
	public String findCoursesByTopic(String topicName, Model model) {
		Topic topic = topicRepo.findByName(topicName);
		model.addAttribute("courses", courseRepo.findByTopicsContains(topic));
		
		return"/topic";	

}
	
	
	@RequestMapping("/sort-courses")
	public String sortCourses (Model model) {
		model.addAttribute("courses", courseRepo.findAllByOrderByNameAsc());

		return"/courses";	
}

}