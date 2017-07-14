package util;

import models.Admin;
import play.Environment;
import play.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by anuradha_uduwage on 7/13/17.
 */

@Singleton
public class DemoData {

    public Admin admin1;

    @Inject
    public DemoData(Environment environment) {
        if (environment.isDev() || environment.isTest()) {
            if (Admin.findByEmailAndPassword("admin1@demo.com", "password") == null) {
                Logger.info("Loading Demo Data");

                admin1 = new Admin();

                admin1.setEmail("admin1@demo.com");
                admin1.setPassword("password");
                admin1.save();
            }
        }
    }
}