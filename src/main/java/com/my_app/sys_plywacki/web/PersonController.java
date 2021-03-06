package com.my_app.sys_plywacki.web;

import com.my_app.sys_plywacki.model.*;
import com.my_app.sys_plywacki.repository.RefereeRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.my_app.sys_plywacki.service.*;
import com.my_app.sys_plywacki.repository.*;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.http.ResponseEntity;


@Controller
@SessionAttributes({"verification","acceptedRegistrationForCompetition"})
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
    private FileRepository fileRepository;

    @Autowired
    FileService fileService;

    @Autowired
    private MessageService messageService;

    private List <Club> clubs;


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
    private DisqualificationRepository disqualificationRepository;

    @Autowired
    private DisqualificationService disqualificationService;

    @Autowired
    private RegistrationForCompetitionRepository registrationForCompetitionRepository;

    @Autowired
    private AcceptedRegistrationForCompetitionRepository acceptedRegistrationForCompetitionRepository;

    @Autowired
    private ResultRepository resultRepository;

    List<Player> listOfPlayers = new ArrayList<>();
    List<RegistrationForCompetition> listOfRegForCompetitions = new ArrayList<>();
    List<AcceptedRegistrationForCompetition> listOfAcceptedRegistrationForCompetition = new ArrayList<>();

    boolean messageSent = false;

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
        listOfPlayers.clear();
        listOfRegForCompetitions.clear();
        messageSent = false;

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
        if (!securityService.isAuthenticated()) {
            System.out.println("Not authenticated!");
            return "redirect:/login";
        }
        personService.update_user_role_if_exists();
        if(refereeRolesRepository.findAll().isEmpty()) {
            refereeRolesService.addRoles();
        }
        if(categoriesRepository.findAll().isEmpty()){
            categoriesService.addCategories();
        }
        if(disqualificationRepository.findAll().isEmpty()){
            disqualificationService.addDisqualifications();
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
        return "/changeYourRole";
    }
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

        if(role.getName().equals("zawodnik") || role.getName().equals("sedzia") || role.getName().equals("trener")){
            return "redirect:addDocumentation";
        }
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

    //edycja zawodnika przez moderatora: zmiana daty wygaśnięcia dokumentacji:
    @RequestMapping(value = "/redirectToEditPlayerByModerator", method = RequestMethod.GET)
    public String redirectToEditPlayerByModerator() {
        System.out.println("Redirecting Result To Edit Player Page");
        return "redirect:editPlayerByModerator";
    }
    @GetMapping("/editPlayerByModerator")
    public String editPlayerByModerator(Model model) {
        model.addAttribute("player",new Player());
        System.out.println("Jestem w funkcji editPlayer");
        return "/editPlayerByModerator";
    }
    @PostMapping("/editPlayerByModerator")
    public String editPlayerByModerator(@SessionAttribute("verification") Verification verification,@ModelAttribute Player player, Model model, BindingResult bindingResult) {

        System.out.println("verification.getIdPerson = "+verification.getIdPerson());
        List<Player> saved_player = playerRepository.findByIdPerson(verification.getIdPerson());
        saved_player.get(0).setMedExDate(player.getMedExDate());
        System.out.println("Id person: "+player.getIdPerson());
        System.out.println("Data wygasniecia dokumentacji: "+player.getMedExDate());
        playerRepository.save(saved_player.get(0));

        usun_zadanie(verification.getId_verification());

        return "redirect:welcome";
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
        System.out.println("Dokumentacja medyczna: "+saved_player.get(0).getMedExDate());
        saved_player.get(0).setClub(club.get());
        saved_player.get(0).setMedExDate(saved_player.get(0).getMedExDate());
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
    public String editCoach(@SessionAttribute("verification") Verification verification,@ModelAttribute Coach coach, Model model, BindingResult bindingResult) {

        System.out.println("verification.getIdPerson = "+verification.getIdPerson());
        List<Coach> saved_coach = coachRepository.findByIdPerson(verification.getIdPerson());
        saved_coach.get(0).setCoachlegidate(coach.getCoachlegidate());
        System.out.println("Id person: "+coach.getIdPerson());
        System.out.println("Data wygasniecia dokumentacji: "+coach.getCoachlegidate());

        if(verification.getOldRole().equals(verification.getNew_role())){

        }
        coachRepository.save(saved_coach.get(0));

        usun_zadanie(verification.getId_verification());

        return "redirect:welcome";
    }

    @GetMapping(value="/redirectToEditReferee")
    public String redirectToEditReferee(){
        return "redirect:/editReferee";
    }

    @GetMapping("/editReferee")
    public String editReferee(Model model, Referee referee){
        return "/editReferee";
    }
    @PostMapping("/editReferee")
    public String editReferee(@SessionAttribute("verification") Verification verification, @ModelAttribute Referee referee, Model model, BindingResult bindingResult) {
        System.out.println("verification.getIdPerson = "+verification.getIdPerson());
        List<Referee> saved_referee = refereeRepository.findByIdPerson(verification.getIdPerson());
        saved_referee.get(0).setRefereelegidate(referee.getRefereelegidate());
        System.out.println("Id person: "+referee.getIdPerson());
        System.out.println("Data wygasniecia dokumentacji: "+referee.getRefereelegidate());
        refereeRepository.save(saved_referee.get(0));

        usun_zadanie(verification.getId_verification());

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

        Organizer organizer = organizerRepository.findOrganizerByPerson(p);
        competitionForm.setOrganizer(organizer);

        competitionRepository.save(competitionForm);
        return "redirect:searchCompetitions";
    }




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

                Coach coach = coachRepository.findCoachByPerson(p);
                if (clubRepository.existsClubByCoach(coach)) {
                    return coach.getClub().getClubname();
                }
                return "Stwórz klub!";
            }
        }

        return "Musisz wysłać dokumentacje aby dostać uprawnienia";
    }
    @ModelAttribute("playerWelcomeMedExDate")
    public String getPlayerMedExDate(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());
        Role role = roleRepository.findByPerson(p);
        if(roleRepository.existsByPerson(p)) {
            if (role.getName().contains("zawodnik")) {

                List<Player> player = playerRepository.findByIdPerson(p.getIdPerson());
                if (player.get(0).getMedExDate()!=null) {

                    if(!messageSent) {
                        LocalDate now = LocalDate.now();
                        LocalDate medExDate = player.get(0).getMedExDate();
                        //LocalDate toCompare = LocalDate.of(medExDate.getYear(),medExDate.getMonth().minus(1),medExDate.getDayOfMonth());
                        LocalDate toCompare = medExDate.minusMonths(1);

                        System.out.println("Data wygasniecia dokumentacji: "+medExDate);
                        System.out.println("Wsyslam wiadomosc od: "+toCompare);
                        if(now.isAfter(medExDate)){
                            Message message = new Message();
                            message.setIdPerson(p.getIdPerson());
                            message.setContent(now.toString()+": TWOJA DOKUMENTACJA WYGASLA!");
                            messageRepository.save(message);
                            System.out.println("Zapiano wiadomosc do uzytkownika");
                            messageSent = true;
                        }
                        else if(now.isAfter(toCompare)||now.equals(toCompare)){
                            Message message = new Message();
                            message.setIdPerson(p.getIdPerson());
                            message.setContent(now.toString()+": Data wygasniecia Twojej dokumentacji jest mniejsza niz 1 miesiac: ");
                            messageRepository.save(message);
                            System.out.println("Zapiano wiadomosc do uzytkownika");
                            messageSent = true;
                        }
                    }

                    return player.get(0).getMedExDate().toString();
                }
                return "Problem z dokumentacja";
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

                Coach coach = coachRepository.findCoachByPerson(p);
                if (coach.getCoachlegidate()!=null) {

                    if(!messageSent) {
                        LocalDate now = LocalDate.now();
                        LocalDate medExDate = coach.getCoachlegidate();
                        //LocalDate toCompare = LocalDate.of(medExDate.getYear(),medExDate.getMonth().minus(1),medExDate.getDayOfMonth());
                        LocalDate toCompare = medExDate.minusMonths(1);
                        System.out.println("Data wygasniecia dokumentacji: "+medExDate);
                        System.out.println("Wsyslam wiadomosc od: "+toCompare);
                        if(now.isAfter(medExDate)){
                            Message message = new Message();
                            message.setIdPerson(p.getIdPerson());
                            message.setContent(now.toString()+": TWOJA DOKUMENTACJA WYGASLA!");
                            messageRepository.save(message);
                            System.out.println("Zapiano wiadomosc do uzytkownika");
                            messageSent = true;
                        }
                        else if(now.isAfter(toCompare)||now.equals(toCompare)){
                            Message message = new Message();
                            message.setIdPerson(p.getIdPerson());
                            message.setContent(now.toString()+": Data wygasniecia Twojej dokumentacji jest mniejsza niz 1 miesiac: ");
                            messageRepository.save(message);
                            System.out.println("Zapiano wiadomosc do uzytkownika");
                            messageSent = true;
                        }
                    }

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

                Referee referee = refereeRepository.findRefereeByPerson(p);
                if (referee.getRefereelegidate()!=null) {
                    //Sprawdzam date wygaśnięcia dokumentacji, jeśli wygaśnie za mniej niż miesiąc
                    //wysyłam wiadomość dla sędziego
                    if(!messageSent) {
                        LocalDate now = LocalDate.now();
                        LocalDate medExDate = referee.getRefereelegidate();
                        //LocalDate toCompare = LocalDate.of(medExDate.getYear(),medExDate.getMonth().minus(1),medExDate.getDayOfMonth());
                        LocalDate toCompare = medExDate.minusMonths(1);

                        System.out.println("Data wygasniecia dokumentacji: "+medExDate);
                        System.out.println("Wsyslam wiadomosc od: "+toCompare);
                        if(now.isAfter(medExDate)){
                            Message message = new Message();
                            message.setIdPerson(p.getIdPerson());
                            message.setContent(now.toString()+": TWOJA DOKUMENTACJA WYGASLA!");
                            messageRepository.save(message);
                            System.out.println("Zapiano wiadomosc do uzytkownika");
                            messageSent = true;
                        }
                        else if(now.isAfter(toCompare)||now.equals(toCompare)){
                            Message message = new Message();
                            message.setIdPerson(p.getIdPerson());
                            message.setContent(now.toString()+": Data wygasniecia Twojej dokumentacji jest mniejsza niz 1 miesiac: ");
                            messageRepository.save(message);
                            System.out.println("Zapiano wiadomosc do uzytkownika");
                            messageSent = true;
                        }

                    }
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
        if(verification.get().getOldRole().equals(verification.get().getNew_role())){
            System.out.println("Chcesz zmienic role na taka sama");
        }else {
            System.out.println("Nowa rola: " + r);
            Set<Role> roles = p.get().getRoles();
            Role role = new Role();
            System.out.println("Utworzono nowa role Role role = new Role()");
            role.setName(r);
            System.out.println("Ustawiono nazwe roli na: " + role.getName());
            roles.add(role);
            System.out.println("Dodano nowa role do zbioru rol");
            personService.add_role(p.get(), role);
            role.setPerson(p.get());
            roleRepository.save(role);
            System.out.println("Dodano nowa role do serwisu");


            if(verification.get().getNew_role().equals("zawodnik")) {
                Player player = new Player();
                player.setPerson(p.get());
                player.setIdPerson(id_person);

                //Ustawiam brak klubu jako klub
                Optional<Club> club = clubRepository.findById(Integer.toUnsignedLong(1));
                player.setClub(club.get());

                playerRepository.save(player);

            }
            else if (verification.get().getNew_role().equals("organizator")) {

                Organizer organizer = new Organizer();

                organizer.setPerson(p.get());
                organizerRepository.save(organizer);

            }
            else if (verification.get().getNew_role().equals("trener")) {

                Coach coach = new Coach();
                coach.setPerson(p.get());
                coach.setIdPerson(id_person);
                coachRepository.save(coach);

            }
            else if(verification.get().getNew_role().equals("sedzia")) {

                Referee referee = new Referee();
                referee.setPerson(p.get());
                referee.setIdPerson(id_person);
                refereeRepository.save(referee);
            }

        }
        //p.get().setRoles(roles);




        Message message = new Message();
        message.setIdPerson(verification.get().getIdPerson());
        message.setContent("Twoja rola zostala zmieniona na: "+verification.get().getNew_role()+"\n");
        messageRepository.save(message);
        System.out.println("Zapiano wiadomosc do uzytkownika");


        //System.out.println("Usuwam to zadanie");
        //verificationRepository.deleteById(verification.get().getId_verification());


        if(verification.get().getNew_role().equals("zawodnik")){
            return "redirect:/redirectToEditPlayerByModerator";
        }
        if(verification.get().getNew_role().equals("trener")){
            return "redirect:/redirectToEditCoach";
        }
        if(verification.get().getNew_role().equals("sedzia")){
            //return "redirect:/redirectToEditReferee/"+verification.get().getIdPerson();
            return "redirect:/redirectToEditReferee";
        } else {
            usun_zadanie(id);
            return "redirect:/verificationMedicalExaminations";
        }
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
    public String generateRandomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@SessionAttribute("verification") Verification verification, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());

        File f = new File();

        String fileName =  p.getIdPerson().toString()+"_"+generateRandomString()+"_"+file.getOriginalFilename();
        f.setIdPerson(p.getIdPerson());
        f.setName(fileName);
        f.setContent(file.getBytes());

        fileService.uploadFile(file,fileName);
        fileRepository.save(f);
        Message message = new Message();
        String content = "Plik: " + f.getName() + " zostal poprawnie przeslany!";
        message.setContent(content);
        message.setIdPerson(p.getIdPerson());
        messageRepository.save(message);

        List <Verification> saved_verification = verificationService.findByIdPerson(p.getIdPerson());
        System.out.println("Znaleziono "+saved_verification.size()+" weryfikacji");
        saved_verification.get(0).setIdFile(f.getIdFile());
        saved_verification.get(0).setFileName(f.getName());
        verificationRepository.save(saved_verification.get(0));

        return "redirect:/";
    }
    @GetMapping("/downloadFile/{filename}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String filename) {
        File file = fileRepository.findByName(filename);
        System.out.println("Filename = "+filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"").body(file.getContent());
    }
    @ModelAttribute("verification")
    public Verification getVerification() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(personService.findByUsername(auth.getName()) == null){
            Verification verification = new Verification();
            return verification;
        } else {
            Person p = personService.findByUsername(auth.getName());
            System.out.println("Person "+p.getUsername());
            if (verificationRepository.findByIdPerson(p.getIdPerson()).isEmpty()){
                Verification verification = new Verification();
                return verification;
            } else {
                List<Verification> verification = verificationRepository.findByIdPerson(p.getIdPerson());
                return verification.get(0);
            }
        }
    }


    @RequestMapping(value = "/redirectToOrganizerCompetitionView", method = RequestMethod.GET)
    public String redirectToOrganizerCompetitionView() {

        return "redirect:/organizerCompetitionView";
    }

    @GetMapping("/organizerCompetitionView")
    public String organizerCompetitionView(Model model) {
        return "/organizerCompetitionView";
    }


    @ModelAttribute("organizerCompetitionList")
    public List<Competition> getOrganizerCompetitions(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());
        Role role = roleRepository.findByPerson(p);
        if(roleRepository.existsByPerson(p)) {
            if (role.getName().contains("organizator")) {
                System.out.println("Nie weszłeś");
                Organizer organizer = organizerRepository.findOrganizerByPerson(p);
                if (competitionRepository.existsCompetitionByOrganizer(organizer)) {
                    List<Competition> organizerCompetitionList = competitionRepository.findCompetitionsByOrganizer(organizer);
                    return organizerCompetitionList;
                }
            }
        }
        return null;
    }

    @ModelAttribute("listOfDisqualifiacations")
    public List<Disqualification>chooseDis(){
        List<Disqualification> listOfDisqualifiacations = disqualificationRepository.findAll();
        return listOfDisqualifiacations;
    }

    @ModelAttribute("listOfCategoriesOnCompanies")
    public List<CategoriesOnCompetition>chooseCOM(){
        List<CategoriesOnCompetition> listOfCategoriesOnCompanies = categoriesOnCompetitionRepository.findAll();
        return listOfCategoriesOnCompanies;
    }

    Long compId;
    @RequestMapping(value="/redirectToInsertResults", method=RequestMethod.GET)
    public String redirectToInsertResults(@Param("keyword") Long keyword, Model model){

        compId = keyword;

        return "redirect:/insertResults";
    }

    @GetMapping("/insertResults")
    public String insertResults(Model model) {
        System.out.println("jestem w getmapping");
        Result resultForm = new Result();
        model.addAttribute("resultForm", resultForm);
        return "/insertResults";
    }

    @PostMapping("/insertResults")
    public String insertResults(@ModelAttribute("resultForm") Result resultForm){
//        resultForm = resultRepository.findResultByIdresult(resultId);
        resultForm.setCompetition(competitionRepository.findByIdCompetitions(compId));
        resultRepository.save(resultForm);

        CategoriesOnCompetition compr = categoriesOnCompetitionRepository.findCategoriesOnCompetitionById(resultForm.getCategoriesOnCompetition().getId());
        compr.setResult(resultForm);
        categoriesOnCompetitionRepository.saveAndFlush(compr);


        return "redirect:/organizerCompetitionView";
    }


    @GetMapping("/refereesOnCompetitionView/{idCompetitions}")
    public String refereesOnCompetitionView(@PathVariable(name = "idCompetitions") Long id, Model model) {
        System.out.println("Jestem w @GetMapping( refereesOnCompetitionView/{idCompetitions} ");
        Competition competition = competitionRepository.findByIdCompetitions(id);
        List<RefereeRoleOnCompetition> refereeList = refereeRoleOnCompetitionRepository.findRefereeRoleOnCompetitionByCompetition(competition);
        System.out.println("Lista sedziow: " +refereeList ) ;
        model.addAttribute("refereeList", refereeList);
        for (RefereeRoleOnCompetition x : refereeList) {
            System.out.println("Id klubu: "+x.getRefereeName());
            System.out.println("Nazwa klubu: "+x.getRefereeRoleName());
        }

        return "/refereesOnCompetitionView";
    }
//    @ModelAttribute("/listOfResultsOnCompetition")
//    public String searchResults(){
//        if(resultRepository.findResultByCompetition_IdCompetitions(idCom))
//    }
    @GetMapping("/searchResults/{idCompetitions}")
    public String resultsOfCompetitionsView(@PathVariable(name = "idCompetitions") Long id, Model model){
        if(resultRepository.findResultsByCompetition_IdCompetitions(id).isEmpty()){
            return null;
        }else {
            List<Result> listOfResultsOnCompetition = resultRepository.findResultsByCompetition_IdCompetitions(id);
            model.addAttribute("listOfResultsOnCompetition", listOfResultsOnCompetition);
            return "/searchResults";
        }

    }
    @GetMapping("/searchPlayerResults/{idPlayer}")
    public String resultsOfPlayersView(@PathVariable(name = "idPlayer") Long id, Model model){
        if(categoriesOnCompetitionRepository.findCategoriesOnCompetitionByPlayers(playerRepository.findPlayerByIdPlayer(id)).isEmpty()){
            return null;
        }else {
            List<CategoriesOnCompetition> listOfPlayerResults = categoriesOnCompetitionRepository.findCategoriesOnCompetitionByPlayers(playerRepository.findPlayerByIdPlayer(id));
            model.addAttribute("listOfPlayerResults", listOfPlayerResults);
            return "/searchPlayerResults";
        }

    }

    @PostMapping("/refereesOnCompetitionView")
    public String refereesOnCompetitionView() {
        return "/refereesOnCompetitionView";
    }

    @ModelAttribute("listOfResults")
    public List<Result> searchListOfResults(){
        if(resultRepository.findAll().isEmpty()){
            return null;
        }else {
            List<Result> listOfResults = resultRepository.findAll();
            return listOfResults;
        }
    }
    @ModelAttribute("listOfAllCompetitions")
    public List<Competition> searchListOfAllCompetitions(){
        if(competitionRepository.findAll().isEmpty()){
            return null;
        }else {
            List<Competition> listOfAllCompetitions = competitionRepository.findAll();
            return listOfAllCompetitions;
        }
    }
    @RequestMapping(value = "/redirectRegClubForCompetition", method = RequestMethod.GET)
    public String redirectRegClubForCompetition() {
        return "redirect:/regClubForCompetition";
    }
    @GetMapping("regClubForCompetition")
    public String regClubForCompetition(Model model){
        model.addAttribute("registrationForCompetitionForm", new RegistrationForCompetition());
        return "regClubForCompetition";
    }
    @PostMapping("regClubForCompetition")
    public String regClubForCompetition(RedirectAttributes redirAttrs, @ModelAttribute("registrationForCompetitionForm") RegistrationForCompetition registrationForCompetition){
        // RegistrationForCompetition registrationForCompetition = new RegistrationForCompetition();

        System.out.println("registrationForCompetition id = "+registrationForCompetition.getIdRegistrationForCompetition());
        System.out.println("registrationForCompetition list of players = "+registrationForCompetition.getPlayers());
        System.out.println("Dodaje zawodnikow ...");
        registrationForCompetition.setPlayers(listOfPlayers);
        System.out.println("registrationForCompetition list of players = "+registrationForCompetition.getPlayers());
        System.out.println("Id zawodow: "+registrationForCompetition.getIdCompetition());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = personService.findByUsername(auth.getName());
        Coach coach = coachRepository.findCoachByPerson(p);
        String coachName = coach.getPerson().getUsername();
        Club club = clubRepository.findClubByCoach(coach);
        String clubName = club.getClubname();
        registrationForCompetition.setIdCoach(coach.getIdCoach());
        registrationForCompetition.setIdClub(coach.getClub().getId_club());
        registrationForCompetition.setClubName(clubName);
        registrationForCompetition.setCoachName(coachName);
        Optional <Categories> categories = categoriesRepository.findById(registrationForCompetition.getIdCategories());
        registrationForCompetition.setCategoryName(categories.get().getNamecategory());

        System.out.println("Rejestracja na zawody");
        System.out.println("Id trenera:"+registrationForCompetition.getIdCoach());
        System.out.println("Id klubu:"+registrationForCompetition.getIdClub());

        registrationForCompetitionRepository.save(registrationForCompetition);

        listOfPlayers.clear();

        if(listOfPlayers.isEmpty()) System.out.println("Wyczyszczono liste zawodnikow");
        else System.out.println("Nie udalo sie wyczyscic listy zawodnikow");
        Competition competition = competitionRepository.findByIdCompetitions(registrationForCompetition.getIdCompetition());
        redirAttrs.addFlashAttribute("success", "Dodano Twoj klub na zawody: "+competition.getCompetitionName());
        return "redirect:/welcome";
    }
    /*
    @PostMapping("regClubForCompetition")
    public String regClubForCompetition(Model model){
        return "regClubForCompetition";
    }*/
    @GetMapping("/acceptPlayer/{id_person}")
    public String acceptPlayer(@PathVariable(name = "id_person") Long id, Model model,  RedirectAttributes redirAttrs) {

        System.out.println("Jestem w /acceptPlayer/{id_person}\n\n\n");
        Optional <Player> player = playerRepository.findById(id);
        System.out.println("Ten zawodnik ma id = "+player.get().getIdPlayer());
        System.out.println("I nazywa się: "+player.get().getUsername());
        redirAttrs.addFlashAttribute("success", "Dodano zawodnika: "+player.get().getUsername());
        addToListOfPlayers(player.get());
        return "redirect:/regClubForCompetition";
    }
    @GetMapping("/searchApplication/{idCompetitions}")
    public String searchApplicationForCompetition(@PathVariable(name = "idCompetitions") Long id, Model model,  RedirectAttributes redirAttrs){
        System.out.println("Jestem w /searchApplicationForCompetition/{idCompetitions}\n\n\n");
        List<RegistrationForCompetition> registrationForCompetition=registrationForCompetitionRepository.findByIdCompetition(id);
        listOfRegForCompetitions.clear();
        listOfRegForCompetitions = registrationForCompetition;
        for (RegistrationForCompetition x : listOfRegForCompetitions) {
            System.out.println("listOfRegForCompetitions: "+x.getIdRegistrationForCompetition());
        }
        return "redirect:/searchApplication";
    }

    @GetMapping("/seePlayersInRegistration/{idRegistration}")
    public String seePlayersInRegistration(@PathVariable(name = "idRegistration") Long id, Model model,  RedirectAttributes redirAttrs){
        System.out.println("Jestem w funkcji seePlayersInRegistration");
        Optional<RegistrationForCompetition> registrationForCompetition = registrationForCompetitionRepository.findById(id);

        List<Player> listOfPlayers = registrationForCompetition.get().getPlayers();

        String text = "Zgłoszeni zawodnicy:\n";
        for (Player player : listOfPlayers) {
            System.out.println("Zawodnik: "+player.getUsername());
            text = text+" "+player.getUsername();
        }
        redirAttrs.addFlashAttribute("success", text);
        return "redirect:/searchApplication";
    }

    @GetMapping("/seePlayersInCompetition/{id}")
    public String seePlayersInCompetiton(@PathVariable(name = "id") Long id, Model model,  RedirectAttributes redirAttrs){
        Optional<AcceptedRegistrationForCompetition> acceptedRegistrationForCompetition = acceptedRegistrationForCompetitionRepository.findById(id);

        List<Player> listOfPlayers = acceptedRegistrationForCompetition.get().getPlayers();

        String text = "Zgłoszeni zawodnicy:\n";
        for (Player player : listOfPlayers) {
            System.out.println("Zawodnik: "+player.getUsername());
            text = text+" "+player.getUsername();
        }
        redirAttrs.addFlashAttribute("success", text);
        return "redirect:/seeAcceptedRegistrations";
    }
    @GetMapping("/seeAcceptedRegistrations/{idCompetitions}")
    public String seeAcceptedRegistrations(@PathVariable(name = "idCompetitions") Long id, Model model,  RedirectAttributes redirAttrs){
        List<AcceptedRegistrationForCompetition> acceptedRegistrationForCompetitionList = acceptedRegistrationForCompetitionRepository.findByIdCompetition(id);
        listOfAcceptedRegistrationForCompetition.clear();
        listOfAcceptedRegistrationForCompetition = acceptedRegistrationForCompetitionList;
        model.addAttribute("acceptedRegistrationForCompetition",listOfAcceptedRegistrationForCompetition);
        for(AcceptedRegistrationForCompetition x: acceptedRegistrationForCompetitionList){
            System.out.println("acceptedRegistrationForCompetition ID "+x.getId());
        }

        System.out.println("Jestem w /seeAcceptedRegistrations/{idCompetitions}");
        return "redirect:/seeAcceptedRegistrations";

    }
    @GetMapping("seeAcceptedRegistrations")
    public String seeAcceptedRegistrations(Model model) {
        System.out.println("Jestem w  @GetMapping(\"seeAcceptedRegistrations\")");
        model.addAttribute(listOfAcceptedRegistrationForCompetition);
        return "/seeAcceptedRegistrations";
    }
    @PostMapping("seeAcceptedRegistrations")
    public String seeAcceptedRegistrations(@ModelAttribute("listOfAcceptedRegistrationForCompetition") AcceptedRegistrationForCompetition acceptedRegistrationForCompetition){
        System.out.println("Jestem w PostMapping seeAcceptedRegistrations");
        return "redirect:/seeAcceptedRegistrations";
    }

    @ModelAttribute("acceptedRegistrationForCompetition")
    public List<AcceptedRegistrationForCompetition> getAcceptedRegistrationForCompetition() {
        System.out.println("Szukam zaakceptowanych klubów na zawody...");
        for(AcceptedRegistrationForCompetition a: listOfAcceptedRegistrationForCompetition){
            System.out.println("Na te zawody zaakceptowano klub: "+a.getClubName());
        }
        return listOfAcceptedRegistrationForCompetition;
    }
    @GetMapping("/searchApplication")
    public String searchApplication(Model model) {
        System.out.println("jestem w getmapping searchApplication");

        for (RegistrationForCompetition x : listOfRegForCompetitions) {
            System.out.println("listOfRegForCompetitions: "+x.getIdRegistrationForCompetition());
        }
        return "searchApplication";
    }

    @ModelAttribute("regClubForCompetition")
    public List<RegistrationForCompetition> getRegClubForCompetition(){
        for (RegistrationForCompetition x : listOfRegForCompetitions) {
            System.out.println("model attribute: listOfRegForCompetitions: "+x.getIdRegistrationForCompetition());
        }
        return listOfRegForCompetitions;
    }

    public void addToListOfPlayers(Player player){
        listOfPlayers.add(player);
    }
    @ModelAttribute("listOfPlayersForCompetitions")
    public List<Player> getListOfPlayers(){
        return listOfPlayers;
    }

    @ModelAttribute("listOfCategoriesForPlayers")
    public List<Categories> getListOfCategoriesForPlayer(){
        List<Categories> categoriesList = categoriesRepository.findAll();
        return categoriesList;
    }

    @GetMapping("/acceptRegistrationForCompetition/{id_registration}")
    public String acceptRegistrationForCompetition(@PathVariable(name = "id_registration") Long id, Model model) {

        System.out.println("Jestem w /acceptRegistrationForCompetition/{id_registration}\n\n\n");
        Optional<RegistrationForCompetition> registrationForCompetition = registrationForCompetitionRepository.findById(id);

        AcceptedRegistrationForCompetition acceptedRegistrationForCompetition = new AcceptedRegistrationForCompetition();
        acceptedRegistrationForCompetition.setClubName(registrationForCompetition.get().getClubName());
        acceptedRegistrationForCompetition.setIdClub(registrationForCompetition.get().getIdClub());
        acceptedRegistrationForCompetition.setCoachName(registrationForCompetition.get().getCoachName());
        acceptedRegistrationForCompetition.setIdCoach(registrationForCompetition.get().getIdCoach());
        acceptedRegistrationForCompetition.setIdCompetition(registrationForCompetition.get().getIdCompetition());
        acceptedRegistrationForCompetition.setIdCategories(registrationForCompetition.get().getIdCategories());
        acceptedRegistrationForCompetition.setCategoryName(registrationForCompetition.get().getCategoryName());

        List<Player> listOfPlayers = new ArrayList<>();
        listOfPlayers.addAll(registrationForCompetition.get().getPlayers());
        acceptedRegistrationForCompetition.setPlayers(listOfPlayers);
        Optional <Categories> categories = categoriesRepository.findById(registrationForCompetition.get().getIdCategories());

        CategoriesOnCompetition categoriesOnCompetition = new CategoriesOnCompetition();
        categoriesOnCompetition.setCategories(categories.get());
        categoriesOnCompetition.setPlayers(listOfPlayers);
        Competition competition = competitionRepository.findByIdCompetitions(registrationForCompetition.get().getIdCompetition());
        categoriesOnCompetition.setCompetition(competition);
        categoriesOnCompetitionRepository.save(categoriesOnCompetition);

        acceptedRegistrationForCompetitionRepository.save(acceptedRegistrationForCompetition);

        Message message = new Message();
        message.setIdPerson(registrationForCompetition.get().getIdCoach());
        message.setContent("Twoj klub zostal zaakceptowany na zawody "+competition.getCompetitionName()+"\n");

        registrationForCompetitionRepository.delete(registrationForCompetition.get());
        List<RegistrationForCompetition> registrationForCompetitionList=registrationForCompetitionRepository.findByIdCompetition(competition.getIdCompetitions());
        listOfRegForCompetitions.clear();
        listOfRegForCompetitions = registrationForCompetitionList;
        return "redirect:/organizerCompetitionView";
    }

    @GetMapping("/rejectRegistrationForCompetition/{id_registration}")
    public String rejectRegistrationForCompetition(@PathVariable(name = "id_registration") Long id, Model model) {

        System.out.println("Jestem w /rejectRegistrationForCompetition/{id_registration}\n\n\n");

        Optional <RegistrationForCompetition> registrationForCompetition = registrationForCompetitionRepository.findById(id);
        registrationForCompetitionRepository.delete(registrationForCompetition.get());
        Competition competition = competitionRepository.findByIdCompetitions(registrationForCompetition.get().getIdCompetition());
        Message message = new Message();
        message.setIdPerson(registrationForCompetition.get().getIdCoach());
        message.setContent("Twoj klub zostal odrzucony na zawody "+competition.getCompetitionName()+"\n");
        messageRepository.save(message);
        List<RegistrationForCompetition> registrationForCompetitionList=registrationForCompetitionRepository.findByIdCompetition(competition.getIdCompetitions());
        listOfRegForCompetitions.clear();
        listOfRegForCompetitions = registrationForCompetitionList;
        return "redirect:/searchApplication";
    }
}

