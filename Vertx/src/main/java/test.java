import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class test
{
    public static void main(String[] args)
    {
        var outmap = new ConcurrentHashMap<Integer,HashMap>();

        var inmap = new HashMap<Integer,Integer>();

        inmap.put(1,1);
        inmap.put(2,1);
        inmap.put(3,1);
        inmap.put(4,1);
        inmap.put(5,1);
        inmap.put(6,1);
        inmap.put(7,1);
        inmap.put(8,1);

        outmap.put(1,inmap);

        new Thread(()->{
            inmap.put(10,12);
            inmap.put(9,12);
            inmap.put(11,12);
            inmap.put(12,12);
            inmap.put(13,12);
        }).start();

        for(var map: outmap.values())
        {
            for(var key: map.keySet())
            {
                System.out.println(map.get(key));
            }
        }

    }
}
