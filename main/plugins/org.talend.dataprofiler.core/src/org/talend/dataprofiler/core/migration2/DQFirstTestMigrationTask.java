package org.talend.dataprofiler.core.migration2;

import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.talend.core.model.migration.AbstractItemMigrationTask;
import org.talend.core.model.properties.Item;


public class DQFirstTestMigrationTask extends AbstractItemMigrationTask {

    private static Logger log = Logger.getLogger(DQFirstTestMigrationTask.class);
    @Override
    public Date getOrder() {
        GregorianCalendar gc = new GregorianCalendar(2020, 5, 9, 0, 0, 0);
        return gc.getTime();
    }

    @Override
    public ExecutionResult execute(Item item) {
        log.error("Hello Talend! Hello the first test migration task!");
        return ExecutionResult.SUCCESS_NO_ALERT;
    }

}
