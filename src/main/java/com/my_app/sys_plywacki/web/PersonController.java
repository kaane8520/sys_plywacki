package com.my_app.sys_plywacki.web;

import com.my_app.sys_plywacki.model.*;
import com.my_app.sys_plywacki.repository.RefereeRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.my_app.sys_plywacki.service.*;
import com.my_app.sys_plywacki.repository.*;
import org.springframework.security.core.Authentication;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Ref;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.security.core.userdetails.User;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;


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

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RefereeRepository refereeRepository;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private VerificationRepository verificationRepository;

    @Autowired
    private FileDBService storageService;

    @Autowired
    private MessageService messageService;

    private List <Club> clubs;

    @Autowired
    private OrganizerCompetitionConnectionRepository organizerCompetitionConnectionRepository;
    @Autowired
    private OrganizerRepository organizerRepository;

    @Autowired
    private RefereeRolesRepository refereeRolesRepository;

    @Autowired
    private RefereeRoleOnCompetitionRepository refereeRoleOnCompetitionRepository;

    @Autowired
    private RefereeRolesService refereeRolesService;

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private MessageRepository messageRepository;


    //private RefereeRoleOnCompetitionRepository refereeRoleOnCompetitionRepository;*/
    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private CategoriesOnCompetitionRepository categoriesOnCompetitionRepository;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private UserDetailsService personDetailsService;

    @Autowired
    private FileUploadDAO fileUploadDao;

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
        if(personRepository.findAll().isEmpty()){
            personService.addModerator();
        }
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
        if(refereeRolesRepository.findAll().isEmpty()) {
            refereeRolesService.addRoles();
        }
        if(categoriesRepository.findAll().isEmpty()){
            categoriesService.addCategories();
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());
        if(messageRepository.findByIdPerson(p.getIdPerson()).isEmpty()){
            messageService.addMessage();
        }
        if(clubRepository.findAll().isEmpty()){
            clubService.add_no_club();
        }

        return "welcome";
    }
    //CHANGE YOUR ROLE

    @RequestMapping(value = "/redirectToChangeYourRolePage", method = RequestMethod.GET)
    public String redirectToChangeYourRolePage() {
        System.out.println("Redirecting Result To ChangeYourRolePage Page");
        return "redirect:changeYourRole";
    }
    @GetMapping("/changeYourRole")
    public String changeYourRole(Model model) {
        System.out.println("\n\nJestem w editMapping changeYourRole");
        model.addAttribute("role", new Role());
        //personService.update_user_role_if_exists();
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //System.out.println("Twoj login to: "+auth.getPrincipal().toString());
        return "/changeYourRole";
    }
    /*
    @RequestMapping(value="/changeYourRole",params="changeYourRoleButton",method=RequestMethod.POST)
    public void acction_changeYourRole(){
    }
    @RequestMapping(value="/changeYourRole",params="addDocumentationButton",method=RequestMethod.POST)
    public void acction_uploadFile(){
    }*/

    @PostMapping("/changeYourRole")
    public String changeYourRole(@ModelAttribute Role role, Model model, BindingResult bindingResult) {
        System.out.println("Jestem w PostMapping /changeYourRole");

        //Tworze nowy obiekt Verification:
        System.out.println("Tworze nowy obiekt Verification: \n\n");
        Verification verification = new Verification();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());
        verification.setIdPerson(p.getIdPerson());
        System.out.println("\n\nUstswiam id_person na: "+p.getIdPerson());

        //szukam userName
        verification.setUserName(p.getUsername());
        verification.setOldRole(p.printYourRole());
        verification.setNew_role(role.getName());
        verificationService.save(verification);
        if (bindingResult.hasErrors()) {
            return "changeYourRole";
        }
        Message message = new Message();
        String content = "Zadanie zmiany roli zostalo wyslane\n";
        message.setContent(content);
        message.setIdPerson(p.getIdPerson());
        messageRepository.save(message);

        return "redirect:welcome";

    }
    @GetMapping("/addDocumentation")
    public String addDocumentation(Model model) {
        return "/addDocumentation";
    }

    @PostMapping("/addDocumentation")
    public String addDocumentation() {
        return "redirect:/addDocumentation";
    }

//EDIT

    @RequestMapping(value = "/redirectToEditPage", method = RequestMethod.GET)
    public String redirectToEditPage() {
        System.out.println("Redirecting Result To The Final Page");
        return "redirect:edit";
    }

    @GetMapping("/edit")
    public String edit(Model model) {
        model.addAttribute("role", new Role()); //nowa rola, którą chce dostać uzytkownik serwisu
        System.out.println("Jestem w GetMapping /edit");
        personService.update_user_role_if_exists();
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //System.out.println("Twoj login to: "+auth.getPrincipal().toString());
        return "edit";
    }
    //stara - dzialajaca metoda edit
    /**/
    /*
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
        else if (role.getName().equals("organizator")) {
            Organizer organizer = new Organizer();
            model.addAttribute("organizer", organizer);
            organizer.setPerson(p);
            organizerRepository.save(organizer);
//            organizerPersonConnectionRepository.save(new OrganizerPersonConnection(organizer, p));
            return "redirect:welcome";
        }
        else if (role.getName().equals("trener")) {
            return "redirect:editCoach";
        }
        else if(role.getName().equals("sedzia")) {
            System.out.println("Twoja rola = "+role.getName());
            return "redirect:editReferee";
        }
        else return "redirect:welcome";
    }
*/
    //nowa metoda edit
    @PostMapping("/edit")
    public String edit(@ModelAttribute Role role, Model model, BindingResult bindingResult) {
        System.out.println("Jestem w PostMapping /edit");
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        System.out.println("Chcesz zmienić rolę na: "+role.getName());
        //tutaj powinnam dodać nowa role, ale zrobie do dopiero po akceptacji przez pracownika dzialu weryfikacji!
        return "redirect:redirectToChangeYourRolePage";
       /* if(role.getName().equals("zawodnik")) {
            System.out.println("Twoja rola = "+role.getName());
            return "redirect:editPlayer";
        }
        else if (role.getName().equals("organizator")) {
            return "redirect:editOrganizer";
        }
        else if (role.getName().equals("trener")) {
            return "redirect:editCoach";
        }
        else if(role.getName().equals("sedzia")) {
            System.out.println("Twoja rola = "+role.getName());
            return "redirect:editReferee";
        }
        else return "redirect:welcome";*/
    }
    /* @GetMapping("/editOrganizer")
     public String editOrganizer(Model model) {
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         Person p = personService.findByUsername(auth.getName());
         return "redirect:/welcome";
     }*/
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
//    	System.out.println("Jestem w funkcji editPlayer @ModelAttribute");
        List<Club> listOfAvailableClubs = clubService.findAll();
        // for (Club x : listOfAvailableClubs) {
        //System.out.println("Id klubu: "+x.getId_club());
        //System.out.println("Nazwa klubu: "+x.getClubname());
        // }

//        for (Club x : listOfAvailableClubs) {
//        	System.out.println("Id klubu: "+x.getId_club());
//            System.out.println("Nazwa klubu: "+x.getClubname());
//        }
        return listOfAvailableClubs;
    }
    @PostMapping("/editPlayer")
    public String editPlayer(@ModelAttribute Player player, Model model, BindingResult bindingResult) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());


        Long x = player.getIdClub();
        Optional<Club> club = clubRepository.findById(x);

        ///!!!!!!!!!!!!!!!!!!!!
        List<Player> saved_player = playerRepository.findByIdPerson(p.getIdPerson());
        System.out.println("Znaleziono "+saved_player.size()+" zawodnikow");
        saved_player.get(0).setClub(club.get());
        saved_player.get(0).setMedExDate(player.getMedExDate());
        playerRepository.save(saved_player.get(0));
        //System.out.println("to jest club get" + club.get().getId_club());


        return "redirect:welcome";
    }

    @RequestMapping(value = "/redirectToEditCoach", method = RequestMethod.GET)
    public String redirectToEditCoach() {

        return "redirect:editCoach";
    }

    @GetMapping("/editCoach")
    public String editCoach(@ModelAttribute Coach coach, Model model) {
        model.addAttribute("coach",new Coach());

        //model.addAttribute("club", new Club());
        System.out.println("Jestem w funkcji editCoach");
        return "/editCoach";
    }
    @PostMapping("/editCoach")
    public String editCoach(@ModelAttribute Coach coach, Model model, BindingResult bindingResult) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());

        List<Coach> saved_coach = coachRepository.findByIdPerson(p.getIdPerson());

        saved_coach.get(0).setCoachlegidate(coach.getCoachlegidate());

        coachRepository.save(saved_coach.get(0));

        return "redirect:welcome";
    }
    //edycja sedziego
    @RequestMapping(value = "/redirectToEditReferee", method = RequestMethod.GET)
    public String redirectToEditReferee() {

        return "redirect:editReferee";
    }

    @GetMapping("/editReferee")
    public String editReferee(@ModelAttribute Coach referee, Model model) {
        model.addAttribute("referee",new Referee());

        //model.addAttribute("club", new Club());
        System.out.println("Jestem w funkcji editReferee");
        return "/editReferee";
    }
    @PostMapping("/editReferee")
    public String editReferee(@ModelAttribute Referee referee, Model model, BindingResult bindingResult) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());

        List<Referee> saved_referee = refereeRepository.findByIdPerson(p.getIdPerson());

        saved_referee.get(0).setRefereelegidate(referee.getRefereelegidate());

        refereeRepository.save(saved_referee.get(0));

        return "redirect:welcome";
    }



    @GetMapping("/registrationClub")
    public String clubReg(Model model){

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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //nazwa użytkownika
        Person p = personService.findByUsername(auth.getName());
        //id użytkownika
        Long x = p.getId();

//        Long id_coach = coachRepository.findCoachByPerson(p).getIdCoach();
//        Optional<Coach> coach = coachRepository.findById(id_coach);
        Coach coach = coachRepository.findCoachByPerson(p);
        clubService.save(clubForm);
        clubForm.getId_club();
        coach.setClub(clubForm);
        clubForm.setCoach(coach);

        coachRepository.saveAndFlush(coach);
        clubRepository.saveAndFlush(clubForm);
        return "redirect:/welcome";
    }


    @GetMapping("/deleteClub/{id}")
    public String deleteClub(@PathVariable("id") long id, Model model) {
        Coach coach = new Coach();
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid club Id:" + id));
        if(coach.getIdCoach() == club.getId_coach())
            clubRepository.delete(club);

        //model.addAttribute("clubs", clubRepository.findAll());
        return "redirect:/searchClubs";
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

    @RequestMapping("/searchClubKey")
    public String viewClubPage(Model model, @Param("keyword") String keyword){

        List<Club> clubList = clubRepository.findClubsByClubnameContains(keyword);
        model.addAttribute("clubList", clubList);
        return "/searchClubs";
    }

    @RequestMapping("/searchCompKey")
    public String viewCompetitionPage(Model model, @Param("keywordComp") String keywordComp){
        List<Competition> competitionList = competitionRepository.findCompetitionsByCompetitionNameContains(keywordComp);
        model.addAttribute("competitionList", competitionList);
        return "/searchCompetitions";
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
    @PostMapping("/searchCompetitions")
    public String viewCompetitionPage(){
        return "/";
    }

    @GetMapping("/searchPlayers")
    public String viewPlayersPage(Model model){
        List<Player> playersList = playerRepository.findAll();
        model.addAttribute("playersList", playersList);
        return "/searchPlayers";
    }
    @RequestMapping("/searchPlayerKey")
    public String viewPlayersPage(Model model, @Param("keywordPlayer") String keywordPlayer){
        List<Player> playersList = playerRepository.findAllByPersonUsernameContains(keywordPlayer);
        model.addAttribute("playersList", playersList);
        return "/searchPlayers";
    }

    @RequestMapping(value = "/redirectToSearchPlayersPage", method = RequestMethod.GET)
    public String redirectToSearchPlayersPage() {
        System.out.println("Redirecting Result To The Final Page");
        return "redirect:searchPlayers";
    }

    @GetMapping("registrationCompetitions")
    public String registrationCompetition(Model model){
        model.addAttribute("competitionForm", new Competition());

        return "registrationCompetitions";
    }

    @PostMapping("registrationCompetitions")
    public String registrationCompetition(@ModelAttribute("competitionForm") Competition competitionForm){
        competitionService.save(competitionForm);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //username
        Person p = personService.findByUsername(auth.getName());
        //id osoby
        Long x = p.getId();
        Long id_organizer = organizerRepository.findOrganizerByPerson(p).getId_organizer();
        Optional<Organizer> organizer = organizerRepository.findById(id_organizer);

        organizerCompetitionConnectionRepository.save(new OrganizerCompetitionConnection(competitionForm, organizer.get()));
        return "redirect:searchCompetitions";
    }



    private Long idCompetitionForPlayers;
    @RequestMapping(value = "/redirectRegCompetitorsPlayer", method = RequestMethod.GET)
    public String redirectRegCompetitorsPlayer(@Param("keyword") Long keyword) {
        System.out.println("Redirecting Result To Reg Competitors Player:" + keyword);


        Competition competition = competitionRepository.findByIdCompetitions(keyword);
        categoriesOnCompetitionRepository.save(new CategoriesOnCompetition(competition));
        return "redirect:regCompetitorsPlayer";
    }


    @GetMapping("regCompetitorsPlayer")
    public String regCompetitorsPlayer(Model model){

        model.addAttribute("regCompetitorsPlayerForm", new Player());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());
        Long x = p.getId();
        System.out.println("To jest id trenera " +x);
        Long id_coach = coachRepository.findCoachByPerson(p).getIdCoach();
        Coach coach = coachRepository.findCoachByIdCoach(id_coach);

        Club club = coach.getClub();
        List <Player> listOfPlayersInClub = playerRepository.findAllByClub(club);

        model.addAttribute("listOfPlayersInClub", listOfPlayersInClub);

        return "regCompetitorsPlayer";
    }

    @PostMapping("regCompetitorsPlayer")
    public String regCompetitorsPlayer(){

//        System.out.println("To jest reg form zawodników: " + regCompetitorsPlayerForm);


        return "redirect:/registrationCompetitorsPlayer";
    }
    //TO NIE DZIAŁA ALE MNIEJ WIĘCEJ TAK TRZEBA ZROBIĆ, może w request mappingu, może gdzieś indziej
    @PutMapping(value="/registrationCompetitorsPlayer")
    public List<Long> showNewCompetitionPage(@RequestParam("playersList") List<Long> playersList, Model model){
        //to ważne
        List <Player> players = playerRepository.findAllById(playersList);
        model.addAttribute("playerstList", playersList);
        categoriesOnCompetitionRepository.save(new CategoriesOnCompetition(players));
        //to już nieważne
        System.out.println("PlayerList: " + playersList);
        System.out.println("Players: " + players + " players z: " +players.isEmpty());

        return playersList;
    }
    @RequestMapping(value = "/registrationCompetitorsPlayer", method = RequestMethod.GET)
    public String showCompetitorsPage(){
        return "redirect:/registrationCompetitiorsPlayer";
    }

    @GetMapping("registrationCompetitiorsPlayer")
    public String registrationCompetitiorsPlayer(Model model){
        model.addAttribute("competitiorsPlayerForm", new Player());

        return "registrationCompetitiorsPlayer";
    }

    @PostMapping("registrationCompetitiorsPlayer")
    public String registrationCompetitiorsPlayer(@ModelAttribute("competitiorsPlayerForm") Player competitiorsPlayerForm){
        playerService.save(competitiorsPlayerForm);

        return "/searchCompetitions";
    }


    @RequestMapping(value = "/redirectToReferee", method = RequestMethod.GET)
    public String redirectToReferee() {
        System.out.println("Redirecting Result To Edit Referee Page");
        return "redirect:editReferee";
    }
/*
    @GetMapping("/editReferee")
    public String editReferee(Model model) {
        model.addAttribute("referee", new Referee());
        System.out.println("Jestem w funkcji editReferee");
        return "/editReferee";
    }
    @PostMapping("/editReferee")
    public String editReferee(@ModelAttribute Referee referee, Model model, BindingResult bindingResult) {
//        System.out.println("Data wygasniecia dokumentacji sędziego: " + referee.getRefereeLegDate());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());

        return "redirect:welcome";
    }
*/
    @RequestMapping(value = "/redirectChooseCompetitionForReferee", method = RequestMethod.GET)
    public String redirectToChooseCompetitions() {
        System.out.println("Redirecting Result To Judging Competitions Page");
        return "redirect:chooseCompetitionForReferee";
    }

    @ModelAttribute("listOfCompetition")
    public List<Competition> chooseCompetitions() {
        //System.out.println("Jestem w funkcji editPlayer @ModelAttribute");
        List<Competition> listOfCompetition = competitionRepository.findAll();

        return listOfCompetition;
    }
    @ModelAttribute("playerWelcomeClubname")
    public String getPlayerClubname(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());
        Role role = roleRepository.findByPerson(p);
        if(roleRepository.existsByPerson(p)) {
            if (role.getName().contains("zawodnik")) {
                if (playerRepository.existsByIdPerson(p.getId())) {
                    Player player = playerRepository.findPlayerByPerson(p);

                    if (player.getClubname() != null) {
                        if (player.getClubname().contains("Brak klubu")) {
                            return "Musisz wybrać swój klub!";
                        }
                        String cbnm = player.getClubname();
                        return cbnm;
                    } else {
                        return "Musisz wybrać swój klub!";
                    }

                }
                return "Musisz wysłać dokumentacje aby dostać uprawnienia";
            }
        }

        return "Musisz wysłać dokumentacje aby dostać uprawnienia";
    }
    @ModelAttribute("coachWelcomeClubname")
    public String getCoachClubname(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());
        Role role = roleRepository.findByPerson(p);
        if(roleRepository.existsByPerson(p)) {
            if (role.getName().contains("trener")) {
                System.out.println("Nie weszłeś");
                Coach coach = coachRepository.findCoachByPerson(p);
                if (clubRepository.existsClubByCoach(coach)) {
                    return coach.getClub().getClubname();
                }
                return "Stwórz klub!";
            }
        }

        return "Musisz wysłać dokumentacje aby dostać uprawnienia";
    }
    @ModelAttribute("coachWelcomeMedExDate")
    public String getCoachMedExDate(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());
        Role role = roleRepository.findByPerson(p);
        if(roleRepository.existsByPerson(p)) {
            if (role.getName().contains("trener")) {
                System.out.println("Nie weszłeś");
                Coach coach = coachRepository.findCoachByPerson(p);
                if (coach.getCoachlegidate()!=null) {
                    return coach.getCoachlegidate().toString();
                }
                return "Edytuj date wygaśnięcia dokumentacji";
            }
        }

        return "Musisz wysłać dokumentacje aby dostać uprawnienia";
    }
    @ModelAttribute("refereeWelcomeMedExDate")
    public String getRefereeMedExDate(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());
        Role role = roleRepository.findByPerson(p);
        if(roleRepository.existsByPerson(p)) {
            if (role.getName().contains("sedzia")) {
                System.out.println("Nie weszłeś");
                Referee referee = refereeRepository.findRefereeByPerson(p);
                if (referee.getRefereelegidate()!=null) {
                    return referee.getRefereelegidate().toString();
                }
                return "Edytuj date wygaśnięcia dokumentacji";
            }
        }

        return "Musisz wysłać dokumentacje aby dostać uprawnienia";
    }

    @ModelAttribute("playersListInCoachClub")
    public List<Player> getPlayersInCoachClub(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());
        Role role = roleRepository.findByPerson(p);
        if(roleRepository.existsByPerson(p)) {
            if (role.getName().contains("trener")) {
                System.out.println("Nie weszłeś");
                Coach coach = coachRepository.findCoachByPerson(p);
                if (clubRepository.existsClubByCoach(coach)) {
                    List<Player> playersListInCoachClub = playerRepository.findPlayersByClubCoach(coach);
                    return playersListInCoachClub;
                }
            }
        }


        return null;
    }

    @ModelAttribute("messagesForUser")
    public List<Message> getMyMessage() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());

        List<Message> messages = new ArrayList<>();

        if (securityService.isAuthenticated()) {
            if(messageService.findByPersonId(p.getIdPerson()).isEmpty()){ messageService.addMessage(); }
            else{
                messages = messageService.findByPersonId(p.getIdPerson());
                if(messages.size()>1){
                    messages.remove(0); //usuwam pierwsza wiadomosc, ktora mowi ze nie ma zadnych wiadomosci
                }
            }
        }
        return messages;
    }
    @GetMapping("/chooseCompetitionForReferee")
    public String chooseCompetitions(Model model){
        model.addAttribute("competitionForm", new Competition());
        return "/chooseCompetitionForReferee";
    }
    private Long idRefereeOnCompetition;
    @PostMapping("/chooseCompetitionForReferee")
    public String chooseCompetitions(@ModelAttribute Competition competitionForm){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());
        Long x = p.getId();
        System.out.println("To jest id sedziego " +x);
        Long id_referee = refereeRepository.findRefereeByPerson(p).getIdReferee();
        Optional<Referee> referee = refereeRepository.findById(id_referee);
        RefereeRoleOnCompetition refereeRoleOnCompetition = new RefereeRoleOnCompetition(referee.get(), competitionForm);
        //zapisuje referee role on competition z id sędziego i id zawodów
        refereeRoleOnCompetitionRepository.save(refereeRoleOnCompetition);
        //pobieram id rekordu referee role on competition
        idRefereeOnCompetition = refereeRoleOnCompetitionRepository.save(refereeRoleOnCompetition).getId();
        System.out.println("To jest id roli sędziego na zawodach: " + idRefereeOnCompetition);
        return "redirect:judgingCompetitions";
    }
    @RequestMapping(value = "/redirectToJudgingCompetitions", method = RequestMethod.GET)
    public String redirectToJudgingCompetitions() {
        System.out.println("Redirecting Result To Judging Competitions Page");
        return "redirect:/judgingCompetitions";
    }

    @ModelAttribute("listOfRefereeRoles")
    public List<RefereeRoles> judgingCompetitions() {

        List<RefereeRoles> listOfRefereeRoles = refereeRolesService.findAll();

        return listOfRefereeRoles;
    }

    @GetMapping("/judgingCompetitions")
    public String judgingCompetitions(Model model){
        model.addAttribute("refereeRolesForm", new RefereeRoles());

        return "/judgingCompetitions";
    }
    @PostMapping("/judgingCompetitions")
    public String judgingCompetitions(@ModelAttribute("refereeRolesForm") RefereeRoles refereeRolesForm){
        System.out.println("Jestem w funkcji judgingCompetitions @ModelAttribute");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());
        Long x = p.getId();
        System.out.println("To jest id sedziego " +x);
        Long id_referee = refereeRepository.findRefereeByPerson(p).getIdReferee();
        Optional<Referee> referee = refereeRepository.findById(id_referee);

        System.out.println("To jest id roli sędziego na zawodach: " + idRefereeOnCompetition);
        //edycja rekordu referee role on competition z poprzedniej karty
        RefereeRoleOnCompetition refereeRoleOnCompetition = refereeRoleOnCompetitionRepository.getOne(idRefereeOnCompetition);
        //ustawiam id referee roli w rokordzie
        refereeRoleOnCompetition.setRefereeRoles(refereeRolesForm);
        //update rekordu
        refereeRoleOnCompetitionRepository.saveAndFlush(refereeRoleOnCompetition);
        return "redirect:/welcome";
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


    @RequestMapping(value = "/redirectToVerificationMedicalExaminations", method = RequestMethod.GET)
    public String redirectToVerificationMedicalExaminations() {
        System.out.println("Redirecting Result ToVerificationMedicalExaminations Page");
        return "redirect:/verificationMedicalExaminations";
    }

    @GetMapping("/verificationMedicalExaminations")
    public String verificationMedicalExaminations(Model model) {
        return "verificationMedicalExaminations";
    }

    @PostMapping("/verificationMedicalExaminations")
    public String verificationMedicalExaminations() {
        return "/verificationMedicalExaminations";
    }

    @ModelAttribute("listOfRequests")
    public List<Verification> showUsers() {
        //System.out.println("Jestem w funkcji showUsers @ModelAttribute");
        List<Verification> listOfRequests = verificationService.findAll();
        for (Verification x : listOfRequests) {
            //System.out.println("Id weryfikacji: "+x.getId_verification());
            Optional <Person> p = personRepository.findById(x.getIdPerson());
            //System.out.println("Username: "+p.get().getUsername());
            //System.out.println("Nowa rola: "+x.getNew_role());
        }
        return listOfRequests;
    }
    //usuniecie wiadomosci
    @GetMapping("/deleteMessage/{id_message}")
    public String deleteMyMessage(@PathVariable(name = "id_message") Long id, Model model){
        System.out.println("Jestem w /deleteMessage/{id_message}\n\n\n");
        System.out.println("Usuwam ta wiadomosc");
        Optional <Message> message = messageRepository.findById(id);
        if(message==null) System.out.println("Nie znaleziono takiej wiadomosci");
        else {
            messageRepository.deleteById(message.get().getIdMessage());
        }
        return "redirect:/welcome";
    }

    //akceptacja weryfikacji

    @GetMapping("/acceptVerifyForm/{id_verification}")
    public String acceptVerifyForm(@PathVariable(name = "id_verification") Long id, Model model) {

        System.out.println("Jestem w /acceptVerifyForm/{id_verification}\n\n\n");

        Optional <Verification> verification = verificationRepository.findById(id);
        model.addAttribute("verification", verification.get());

        Long id_person = verification.get().getIdPerson();

        System.out.println("Id person, verification.getIdPerson() = "+id_person);
        Optional <Person> p = personRepository.findById(id_person);
        if(p==null) System.out.println("Nie znaleziono takiej osoby!");
        else System.out.println("Znaleziono osobe z takim id, username = "+p.get().getUsername());
        String r = verification.get().getNew_role();
        System.out.println("Nowa rola: "+r);
        Set<Role> roles = p.get().getRoles();
        Role role = new Role();
        System.out.println("Utworzono nowa role Role role = new Role()");
        role.setName(r);
        System.out.println("Ustawiono nazwe roli na: "+role.getName());
        roles.add(role);
        System.out.println("Dodano nowa role do zbioru rol");
        personService.add_role(p.get(), role);
        role.setPerson(p.get());
        roleRepository.save(role);
        System.out.println("Dodano nowa role do serwisu");
        //p.get().setRoles(roles);

        if(role.getName().equals("zawodnik")) {
            Player player = new Player();
            player.setPerson(p.get());
            player.setIdPerson(id_person);

            //Ustawiam brak klubu jako klub
            Optional<Club> club = clubRepository.findById(Integer.toUnsignedLong(1));
            player.setClub(club.get());

            playerRepository.save(player);

        }
        else if (role.getName().equals("organizator")) {

            Organizer organizer = new Organizer();

            organizer.setPerson(p.get());
            organizerRepository.save(organizer);

        }
        else if (role.getName().equals("trener")) {

            Coach coach = new Coach();
            coach.setPerson(p.get());
            coach.setIdPerson(id_person);
            coachRepository.save(coach);

        }
        else if(role.getName().equals("sedzia")) {

            Referee referee = new Referee();
            referee.setPerson(p.get());
            referee.setIdPerson(id_person);
            refereeRepository.save(referee);
        }


        Message message = new Message();
        message.setIdPerson(verification.get().getIdPerson());
        message.setContent("Twoja rola zostala zmieniona na: "+role.getName()+"\n");
        messageRepository.save(message);
        System.out.println("Zapiano wiadomosc do uzytkownika");

        usun_zadanie(id);
        //System.out.println("Usuwam to zadanie");
        //verificationRepository.deleteById(verification.get().getId_verification());

        return "redirect:/welcome";
    }
    @PostMapping("/deleteVerification")
    public String acceptVerifyForm(@ModelAttribute Verification verification, Model model, BindingResult bindingResult){
        System.out.println("Jestem w postMapping acceptVerifyForm");
        return "redirect:/welcome";
    }
    public void usun_zadanie(Long id){
        Optional <Verification> verification = verificationRepository.findById(id);
        if(verification==null) System.out.println("Nie znaleziono takiej weryfikacji");
        else {
            System.out.println("Znaleziono taka weryfikacje: "+verification.get().getId_verification());
            verificationRepository.deleteById(verification.get().getId_verification());
        }
    }

    //odrzucanie weryfikacji
    @GetMapping("/rejectVerifyForm/{id_verification}")
    public String rejectVerifyForm(@PathVariable(name = "id_verification") Long id, Model model) {

        System.out.println("Jestem w /rejectVerifyForm/{id_verification}\n\n\n");
        System.out.println("Usuwam to zadanie");
        Optional <Verification> verification = verificationRepository.findById(id);
        if(verification==null) System.out.println("Nie znaleziono takiej weryfikacji");
        else {
            System.out.println("Znaleziono taka weryfikacje: "+verification.get().getId_verification());
            Message message = new Message();
            message.setIdPerson(verification.get().getIdPerson());
            message.setContent("Twoja prosba zmiany roli zostala odrzucona \n");
            messageRepository.save(message);

            verificationRepository.deleteById(verification.get().getId_verification());

        }
        return "redirect:/welcome";
    }
/*
    @GetMapping("/acceptVerifyForm")
    public String acceptVerifyForm(Model model) {
        System.out.println("Jestem w funkcji acceptVerifyForm /GetMapping");
        model.addAttribute("verification", new Verification());
        System.out.println("Tu jeszcze jest ok");
        return "/acceptVerifyForm";
    }
    @PostMapping("/acceptVerifyForm")
    public String acceptVerifyForm(@ModelAttribute Verification verification, Model model, BindingResult bindingResult) {
        System.out.println("Jestem w funkcji acceptVerifyForm /PostMapping");
        Long id_person = verification.getIdPerson();
        System.out.println("Id person, verification.getIdPerson() = "+id_person);
        Optional <Person> p = personRepository.findById(id_person);
        String r = verification.getNew_role();
        Set<Role> roles = null;
        Role role = new Role();
        role.setName(r);
        roles.add(role);
        p.get().setRoles(roles);
        return "redirect:/welcome";
    }
*/



    @GetMapping("/denyVerifyForm")
    public String denyVerification(Model model) {
        return "/denyVerifyForm";
    }
    @PostMapping("/denyVerifyForm")
    public String denyVerification() {
        return "redirect:/welcome";
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            storageService.store(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        FileDB fileDB = storageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }
    /*@GetMapping("/doUpload")
    public String handleFileUpload(Model model){
        System.out.println("Jestem w doUpload GetMapping");
        return "/doUpload";
    }*/
    @RequestMapping(value = "/doUpload", method = RequestMethod.POST)
    public String handleFileUpload(HttpServletRequest request,
                                   @RequestParam CommonsMultipartFile[] fileUpload, Model model) throws Exception {
        System.out.println("\n\nJestem w /doUpload");
        if (fileUpload != null && fileUpload.length > 0) {
            for (CommonsMultipartFile aFile : fileUpload){

                System.out.println("Saving file: " + aFile.getOriginalFilename());

                UploadFile uploadFile = new UploadFile();
                uploadFile.setFileName(aFile.getOriginalFilename());
                uploadFile.setData(aFile.getBytes());
                fileUploadDao.save(uploadFile);
            }
        }
        String message = "Pomyslnie przeslano dokumentacje";
        model.addAttribute("message",message);
        return "redirect:/welcome";
    }

}
