package SocketProgramming;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlClass
{
    public static void main(String[] args) throws MalformedURLException
    {
        URL url = new URL("https://www.google.com/search?q=tmkoc&oq=tmkoc&gs_lcrp=EgZjaHJvbWUqEAgAEAAYgwEY4wIYsQMYgAQyEAgAEAAYgwEY4wIYsQMYgAQyDQgBEC4YgwEYsQMYgAQyDQgCEAAYgwEYsQMYgAQyDQgDEAAYgwEYsQMYgAQyCggEEAAYsQMYgAQyDQgFEAAYgwEYsQMYgAQyBwgGEAAYgAQyEAgHEAAYgwEYsQMYgAQYigUyDQgIEAAYgwEYsQMYgAQyBwgJEAAYgATSAQgxNTc1ajBqN6gCALACAA&sourceid=chrome&ie=UTF-8");
        System.out.println("Protocol: " + url.getProtocol());
        System.out.println("Host: " + url.getHost());
        System.out.println("Port: " + url.getPort());
        System.out.println("Path: " + url.getPath());
        System.out.println("Query " + url.getQuery());
        System.out.println("User " + url.getDefaultPort());

    }
}
