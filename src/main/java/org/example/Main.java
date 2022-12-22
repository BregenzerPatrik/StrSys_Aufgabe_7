package org.example;

import dataCreationPackage.datapointCreator;
import dataCreationPackage.datapointCreatorMock;
import esperVesper.EsperProcessor;

public class Main {
    public static void main(String[] args) {
        System.setProperty(org.slf4j.simple.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "off");
        EsperProcessor consumer = new EsperProcessor();
        Thread consume = new Thread(consumer);
        consume.start();
        //DatapointCreator starten

        //datapointCreator creator = new datapointCreator(5, -20.0, 69.0, 420, 1337, 0, 3);
        datapointCreatorMock creator = new datapointCreatorMock();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Thread publish = new Thread(creator);
        publish.start();
    }
}