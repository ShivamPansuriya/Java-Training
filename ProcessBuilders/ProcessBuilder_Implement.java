package ProcessBuilders;

import java.io.*;
import java.util.*;

public class ProcessBuilder_Implement
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        Scanner sc = new Scanner(System.in);

        List<String> hostList = new ArrayList<>();
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./src/ProcessBuilders/hostlist.txt")))
        {
            System.out.println("Entre the host IP list");
            var userIp = sc.next();

            hostList = List.of(userIp.replaceAll(",", "\n").split("\n"));
            bufferedWriter.write(userIp.strip().replaceAll(",", "\n"));
        }

        System.out.println(hostList);
        String path = "./src/ProcessBuilders/hostlist.txt";
        ProcessBuilder processBuilder = new ProcessBuilder();

        processBuilder.command("fping", "-c3", "-f", path).redirectErrorStream(true);

        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String outputLine;

        Map<String,Integer> resultMap = new HashMap<>();

        while((outputLine = reader.readLine()) != null)
        {
            for(var host : hostList){

                if(outputLine.contains(host) && outputLine.contains("0% loss"))
                    resultMap.merge(host,1,Integer::sum);
            }
        }

        for(var key : resultMap.keySet()){
            if(resultMap.get(key)==3){
                System.out.println(key + " UP");
            }
            else
                System.out.println(key + " Down");
        }

        System.out.println(resultMap);


        int exitCode = process.waitFor();


    }
}
