package model;

import controller.Logging;
import model.transports.Transport;
import org.junit.Assert;
import org.junit.Test;

public class JUnitTest {

    @Test
    public void testAddArrayList(int count, String file, Transport testTransport) {
        long startTime = System.currentTimeMillis();
        testTransport.addDefaultCar(count, file);
        long endTime = System.currentTimeMillis();
//        Logging.log(file, this, "Test | AddArrayList | " + count + " | "+ (endTime - startTime));
    }

    @Test
    public void testAddHashMap(int count, String file, Transport testTransport) {
        long startTime = System.currentTimeMillis();
        testTransport.addDefaultCarMap(count, file);
        long endTime = System.currentTimeMillis();
//        Logging.log(file, this, "Test | AddHashMap | " + count + " | "+ (endTime - startTime));
    }
}
