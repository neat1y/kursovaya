package com.example.demo.Service;

import com.example.demo.DTO.SectionDTO;
import com.example.demo.Entities.Section;
import com.example.demo.Repository.Person_Repository;
import com.example.demo.Repository.Section_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class Section_Service {
        private final Section_Repository sectionRepository;
        @Autowired
        public Section_Service (Section_Repository sectionRepository){

            this.sectionRepository = sectionRepository;
        }
        public Optional<Section> find_by_name(String name){
            return sectionRepository.findByName(name);
        }

    public void save(Section sectionDTO) {
        sectionRepository.save(sectionDTO);
    }
}
