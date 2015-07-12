package org.ugizashinje;

import com.hazelcast.config.Config;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by shiggy on 6/10/15.
 */
public class DemoMain {


    public static void  main(String[] args) throws  Exception{
        Config config = new Config();
        ManagementCenterConfig mancenter = new ManagementCenterConfig("http://localhost:8080/mancenter",100);

        // TODO enable management center
        mancenter.setEnabled(false);

        config.setManagementCenterConfig(mancenter);
        config.setInstanceName("Java Demo instance");

        HazelcastInstance hz = Hazelcast.getOrCreateHazelcastInstance(config);
        Command command = new Command(hz);

        if (hz == null){
            System.out.print("Failed to create HZ instance");
            System.exit(1);
        }
        Map<String, String> map = hz.getMap("firstMap");
        map.put("foo","bar");
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        do {
            System.out.print("> ");
            line = br.readLine();
            command.execute(line);
        }while ( true);
    }
}
