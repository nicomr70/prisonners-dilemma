package fr.uga.l3miage.pc.prisonersdilemma.utils;

import fr.uga.l3miage.pc.prisonersdilemma.models.Tour;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Utils {
    private Utils(){
    }
    public static void displayStrategiesMenu(){
        System.out.println("Please choose one STRATEGY : ");
        System.out.println("1. Donnant donnant");
        System.out.println("2. Donnant donnant / random");
        System.out.println("3. Donnant for two donnants / random");
        System.out.println("4. Donnant for two donnants");
        System.out.println("5. Naive Sounder");
        System.out.println("6. Repentant Sounder");
        System.out.println("7. Naive peacemaker");
        System.out.println("8. True peacemaker");
        System.out.println("9. Random");
        System.out.println("10. Always betray");
        System.out.println("11. Always cooperate");
        System.out.println("12. Resentful");
        System.out.println("13. Pavlov");
        System.out.println("14. Pavlov / random");
        System.out.println("15. Adaptive");
        System.out.println("15. Gradual");
        System.out.println("15. Suspicious donnant donnant");
        System.out.println("15. Sweat Resentful");
    }

    public static int chooseStrategy(String name){
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Player "+name +" , choose strategy number : ");
        int strategyNumber = scanner.nextInt();
        while(strategyNumber <1 || strategyNumber>18){
            System.out.println("Enter a valid strategy number [1 - 18 ] : ");
            strategyNumber = scanner.nextInt();
        }

        scanner.close();
        return strategyNumber;
    };

    public static Tour[] getLastTwoTurns(List<Tour> history) {
        Tour[] lastTwo = new Tour[2];
        Iterator<Tour> iterator = history.iterator();
        Tour previousTour = null;
        Tour lastTour = null;

        while (iterator.hasNext()) {
            previousTour = lastTour;
            lastTour = iterator.next();
        }

        lastTwo[0] = previousTour;
        lastTwo[1] = lastTour;
        return lastTwo;
    }
}
