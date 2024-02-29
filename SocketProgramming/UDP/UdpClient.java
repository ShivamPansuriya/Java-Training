package SocketProgramming.UDP;

import java.io.IOException;
import java.net.*;

public class UdpClient
{
    public static void main(String[] args)
    {
        try
        {
            DatagramSocket clientSocket = new DatagramSocket(0);

            InetAddress inetAddress = InetAddress.getByName("localhost");

            byte[] sendData = new byte[1024];

            byte[] receiveData = new byte[1024];

            String stringSendData = "Hello from client";

            sendData = stringSendData.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,inetAddress, 9090);

            clientSocket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            clientSocket.receive(receivePacket);
            receiveData = receivePacket.getData();

            String stringReceiveData = new String(receiveData);

            System.out.println("DATA FROM SERVER: "+ stringReceiveData);

            clientSocket.close();
        }
        catch(IOException iOE)
        {
            System.out.println(iOE.toString());;
        }

    }
}
