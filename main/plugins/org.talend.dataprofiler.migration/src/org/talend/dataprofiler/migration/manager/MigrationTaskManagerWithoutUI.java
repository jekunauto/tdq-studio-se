// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataprofiler.migration.manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.talend.commons.utils.VersionUtils;
import org.talend.dataprofiler.migration.IMigrationTask;
import org.talend.dataprofiler.migration.IMigrationTask.MigrationTaskCategory;
import org.talend.dataprofiler.migration.IWorkspaceMigrationTask;
import org.talend.dataprofiler.migration.IWorkspaceMigrationTask.MigrationTaskType;
import org.talend.dataprofiler.migration.MigrationPlugin;
import org.talend.dataprofiler.migration.helper.VersionComparator;
import org.talend.utils.ProductVersion;

/**
 * created by xqliu on 2013-11-7 Detailled comment
 *
 */
public class MigrationTaskManagerWithoutUI {

    private static Logger log = Logger.getLogger(MigrationTaskManagerWithoutUI.class);

    protected List<IMigrationTask> allMigrationTask;

    protected ProductVersion workspaceVersion;

    protected ProductVersion currentVersion;

    protected MigrationTaskType taskType;

    public MigrationTaskManagerWithoutUI(ProductVersion workspaceVersion) {
        this(null, workspaceVersion, null, null);
    }

    public MigrationTaskManagerWithoutUI(ProductVersion workspaceVersion, MigrationTaskType taskType) {
        this(null, workspaceVersion, null, taskType);
    }

    public MigrationTaskManagerWithoutUI(ProductVersion workspaceVersion, ProductVersion currentVersion,
            MigrationTaskType taskType) {
        this(null, workspaceVersion, currentVersion, taskType);
    }

    public MigrationTaskManagerWithoutUI(IMigrationTaskProvider taskProvider, ProductVersion workspaceVersion,
            ProductVersion currentVersion, MigrationTaskType taskType) {
        if (taskProvider == null) {
            taskProvider = new DefaultMigrationTaskProvider();
        }

        if (currentVersion == null) {
            this.currentVersion = MigrationPlugin.getDefault().getProductVersion();
        } else {
            this.currentVersion = currentVersion;
        }

        this.workspaceVersion = workspaceVersion;
        this.taskType = taskType;

        this.allMigrationTask = new ArrayList<IMigrationTask>();
        if (taskProvider != null) {
            allMigrationTask.addAll(Arrays.asList(taskProvider.getMigrationTasks()));
            sortTasks(allMigrationTask);
        }
    }

    /**
     * DOC bZhou Comment method "getValidTasks".
     *
     * @param workspaceVersion
     * @param currentVersion
     * @param tasks
     * @return
     */
    public static List<IMigrationTask> getValidTasks(ProductVersion workspaceVersion, ProductVersion currentVersion,
            List<IMigrationTask> tasks) {

        List<IMigrationTask> validTasks = new ArrayList<IMigrationTask>();

        log.info("------check valid migration begin--------------------");
        log.info("workspaceVersion: " + workspaceVersion.toString());
        log.info("currentVersion: " + currentVersion.toString());

        log
                .info("InternalVersion(org.talend.commons.runtime or .eclipseproduct): "
                        + VersionUtils.getInternalVersion());
        log.info("DisplayVersion: " + VersionUtils.getDisplayVersion());
        log.info("TalendVersion(org.talend.commons.runtime or talend.project): " + VersionUtils.getTalendVersion()); //$NON-NLS-1$


        for (IMigrationTask task : tasks) {
            if (task.getTaskCategory() == MigrationTaskCategory.WORKSPACE) {
                IWorkspaceMigrationTask wTask = (IWorkspaceMigrationTask) task;
                ProductVersion taskVersion = ProductVersion.fromString(wTask.getVersion());
                log.info("each task begin");
                log.info("task version set is : " + taskVersion.toString());
                if (taskVersion.compareTo(workspaceVersion) > 0 && taskVersion.compareTo(currentVersion) <= 0) {
                    log
                            .info("taskVersion.compareTo(workspaceVersion) > 0 && taskVersion.compareTo(currentVersion) <= 0");
                    log.info("taskID: " + task.getId() + " is valid.");
                    validTasks.add(task);
                } else if (VersionComparator.isEqual(taskVersion, workspaceVersion)) {
                    // come here means taskversion is the same or lower with workspace version,
                    // need to check the patch monthly release migration.
                    // for example: when workspace is 731 or 731R4
                    log.info("taskVersion == workspaceVersion");

                    // DisplayVersion: 7.3.1.20200417_1111-patch
                    String displayVersion = VersionUtils.getDisplayVersion();
                    if (displayVersion.endsWith("-patch")) { // means the studio with patch
                        // only to patch studio need to check these tasks
                        log.info("displayVersion endsWith -patch ");
                        /// taskVersionWithDate: taskVersion +"."+ task.getOrder() (e.g.:7.3.1.20200507)
                        Date d = task.getOrder();
                        d.setMonth(d.getMonth() - 1);
                        String taskVersionWithDate = taskVersion + "." + new SimpleDateFormat("yyyyMMdd").format(d);

                        if (displayVersion.compareTo(taskVersionWithDate) < 0) {// -1
                            log
                                    .info("task version+date " + taskVersionWithDate + " is later then DisplayVersion "
                                            + displayVersion + " so is valid task"); // 7.3.1.20200507
                            log.info("taskID: " + task.getId() + " is valid.");
                            validTasks.add(task);
                        } else {
                            log
                                    .info("task version+date " + taskVersionWithDate
                                            + " is NOT later then DisplayVersion " + displayVersion
                                            + " so is invalid task");
                            log.info("taskID: " + task.getId() + " is invalid.");
                        }

                    } else { // means 731 release studio without patch
                        // DisplayVersion: 7.3.1.20200214_0837
                        // then import 731 to 731 version task should be invalid
                        log.info("displayVersion does NOT endsWith -patch ");
                        log.info("taskID: " + task.getId() + " is invalid.");
                    }
                } else {
                    log.info("taskID: " + task.getId() + " is invalid.");
                }
            }

            if (task.getTaskCategory() == MigrationTaskCategory.PROJECT) {
                validTasks.add(task);
            }
        }
        log.info("------check valid migration end --------------------");

        return validTasks;
    }

    /**
     * DOC bZhou Comment method "getValidTasks".
     *
     * @return
     */
    public List<IMigrationTask> getValidTasks() {

        if (taskType != null) {
            return getTaskByType(taskType);
        } else {
            return getWorksapceTasks();
        }
    }

    /**
     * DOC bZhou Comment method "getWorksapceTasks".
     *
     * @param wVersion
     *
     * @return
     */
    public List<IMigrationTask> getWorksapceTasks() {
        List<IMigrationTask> validTasks = getValidTasks(workspaceVersion, currentVersion, allMigrationTask);

        Iterator<IMigrationTask> it = validTasks.iterator();

        while (it.hasNext()) {
            IMigrationTask task = it.next();
            if (task.getMigrationTaskType() == MigrationTaskType.DATABASE) {
                it.remove();
            }
        }

        List<IMigrationTask> resortList = new ArrayList<IMigrationTask>();
        for (IMigrationTask task : validTasks) {
            if (task.isModelTask()) {
                resortList.add(task);
            }
        }

        for (IMigrationTask task : validTasks) {
            if (!task.isModelTask()) {
                resortList.add(task);
            }
        }

        return resortList;
    }

    /**
     * DOC bZhou Comment method "getTaskByType".
     *
     * @param type
     * @param specifiedVersion
     * @return
     */
    public List<IMigrationTask> getTaskByType(MigrationTaskType type) {
        List<IMigrationTask> validTasks = new ArrayList<IMigrationTask>();

        for (IMigrationTask task : allMigrationTask) {
            if (task.getMigrationTaskType() == type) {
                validTasks.add(task);
            }
        }

        return getValidTasks(workspaceVersion, currentVersion, validTasks);
    }

    /**
     * DOC bZhou Comment method "sortTasks".
     *
     * @param tasks
     */
    private static void sortTasks(List<IMigrationTask> tasks) {
        Collections.sort(tasks, new Comparator<IMigrationTask>() {

            @Override
            public int compare(IMigrationTask o1, IMigrationTask o2) {
                if (o1.getOrder() == null || o2.getOrder() == null) {
                    return 0;
                }
                if (o1 instanceof IWorkspaceMigrationTask && o2 instanceof IWorkspaceMigrationTask) {
                    int compareResult = ((IWorkspaceMigrationTask) o1).getVersion().compareToIgnoreCase(
                            ((IWorkspaceMigrationTask) o2).getVersion());
                    if (compareResult != 0) {
                        return compareResult;
                    }

                }

                return o1.getOrder().compareTo(o2.getOrder());
            }

        });
    }

    /**
     * DOC bZhou Comment method "doMigrationTask".
     */
    public void doMigrationTaskWithoutUI() {
        doMigrationTaskWithoutUI(getValidTasks());
    }

    /**
     * DOC bZhou Comment method "doMigrationTask".
     *
     * @param tasks
     * @param monitor
     */
    public static void doMigrationTaskWithoutUI(List<IMigrationTask> tasks) {
        for (IMigrationTask task : tasks) {
            if (task.valid()) {
                if (!task.execute()) {
                    log.error("Migration Task failed: " + task.getName()); //$NON-NLS-1$
                } else {
                    if (log.isInfoEnabled()) {
                        log.info("Migration Task success: " + task.getId()); //$NON-NLS-1$
                    }
                }
            }
        }
    }
}
