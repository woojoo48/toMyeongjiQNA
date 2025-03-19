package com.example.QNA.college;

import org.springframework.stereotype.Service;

@Service
public class CollegeService {
    private final CollegeRepository collegeRepository;

    public CollegeService(CollegeRepository collegeRepository) {
        this.collegeRepository = collegeRepository;
    }


}
