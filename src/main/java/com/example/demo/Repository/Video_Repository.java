package com.example.demo.Repository;

import com.example.demo.Entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Video_Repository extends JpaRepository<Video,Integer> {
    List<Video> findByCourse_CourseId(int id);
    Video findByNameAndSectionOfCourse(String name,String section);
}
