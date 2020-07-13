package myorg.javacode.loadBalance.weightRoundRobinTechnique;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/****************************************************************************************/

/*
 class IpPool defines all the servers in the form of ConcurrentHashMap named "ipMap"
 the ConcurrentHashMap contains the server info in key and value pair.
 key --> Server IP (String)
 Value --> Weightage (Integer)
 
 For Weight Round Robin Technique all the servers are treated as per the weightage assigned to them.
 The incoming requests are assigned as per the weight assigned to the servers.
  
 * */

class IpPool {
	
    public static Map<String, Integer> ipMap = new ConcurrentHashMap<String, Integer>();

    static {
        ipMap.put("192.168.1.1", 10);
        ipMap.put("192.168.1.2", 20);
        ipMap.put("192.168.1.3", 30);
        ipMap.put("192.168.1.4", 40);
        ipMap.put("192.168.1.5", 50);
        ipMap.put("192.168.1.6", 60);
        ipMap.put("192.168.1.7", 70);
        ipMap.put("192.168.1.8", 80);
        ipMap.put("192.168.1.9", 90);
        ipMap.put("192.168.1.10", 100);
    }
}

/****************************************************************************************/
/* interface LoadBalance is declared to retrieve the server IP
 The String getServer(String clientIp) method returns the server IP to which an incoming request is assigned.
 The getServer method is implemented in the subsequent class "RoundRobin"
  
 * */
interface LoadBalance {
    String getServer(String clientIp);
}

/****************************************************************************************/
/*
Synchronized Keyword in in Java:

Multi-threaded programs may often come to a situation where multiple threads try to access the same resources and finally produce erroneous and unforeseen results.
So it needs to be made sure by some synchronization method that only one thread can access the resource at a given point of time.

->Java provides a way of creating threads and synchronizing their task by using synchronized blocks. 
->Synchronized blocks in Java are marked with the synchronized keyword. 
->A synchronized block in Java is synchronized on some object. 
->All synchronized blocks synchronized on the same object can only have one thread executing inside them at a time. 
->All other threads attempting to enter the synchronized block are blocked until the thread inside the synchronized block exits the block.

Following is the general form of a synchronized block:

-> Only one thread can execute at a time. 
-> sync_object is a reference to an object
-> whose lock associates with the monitor. 
-> The code is said to be synchronized on
-> the monitor object

synchronized(sync_object)
{
   // Access shared variables and other
   // shared resources
}

This synchronization is implemented in Java with a concept called monitors. 
Only one thread can own a monitor at a given time. When a thread acquires a lock, it is said to have entered the monitor. 
All other threads attempting to enter the locked monitor will be suspended until the first thread exits the monitor.

*/

class WeightRoundRobin implements LoadBalance {
	
    private static Integer position = 0;

    public String getServer(String clientIp) {
        Set<String> servers = IpPool.ipMap.keySet();
        List<String> serverList = new ArrayList<String>();
        
        Iterator<String> iterator = servers.iterator();
        while (iterator.hasNext()) {
            String serverItem = iterator.next();
            Integer weight = IpPool.ipMap.get(serverItem);
            if (weight > 0) {
                for (int i = 0; i < weight; i++) {
                    serverList.add(serverItem);
                }
            }

        }

        synchronized (position) {
            if (position > serverList.size()) {
                position = 0;
            }

            String target = serverList.get(position);
            position++;
            return target;
        }
    }
}

/****************************************************************************************/

public class LoadBalanceByWeightRoundRobin {
	 public static void main(String[] args) {
	        run();
	    }

	    public static void run() {
	        loadBalance();
	    }

	    public static void loadBalance() {
	        doGetServer(new WeightRoundRobin());
	    }


	    public static void doGetServer(LoadBalance loadBalance) {
	        doGetServer(loadBalance, 100);
	    }

	    private static void doGetServer(LoadBalance loadBalance, int queryTimes) {
	        for (int i = 0; i < queryTimes; i++) {
	            String serverId = loadBalance.getServer(String.valueOf(i));
	            System.out.println(String.format("[%s] index:%s,%s", loadBalance.getClass().getSimpleName(), i, serverId));
	        }
	    }
}

/****************************************************************************************/

/*
 Output :
 
[WeightRoundRobin] index:0,192.168.1.1
[WeightRoundRobin] index:1,192.168.1.1
[WeightRoundRobin] index:2,192.168.1.1
[WeightRoundRobin] index:3,192.168.1.1
[WeightRoundRobin] index:4,192.168.1.1
[WeightRoundRobin] index:5,192.168.1.1
[WeightRoundRobin] index:6,192.168.1.1
[WeightRoundRobin] index:7,192.168.1.1
[WeightRoundRobin] index:8,192.168.1.1
[WeightRoundRobin] index:9,192.168.1.1
[WeightRoundRobin] index:10,192.168.1.3
[WeightRoundRobin] index:11,192.168.1.3
[WeightRoundRobin] index:12,192.168.1.3
[WeightRoundRobin] index:13,192.168.1.3
[WeightRoundRobin] index:14,192.168.1.3
[WeightRoundRobin] index:15,192.168.1.3
[WeightRoundRobin] index:16,192.168.1.3
[WeightRoundRobin] index:17,192.168.1.3
[WeightRoundRobin] index:18,192.168.1.3
[WeightRoundRobin] index:19,192.168.1.3
[WeightRoundRobin] index:20,192.168.1.3
[WeightRoundRobin] index:21,192.168.1.3
[WeightRoundRobin] index:22,192.168.1.3
[WeightRoundRobin] index:23,192.168.1.3
[WeightRoundRobin] index:24,192.168.1.3
[WeightRoundRobin] index:25,192.168.1.3
[WeightRoundRobin] index:26,192.168.1.3
[WeightRoundRobin] index:27,192.168.1.3
[WeightRoundRobin] index:28,192.168.1.3
[WeightRoundRobin] index:29,192.168.1.3
[WeightRoundRobin] index:30,192.168.1.3
[WeightRoundRobin] index:31,192.168.1.3
[WeightRoundRobin] index:32,192.168.1.3
[WeightRoundRobin] index:33,192.168.1.3
[WeightRoundRobin] index:34,192.168.1.3
[WeightRoundRobin] index:35,192.168.1.3
[WeightRoundRobin] index:36,192.168.1.3
[WeightRoundRobin] index:37,192.168.1.3
[WeightRoundRobin] index:38,192.168.1.3
[WeightRoundRobin] index:39,192.168.1.3
[WeightRoundRobin] index:40,192.168.1.10
[WeightRoundRobin] index:41,192.168.1.10
[WeightRoundRobin] index:42,192.168.1.10
[WeightRoundRobin] index:43,192.168.1.10
[WeightRoundRobin] index:44,192.168.1.10
[WeightRoundRobin] index:45,192.168.1.10
[WeightRoundRobin] index:46,192.168.1.10
[WeightRoundRobin] index:47,192.168.1.10
[WeightRoundRobin] index:48,192.168.1.10
[WeightRoundRobin] index:49,192.168.1.10
[WeightRoundRobin] index:50,192.168.1.10
[WeightRoundRobin] index:51,192.168.1.10
[WeightRoundRobin] index:52,192.168.1.10
[WeightRoundRobin] index:53,192.168.1.10
[WeightRoundRobin] index:54,192.168.1.10
[WeightRoundRobin] index:55,192.168.1.10
[WeightRoundRobin] index:56,192.168.1.10
[WeightRoundRobin] index:57,192.168.1.10
[WeightRoundRobin] index:58,192.168.1.10
[WeightRoundRobin] index:59,192.168.1.10
[WeightRoundRobin] index:60,192.168.1.10
[WeightRoundRobin] index:61,192.168.1.10
[WeightRoundRobin] index:62,192.168.1.10
[WeightRoundRobin] index:63,192.168.1.10
[WeightRoundRobin] index:64,192.168.1.10
[WeightRoundRobin] index:65,192.168.1.10
[WeightRoundRobin] index:66,192.168.1.10
[WeightRoundRobin] index:67,192.168.1.10
[WeightRoundRobin] index:68,192.168.1.10
[WeightRoundRobin] index:69,192.168.1.10
[WeightRoundRobin] index:70,192.168.1.10
[WeightRoundRobin] index:71,192.168.1.10
[WeightRoundRobin] index:72,192.168.1.10
[WeightRoundRobin] index:73,192.168.1.10
[WeightRoundRobin] index:74,192.168.1.10
[WeightRoundRobin] index:75,192.168.1.10


	|
	|
	|
	|
	goes on till 100 times from 0 to 99
 
 * */
 