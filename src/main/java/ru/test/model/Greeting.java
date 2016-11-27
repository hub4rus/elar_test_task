package ru.test.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rrv on 20.11.16.
 */

public class Greeting implements Serializable
{
    private final long id;
    private final String content;

    private final List<String> list=Arrays.asList(new String[] {"z","x","c"});

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }
    public String getContent() {
        return content;
    }
    public List<String> getList() {
        return list;
    }
}