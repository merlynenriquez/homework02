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
import org.apache.commons.lang3.math.NumberUtils;

import cs5220stu08.hw2.entities.Resource;
import cs5220stu08.hw2.entities.User;
import cs5220stu08.hw2.entities.UserResource;
import cs5220stu08.hw2.entities.UserResourcePk;

public class MyApp {

	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String EXIT_BTN = "x";
	public static final String EXIT_BCK = "b";
	public static final String EXIT_DEL = "d";
	public static final String EXIT_NEW = "n";
	
	private EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("homework02");
	private EntityManager em = emFactory.createEntityManager();
	private String opt;
	private Calendar cal = Calendar.getInstance();
	private Resource root = null;
	
	public static void main(String[] args) throws IOException {
		MyApp obj = new MyApp();
		try {
			obj.execute();	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void execute() throws Exception{
		
		List<Resource> lst1 = listResources( root );		
		opt = "";
		while( !opt.equals(EXIT_BTN) ) {
			
			if( !StringUtils.isEmpty( opt )) {
				switch( opt) {
				case EXIT_NEW: 
					promptMsg("Please enter the name of the Folder You want to Create");
					insertFolder(opt, root);
					lst1 = listResources( root );
					break;
				case EXIT_BCK: 
					root =  getParent( root );
					lst1 = listResources( root );
					break;
				case EXIT_DEL: 
					Resource parent = root.getParentFile();
					System.out.println("parent.getId()"+parent.getId());
					removeElement( root);
					lst1 = listResources(parent );
					break;
				default : 					
					if (NumberUtils.isDigits(opt)) {
						Integer indexSelected = Integer.parseInt(opt) - 1;
						root = lst1.get(indexSelected);
						lst1 = listResources(root);
					}else {
						System.out.println(ANSI_RED + "\r\n---Please enter a Valid Option!--- \r\n " + ANSI_RESET);
					}					 
					break;
				}
			}
			chooseMsgToShow(root , lst1 );
		}
		System.exit(1);
	}

	private Resource getParent(Resource root2) {
		Resource lstResources = null;
		if( root2 !=null ) {
			try {
				lstResources = em.createQuery("from Resource where id = :id ", Resource.class)
						.setParameter("id", root2.getParentFile().getId() )
						.getSingleResult();
			} catch (Exception e) {
				System.out.println( "It doesn't have Parents ");
			}
		}else {
			System.out.println(ANSI_RED + "\r\n---Please enter a Valid Option!--- \r\n " + ANSI_RESET);
		}
		return lstResources;
	}

	private  void chooseMsgToShow(Resource root , List<Resource> lst1 ) {
		if( root==null) {
			principalMsg();
		}else {
			subMsg( );
		}
		
		for (int i = 0; i < lst1.size(); i++) {
			System.out.println( (i+1) + ") " + lst1.get(i).getNameFile());
		}
		
		try {
			promptMsg("Please enter your choice");
		} catch (Exception e) {
			System.out.println( "ERROR : " + e.getMessage() );
		}
	}
	
	public  List<Resource> listResources( Resource root ) {
		List<Resource> lstResources =  null;
		if( root == null ) {
			lstResources = em.createQuery("from Resource where parentFile is null", Resource.class)
			.getResultList();
		}else {
			lstResources = em.createQuery("from Resource where parentFile.id = :parentId", Resource.class)
					.setParameter("parentId", root.getId() )
					.getResultList(); 
		}
		return lstResources;
	}
	
	public List<Resource> listResourcesParent(Integer idParentResource) {
		List<Resource> lstResources = null;

		lstResources = em.createQuery("from Resource where parentFile.id = :parentId", Resource.class)
				.setParameter("parentId", idParentResource).getResultList();

		return lstResources;
	}
	
	@SuppressWarnings("unchecked")
	public void removeElement(Resource root3) {
		StringBuffer sql = new StringBuffer();
		sql.append("with recursive rsc_tree as (select id, name, fileId, create_date, isPublic, modify_date, size, type, version from resources ");
		sql.append("where id = :idRsc union all select child.id, child.name, child.fileId, child.create_date, child.isPublic, child.modify_date, child.size, child.type, child.version ");
		sql.append("from resources as child join rsc_tree as parent on parent.id = child.fileId  ");
		sql.append(") select * from rsc_tree order by fileId desc ");
		
		em.getTransaction().begin();
		List<Resource> lstResources = em.createNativeQuery(sql.toString(), Resource.class).setParameter("idRsc", root3.getId()).getResultList();
		for (Resource resource : lstResources) {
			em.remove(resource); 
		}
		em.getTransaction().commit();
		System.out.println(ANSI_RED + lstResources.size()+" folder(s) deleted " + ANSI_RESET);
	}
	
	public void insertFolder(String nameFile, Resource resource) {
		em.getTransaction().begin();
		User user = em.find(User.class, 1);

		Resource rsr = new Resource();
		rsr.setNameFile(nameFile);
		rsr.setPublic(new Boolean(false));
		rsr.setVersion(1);
		rsr.setType("Folder");
		rsr.setCreationDate(cal.getTime());
		rsr.setParentFile(resource != null ? resource : null);
		em.persist(rsr);

		UserResource usrRsc = new UserResource();
		UserResourcePk pk = new UserResourcePk();
		pk.setIdUser(user.getId());
		pk.setIdResource(rsr.getId());
		usrRsc.setPk(pk);
		usrRsc.setOwnerType("Owner");

		em.persist(usrRsc);
		em.getTransaction().commit();
	}

	public  void principalMsg() {
		System.out.println("CS5220 Online File Manager\r\n" + "");
		System.out.println("n) New Folder" + "");
		System.out.println("x) Exit" + "");
	}

	public  void subMsg( ) {
		System.out.println(ANSI_BLUE + "\r\nCurrent folder: " + this.root.getNameFile() + "\r\n" + ANSI_RESET);
		System.out.println("b) Back to parent" + "");
		System.out.println("n) New Folder" + "");
		System.out.println("d) Delete current folder" + "");
		System.out.println("x) Exit" + "");
	}

	public void promptMsg(String message) throws IOException {
		System.out.println(message + " : ");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		opt = reader.readLine();
	}
}
