package com.my_app.sys_plywacki.web;

import com.my_app.sys_plywacki.model.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.my_app.sys_plywacki.service.*;
import com.my_app.sys_plywacki.repository.*;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
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
    
    @Autowired
    private PlayerRepository playerRepository;
    
    @Autowired
    private ClubRepository clubRepository;

    private List <Club> clubs;

    @Autowired
    private ClubPlayerConnectionRepository clubPlayerConnectionRepository;
    @Autowired
    private PlayerPersonConnectionRepository playerPersonConnectionRepository;



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

    @RequestMapping(value = "/redirectToWelcome", method = RequestMethod.GET)
    public String redirectToWelcome() {
        return "redirect:/";
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
    	personService.update_user_role_if_exists();
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
    	personService.update_user_role_if_exists();
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
                
        Collection<SimpleGrantedAuthority> oldAuthorities = (Collection<SimpleGrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
        List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
        updatedAuthorities.add(authority);
        updatedAuthorities.addAll(oldAuthorities);

        SecurityContextHolder.getContext().setAuthentication(
                   new UsernamePasswordAuthenticationToken(
                           SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                           SecurityContextHolder.getContext().getAuthentication().getCredentials(),
                           updatedAuthorities)
        );
        System.out.println("Authorities updates");
        System.out.println(updatedAuthorities);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());
        personService.add_role(p, role);
        if(role.getName().equals("zawodnik")) {
        	System.out.println("Twoja rola = "+role.getName());
        	return "redirect:editPlayer";
        }
        else return "redirect:welcome";
    }
    //------------edycja zawodnika, utworzenie nowego obiektu Zawodnik:
    @RequestMapping(value = "/redirectToEditPlayer", method = RequestMethod.GET)
    public String redirectToEditPlayer() {
    	System.out.println("Redirecting Result To Edit Player Page");
        return "redirect:editPlayer";
    }
    
    @GetMapping("/editPlayer")
    public String editPlayer(Model model) {
    	model.addAttribute("player",new Player());
    	System.out.println("Jestem w funkcji editPlayer");
    	return "/editPlayer";
    }
    @ModelAttribute("listOfAvailableClubs")
    public List<Club> editPlayer() {
    	System.out.println("Jestem w funkcji editPlayer @ModelAttribute");
    	List<Club> listOfAvailableClubs = clubService.findAll();
        for (Club x : listOfAvailableClubs) {
        	System.out.println("Id klubu: "+x.getId_club());
            System.out.println("Nazwa klubu: "+x.getClub_name());
        }
        return listOfAvailableClubs;
     }
    @PostMapping("/editPlayer")
    public String editPlayer(@ModelAttribute Player player, Model model, BindingResult bindingResult) {
    	System.out.println("Data wygasniecia dokumentacji zawodnika: "+player.getMedExDate());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());
        System.out.println("to jest person id"+p.getId());
        System.out.println("to jest player id"+player.getIdPlayer());
        System.out.println("to jest model atrybut "+model.getAttribute("player.wartosc"));

        playerRepository.save(player);
        Long x=player.getIdClub();
        Optional<Club> club = clubRepository.findById(x);
        System.out.println("to jest club get" + club.get().getId_club());

//Tutaj trzeba wyciągnąć klub do którego sie zapisał zawododnik, żeby zapisać do bd
        clubPlayerConnectionRepository.save(new ClubPlayerConnection(club.get(), player));

        playerPersonConnectionRepository.save(new PlayerPersonConnection(player, p));
        System.out.println("to jest player id"+player.getIdPlayer());
    	return "redirect:welcome";
    }

    
//    @RequestMapping("/searchPlayers")
//    public String searchPlayers(Model model, @Param("keyword") String keyword){
//        List<Player> playerList = playerSearchService.listAll(keyword);
//        model.addAttribute("playerList", playerList);
//        model.addAttribute("keyword", keyword);
//        return "playerSearchService";
//    }





    @GetMapping("/registrationClub")
    public String clubReg(Model model){
//        if(securityService.isAuthenticated()){
//            return"redirect:/welcome";
//        }

//        List<Club> clubForm = clubService.findAll();

        model.addAttribute("clubForm", new Club());

        return "/registrationClub";
    }

    @RequestMapping(value = "/redirectToRegClub", method = RequestMethod.GET)
    public String redirectToRegClub() {
        System.out.println("Redirecting Result To The Final Page");
        return "redirect:registrationClub";
    }

    @PostMapping("/registrationClub")
    public String clubReg(@ModelAttribute("clubForm") Club clubForm){
        System.out.println("Jestem w PostMapping /clubRegistration");

        clubService.save(clubForm);

        return "redirect:/welcome";
    }


    @GetMapping("/searchClubs")
    public String viewClubPage(Model model){
        List<Club> clubList = clubService.findAll();
        model.addAttribute("clubList", clubList);
        return "/searchClubs";
    }

    @RequestMapping(value = "/redirectToSearchClubPage", method = RequestMethod.GET)
    public String redirectToSearchClubPage(Model model) {

        return "redirect:searchClubs";
    }


    @GetMapping("/searchCompetitions")
    public String viewComptetionPage(Model model){
        List<Competition> competitionList = competitionService.findAll();
        model.addAttribute("competitionList", competitionList);
        return "/searchCompetitions";
    }


    @RequestMapping(value = "/redirectToSearchCompetitionsPage", method = RequestMethod.GET)
    public String redirectToSearchCompetitionsPage() {
        System.out.println("Redirecting Result To The Final Page");
        return "redirect:searchCompetitions";
    }

    @GetMapping("/searchPlayers")
    public String viewPlayersPage(Model model){
        List<Player> playerList = playerService.findAll();
        model.addAttribute("playerList", playerList);
        System.out.println("Lista zawodnikow dodana do modelu");
        
        for (Player x : playerList) {
        	System.out.println("Id zawodnika: "+x.getIdPlayer());
            System.out.println("Id klubu: "+x.getIdClub());
            
            List<PlayerPersonConnection> person_player_connection = x.getPersons();
            System.out.println("person_player_connection: "+x.getPersons());
            for(PlayerPersonConnection p: person_player_connection) {
            	//model.addAttribute("per",p.getPerson());
            	System.out.println("Username"+p.getPerson().getUsername());
            }
        }
        return "/searchPlayers";
    }
    @RequestMapping(value = "/redirectToSearchPlayersPage", method = RequestMethod.GET)
    public String redirectToSearchPlayersPage() {
        System.out.println("Redirecting Result To The Final Page");
        return "redirect:searchPlayers";
    }
/*

    @RequestMapping("/")
    public String showNewClubPage(Model model){
        Club club = new Club();
        model.addAttribute("Club", club);
        return "clubRegistration";
    }*/




    @GetMapping("registrationCompetitions")
    public String registrationCompetition(Model model){
        model.addAttribute("competitionForm", new Competition());

        return "registrationCompetitions";
    }

    @PostMapping("registrationCompetitions")
    public String registrationCompetition(@ModelAttribute("competitionForm") Competition competitionForm){
        competitionService.save(competitionForm);

        return "redirect:searchCompetitions";
    }

    @RequestMapping("/")
    public String showNewCompetitionPage(Model model){
        Competition competition = new Competition();
        model.addAttribute("Competition", competition);
        return "registrationCompetitions";
    }

    @GetMapping("registrationCompetitorsPlayer")
    public String registrationCompetitiorsPlayer(Model model){
        model.addAttribute("competitiorsPlayerForm", new Player());

        return "registrationCompetitiorsPlayer";
    }

    @PostMapping("registrationCompetitorsPlayer")
    public String registrationCompetitiorsPlayer(@ModelAttribute("competitiorsPlayerForm") Player competitiorsPlayerForm){
        playerService.save(competitiorsPlayerForm);

        return "/searchCompetitions";
    }

/*

    @RequestMapping("/searchCompetition")
      public String searchCompetition(Model model, @Param("keyword") String keyword){
        List<Competition> competitionList = competitionService.listAll(keyword);
        model.addAttribute("competitionList", competitionList);
        model.addAttribute("keyword", keyword);
        return "competitionSearchService";
    }*/
    
    @GetMapping("/test")
    public String test(Model model) {
        return "test";
    }

    @PostMapping("/test")
    public String test() {
        return "redirect:/welcome";
    }
}

