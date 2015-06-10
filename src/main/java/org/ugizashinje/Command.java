package org.ugizashinje;

import com.hazelcast.core.HazelcastInstance;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shiggy on 6/10/15.
 */
public class Command {
    private HazelcastInstance hzInstance;
    private final String cmdPattern = "\\s*(\\p{Alpha}+)\\.(\\p{Alpha}+)\\s*((\\w+)(\\s+(\\w+))?)?\\s*";
    private Pattern pattern;

    public Command(HazelcastInstance hzInstance) {
        this.pattern = Pattern.compile(cmdPattern);
        this.hzInstance = hzInstance;
    }

    public void execute(String strCommand) {
        Matcher m = null;
        try {
            m = pattern.matcher(strCommand);
            m.find();

            String mapName = m.group(1);
            String operation = m.group(2);
            if (operation != null) {
                operation = operation.toUpperCase();
            }
            String key = null;


            String value = null;

            String returnValue = null;
            Map<String, String> hzMap = hzInstance.getMap(mapName);
            switch (operation) {
                case "PUT":
                    key = m.group(4);
                    value = m.group(6);
                    returnValue = hzMap.put(key, value);
                    break;
                case "GET":
                    key = m.group(4);
                    returnValue = hzMap.get(key);
                    break;
                case "SIZE":
                    returnValue = String.valueOf(hzMap.size());
                    break;
                default:
                    System.out.println("Invalid statement ");
            }
            if (returnValue != null)
                System.out.println("ret = " + returnValue);

        } catch (Exception e) {
            System.out.println("Invalid statement " + e.getMessage());
            return;
        }
    }


}
