package com.voter;

import akka.actor.UntypedActor;

public class Voter extends UntypedActor {

    public enum Action {
        VOTE
    }

    public enum Decision {
        YES, NO
    }

    private Decision decide() {
        try {
            if (Math.random() <= 0.3) {
                Thread.sleep(1000);
                System.out.println("THINK");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Math.random() <= 0.5 ? Decision.YES : Decision.NO;
    }

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof Action && message.equals(Action.VOTE))
            getSender().tell(decide(), getSelf());

        unhandled(message);
    }

}