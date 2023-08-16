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
    public List<Integer>findDistinctContentIds(int organizationId){ return contentRepo.findDistinctContentIds(organizationId);}
    @Override
    public List<Object[]> findAll(int organizationId){
        return contentRepo.findAllContentsWithNames(organizationId);
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
        System.out.println("Content ID: "+contentId);
        Content content = contentRepo.findByContentId(contentId);
        if (content != null) {
            System.out.println("HERE ****** Content ID: "+content.getId());
            contentRepo.delete(content);
            try {
                contentRepo.delete(content);
            } catch (Exception e) {
                e.printStackTrace();
                // Log or handle the exception appropriately
            }
        }
    }

    @Override
    public Content updateContent(Content content) {
        Content existingContent = contentRepo.findByContentId(content.getId());
        if (existingContent != null) {
            existingContent.setName(content.getName());
            existingContent.setDescription(content.getDescription());
            existingContent.setEvent(content.getEvent());
            existingContent.setSpace(content.getSpace());
            existingContent.setSubSpace(content.getSubSpace());
            return contentRepo.save(existingContent);
        } else {
            throw new IllegalArgumentException("Content with name " + content.getName() + " not found." + existingContent.getName());
        }
    }

}
