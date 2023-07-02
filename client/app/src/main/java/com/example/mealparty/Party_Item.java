package com.example.mealparty;

public class Party_Item {
    String member;
    String name;
    String time;
    String jobid;
    int joined;
    String hostname;
    String name1 = null;
    String name2 = null;
    String name3 = null;

    public Party_Item()
    {

    }

    public Party_Item(String member, String time, String name, String jobid, int joined, String hostname, String name1, String name2, String name3)
    {
        this.member = member;
        this.time = time;
        this.name = name;
        this.jobid = jobid;
        this.joined = joined;
        this.hostname = hostname;
        this.name1 = name1;
        this.name2 = name2;
        this.name3 = name3;
    }
    public void setMember(String member)
    {
        this.member = member;
    }
    public void setTime(String time)
    {
        this.time = time;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setJobid(String jobid)
    {
        this.jobid = jobid;
    }

    public String getMember()
    {
        return member;
    }
    public String getTime()
    {
        return time;
    }
    public String getName()
    {
        return name;
    }
    public String getJobid()
    {
        return jobid;
    }
}
