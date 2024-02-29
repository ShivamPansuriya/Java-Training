package SocketProgramming.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpServer
{
    public static void main(String[] args)
    {
        try
        {
            DatagramSocket serverSocket = new DatagramSocket(9090);

            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];

            while(true)
            {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
serverSocket.receive(receivePacket);
                String stringReceiveData = new String(receivePacket.getData());

                System.out.println(stringReceiveData);

                String stringSendData = "Hello from server!!";

                sendData = stringSendData.getBytes();

                InetAddress clientAddress = receivePacket.getAddress();

                int port = receivePacket.getPort();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, port);

                serverSocket.send(sendPacket);
            }

        } catch(IOException e)
        {
            throw new RuntimeException(e);
        }

    }
}
