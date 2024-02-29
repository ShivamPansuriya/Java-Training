package SocketProgramming;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Socket_Programming_InetAddressing
{
    public static void main(String[] args) throws UnknownHostException
    {
        InetAddress inetAddress = InetAddress.getByName("localhost");

        System.out.println(inetAddress.getHostAddress());

        System.out.println(inetAddress.getHostName());
    }
}
