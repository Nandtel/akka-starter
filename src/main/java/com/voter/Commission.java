package com.voter;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.BalancingPool;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Commission extends UntypedActor {

    private static int numberOfVoters = 0;
    private static int processedVoters = 0;
    private Map<Voter.Decision, Integer> result = new HashMap<>();
    ActorRef router = getContext().actorOf(new BalancingPool(10).props(Props.create(Voter.class)), "voters");

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            numberOfVoters = Integer.valueOf((String) message);

            IntStream.range(0, numberOfVoters)
                    .forEach(i -> router.tell(Voter.Action.VOTE, getSelf()));
            return;
        }

        if (message instanceof Voter.Decision) {
            Voter.Decision decision = (Voter.Decision) message;
            result.compute(decision, (key, value) -> (value == null) ? 1 : value + 1);


            System.out.println(result);

            processedVoters++;
            if (numberOfVoters == processedVoters) {
                processedVoters = 0;
                result.clear();
            }

            return;
        }

        unhandled(message);
    }

}
