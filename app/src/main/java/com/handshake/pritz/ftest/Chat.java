package com.handshake.pritz.ftest;

/**
 * Created by pritz on 1/1/17.
 */

public class Chat {
    String name,mess;
    public Chat()
    {

    }
    public Chat(String name,String mess)
    {
        this.name=name;
        this.mess=mess;
    }
    public String getName()
    {
        return name;
    }
    public String getMess()
    {
        return mess;
    }

}
