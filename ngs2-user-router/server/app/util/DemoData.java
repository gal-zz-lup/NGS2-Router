package util;

import models.Admin;
import models.UserInfo;
import play.Environment;
import play.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.time.Instant;

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
            } else {
                Logger.info("User already exists in the database");
            }

            if (UserInfo.find.findRowCount() == 0) {
                UserInfo u1 = new UserInfo();
                u1.setArrivalTime(Timestamp.from(Instant.now()));
                u1.setGallupId("testgallupid1");
                u1.setLanguage("en");
                u1.setRandomizedId("ABCD1234");
                u1.setStatus("WAITING");
                u1.setSampleGroup("1");
                u1.save();

                UserInfo u2 = new UserInfo();
                u2.setArrivalTime(Timestamp.from(Instant.now()));
                u2.setGallupId("testgallupid2");
                u2.setLanguage("en");
                u2.setRandomizedId("EFGH5678");
                u2.setStatus("WAITING");
                u2.setSampleGroup("2");
                u2.save();

                UserInfo u3 = new UserInfo();
                u3.setArrivalTime(Timestamp.from(Instant.now()));
                u3.setGallupId("testgallupid3");
                u3.setLanguage("en");
                u3.setRandomizedId("IJKL2468");
                u3.setStatus("WAITING");
                u3.setSampleGroup("3");
                u3.save();
            }
        }
    }
}