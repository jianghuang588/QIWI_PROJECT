package com.example.mvp.controller;

import com.example.mvp.enums.Major;
import com.example.mvp.enums.Subject;
import com.example.mvp.enums.UserRole;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WebController {

    // LANDING PAGE - Entry point
    @GetMapping("/")
    public String landingPage() {
        return "landing";
    }

    // LOGIN PAGE - Redirects to dashboard if already has userId
    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String redirect, Model model) {
        if (redirect != null) {
            model.addAttribute("redirectUrl", redirect);
        }
        return "login";
    }

    // REGISTRATION PAGE - With all required data
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("majors", Major.values());
        model.addAttribute("roles", UserRole.values());
        return "register";
    }

    // ROLE SELECTION - After registration
    @GetMapping("/role-selection")
    public String roleSelectionPage(@RequestParam String userId, Model model) {
        model.addAttribute("userId", userId);
        return "role-selection";
    }

    // DASHBOARD - Central hub, requires login
    @GetMapping("/dashboard")
    public String dashboardPage(@RequestParam String userId,
                                @RequestParam(required = false) String role,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        if (userId == null || userId.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please login first");
            return "redirect:/login";
        }

        model.addAttribute("userId", userId);
        if (role != null) {
            model.addAttribute("role", role);
        }
        return "dashboard";
    }

    // TUTOR PROFILE - Setup/edit tutor profile
    @GetMapping("/tutor-profile")
    public String tutorProfilePage(@RequestParam String userId, Model model,
                                   RedirectAttributes redirectAttributes) {

        if (userId == null || userId.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please login first");
            return "redirect:/login";
        }

        model.addAttribute("subjects", Subject.values());
        model.addAttribute("majors", Major.values());
        model.addAttribute("userId", userId);
        return "tutor-profile";
    }

    // TUTEE PROFILE - Setup/edit student profile
    @GetMapping("/tutee-profile")
    public String tuteeProfilePage(@RequestParam String userId, Model model,
                                   RedirectAttributes redirectAttributes) {

        if (userId == null || userId.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please login first");
            return "redirect:/login";
        }

        model.addAttribute("subjects", Subject.values());
        model.addAttribute("majors", Major.values());
        model.addAttribute("userId", userId);
        return "tutee-profile";
    }

    // BROWSE TUTORS - Find and contact tutors (FOR STUDENTS)
    @GetMapping("/browse-tutors")
    public String browseTutorsPage(@RequestParam String userId, Model model,
                                   RedirectAttributes redirectAttributes) {

        if (userId == null || userId.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please login first");
            return "redirect:/login";
        }

        model.addAttribute("majors", Major.values());
        model.addAttribute("subjects", Subject.values());
        model.addAttribute("userId", userId);
        return "browse-tutors";
    }

    // MESSAGES - Chat with other users
    @GetMapping("/messages")
    public String messagesPage(@RequestParam String userId,
                               @RequestParam(required = false) String contactId,
                               @RequestParam(required = false) String contactName,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        if (userId == null || userId.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please login first");
            return "redirect:/login";
        }

        model.addAttribute("userId", userId);
        if (contactId != null) {
            model.addAttribute("contactId", contactId);
        }
        if (contactName != null) {
            model.addAttribute("contactName", contactName);
        }
        return "messages";
    }

    // LOGOUT - Clear session and redirect
    @PostMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("success", "You have been logged out successfully!");
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logoutGet(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("success", "You have been logged out successfully!");
        return "redirect:/";
    }

    // BROWSE TUTEES - For tutors to find students who need help
    @GetMapping("/browse-tutees")
    public String browseTuteesPage(@RequestParam String userId, Model model,
                                   RedirectAttributes redirectAttributes) {

        if (userId == null || userId.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please login first");
            return "redirect:/login";
        }

        model.addAttribute("majors", Major.values());
        model.addAttribute("subjects", Subject.values());
        model.addAttribute("userId", userId);
        return "browse-tutees";
    }

    // TERMS AND PRIVACY PAGES
    @GetMapping("/terms")
    public String termsPage() {
        return "terms";
    }

    @GetMapping("/privacy")
    public String privacyPage() {
        return "privacy";
    }

    // NEW PAGES - Added based on navigation requirements
    @GetMapping("/universities")
    public String universitiesPage() {
        return "universities";
    }

    @GetMapping("/how-it-works")
    public String howItWorksPage() {
        return "how-it-works";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }
}