package com.example.demo.Controllers;

import com.example.demo.DTO.*;
import com.example.demo.Entities.*;
import com.example.demo.Service.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.example.demo.Security.JWTUtil;
import com.example.demo.Security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.View;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;


@RestController
@RequestMapping("/curs")
@CrossOrigin(origins = "http://localhost:3000")
public class Course_Controllers {

    private final Course_Service courseService;
    private final Video_Service videoService;
    private final Person_Service personService;
    private final PasswordEncoder passwordEncoder;
    private final Section_Service sectionService;
    private final JWTUtil jwtUtil;


    private final Person_Course_Service personCourseService;
    @Autowired
    public Course_Controllers(Course_Service courseService, Video_Service videoService, Person_Service personService, PasswordEncoder passwordEncoder, Section_Service sectionService, JWTUtil jwtUtil, Person_Course_Service personCourseService) {
        this.courseService = courseService;
        this.videoService = videoService;
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
        this.sectionService = sectionService;
        this.jwtUtil = jwtUtil;
        this.personCourseService = personCourseService;
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/students")
    public List<CourseDTO> all(@RequestParam(name="search") Optional<String> search){
        if(search.isPresent() && !search.isEmpty()){

            List<CourseDTO> courseDTOListM=courseService.findNameAll(search.get()).stream().map(course -> new CourseDTO(course.getCourseId(),course.getName(), course.getSection(), course.getDescription(), course.getImg(),course.getRate())).toList();
            return  courseDTOListM;
        }
        return courseService.all().stream().map(course -> new CourseDTO(course.getCourseId(),course.getName(), course.getSection(), course.getDescription(), course.getImg(),course.getRate())).toList();
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> getImage() throws IOException {
        String path = courseService.find_id(1).get().getImg();
        byte[] image = Files.readAllBytes(Paths.get(path));
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }
    @GetMapping("/user/image/{id}")
    public ResponseEntity<byte[]> getImageByUser(@PathVariable("id") int id) throws IOException {
        Person person = personService.findbyId(id).orElse(null);
        if (person == null || person.getImg() == null || person.getImg().isEmpty()) {
            return ResponseEntity.notFound().build();
        }
       String path=person.getImg();
        byte[] image = Files.readAllBytes(Paths.get(path));
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") int id) throws IOException {
        String path = courseService.find_id(id).get().getImg();
        byte[] image = Files.readAllBytes(Paths.get(path));
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }
    @GetMapping("/video/{id}")
    public ResponseEntity<byte[]> getVideo(@PathVariable("id") int id) throws IOException {
        String path = videoService.findby_id(id).getReference();
        try {
            byte[] videoBytes = Files.readAllBytes(Paths.get(path));

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("video/mp4")) // Установка MediaType для MP4 видео
                    .contentLength(videoBytes.length).body(videoBytes);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/profile/someinfo/{id}")
    public Map<String,Object> getProfileSomeInfo(@PathVariable("id")int id){
        Map<String,Object> response=new HashMap<>();
        Person person= personService.findbyId(id).get();
        response.put("name",person.getName());
        response.put("email",person.getGmail());
        response.put("about",person.getAbout());
        List<CourseDTO> courseDTOS=personCourseService.your_teacher_course(id);
        response.put("courses",courseDTOS);
        return response;
    }
    @GetMapping("/teacher_course")
    public List<CourseDTO> get_my_teacher_course() {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Integer id = personService.findbyName(personDetails.getUsername()).get().getPersonId();
        return personCourseService.your_teacher_course(id);
    }

    @GetMapping("/students/{id}")
    public List<VideoDTO> all(@PathVariable("id") int id){
       return videoService.for_course(id).stream().map(video -> new VideoDTO(video.getName(),video.getCourse().getCourseId(),video.getVideoId(),video.getSectionOfCourse())).toList();
    }
    @GetMapping("/showUserInfo")
    @ResponseBody
    public PersonDTO showUserInfo() {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        System.out.println(personDetails.getPerson());
        Person person=personService.findbyName(personDetails.getUsername()).get();
       return null;
    }
    @GetMapping("/students/my_courses")
    public List<CourseDTO> my_courses(){
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Integer id = personService.findbyName(personDetails.getUsername()).get().getPersonId();
        return personCourseService.your_course(id);
    }
    @PostMapping("/teacher/upload/img/{id}")
    public ResponseEntity<String> img_upload(@RequestParam("file") MultipartFile file, @PathVariable("id") Integer id) throws IOException {
            Optional<Course> optionalCourse = courseService.find_id(id);
         if (optionalCourse.isPresent()) {
                Course course1 = optionalCourse.get();
                Path directory = Paths.get("D:\\curs\\png");
                Files.createDirectories(directory);
                Path path = directory.resolve(file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                course1.setImg(path.toString());
                courseService.save(course1);
                return ResponseEntity.ok("ok");
            } else {
                return ResponseEntity.notFound().build(); // Курс не найден
            }
    }
    @PostMapping("/user/upload/img")
    public ResponseEntity<String> user_img_upload(@RequestParam("file") MultipartFile file) throws IOException {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Optional<Person> personOptional = personService.findbyName(personDetails.getUsername());
        if (personOptional.isPresent()) {
            Person person = personOptional.get();

            Path path_del = Paths.get(person.getImg());
            Path defaultImagePath = Paths.get("D:\\curs\\user.jpg");
            if (!path_del.equals(defaultImagePath) && Files.exists(path_del)) {
                Files.delete(path_del);
            }            Path directory = Paths.get("D:\\curs\\png\\user");
            Files.createDirectories(directory);
            Path path = directory.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
             person.setImg(path.toString());
            personService.save(person);

            return ResponseEntity.ok("ok");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/user/upload/data")
    public Map<String,Object> user_data_upload(@RequestBody UpdateDTO updateDTO) throws IOException {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Optional<Person> personOptional = personService.findbyName(personDetails.getUsername());
        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            person.setName(updateDTO.getName());
            person.setGmail(updateDTO.getEmail());
            person.setAbout(updateDTO.getAbout());
            personService.save(person);
            String token = jwtUtil.generateToken(person.getName());
            Map<String, Object> response = new HashMap<>();
            response.put("jwt-token", token);
            response.put("name", person.getName());
            response.put("email", person.getGmail());
            response.put("id", person.getPersonId().toString());
            response.put("about", person.getAbout());
            return response;
        } else {
            return null;
        }
    }


    @GetMapping("/students/my_courses/{id}")
    public Map<String,Object> course_by_id(@PathVariable("id") Integer id){
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Integer person_id = personService.findbyName(personDetails.getUsername()).get().getPersonId();
        Map<String,Object> response=new HashMap<>();
        Map<String, Object> second_half =new HashMap<>();
        Optional <Course> course= courseService.find_id(id);
        if(!course.isPresent()){
            second_half.put("no course", "no course");
            return second_half;
        }
        second_half.put("name",course.get().getName());
        second_half.put("description",course.get().getDescription());
        second_half.put("Img",course.get().getImg());
        if(course.get().getRate()!=null) {
            second_half.put("Rate", course.get().getRate().toString());
        }
        second_half.put("section",course.get().getSection().getName());
        response.put("course",second_half);
        List<Person> personList=personCourseService.teacher_id(id);
       ArrayList<Map<String,String>> qwe =new ArrayList<>();
        Integer u=1;
        for(Person person:personList){
            Map<String,String> second_half1=new HashMap<String,String>();
            second_half1.put("id",person.getPersonId().toString());
            second_half1.put("name",person.getName());
            second_half1.put("gmail",person.getGmail());
            second_half1.put("Img",person.getImg());
            qwe.add(second_half1);

        }
        response.put("check",personCourseService.checkList(course.get().getCourseId(),person_id));
        response.put("teachers",qwe);
        return response;
    }


    @PostMapping("/update_user_info")
    public Map<String, String> update(PersonDTO personDTO,@RequestParam("file") MultipartFile file){
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person  person = personService.findbyName(personDetails.getUsername()).get();
        person.setName(personDTO.getName());
        person.setGmail(personDTO.getEmail());
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        Map<String,String> response=new HashMap<>();
        response.put("jwt-token", jwtUtil.generateToken(person.getName()));
        response.put("name", person.getName());
        response.put("email", person.getGmail());
        List<String> supportedFormats = Arrays.asList("txt","mp4", "mov", "wmv", "avi", "mkv", "flv", "webm", "hevc");
        List<String> img=Arrays.asList("png","jpg","jpeg");
        // Проверьте расширение файла
//        if (!supportedFormats.contains(extension) && !img.contains(extension)) {
//            return null;
//        }
//        try {
//            // Задайте путь к каталогу, где вы хотите сохранить файл
//            Path path = Paths.get("D:\\curs\\"+cusr_name+"\\main",file.getOriginalFilename());
//
//            if (!Files.exists(path)) {
//                Files.createDirectories(path);
//            }
//
//            // Сохраняем файл
//            Files.copy(file.getInputStream(), path.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
//            if(img.contains(extension)){
//                course.setImg(path.toString());
//            }
//            else {
//                Video video = new Video();
//                video.setCourse(course);
//                video.setName(file.getOriginalFilename());
//                video.setReference(path.toString());
//                video.setSectionOfCourse("main");
//                videoService.save(video);
//            }
//            return null;
//            // Сохраните файл в заданном каталоге
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }

        return  response;
    }
    @PostMapping("/teacher/upload")
    public ResponseEntity<Map<String,Object>> add(@RequestBody Create_Course_DTO course){
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person  person = personService.findbyName(personDetails.getUsername()).get();
        Course course1=new Course();
        course1.setName(course.getName());
        course1.setDescription(course.getDescription());
        Optional<Section> section=sectionService.find_by_name(course.getSection());
        if(section.isPresent()){
            course1.setSection(section.get());
        }
        else {
            Section section1=new Section(course.getName());
            sectionService.save(section1);
            course1.setSection(section1);
        }
        course1.setImg("D:\\curs\\2.jpg");
        courseService.save(course1);
        personCourseService.save_teacher(course1,person);
        Map<String,Object> qwe=new HashMap<>();

        qwe.put("id",courseService.find_by_name(course1.getName()).getCourseId());
        return  ResponseEntity.ok(qwe);
    }   
    // все желаймые курсы
    @GetMapping("/wish")
    public List<CourseDTO> wish(){
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person  person = personService.findbyName(personDetails.getUsername()).get();
        return personCourseService.find_wish(person.getPersonId());
    }
    // сохранение курса как студента
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/{id}")
    public  ResponseEntity<Object> takeit(@PathVariable("id") int id){
        org.springframework.security.core.Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails=(PersonDetails) authentication.getPrincipal();
        Person  person = personService.findbyName(personDetails.getUsername()).get();
        Optional<Course> course=courseService.find_id(id);
        if(!course.isPresent()){
           return ResponseEntity.ok("no course");
        }

        Optional<Person_Course> personCourse=personCourseService.person_course(person.getPerson_id(),course.get().getCourseId());
        if(!personCourse.isPresent()){
            personCourseService.save_student(course.get(),person);
            return ResponseEntity.ok("ok");
        }
        if(personCourse.get().getRole().equals("ROLE_STUDENT") ||personCourse.get().getRole().equals("ROLE_TEACHER")) {
            return ResponseEntity.ok("have this");
        }
        personCourse.get().setRole("ROLE_STUDENT");
        return ResponseEntity.ok("you wish");
    }
    // сохранение желаний)
    @PostMapping("/wish/{id}")
    public  ResponseEntity<String> take_in_wish(@PathVariable("id") int id){
        org.springframework.security.core.Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails=(PersonDetails) authentication.getPrincipal();
        Person  person = personService.findbyName(personDetails.getUsername()).get();
        Optional<Course> course=courseService.find_id(id);
        if(!course.isPresent()){
            ResponseEntity.notFound();
        }
        personCourseService.save_wish(course.get(),person);
        return ResponseEntity.ok("ok");
    }
    /*
    * Доделать файловую систему (дерево)
    *
    *
    *
    *
    *
    *
    * */

    @PostMapping("/teacher/upload/{id}")
    public Map<String,Object> uploadFile(@RequestParam("file") MultipartFile file,@PathVariable("id") int id) {
        String filename = file.getOriginalFilename();
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        Course course=courseService.find_id(id).get();
        String cusr_name=courseService.find_id(id).get().getName();
        List<String> supportedFormats = Arrays.asList("txt","mp4", "mov", "wmv", "avi", "mkv", "flv", "webm", "hevc");
        List<String> img=Arrays.asList("png","jpg","jpeg");
        if (!supportedFormats.contains(extension) && !img.contains(extension)) {
            return (Map<String, Object>) ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Неверный формат файла: " + extension);
        }
        try {

            Path path = Paths.get("D:\\curs\\"+cusr_name+"\\main",file.getOriginalFilename());
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            Files.copy(file.getInputStream(), path.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            if(img.contains(extension)){
                course.setImg(path.toString());
            }
            else {
                Video video = new Video();
                video.setCourse(course);
                video.setName(file.getOriginalFilename());
                video.setReference(path.toString()+"\\"+file.getOriginalFilename());
                video.setSectionOfCourse("main");
                videoService.save(video);
            }
            Video video=videoService.findby_name(filename);
            Section_of_Video video1=new Section_of_Video(video.getSectionOfCourse(),video.getVideoId());
            Map <String,Object> response=new HashMap<>();
            response.put("video",video1);
            return response;
            // Сохраните файл в заданном каталоге

            } catch (IOException e) {
                e.printStackTrace();

                return (Map<String, Object>) ResponseEntity.ok("Произошла ошибка при загрузке файла ");
            }

    }
    @PostMapping("/change/video")
    public ResponseEntity<String> change_section(@RequestBody Section_of_Video video){
        video.getId();
        Video video1 =videoService.findby_id(video.getId());
        video1.setSectionOfCourse(video.getSection());
        videoService.save(video1);
        return ResponseEntity.ok("все keuto");
    }
    @GetMapping("/teacher/{id}")
    public List<CourseDTO> teacher_course(@PathVariable("id") int id) {
        return courseService.teacher_course(id).stream().map(course -> new CourseDTO(course.getCourseId(),course.getName(), course.getSection(), course.getDescription(),course.getImg(),course.getRate())).toList();
    }
    @GetMapping("/video/all_info/{id}")
    public List<Map<String,Object>> course_info(@PathVariable("id") int id) {
        List<Map<String,Object>> last=new LinkedList<>();
        Map<Object,Object> map=new HashMap<>();
       List<VideoDTO> videoDTO= videoService.for_course(id).stream().map(qwe->new VideoDTO(qwe.getName(),qwe.getCourse().getCourseId(),qwe.getVideoId() ,qwe.getSectionOfCourse())).toList();
      Map<String,List<VideoDTO>>  videos=videoDTO.stream().collect(Collectors.groupingBy(VideoDTO::getSectionOfCourse));
        Map<String,Object> qwe=new HashMap<>();
        for(String key: videos.keySet()){
            qwe.put("title",key);
            qwe.put("children",videos.get(key));
            last.add(qwe);
            qwe=new HashMap<>();
        }
       return last;
    }

    //String name, String password, String gmail, String role, int i
    @GetMapping("/info")
    public List<PersonDTO_course> all_info(){
        return personService.all().stream().map(person -> new PersonDTO_course(person.getName(), person.getPassword(),
                person.getGmail(), person.getCourseList().stream().map(Person_Course::getRole).toList(), person.getCourseList().stream().map(personCourse -> personCourse.getCourse().getCourseId()).toList()
                )).toList();
    }


}
