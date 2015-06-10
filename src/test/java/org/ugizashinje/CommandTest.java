package org.ugizashinje;


import org.junit.Test;

public class CommandTest {

    private Command underTest = new Command(null);

    public void testExecute(){
        String cmd = " m.put  1  2    ";
        underTest.execute(cmd);
    }
}
