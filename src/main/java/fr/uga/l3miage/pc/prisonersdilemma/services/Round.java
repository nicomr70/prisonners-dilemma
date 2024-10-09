package fr.uga.l3miage.pc.prisonersdilemma.services;

import java.util.concurrent.CountDownLatch;

public class Round {

    private String player1Choice;
    private String player2Choice;
    private CountDownLatch choiceFollower;
    private CountDownLatch roundResultConsultationFollower;

    public Round() {
        this.choiceFollower = new CountDownLatch(2);
        this.roundResultConsultationFollower = new CountDownLatch(2);
    }

    public void countAPlayerChoice() throws InterruptedException {
        choiceFollower.countDown();  // Décrémente le countDown pour signaler que le joueur 1 a fait son choix
    }

    public void waitForChoices() throws InterruptedException {
        choiceFollower.await();  // Attend que les deux joueurs aient fait leurs choix
    }

    public void countARoundResultConsultation() throws InterruptedException {
        roundResultConsultationFollower.countDown();
    }

    public void waitForRoundResultConsultation() throws InterruptedException {
        roundResultConsultationFollower.await();
    }

}

