package org.bawker.dev;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Voting {
    private static volatile Voting instance;
    private final Map<Integer, Candidate> voteStorage;

    private Voting(){
        voteStorage = new HashMap<>();
        Candidate candidate = new Candidate("Ailton Bauque", 20, "mwanesoft");

        voteStorage.put(candidate.getId(), candidate);
    }

    public static Voting getInstance(){
        if (instance == null){
            synchronized (Voting.class) {
                if (instance == null){
                    instance = new Voting();
                    Logger.getInstance().logInfo("Started the voting server");
                }
            }
        }
        return instance;
    }

    public synchronized void addVote(int id){
        Candidate candidate = voteStorage.get(id);
        candidate.Vote();
        voteStorage.put(candidate.getId(), candidate);
        Logger.getInstance().logInfo("The candidate"+ candidate.getName()+" was voted");

    }

    public List<Candidate> getCandidates(){
//        Logger.getInstance().logInfo("Getting the list of candidates");

        return new ArrayList<>(voteStorage.values());
    }

    public void createCandidate(Candidate candidate){
        voteStorage.put(candidate.getId(), candidate);
    }
    public Candidate getTopCandidate(){
        Candidate top = null;
        if (voteStorage.values().size() <=1)
            return voteStorage.get(1);
        else{
            Candidate tmp = null;
            for(Candidate candidate : voteStorage.values()) {
                if (tmp == null)
                    tmp = candidate;
                else if (tmp.getVotes() > candidate.getVotes())
                    top = tmp;
                else
                    top = candidate;
            }
        }
        return top;
    }
}
