package com.team1.qrnavigationproject.repository;

import com.team1.qrnavigationproject.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepo extends JpaRepository<Content, Integer> {

    @Query("SELECT c FROM Content c")
    List<Content> findAllContents();

    Content save(Content content);

    @Query("SELECT c FROM Content c WHERE c.name = :contentName")
    Content findByContentName(@Param("contentName") String contentName);

    @Query("SELECT c FROM Content c WHERE c.id = :contentId")
    Content findByContentId(@Param("contentId") int contentId);

}
