package com.farmdora.farmdoraactivity.user.repository;

import com.farmdora.farmdoraactivity.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserQuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findAllByUser_UserId(Integer userId);


}
