package com.turboic.cloud.config;

import java.io.Serializable;

/**
 * @author liebe
 */
public class Msg implements Serializable {
    private Long id;
    private String header;
    private String body;

    public Msg() {
    }

    public Msg(Long id, String header, String body) {
        this.id = id;
        this.header = header;
        this.body = body;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "id='" + id + '\'' +
                ", header='" + header + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
