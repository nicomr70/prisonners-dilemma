package fr.uga.miage.m1.my_project;

import fr.uga.miage.m1.my_project.Enums.TypeAction;
import fr.uga.miage.m1.my_project.Strategies.PavlovStrategie;
import fr.uga.miage.m1.my_project.Strategies.Strategie;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MyProjectApplication {

	public static void main(String[] args) {

		SpringApplication.run(MyProjectApplication.class, args);

		Strategie strategie = new PavlovStrategie("test");

		List<TypeAction> actions = new ArrayList<>();

		actions.add(TypeAction.COOPERER);
		actions.add(TypeAction.TRAHIR);

		// Cas où dernierResultat est 5
		TypeAction action1 = strategie.getAction(actions, 5);
		System.out.println("Action1: " + action1); // Devrait retourner TRAHIR

		// Cas où dernierResultat est 2
		TypeAction action2 = strategie.getAction(actions, 2);
		System.out.println("Action2: " + action2); // Devrait retourner TRAHIR

		// Cas où lastAction est COOPERER
		actions.set(actions.size() - 1, TypeAction.COOPERER);
		TypeAction action3 = strategie.getAction(actions, 2);
		System.out.println("Action3: " + action3); // Devrait retourner COOPERER

		// Cas où la liste est vide
		List<TypeAction> emptyActions = new ArrayList<>();
		TypeAction action4 = strategie.getAction(emptyActions, 2);
		System.out.println("Action4: " + action4); // Devrait retourner COOPERER

		// Cas où la liste est nulle
		TypeAction action5 = strategie.getAction(null, 2);
		System.out.println("Action5: " + action5); // Devrait retourner COOPERER
	}

}



