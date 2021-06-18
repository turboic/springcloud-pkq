package com.turboic.cloud.pojo;
import java.io.Serializable;
import java.util.Date;

/**
 * @author liebe
 */
public class AlaDing implements Serializable {
    public AlaDing(String name, String story, Date date) {
        this.name = name;
        this.story = story;
        this.date = date;
    }

    private String name;
    private String story;
    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
