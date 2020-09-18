package cs5220stu08.hw2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.lang3.StringUtils;

import cs5220stu08.hw2.entities.Resource;
import cs5220stu08.hw2.entities.User;
import cs5220stu08.hw2.entities.UserResource;
import cs5220stu08.hw2.entities.UserResourcePk;

public class MyApp {

	static EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("homework02");
	static EntityManager em = emFactory.createEntityManager();
	static String opt;
	static Calendar cal = Calendar.getInstance();

	public static void main(String[] args) throws IOException {
		List<Resource> lst1 = lstParents();

		principalMsg();
		for (int i = 0; i < lst1.size(); i++) {
			System.out.println(lst1.get(i).getId() + ") " + lst1.get(i).getNameFile());
		}

		promptMsg("Please enter your choice");
		if (opt.equals("n")) {
			System.out.println("New");
		} else if (opt.equals("x")) {
			System.out.println("Exit");
		} else {
			
			
			System.out.println("===>"+opt);
			if (StringUtils.isNumeric(opt) ) 
				lstChilds(opt);

			if (opt.equals("b")) {
				principalMsg();
				for (int i = 0; i < lstParents().size(); i++) {
					System.out.println(lst1.get(i).getId() + ") " + lst1.get(i).getNameFile());
				}
				promptMsg("Please enter one option");
				lstParents();
			} else if (opt.equals("d")) {
				removeElement(3);

			} else if (opt.equals("n")) {
				promptMsg("Please enter the name of the Folder");
				insertElement(opt);
			}
		}
	}

	public static List<Resource> lstParents() {
		List<Resource> lstResources = em.createQuery("from Resource where parentFile is null", Resource.class)
				.getResultList();
		return lstResources;
	}

	public static int removeElement(Integer idResource) {
		em.getTransaction().begin();
		int isSuccessful = em.createQuery("delete from Resource r where r.id = :idResource")
				.setParameter("idResource", idResource).executeUpdate();
		System.out.println("remove " + isSuccessful);
		em.getTransaction().commit();
		return isSuccessful;
	}
	
	public static void insertElement(String nameFile) {
		em.getTransaction().begin();
		User user = em.find(User.class, 1);
		
		Resource resource = new Resource();
		resource.setNameFile(nameFile);
		resource.setPublic(new Boolean(false));
		resource.setVersion(1);
		resource.setCreationDate(cal.getTime());
		em.persist(resource);
		
		UserResource usrRsc = new UserResource();
		UserResourcePk pk = new UserResourcePk();
		pk.setIdUser(user.getId());
		pk.setIdResource(resource.getId());
		usrRsc.setPk(pk);
		usrRsc.setOwnerType("Owner");
				
		em.persist(usrRsc);
		em.getTransaction().commit();
	}

	public static void principalMsg() {
		System.out.println("CS5220 Online File Manager\r\n" + "");
		System.out.println("n) New Folder" + "");
		System.out.println("x) Exit" + "");
	}

	public static void subMsg(String idOption) {
		System.out.println(
				"Current folder: " + em.find(Resource.class, Integer.parseInt(idOption)).getNameFile() + "\r\n");
		System.out.println("b) Back to parent" + "");
		System.out.println("n) New Folder" + "");
		System.out.println("d) Delete current folder" + "");
		System.out.println("x) Exit" + "");
	}

	public static void promptMsg(String message) throws IOException {
		System.out.println(message + " : ");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		opt = reader.readLine();
	}
	
	public static void lstChilds(String idParent) throws IOException {
		List<Resource> lstResourcesChild = em
				.createQuery("from Resource where parentFile.id = :parentId", Resource.class)
				.setParameter("parentId", Integer.parseInt(idParent)).getResultList();
		subMsg(opt);
		for (int i = 0; i < lstResourcesChild.size(); i++) {
			System.out.println(lstResourcesChild.get(i).getId() + ") " + lstResourcesChild.get(i).getNameFile());
		}

		promptMsg("Please enter one option");
		if (StringUtils.isNumeric(opt) ) 
			lstChilds(opt);
	}
	
	

}
