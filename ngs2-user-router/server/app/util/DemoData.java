package util;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import models.Admin;
import models.Experiment;
import models.ExperimentInstance;
import models.UserInfo;
import play.Environment;
import play.Logger;
import play.db.ebean.EbeanConfig;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * Created by anuradha_uduwage on 7/13/17.
 */

@Singleton
public class DemoData {

  private final EbeanServer ebeanServer;

  @Inject
  public DemoData(Environment environment, EbeanConfig ebeanConfig) {
    this.ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());

    if (environment.isDev() || environment.isTest()) {
      Logger.debug("Loading demo data");
      if (ebeanServer.find(Admin.class).findCount() == 0) {
        Logger.debug("Loading first Admin");
        Admin admin1 = new Admin();

        admin1.setEmail("admin1@demo.com");
        admin1.setPassword("password");
        admin1.save();
      } else {
        Logger.debug("Admin table is not empty");
      }

      if (ebeanServer.find(UserInfo.class).findCount() == 0) {
        Logger.debug("Loading demo Users");
        UserInfo u1 = new UserInfo();
        u1.setArrivalTime(Timestamp.from(Instant.now()));
        u1.setGallupId("testgallupid1");
        u1.setLanguage("en");
        u1.setRandomizedId("ABCD1234");
        u1.setStatus("NEW");
        u1.setSampleGroup("1");
        u1.save();

        UserInfo u2 = new UserInfo();
        u2.setArrivalTime(Timestamp.from(Instant.now()));
        u2.setGallupId("testgallupid2");
        u2.setLanguage("en");
        u2.setRandomizedId("EFGH5678");
        u2.setStatus("NEW");
        u2.setSampleGroup("2");
        u2.save();

        UserInfo u3 = new UserInfo();
        u3.setArrivalTime(Timestamp.from(Instant.now()));
        u3.setGallupId("testgallupid3");
        u3.setLanguage("en");
        u3.setRandomizedId("IJKL2468");
        u3.setStatus("NEW");
        u3.setSampleGroup("3");
        u3.save();
      } else {
        Logger.debug("UserInfo table is not empty");
      }

      if (ebeanServer.find(Experiment.class).findCount() == 0) {
        Logger.debug("Loading demo Experiments");
        Experiment e1 = new Experiment();
        e1.setExperimentName("experiment-1");

        ExperimentInstance ei1 = new ExperimentInstance();
        ei1.nParticipants = 20;
        ei1.experiment = e1;
        ei1.experimentInstanceName = "e1_2017-08-08-01";
        ei1.experimentInstanceUrlActual = "http://brdbrd.net";
        ei1.experimentInstanceUrlShort = "";
        ei1.priority = 1;
        ei1.status = "ACTIVE";

        ExperimentInstance ei2 = new ExperimentInstance();
        ei2.nParticipants = 25;
        ei2.experiment = e1;
        ei2.experimentInstanceName = "e1_2017-08-08-02";
        ei2.experimentInstanceUrlActual = "http://xkcd.com";
        ei2.experimentInstanceUrlShort = "";
        ei2.priority = 2;
        ei2.status = "ACTIVE";

        ExperimentInstance ei3 = new ExperimentInstance();
        ei3.nParticipants = 15;
        ei3.experiment = e1;
        ei3.experimentInstanceName = "e1_2017-08-08-03";
        ei3.experimentInstanceUrlActual = "http://breadboard.yale.edu";
        ei3.experimentInstanceUrlShort = "";
        ei3.priority = 3;
        ei3.status = "ACTIVE";

        e1.experimentInstances.add(ei1);
        e1.experimentInstances.add(ei2);
        e1.experimentInstances.add(ei3);

        e1.save();
      } else {
        Logger.debug("Experiment table is not empty");
      }

    }
  }
}