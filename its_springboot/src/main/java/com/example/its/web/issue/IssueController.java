package com.example.its.web.issue;

import com.example.its.domain.issue.IssueEntity;
import com.example.its.domain.issue.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/issues")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    @GetMapping
    public String showList(Model model) {
        model.addAttribute("issueList", issueService.findAll());
        return "issues/list";
    }


    @GetMapping("/creationForm")
    public String showCreationForm(Model model) {
        if (!model.containsAttribute("issueForm")) {
            model.addAttribute("issueForm", new IssueForm());
        }
        return "issues/creationForm";
    }

    @PostMapping
    public String create(@Validated @ModelAttribute("issueForm") IssueForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "issues/creationForm";
        }
        issueService.create(form.getSummary(), form.getDescription());
        return "redirect:/issues";
    }

    @GetMapping("/{issueId}")
    public String showDetail(@PathVariable("issueId") long issueId, Model model) {
        model.addAttribute("issue", issueService.findById(issueId));
        return "issues/detail";
    }
}
