package com.pony.rules;

import com.pony.PonyException;
import com.pony.advertiser.AdvertiserPollContext;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.IRoutingCandidateFactory;
import com.pony.advertiser.RoutingCandidate;
import com.pony.advertiser.RoutingCandidateFactory;
import com.pony.advertiser.RoutingCandidateSet;
import com.pony.advertiser.RoutingCandidateTree;
import com.pony.routing.AdvertiserPostContext;
import com.pony.routing.IAdvertiserPostContext;
import com.pony.routing.PingContext;
import com.pony.validation.ValidationResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Created by slin on 11/17/16.
 */
public class ScatterRule extends Rule {

    private static final double SCATTER_PERCENTAGE = 0.2;
    public ScatterRule(){
        super("ScatterRule");
    }

    @Override
    public RuleResponse beforePing(PingContext pingContext, ValidationResponse validationResponse) {return RuleResponse.NOOP;}

    @Override
    public RuleResponse afterPing(PingContext pingContext) {
        return RuleResponse.NOOP;
    }

    @Override
    public RuleResponse beforePost(IAdvertiserPostContext postContext, ValidationResponse validationResponse) throws PonyException {
    	return beforePost(postContext, validationResponse, new Random(), new RoutingCandidateFactory());
    }

    RuleResponse beforePost(IAdvertiserPostContext postContext, ValidationResponse validationResponse, Random random, IRoutingCandidateFactory factory) throws PonyException {
        //Need the current sales stack and rearrange
        List<RoutingCandidate> currentList = new ArrayList<RoutingCandidate>(postContext.getRoutingCandidates());

        if(random.nextDouble() <= SCATTER_PERCENTAGE){
            //Pick a random candidate from the rest of the stack and put to the top
            //int newTopCandidateInd = random.nextInt(currentList.size() - 2) + 1;
            //Collections.swap(currentList,0,newTopCandidateInd);

            //Currently, shuffles entire list
            Collections.shuffle(currentList,random);
            Collections.reverse(currentList);

            RoutingCandidateSet scatteredStack = factory.newRoutingCandidateSet();
            scatteredStack.addAll(currentList);
            RoutingCandidateTree newTree = factory.newRoutingCandidateTree();
            newTree.addRoutingCandidateSet(scatteredStack);
            postContext.resetCandidateStack(newTree);

            //TODO ADD LOGGING with LOGBACK
        }

        return RuleResponse.createStopPhaseResponse();
    }

    @Override
    public RuleResponse afterPost(AdvertiserPostContext postContext, RoutingCandidate routingCandidate, Disposition advertiserResponse){ return RuleResponse.NOOP;}

    @Override
    public RuleResponse poll(AdvertiserPollContext pollContext) {
        return RuleResponse.NOOP;
    }
}
