package com.example.mealparty;

public class Party_Item {
    String member;
    String name;
    String time;

    public Party_Item()
    {

    }

    public Party_Item(String member, String time, String name)
    {
        this.member = member;
        this.time = time;
        this.name = name;
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
}
