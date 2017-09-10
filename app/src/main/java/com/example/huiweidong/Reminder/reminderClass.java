package com.example.huiweidong.Reminder;


public class reminderClass {
    private long id;
    private String startsAt;
    private String contactPerson;
    private String repeatsNr;
    private String RepeatsInterval;
    private String unsharpen;
    private String unsharpenNr;
    private String unsharpenInterval;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getRepeatsNr() {
        return repeatsNr;
    }

    public void setRepeatsNr(String repeatsNr) {
        this.repeatsNr = repeatsNr;
    }

    public String getRepeatsInterval() {
        return RepeatsInterval;
    }

    public void setRepeatsInterval(String repeatsInterval) {
        RepeatsInterval = repeatsInterval;
    }

    public String getUnsharpen() {
        return unsharpen;
    }

    public void setUnsharpen(String unsharpen) {
        this.unsharpen = unsharpen;
    }

    public String getUnsharpenNr() {
        return unsharpenNr;
    }

    public void setUnsharpenNr(String unsharpenNr) {
        this.unsharpenNr = unsharpenNr;
    }

    public String getUnsharpenInterval() {
        return unsharpenInterval;
    }

    public void setUnsharpenInterval(String unsharpenInterval) {
        this.unsharpenInterval = unsharpenInterval;
    }

    public String getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(String startsAt) {
        this.startsAt = startsAt;
    }
}

