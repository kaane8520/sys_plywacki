package com.my_app.sys_plywacki.web;

import com.my_app.sys_plywacki.model.*;

import com.my_app.sys_plywacki.service.*;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PersonController {
    @Autowired
    private PersonService personService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private PersonValidator personValidator;

    @Autowired
    private PlayerSearchService playerService;

    @Autowired
    private ClubService clubService;

    @Autowired
    private CompetitionService competitionService;



    @GetMapping("/registration")
    public String registration(Model model) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }

        model.addAttribute("personForm", new Person());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") Person userForm, BindingResult bindingResult) {
        personValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        personService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }

        if (error != null)
            model.addAttribute("error", "Nazwa użytkownika lub hasło zostało źle wpisane.");

        if (logout != null)
            model.addAttribute("message", "Wylogowałeś się pomyślnie.");

        return "login";
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
        return "welcome";
    }
    
    @RequestMapping(value = "/redirectToEditPage", method = RequestMethod.GET)
    public String redirectToEditPage() {
    	System.out.println("Redirecting Result To The Final Page");
        return "redirect:edit";
    }

    @GetMapping("/edit")
    public String edit(Model model) {
    	model.addAttribute("role", new Role());
    	System.out.println("Jestem w GetMapping /edit");
    	//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	//System.out.println("Twoj login to: "+auth.getPrincipal().toString());
        return "edit";
    }
    @PostMapping("/edit")
    public String edit(@ModelAttribute Role role, Model model, BindingResult bindingResult) {
        System.out.println("Jestem w PostMapping /edit");
        if (bindingResult.hasErrors()) {
            return "edit";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Twój username: " + auth.getName().toString());

        System.out.println("Tworze obiekt person");
        Person p = personService.findByUsername(auth.getName());

        System.out.println("Twoja rola to (role.getName()): " + role.getName());

        personService.add_role(p, role);

        System.out.println("Twoja rola to (person.getRoles()): " + Arrays.toString(p.getRoles().toArray()));

        Collection r = personService.getAuthorities(role);
        System.out.println(Arrays.toString(r.toArray()));

        //System.out.println("Your role is: "+p.getRoles());

        return "redirect:/welcome";
    }

//    @RequestMapping("/searchPlayers")
//    public String searchPlayers(Model model, @Param("keyword") String keyword){
//        List<Player> playerList = playerSearchService.listAll(keyword);
//        model.addAttribute("playerList", playerList);
//        model.addAttribute("keyword", keyword);
//        return "playerSearchService";
//    }
//
//
//



/*
    @GetMapping("clubRegistration")
    public String clubRegistration(Model model){
        model.addAttribute("clubForm", new Club());

        return "clubRegistration";
    }

    @PostMapping("clubRegistration")
    public String clubRegistration(@ModelAttribute("clubForm") Club clubForm){
        clubService.save(clubForm);

        return "clubRegistration";
    }

    @RequestMapping("/clubRegistration")
    public String viewClubPage(Model model){
        List<Club> clubList = clubService.listAll();
        model.addAttribute("clubList", clubList);
        return "clubRegistration";
    }
    @RequestMapping("/")
    public String showNewClubPage(Model model){
        Club club = new Club();
        model.addAttribute("Club", club);
        return "clubRegistration";
    }

    @RequestMapping("/searchClubs")
    public String searchClubs(Model model, @Param("keyword") String keyword){
        List<Club> clubList = clubService.listAll(keyword);
        model.addAttribute("clubList", clubList);
        model.addAttribute("keyword", keyword);
        return "clubSearchService";
    }


    @GetMapping("registrationCompetitions")
    public String registrationCompetition(Model model){
        model.addAttribute("competitionForm", new Competition());

        return "registrationCompetitions";
    }

    @PostMapping("registrationCompetitions")
    public String registrationCompetition(@ModelAttribute("competitionForm") Competition competitionForm){
        competitionService.save(competitionForm);

        return "registrationCompetitions";
    }

    @RequestMapping("/registrationCompetitions")
    public String viewCompetitionPage(Model model){
        List<Competition> competitionList = competitionService.listAll();
        model.addAttribute("competitionList", competitionList);
        return "registrationCompetitions";
    }
    @RequestMapping("/")
    public String showNewCompetitionPage(Model model){
        Competition competition = new Competition();
        model.addAttribute("Competition", competition);
        return "registrationCompetitions";
    }

    @RequestMapping("/searchCompetition")
      public String searchCompetition(Model model, @Param("keyword") String keyword){
        List<Competition> competitionList = competitionService.listAll(keyword);
        model.addAttribute("competitionList", competitionList);
        model.addAttribute("keyword", keyword);
        return "competitionSearchService";
    }*/
}

