package com.myapplication.wishes;

public class History {
    /**
     * id 主键，自增
     */
    private int id;
    /**
     * 搜索的内容
     */
    private String content="";//name
    /**
     * 搜索的时间
     */
    private String time="";//username
    private String title="";
    private int times=0;
    private int state=0;
    private int timesall=0;
    private String engcode;
    private String cmycode;
    private String engmat;
    private String guys;
    private String process="0";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    public int getTimesall() {
        return timesall;
    }

    public void setTimesall(int timesall) {
        this.timesall = timesall;
    }

    public String getEngcode() {
        return engcode;
    }

    public void setEngcode(String engcode) {
        this.engcode = engcode;
    }
    public String getCmycode() {
        return cmycode;
    }

    public void setCmycode(String cmycode) {
        this.cmycode = cmycode;
    }
    public String getEngmat() {
        return engmat;
    }

    public void setEngmat(String engmat) {
        this.engmat = engmat;
    }
    public String getGuys() {
        return guys;
    }

    public void setGuys(String guys) {
        this.guys = guys;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process= process;
    }
}
