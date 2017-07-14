package util;

import com.google.inject.AbstractModule;


/**
 * Created by anuradha_uduwage on 7/13/17.
 */
public class DemoDataModule extends AbstractModule {

    protected void configure() {
        bind(DemoData.class).asEagerSingleton();
    }
}
