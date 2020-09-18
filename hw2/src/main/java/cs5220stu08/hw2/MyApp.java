package cs5220stu08.hw2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import cs5220stu08.hw2.entities.Resource;

public class MyApp {

	static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("homework02");
	static EntityManager entityManager = entityManagerFactory.createEntityManager();

	public static void main(String[] args) throws IOException {

		List<Resource> lst1 = new ArrayList<>();
		lst1 = lstParents();

		System.out.println("CS5220 Online File Manager\r\n" + "");
		System.out.println("n) New Folder" + "");
		System.out.println("x) Exit" + "");
		for (int i = 0; i < lst1.size(); i++) {
			System.out.println(i + 1 + ") " + lst1.get(i).getNameFile());
		}

		System.out.println("Please enter your choice:" + "");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String name = reader.readLine();
		if (name.equals("n")) {
			System.out.println("New");
		} else if (name.equals("x")) {
			System.out.println("Exit");
		} else {
			List<Resource> lstResourcesChild = entityManager
					.createQuery("from Resource where parentFile.id = :parentId", Resource.class)
					.setParameter("parentId", Integer.parseInt(name)).getResultList();
			System.out.println();
			System.out.println("Current folder: "
					+ entityManager.find(Resource.class, Integer.parseInt(name)).getNameFile() + "\r\n");
			System.out.println("b) Back to parent" + "");
			System.out.println("n) New Folder" + "");
			System.out.println("d) Delete current folder" + "");
			System.out.println("x) Exit" + "");
			for (int i = 0; i < lstResourcesChild.size(); i++) {
				System.out.println(i + 1 + ") " + lstResourcesChild.get(i).getNameFile());
			}
			System.out.println("Please enter your choice:" + "");
			BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));
			String option = reader2.readLine();
			System.out.println("option : " + option);
			if (option.equals("b")) {
				System.out.println("CS5220 Online File Manager\r\n" + "");
				System.out.println("n) New Folder" + "");
				System.out.println("x) Exit" + "");
				for (int i = 0; i < lstParents().size(); i++) {
					System.out.println(i + 1 + ") " + lst1.get(i).getNameFile());
				}
			} else if (option.equals("d")) {
				removeElement(3);

			}
		}
	}

	public static List<Resource> lstParents() {
		List<Resource> lstResources = entityManager
				.createQuery("from Resource where parentFile is null", Resource.class).getResultList();
		return lstResources;
	}
	
	public static int removeElement(Integer idResource) {
		entityManager.getTransaction().begin();
		int isSuccessful = entityManager.createQuery("delete from Resource r where r.id = :idResource")
				.setParameter("idResource", idResource).executeUpdate();
		System.out.println("remove " + isSuccessful);
		entityManager.getTransaction().commit();
		return isSuccessful;
	}

}
