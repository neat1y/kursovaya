package com.example.demo.DTO;

import com.example.demo.Entities.Section;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Create_Course_DTO {
        private String name;

         private String section;

        private String description;


    public Create_Course_DTO() {
    }

    public Create_Course_DTO(String name, String section, String description) {
        this.name = name;
        this.section = section;
        this.description = description;
    }

    public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSection() {
            return section;
        }

        public void setSection(String section) {
            this.section = section;
        }

        public String getDescription() {
            return description;
        }


        public void setDescription(String description) {
            this.description = description;
        }

}
