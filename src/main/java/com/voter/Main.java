package com.voter;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ActorSystem system = ActorSystem.create("vote");
        final ActorRef commission = system.actorOf(Props.create(Commission.class), "commission");

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String msg = scanner.nextLine();
            if (msg.equals("exit")) break;
            commission.tell(msg, ActorRef.noSender());
        }

        system.shutdown();
    }

}
