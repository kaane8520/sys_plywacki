package com.my_app.sys_plywacki.web;

import com.my_app.sys_plywacki.model.*;
import com.my_app.sys_plywacki.repository.RefereeRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.my_app.sys_plywacki.service.*;
import com.my_app.sys_plywacki.repository.*;
import org.springframework.security.core.Authentication;

import java.nio.file.Path;
import java.nio.file.Paths;
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
    private ClubPlayerConnectionRepository clubPlayerConnectionRepository;
    @Autowired
    private PlayerPersonConnectionRepository playerPersonConnectionRepository;

    @Autowired
    private OrganizerCompetitionConnectionRepository organizerCompetitionConnectionRepository;
    @Autowired
    private OrganizerRepository organizerRepository;

    @Autowired
    private OrganizerPersonConnectionRepository organizerPersonConnectionRepository;

    @Autowired
    private CoachPersonConnectionRepository coachPersonConnectionRepository;
    @Autowired
    private RefereePersonConnectionRepository refereePersonConnectionRepository;

    @Autowired
    private RefereeRolesRepository refereeRolesRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RefereeRoleOnCompetitionRepository refereeRoleOnCompetitionRepository;

    @Autowired
    private RefereeRolesService refereeRolesService;

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private VerificationService verificationService;


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
        String message = "Żądanie zmiany roli zostało wysłane";
        model.addAttribute("message",message);
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
            organizerRepository.save(organizer);
            organizerPersonConnectionRepository.save(new OrganizerPersonConnection(organizer, p));
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
    @GetMapping("/editOrganizer")
    public String editOrganizer(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());
        Organizer organizer = new Organizer();
        model.addAttribute("organizer", organizer);
        organizerRepository.save(organizer);
        organizerPersonConnectionRepository.save(new OrganizerPersonConnection(organizer, p));

        return "redirect:/welcome";
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
        	//System.out.println("Id klubu: "+x.getId_club());
            //System.out.println("Nazwa klubu: "+x.getClubname());
        }
        return listOfAvailableClubs;
     }
    @PostMapping("/editPlayer")
    public String editPlayer(@ModelAttribute Player player, Model model, BindingResult bindingResult) {
    	System.out.println("Data wygasniecia dokumentacji zawodnika: "+player.getMedExDate());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());
        //System.out.println("to jest person id"+p.getId());
        //System.out.println("to jest player id"+player.getIdPlayer());
        //System.out.println("to jest model atrybut "+model.getAttribute("player.wartosc"));

        playerRepository.save(player);
        Long x = player.getIdClub();
        Optional<Club> club = clubRepository.findById(x);
        //System.out.println("to jest club get" + club.get().getId_club());

//Tutaj trzeba wyciągnąć klub do którego sie zapisał zawododnik, żeby zapisać do bd
        clubPlayerConnectionRepository.save(new ClubPlayerConnection(club.get(), player));

        playerPersonConnectionRepository.save(new PlayerPersonConnection(player, p));
        System.out.println("to jest player id"+player.getIdPlayer());
    	return "redirect:welcome";
    }

    @RequestMapping(value = "/redirectToEditCoach", method = RequestMethod.GET)
    public String redirectToEditCoach() {
        System.out.println("Redirecting Result To Edit CoachPage");
        return "redirect:editCoach";
    }

    @GetMapping("/editCoach")
    public String editCoach(@ModelAttribute Club club, Model model) {
        model.addAttribute("coach",new Coach());

        model.addAttribute("club", new Club());
        System.out.println("Jestem w funkcji editCoach");
        return "/editCoach";
    }
    @PostMapping("/editCoach")
    public String editCoach(@ModelAttribute Coach coach, Model model, BindingResult bindingResult) {
        System.out.println("Data wygasniecia dokumentacji zawodnika: "+coach.getCoachlegidate());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());


        coachRepository.save(coach);
        coachPersonConnectionRepository.save(new CoachPersonConnection(coach, p));
        return "redirect:registrationClub";
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
        System.out.println("Jestem w PostMapping /clubRegistration");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //nazwa użytkownika
        Person p = personService.findByUsername(auth.getName());
        //id użytkownika
        Long x = p.getId();

        System.out.println("Id połączenia: " + coachPersonConnectionRepository.findByPerson(p).getIdcoach());


        Long id_coach = coachPersonConnectionRepository.findByPerson(p).getIdcoach();
        Optional<Coach> coach = coachRepository.findById(id_coach);

        clubService.save(clubForm);
        clubForm.getId_club();
        coach.get().setClub(clubForm);
        clubForm.setCoach(coach.get());

        coachRepository.saveAndFlush(coach.get());
        clubRepository.saveAndFlush(clubForm);
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

        return "/searchPlayers";
    }
    @ModelAttribute("listOfPlayers")
    public List<PlayerPersonConnection> viewPlayersPage() {

        List<PlayerPersonConnection> listOfPlayers = playerPersonConnectionRepository.findAll();

        return listOfPlayers;
    }
    @ModelAttribute("listOfClubs")
    public List<ClubPlayerConnection> viewPlayersPageC() {

        List<ClubPlayerConnection> listOfClubs = clubPlayerConnectionRepository.findAll();

        return listOfClubs;
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
        Long id_organizer = organizerPersonConnectionRepository.findByPerson(p).getIdorganizer();
        Optional<Organizer> organizer = organizerRepository.findById(id_organizer);

        organizerCompetitionConnectionRepository.save(new OrganizerCompetitionConnection(competitionForm, organizer.get()));
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


    @RequestMapping(value = "/redirectToReferee", method = RequestMethod.GET)
    public String redirectToReferee() {
        System.out.println("Redirecting Result To Edit Referee Page");
        return "redirect:editReferee";
    }

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
        refereeRepository.save(referee);
        refereePersonConnectionRepository.save(new RefereePersonConnection(referee, p));

        return "redirect:welcome";
    }

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
/*
    @ModelAttribute("messagesForUser")
    public List<Message> getMyMessage() {
        String content = "Brak nowych wiadomosci";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());

        List<Message> messages = new ArrayList<>();
        messages.add(new Message(content));

        if(messageService.findByPersonId(p.getIdPerson()) != null){
            messages.clear();
            messages = messageService.findByPersonId(p.getIdPerson());
        }

        return messages;
    }
*/
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
        Long id_referee = refereePersonConnectionRepository.findByPerson(p).getIdReferee();
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
        Long id_referee = refereePersonConnectionRepository.findByPerson(p).getIdReferee();
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
        return "redirect:/welcome";
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
        System.out.println("Dodano nowa role do serwisu");
        p.get().setRoles(roles);

        Message message = new Message();
        message.setIdPerson(verification.get().getIdPerson());
        message.setContent("Twoja rola zostala zmieniona na: "+role.getName());
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
        else System.out.println("Znaleziono taka weryfikacje: "+verification.get().getId_verification());


        Message message = new Message();
        message.setIdPerson(verification.get().getIdPerson());
        message.setContent("Twoja prosba zmiany roli zostala odrzucona");
        messageRepository.save(message);

        verificationRepository.deleteById(verification.get().getId_verification());

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

