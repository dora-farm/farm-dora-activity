package com.farmdora.farmdoraactivity.seller.repository;

import com.farmdora.farmdoraactivity.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyQuestionRepository extends JpaRepository<Question, Integer> {

}
