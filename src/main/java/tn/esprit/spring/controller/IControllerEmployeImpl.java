package tn.esprit.spring.controller;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;
import tn.esprit.spring.services.ITimesheetService;


@Scope(value = "session")
@Controller(value = "employeController") // Name of the bean in Spring IoC
@ELBeanName(value = "employeController") // Name of the bean used by JSF
@Join(path = "/", to = "/login.jsf")
public class IControllerEmployeImpl  {
	@Autowired
	IEmployeService iemployeservice;
	@Autowired
	IEntrepriseService ientrepriseservice;
	@Autowired
	ITimesheetService itimesheetservice;
	private String login; private String password; 
	private Boolean loggedIn;
	
	private String nom;
	
	private String prenom;  private String email;
	private boolean actif; private Role role;
	private Employe authenticatedUser;

	
	

	

	public Employe getAuthenticatedUser() {
		return authenticatedUser;
	}

	public void setAuthenticatedUser(Employe authenticatedUser) {
		this.authenticatedUser = authenticatedUser;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	

	

	

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void removeEmploye(int employeId)
	{
	iemployeservice.deleteEmployeById(employeId);
	}
	
	private Integer employeIdToBeUpdated; // Ajouter getter et setter

	public void displayEmploye(Employe empl)
	{
	this.setPrenom(empl.getPrenom());
	this.setNom(empl.getNom());
	this.setActif(empl.isActif());
	this.setEmail(empl.getEmail());
	this.setRole(empl.getRole());
	this.setPassword(empl.getPassword());
	this.setEmployeIdToBeUpdated(empl.getId());
	}
	
	public void updateEmploye()
	{ iemployeservice.addOrUpdateEmploye(new Employe(employeIdToBeUpdated, nom,
	prenom, email, password, actif, role)); }
	
	
	private List<Employe> employes; // ajouter le getter et le setter

	public List<Employe> getEmployes() {
	employes = iemployeservice.getAllEmployes();
	return employes;
	}

//	public void addEmploye() {
//		iemployeservice.addOrUpdateEmploye(new Employe( actif, email, nom,  password, prenom,   role));
//		}
	
	public String addEmploye() {
		String navigateTo =

		"null";

		if (authenticatedUser==null || !loggedIn) return "/login.xhtml";
		iemployeservice.addOrUpdateEmploye(new Employe( actif, email, nom,  password, prenom,role));
		return navigateTo;
		}
	
	
	

	public Role[] getRoles() { return Role.values(); }
	

	public String doLogin() {
		String navigateTo = "null";
		
		authenticatedUser=iemployeservice.authenticate(login, password);
		 if (authenticatedUser != null && authenticatedUser.getRole() ==
		Role.ADMINISTRATEUR) {
			 
		navigateTo = "/pages/admin/welcome.xhtml?faces-redirect=true";
		loggedIn = true; }
		else {
		FacesMessage facesMessage =

		new FacesMessage("Login Failed: please check your username/password and try again.");

		FacesContext.getCurrentInstance().addMessage("form:btn",facesMessage);
		}
		return navigateTo;
		}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	

	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	public Boolean getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(Boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String doLogout() {
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			return "/login.xhtml?faces-redirect=true";
			}
	
	
	
	public int ajouterEmploye(Employe employe)
	{
		iemployeservice.ajouterEmploye(employe);
		return employe.getId();
	}
    
	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		iemployeservice.mettreAjourEmailByEmployeId(email, employeId);
		
	}

	public void affecterEmployeADepartement(int employeId, int depId) {
		iemployeservice.affecterEmployeADepartement(employeId, depId);
		
	}


	
	public void desaffecterEmployeDuDepartement(int employeId, int depId)
	{
		iemployeservice.desaffecterEmployeDuDepartement(employeId, depId);
	}

	
	public int ajouterContrat(Contrat contrat) {
		iemployeservice.ajouterContrat(contrat);
		return contrat.getReference();
	}
	
	public void affecterContratAEmploye(int contratId, int employeId)
	{
		iemployeservice.affecterContratAEmploye(contratId, employeId);
	}

	
	public String getEmployePrenomById(int employeId) {
		return iemployeservice.getEmployePrenomById(employeId);
	}

	
	public void deleteEmployeById(int employeId) {
		iemployeservice.deleteEmployeById(employeId);
		
	}
	public void deleteContratById(int contratId) {
		iemployeservice.deleteContratById(contratId);
	}

	
	public int getNombreEmployeJPQL() {
		
		return iemployeservice.getNombreEmployeJPQL();
	}

	
	public List<String> getAllEmployeNamesJPQL() {
		
		return iemployeservice.getAllEmployeNamesJPQL();
	}

	
	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		return iemployeservice.getAllEmployeByEntreprise(entreprise);
	}


	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {	
	iemployeservice.mettreAjourEmailByEmployeIdJPQL(email, employeId);
		
	}


	public void deleteAllContratJPQL() {
		iemployeservice.deleteAllContratJPQL();
		
	}

	
	public float getSalaireByEmployeIdJPQL(int employeId) {
		// TODO Auto-generated method stub
		return iemployeservice.getSalaireByEmployeIdJPQL(employeId);
	}


	public Double getSalaireMoyenByDepartementId(int departementId) {
		// TODO Auto-generated method stub
		return iemployeservice.getSalaireMoyenByDepartementId(departementId);
	}

	
	
	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		return iemployeservice.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}




	public List<Employe> getAllEmployes() {
		
		return iemployeservice.getAllEmployes();
	}

	public Integer getEmployeIdToBeUpdated() {
		return employeIdToBeUpdated;
	}

	public void setEmployeIdToBeUpdated(Integer employeIdToBeUpdated) {
		this.employeIdToBeUpdated = employeIdToBeUpdated;
	}

	
	

	
	

	
	

	
	
    
	
	
	
}
