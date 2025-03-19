package com.example.QNA.studentclub;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentClubRepository extends JpaRepository<StudentClub, Long> {
}
