package com.example.demo.Service;

import com.example.demo.Entities.Video;
import com.example.demo.Repository.Video_Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Video_Service {
    private final Video_Repository videoRepository;


    public Video_Service(Video_Repository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public List<Video> for_course(int id){
        return videoRepository.findByCourse_CourseId(id);
    }
    public Video findby_id(int id){
    return videoRepository.getById(id);
    }
    public Video findby_name(String name){
        return videoRepository.findByNameAndSectionOfCourse(name,"main");
    }

    public void save(Video video){
        videoRepository.save(video);
    }
}
