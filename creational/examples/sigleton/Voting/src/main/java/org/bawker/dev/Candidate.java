package org.bawker.dev;

public class Candidate {
    private String name;
    private int age;
    private int votes;
    private int Id;
    private String affiliation;
    private static int count = 0;

    public Candidate(String name, int age, String affiliation) {
        count++;
        this.name = name;
        this.age = age;
        this.votes = 0;
        this.affiliation = affiliation;
        this.Id = count;
    }
    public Candidate(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getVotes() {
        return votes;
    }

    public void Vote() {
        this.votes++;
    }

    public int getId() {
        return Id;
    }

    public String getAffiliation() {
        return affiliation;
    }
}
