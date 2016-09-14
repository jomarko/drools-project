package com.redhat;

import java.util.Collections;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Main {
    private static final int MAXIMUM = 10000;
    private static final int SLEEP = 1000;

    public static void main(String[] args) {
        KieServices ks = KieServices.Factory.get();
        KieContainer kcontainer = ks.getKieClasspathContainer("abcContainer");

        KieSession ksession = kcontainer.newKieSession("rules-session");

        ksession.setGlobal("maximum", MAXIMUM);

        for (int i = 0; i < MAXIMUM; i++) {
            long number = 2 + Math.round(Math.sin(i));
            for (int j = 0; j < number; j++) {
                ksession.insert(new String(Long.toString(number)));
            }
            ksession.insert(new NumberHolder(i));
            ksession.startProcess("com.redhat.process", Collections.<String, Object>singletonMap("round", i));

            ksession.fireAllRules();

            try { Thread.sleep(number * SLEEP); } catch (InterruptedException ex) {}
        }

        ksession.dispose();
    }

}
