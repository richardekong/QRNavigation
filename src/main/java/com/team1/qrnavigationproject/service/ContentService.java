package com.team1.qrnavigationproject.service;

import com.team1.qrnavigationproject.model.Content;
import java.util.List;

public interface ContentService {
    List<Integer> findDistinctContentIds(int organizationId);
    List<Object[]> findAll(int organizationId);
    List<Object[]> getContentsByEventId(int eventId);
    Content saveContent(Content content);
    Content findContentByName(String contentName);
    Content findContentById(int contentId);
    Content updateContent(Content content);
    void deleteContentById(int contentId);
}
