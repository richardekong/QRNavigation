package com.team1.qrnavigationproject.service;

import com.team1.qrnavigationproject.model.Content;
import com.team1.qrnavigationproject.repository.ContentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService{
    private ContentRepo contentRepo;

    @Autowired
    void setContentRepo(ContentRepo contentRepo){

        this.contentRepo = contentRepo;
    }

    @Override
    public List<Integer>findDistinctContentIds(){ return contentRepo.findDistinctContentIds();}
    @Override
    public List<Content> findAll(){
        return contentRepo.findAllContents();
    }

    @Override
    public Content saveContent(Content content) {
        return contentRepo.save(content);
    }

    @Override
    public Content findContentByName(String contentName) {
        return contentRepo.findByContentName(contentName);
    }
    @Override
    public Content findContentById(int contentId) {
        return contentRepo.findByContentId(contentId);
    }
    @Override
    public void deleteContentById(int contentId) {
        Content content = contentRepo.findByContentId(contentId);
        if (content != null) {
            contentRepo.delete(content);
        }
    }

    @Override
    public Content updateContent(Content content) {
        Content existingContent = contentRepo.findByContentId(content.getId());
        if (existingContent != null) {
            existingContent.setName(content.getName());
            existingContent.setDescription(content.getDescription());
            return contentRepo.save(existingContent);
        } else {
            throw new IllegalArgumentException("Content with name " + content.getName() + " not found." + existingContent.getName());
        }
    }

}
