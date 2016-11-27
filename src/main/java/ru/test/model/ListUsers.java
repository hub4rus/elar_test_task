package ru.test.model;

import java.util.List;

/**
 * Created by rrv on 22.11.16.
 */
public class ListUsers
{
    public List<User> getList() {
        return list;
    }

    public void setList(List<User> ll) {
        this.list = ll;
    }

    private List<User> list;

    public ListUsers() {
        this(null);
    }

    public ListUsers(List<User> list) {
        this.list=list;
    }

    @Override
    public String toString() {
        StringBuilder b=new StringBuilder();
        if (list!=null)
        for(User u: list) {
            b.append(" User [id=" + u.getId() + ", name=" + u.getName() + ", login=" + u.getLogin() + "];");
        }
        return b.toString();
    }
}
