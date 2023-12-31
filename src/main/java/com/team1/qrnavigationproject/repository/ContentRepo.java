package com.team1.qrnavigationproject.repository;

import com.team1.qrnavigationproject.model.Content;
import com.team1.qrnavigationproject.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepo extends JpaRepository<Content, Integer> {
    @Query("SELECT DISTINCT c.event.id FROM Content c WHERE c.event.organizer.id = :organizationId")
    List<Integer> findDistinctContentIds(int organizationId);
    @Query("SELECT c, e.name AS eventName, ss.name AS subSpaceName, s.name AS spaceName " +
            "FROM Content c " +
            "JOIN c.event e " +
            "JOIN c.subSpace ss " +
            "JOIN ss.space s " +
            "WHERE e.organizer.id = :organizationId " +
            "ORDER BY c.event.id")
    List<Object[]> findAllContentsWithNames(int organizationId);
    @Query("SELECT c, e.name AS eventName, ss.name AS subSpaceName, s.name AS spaceName " +
            "FROM Content c " +
            "JOIN c.event e " +
            "JOIN c.subSpace ss " +
            "JOIN ss.space s " +
            "WHERE e.id = :eventId " +
            "ORDER BY c.event.id")
    List<Object[]> findAllContentsWithNamesByEventId(int eventId);
    Content save(Content content);
    @Query("SELECT c FROM Content c WHERE c.name = :contentName")
    Content findByContentName(@Param("contentName") String contentName);
    @Query("SELECT c FROM Content c WHERE c.id = :contentId")
    Content findByContentId(@Param("contentId") int contentId);

    @Query("SELECT c FROM Content c WHERE c.event IN :events AND c.space.id = :spaceId AND c.subSpace.id = :subSpaceId")
    List<Content> getContentByEventAndSpaceAndSubSpace(List<Event> events , int spaceId , int subSpaceId);
}
