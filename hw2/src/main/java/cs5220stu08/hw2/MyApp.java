package cs5220stu08.hw2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import cs5220stu08.hw2.entities.Resource;

public class MyApp {

	public static void main(String[] args) throws IOException {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("homework02");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		// Find the employee whose id is 3.
		Resource r = entityManager.find(Resource.class, 1);
		System.out.println(r.getNameFile());

		// Enter data using BufferReader
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		// Reading data using readLine
		String name = reader.readLine();
		// Printing the read line
		System.out.println(name);
	}

}
