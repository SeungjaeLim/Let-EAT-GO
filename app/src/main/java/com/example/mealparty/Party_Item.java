package com.example.mealparty;

public class Party_Item {
    String member;
    String name;
    String time;
    String jobid;

    public Party_Item()
    {

    }

    public Party_Item(String member, String time, String name, String jobid)
    {
        this.member = member;
        this.time = time;
        this.name = name;
        this.jobid = jobid;
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
