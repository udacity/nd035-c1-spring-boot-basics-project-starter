package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {

    Integer noteid;
    public String notetitle;
    public String notedescription;
    Integer userid;

    public Integer getNoteid() {
        return noteid;
    }

    public void setNoteid(Integer noteid) {
        this.noteid = noteid;
    }

    public String getnotetitle() { return notetitle;}

    public void setnotetitle(String notetitle) {
        this.notetitle = notetitle;
    }

    public String getnotedescription() {
        return notedescription;
    }

    public void setnotedescription(String notedescription) {
        this.notedescription = notedescription;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
