package com.team1.qrnavigationproject.controller;

import com.team1.qrnavigationproject.model.Content;
import com.team1.qrnavigationproject.response.CustomException;
import com.team1.qrnavigationproject.response.Response;
import com.team1.qrnavigationproject.service.ContentService;
import com.team1.qrnavigationproject.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class contentController {

    private ContentService contentService;
    private SpaceService spaceService;

    @Autowired
    public void setContentService(ContentService contentService){
        this.contentService = contentService;
    }

    @Autowired
    public void SetSpaceService(SpaceService spaceService){ this.spaceService = spaceService;}

    @GetMapping("/admin/contents")
    public String ShowContentManagementPage(Model model) {
        Optional<List<Content>> unConfirmedContents = Optional.of(contentService.findAll());
        unConfirmedContents.ifPresentOrElse(
                contents -> model.addAttribute("contents", contents),
                () -> {
                    HttpStatus status = HttpStatus.NO_CONTENT;
                    model.addAttribute("ContentsLoadErrorDisplay", new Response(status.value(), status.getReasonPhrase(), System.currentTimeMillis()));
                    throw new CustomException(HttpStatus.NO_CONTENT.getReasonPhrase(), HttpStatus.NO_CONTENT);
                });

        model.addAttribute(
                "contentsLoadSuccessDisplay",
                new Response(HttpStatus.OK.value(), "Contents Loaded Successfully!", System.currentTimeMillis())
        );
        model.addAttribute("contents", unConfirmedContents.get());
        return "contentManagementPage";
    }

    @GetMapping("/admin/contents/createContent")
    public String ViewCreateContentPage() {
        return "createContentPage";
    }

    @PostMapping("/admin/contents/createNewContent")
    public String CreateNewContent(@ModelAttribute Content content){
        contentService.saveContent(content);

        return "redirect:/admin/contents";
    }

    @PostMapping("/admin/contents/deleteContent")
    public String deleteContent(@RequestParam("contentId") int contentId) {
        contentService.deleteContentById(contentId);
        return "redirect:/admin/contents";
    }

    @PostMapping("/admin/contents/viewContent")
    public String viewContentPage(@RequestParam("contentId") int contentId,  Model model) {
        Content content = contentService.findContentById(contentId);
        model.addAttribute("content", content);
        return "contentUpdate";
    }

    @PostMapping("/admin/contents/updateContent")
    public String UpdateContent(@ModelAttribute Content content){
        contentService.updateContent(content);
        return "redirect:/admin/contents";
    }
}
