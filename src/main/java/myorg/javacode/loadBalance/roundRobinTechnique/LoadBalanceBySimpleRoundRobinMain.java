package myorg.javacode.loadBalance.roundRobinTechnique;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/****************************************************************************************/

/*
 class IpPool defines all the servers in the form of ConcurrentHashMap named "ipMap"
 the ConcurrentHashMap contains the server info in key and value pair.
 key --> Server IP (String)
 Value --> Weightage (Integer)
 
 For Simple Round Robin Technique all the servers are treated equally with equal weightage
 The incoming requests are assigned sequential and Rotational manner.
  
 * */

class IpPool {
	
    public static Map<String, Integer> ipMap = new ConcurrentHashMap<String, Integer>();

    static {
        ipMap.put("192.168.1.1", 10);
        ipMap.put("192.168.1.2", 10);
        ipMap.put("192.168.1.3", 10);
        ipMap.put("192.168.1.4", 10);
        ipMap.put("192.168.1.5", 10);
        ipMap.put("192.168.1.6", 10);
        ipMap.put("192.168.1.7", 10);
        ipMap.put("192.168.1.8", 10);
        ipMap.put("192.168.1.9", 10);
        ipMap.put("192.168.1.10", 10);
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

class RoundRobin implements LoadBalance {
    private static Integer position = 0;

    public String getServer(String clientIp) {
        Set<String> servers = IpPool.ipMap.keySet();
        List<String> serverList = new ArrayList<String>();
        serverList.addAll(servers);
        String target = null;

        synchronized (position) {
            if (position > serverList.size() - 1) {
                position = 0;
            }
            target = serverList.get(position);
            position++;
        }
        return target;
    }
}

/****************************************************************************************/

public class LoadBalanceBySimpleRoundRobinMain {
	 public static void main(String[] args) {
	        run();
	    }

	    public static void run() {
	        loadBalance();
	    }

	    public static void loadBalance() {
	        doGetServer(new RoundRobin());
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
 
[RoundRobin] index:0,192.168.1.1
[RoundRobin] index:1,192.168.1.3
[RoundRobin] index:2,192.168.1.10
[RoundRobin] index:3,192.168.1.2
[RoundRobin] index:4,192.168.1.9
[RoundRobin] index:5,192.168.1.8
[RoundRobin] index:6,192.168.1.5
[RoundRobin] index:7,192.168.1.4
[RoundRobin] index:8,192.168.1.7
[RoundRobin] index:9,192.168.1.6
[RoundRobin] index:10,192.168.1.1
[RoundRobin] index:11,192.168.1.3
[RoundRobin] index:12,192.168.1.10
[RoundRobin] index:13,192.168.1.2
[RoundRobin] index:14,192.168.1.9
[RoundRobin] index:15,192.168.1.8
[RoundRobin] index:16,192.168.1.5
[RoundRobin] index:17,192.168.1.4
[RoundRobin] index:18,192.168.1.7
[RoundRobin] index:19,192.168.1.6
[RoundRobin] index:20,192.168.1.1
	|
	|
	|
	|
	goes on
 
 * */
 