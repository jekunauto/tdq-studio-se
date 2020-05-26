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
package org.talend.dataprofiler.core.ui.editor.analysis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.part.FileEditorInput;
import org.talend.commons.emf.FactoriesUtil;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.PersistenceException;
import org.talend.core.model.context.ContextUtils;
import org.talend.core.model.context.ContextUtils.ContextItemParamMap;
import org.talend.core.model.context.JobContextManager;
import org.talend.core.model.context.link.ContextLinkService;
import org.talend.core.model.context.link.ContextParamLink;
import org.talend.core.model.context.link.ItemContextLink;
import org.talend.core.model.process.IContext;
import org.talend.core.model.process.IContextParameter;
import org.talend.core.model.properties.ContextItem;
import org.talend.core.model.properties.Item;
import org.talend.core.repository.model.IRepositoryFactory;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.repository.model.RepositoryFactoryProvider;
import org.talend.dataprofiler.core.helper.ContextViewHelper;
import org.talend.dataprofiler.core.i18n.internal.DefaultMessagesImpl;
import org.talend.dataprofiler.core.ui.IRuningStatusListener;
import org.talend.dataprofiler.core.ui.action.actions.DefaultSaveAction;
import org.talend.dataprofiler.core.ui.action.actions.RunAnalysisAction;
import org.talend.dataprofiler.core.ui.editor.SupportContextEditor;
import org.talend.dataprofiler.core.ui.editor.TdEditorToolBar;
import org.talend.dataprofiler.core.ui.events.EventEnum;
import org.talend.dataprofiler.core.ui.events.EventManager;
import org.talend.dataprofiler.core.ui.events.EventReceiver;
import org.talend.dataprofiler.core.ui.utils.WorkbenchUtils;
import org.talend.dataquality.analysis.Analysis;
import org.talend.dataquality.analysis.AnalysisType;
import org.talend.dataquality.analysis.ExecutionLanguage;
import org.talend.dataquality.helpers.AnalysisHelper;
import org.talend.dataquality.properties.TDQAnalysisItem;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.dq.helper.PropertyHelper;
import org.talend.dq.helper.ProxyRepositoryManager;
import org.talend.dq.helper.resourcehelper.AnaResourceFileHelper;
import org.talend.dq.nodes.AnalysisRepNode;
import org.talend.dq.nodes.ReportAnalysisRepNode;
import org.talend.dq.writer.impl.ElementWriterFactory;
import org.talend.repository.UpdateRepositoryUtils;
import org.talend.repository.model.IRepositoryNode;
import org.talend.repository.model.RepositoryConstants;
import org.talend.utils.sugars.ReturnCode;

/**
 * @author rli
 *
 */
public class AnalysisEditor extends SupportContextEditor {

    private static Logger log = Logger.getLogger(AnalysisEditor.class);

    public static final String RESULT_PAGE = "SecondPage";//$NON-NLS-1$

    public static final String MASTER_PAGE = "MasterPage";//$NON-NLS-1$

    private static final String ANALYSIS_RESULTS = DefaultMessagesImpl.getString("AnalysisEditor.analysisResult"); //$NON-NLS-1$

    private static final String ANALYSIS_SETTINGS = DefaultMessagesImpl.getString("AnalysisEditor.analysisSettings"); //$NON-NLS-1$

    private static final int RESULT_PAGE_INDEX = 1;

    private AbstractAnalysisResultPage resultPage;

    private AnalysisType analysisType;

    private RunAnalysisAction runAction;

    // MOD xqliu 2009-07-02 bug 7687
    private DefaultSaveAction saveAction;

    // ~

    // Added 20130725 TDQ-7639 yyin
    private EventReceiver refreshReceiver = null;

    private EventReceiver checkBeforeRunReceiver = null;

    private EventReceiver refresh2ShowMatchIndicator = null;

    private EventReceiver reopenEditor = null;

    // Added TDQ-8787 2014-06-16 yyin
    private EventReceiver registerDynamicEvent = null;

    private EventReceiver unRegisterDynamicEvent = null;

    private boolean isRefreshResultPage = false;

    /**
     *
     */
    public AnalysisEditor() {

    }

    @Override
    protected void addPages() {

        TdEditorToolBar toolbar = getToolBar();
        if (toolbar != null) {
            saveAction = new DefaultSaveAction(this);
            runAction = new RunAnalysisAction();
            toolbar.addActions(saveAction, runAction);
        }

        switch (analysisType) {

        case COLUMN_CORRELATION:
            masterPage = new CorrelationAnalysisDetailsPage(this, MASTER_PAGE, ANALYSIS_SETTINGS);
            resultPage = new CorrelationAnalysisResultPage(this, RESULT_PAGE, ANALYSIS_RESULTS);
            break;
        case MULTIPLE_COLUMN:
            masterPage = new ColumnAnalysisDetailsPage(this, MASTER_PAGE, ANALYSIS_SETTINGS);
            resultPage = new ColumnAnalysisResultPage(this, RESULT_PAGE, ANALYSIS_RESULTS);
            break;
        case CONNECTION:
            masterPage = new ConnectionAnalysisDetailsPage(this, MASTER_PAGE, ANALYSIS_SETTINGS);
            resultPage = new OverviewResultPage(this, RESULT_PAGE, ANALYSIS_RESULTS);
            break;
        case CATALOG:
            masterPage = new CatalogAnalysisDetailsPage(this, MASTER_PAGE, ANALYSIS_SETTINGS);
            resultPage = new OverviewResultPage(this, RESULT_PAGE, ANALYSIS_RESULTS);
            break;
        case SCHEMA:
            masterPage = new SchemaAnalysisDetailsPage(this, MASTER_PAGE, ANALYSIS_SETTINGS);
            resultPage = new OverviewResultPage(this, RESULT_PAGE, ANALYSIS_RESULTS);
            break;
        case COLUMNS_COMPARISON:
            masterPage = new RedundancyAnalysisDetailsPage(this, MASTER_PAGE, ANALYSIS_SETTINGS);
            resultPage = new RedundancyAnalysisResultPage(this, RESULT_PAGE, ANALYSIS_RESULTS);
            break;
        case TABLE:
            masterPage = new BusinessRuleAnalysisDetailsPage(this, MASTER_PAGE, ANALYSIS_SETTINGS);
            resultPage = new BusinessRuleAnalysisResultPage(this, RESULT_PAGE, ANALYSIS_RESULTS);
            break;
        case TABLE_FUNCTIONAL_DEPENDENCY:
            masterPage = new FunctionalDependencyAnalysisDetailsPage(this, MASTER_PAGE, ANALYSIS_SETTINGS);
            resultPage = new FunctionalDependencyAnalysisResultPage(this, RESULT_PAGE, ANALYSIS_RESULTS);
            break;
        case COLUMN_SET:
            masterPage = new ColumnSetAnalysisDetailsPage(this, MASTER_PAGE, ANALYSIS_SETTINGS);
            resultPage = new ColumnSetAnalysisResultPage(this, RESULT_PAGE, ANALYSIS_RESULTS);
            break;
        case MATCH_ANALYSIS:// Added 20130724 TDQ-7504
            masterPage = new MatchAnalysisDetailsPage(this, MASTER_PAGE, ANALYSIS_SETTINGS);
            resultPage = new MatchAnalysisResultPage(this, RESULT_PAGE, ANALYSIS_RESULTS);
            break;
        default:

        }
        try {
            if (masterPage != null) {
                addPage(masterPage);
                setPartName(masterPage.getIntactElemenetName());

                initContext();
                // Added 20130930 TDQ-8117, yyin
                // init the run analysis action, to give it the analysis item and listener
                this.runAction.setAnalysisItems(new TDQAnalysisItem[] { (TDQAnalysisItem) getMasterPage().getCurrentRepNode()
                        .getObject().getProperty().getItem() });
            }

            if (resultPage != null) {
                addPage(resultPage);
            }

        } catch (PartInitException e) {
            ExceptionHandler.process(e, Level.ERROR);
        }
        // Added 20130725 TDQ-7639 yyin : register the run analysis event, which need to refresh the pages
        registerUpdateExecutionEvent();

    }


    /**
     * init the context for the analysis.
     */
    private void initContext() {
        checkAndUpdateContext();
        Analysis analysis = getMasterPage().getCurrentModelElement();
//        contextManager = new JobContextManager(analysis.getContextType(), analysis.getDefaultContext());
        this.setLastRunContextGroupName(AnalysisHelper.getContextGroupName(analysis));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataprofiler.core.ui.editor.SupportContextEditor#checkAndUpdateContext()
     */
    @Override
    public void checkAndUpdateContext() {
        Analysis analysis = getMasterPage().getCurrentModelElement();

        Item currentItem = PropertyHelper.getProperty(analysis).getItem();
        EList<ContextType> anaContextType = analysis.getContextType();

        boolean onlySimpleShow = false;

        // Map<String, String> contextParamterRenamedMap = ContextHelper.getContextParamterRenamedMap(currentItem);
        // Map<ContextItem, List<IContext>> repositoryAddGroupContext = ruManager.getRepositoryAddGroupContext();
        // Map<ContextItem, List<IContext>> repositoryRemoveGroupContext = ruManager.getRepositoryRemoveGroupContext();
        // Map<ContextItem, Map<String, String>> contextRenamedMap =
        // ContextHelper.getContextParamterRenamedMap(currentItem);
        // Map<IContext, String> renameGroupContext = ruManager.getRenameContextGroup();


        // List<UpdateResult> contextResults = new ArrayList<UpdateResult>();
        // final IContextManager contextManager = this.getContextManager();
         contextManager =
                new JobContextManager(anaContextType, analysis.getDefaultContext());

        final String defaultContextName = contextManager.getDefaultContext().getName();
        // record the unsame
        ContextItemParamMap unsameMap = new ContextItemParamMap();
        // built in
        ContextItemParamMap builtInMap = new ContextItemParamMap();
        Set<String> builtInSet = new HashSet<String>();

        Map<Item, Map<String, String>> repositoryRenamedMap = new HashMap<Item, Map<String, String>>();

        ContextItemParamMap deleteParams = new ContextItemParamMap();

        final List<ContextItem> allContextItem = ContextUtils.getAllContextItem();

        Set<String> refContextIds = new HashSet<String>();

        // List<IProcess2> openedProcesses = UpdateManagerUtils.getOpenedProcess();

        Map<Item, Set<String>> existedParams = new HashMap<Item, Set<String>>();

        Map<String, Item> tempItemMap = new HashMap<String, Item>();// current real rep context
        ItemContextLink itemContextLink = null;
        try {
            itemContextLink =
                    ContextLinkService.getInstance().loadContextLinkFromJson(currentItem);
        } catch (PersistenceException e) {
            ExceptionHandler.process(e);
        }

        for (IContext context : contextManager.getListContext()) {
            for (IContextParameter param : context.getContextParameterList()) {
                if (!param.isBuiltIn()) {
                    String source = param.getSource();
                    String paramName = param.getName();
                    refContextIds.add(source);
                    ContextParamLink paramLink = null;
                    if (itemContextLink != null) {
                        paramLink = itemContextLink
                                .findContextParamLinkByName(param.getSource(), context.getName(), param.getName());
                    }

                    Item item = tempItemMap.get(source);
                    if (item == null) {
                        item = ContextUtils.findContextItem(allContextItem, source);
                        tempItemMap.put(source, item);
                    }
                    if (item != null) {
                        boolean builtin = true;
                        final ContextType contextType = ContextUtils.getContextTypeByName(item, context.getName());
                        builtin = ContextUtils
                                .compareContextParameter(item, contextType, param, paramLink, repositoryRenamedMap,
                                        existedParams, unsameMap, deleteParams, onlySimpleShow,
                                        StringUtils.equals(context.getName(), defaultContextName));
                        if (!builtin && StringUtils.equals(source, currentItem.getProperty().getId())) {
                            builtin = true;
                        }
                        if (builtin) {
                            // built in
                            if (item != null) {
                                builtInMap.add(item, paramName);
                            } else {
                                builtInSet.add(paramName);
                            }
                        }
                    }
                }
            }
        }


        // checkNewAddParameterForRef(existedParams, contextManager, ContextUtils.isPropagateContextVariable());
        // // see 0004661: Add an option to propagate when add or remove a variable in a repository context to
        // // jobs/joblets.
        // checkPropagateContextVariable(contextResults, contextManager, deleteParams, allContextItem, refContextIds);


        // if have delete-->buildin
        // if have rename or update, popup to ask if update
        // if (ContextViewHelper.findAndUpdateContext(anaContextType, contextItem, null, true)) {
        // if (popupUpdateContextConfirmDialog() == Window.OK) {
        // if (!repositoryRenamedMap.isEmpty()) {
        // findAndUpdateFieldUseContext(analysis, repositoryRenamedMap.get(contextItem));
        // }
        // ElementWriterFactory.getInstance().createAnalysisWrite().save(analysis);
        // // refresh the analysis
        // WorkbenchUtils.refreshCurrentAnalysisEditor(analysis.getName());
        // }
        // }

        // if have rename or update, popup to ask if update
        if (!unsameMap.isEmpty() || !repositoryRenamedMap.isEmpty() || !builtInMap.isEmpty()) {
            // DefaultMessagesImpl.getString("DeleteModelElementConfirmDialog.confirmResourcesDelete")
            // confirmDialog
            if (popupUpdateContextConfirmDialog() == Window.OK) {
                // change current context

                // context group name A B, context variable: filter is "1=1"

                // case1: change context variable name: filter -->filter1 (still repository)
                // renamed
                if (!repositoryRenamedMap.isEmpty()) {
                    for (Item item : repositoryRenamedMap.keySet()) {
                        Map<String, String> nameMap = repositoryRenamedMap.get(item);
                        if (nameMap != null && !nameMap.isEmpty()) {
                            for (String newName : nameMap.keySet()) {
                                String oldName = nameMap.get(newName);
                                if (newName.equals(oldName)) {
                                    continue;
                                }

                                for (IContext context : contextManager.getListContext()) {
                                    for (IContextParameter param : context.getContextParameterList()) {
                                        if (param.isBuiltIn()) { // for buildin, no need to update
                                            continue;
                                        }
                                        if (oldName.equals(param.getName())) {
                                            ContextUtils
                                                    .updateParameterFromRepository(item, param, context.getName(),
                                                            nameMap);
                                        }
                                    }
                                }
                            }
                        }

                        // Set<String> nameSet = new HashSet<String>();
                        // nameSet.add(oldName);
                        // System.out.println("oldName: " + oldName);
                        //
                        // List<Object> parameterList = new ArrayList<Object>();
                        // parameterList.add(item);
                        // parameterList.add(oldName);
                        // parameterList.add(newName);
                        // }
                        // }
                    }
                    for (Map<String, String> renamedMap : repositoryRenamedMap.values()) {
                        ContextViewHelper.findAndUpdateFieldUseContext(analysis, renamedMap);
                    }
                }

                // case2: change context variable value: "1=1"-->"2=2" (still repository)
                // changed value
                if (!unsameMap.isEmpty()) {
                    for (Item item : unsameMap.getContexts()) {
                        Set<String> names = unsameMap.get(item);
                        // set unsameMap key context which name is in names to contextManager.getListContext();
                        if (names != null && !names.isEmpty()) {
                            for (IContext context : contextManager.getListContext()) {
                                for (IContextParameter param : context.getContextParameterList()) {
                                    if (param.isBuiltIn()) { // for buildin, no need to update
                                        continue;
                                    }
                                    for (String contextVariName : names) {
                                        if (contextVariName.equals(param.getName())) {
                                            ContextUtils.updateParameterFromRepository(item, param, context.getName());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // case3: delete context variable: --->change to buildIn mode
                // case4: delete context group--->change to buildIn mode
                if (!deleteParams.isEmpty()) {

                }

                if (!builtInMap.isEmpty()) {
                    for (Item item : builtInMap.getContexts()) {
                        Set<String> names = builtInMap.get(item);
                        if (names != null && !names.isEmpty()) {
                            System.out.println(UpdateRepositoryUtils.getRepositorySourceName(item));
                            System.out.println("buildInMap" + names.toString());

                        }
                    }
                }

                // case5: add new context group-->do nothing
                // case6: add new context variable-->do nothing
                // case7: change context group name A->C-->do nothing

                // save analysis
                contextManager.saveToEmf(analysis.getContextType());
                ElementWriterFactory.getInstance().createAnalysisWrite().save(analysis);

                // reload current model analysis
                getMasterPage().init(this);

            }
        }
    }

    /**
     * DOC msjian Comment method "popupUpdateContextConfirmDialog".
     * @return
     */
    private int popupUpdateContextConfirmDialog() {
        MessageDialog confirmDialog =
                new MessageDialog(null, "Update Detection", null,
                        "there have changes for the context used by " + this.getPartName()
                                + ", do you want to update now?",
                        MessageDialog.WARNING,
                        new String[] { IDialogConstants.OK_LABEL, IDialogConstants.CANCEL_LABEL }, 0);
        return confirmDialog.open();
    }

    @Override
    public void doSave(IProgressMonitor monitor) {
        if (masterPage != null && masterPage.isDirty()) {
            masterPage.doSave(monitor);
            setPartName(masterPage.getIntactElemenetName());
            // TDQ-11312
            if (this.getEditorInput() instanceof FileEditorInput) {
                this.setInput(masterPage.getEditorInput());
            }
            // reset the modified status of ContextManager according to the masterPage
            if (contextManager instanceof JobContextManager) {
                JobContextManager jobContextManager = (JobContextManager) contextManager;
                jobContextManager.setModified(masterPage.isDirty());
            }

        }
        setEditorObject(getMasterPage().getCurrentRepNode());
        super.doSave(monitor);

        masterPage.setOldDataproviderName(masterPage.getCurrentModelElement().getName());

    }

    @Override
    protected void firePropertyChange(final int propertyId) {
        if (masterPage.isActive()) {
            setRunActionButtonState(true);
        }
        // MOD klliu 2011-04-08 if masterPage is dirty,then button of SaveAction can been used.
        setSaveActionButtonState(masterPage.isDirty());
        super.firePropertyChange(propertyId);
    }

    @Override
    protected void translateInput(IEditorInput input) {
        // MOD klliu 2010-12-10
        Analysis findAnalysis = null;
        String label = "";//$NON-NLS-1$
        if (input instanceof AnalysisItemEditorInput) {
            AnalysisItemEditorInput fileEditorInput = (AnalysisItemEditorInput) input;
            findAnalysis = (Analysis) fileEditorInput.getModel();
            label = fileEditorInput.getName();
        } else if (input instanceof FileEditorInput) {
            FileEditorInput fileEditorInput = (FileEditorInput) input;
            IFile file = fileEditorInput.getFile();
            label = file.getFullPath().toString();
            if (FactoriesUtil.isAnalysisFile(file.getFileExtension())) {
                findAnalysis = AnaResourceFileHelper.getInstance().findAnalysis(file);
            }
        }
        if (findAnalysis != null) {
            analysisType = findAnalysis.getParameters().getAnalysisType();
        } else {
            log.error("Could not find an analysis in file: " + label);//$NON-NLS-1$
        }
    }

    @Override
    protected void pageChange(int newPageIndex) {
        // TDQ-11422 msjian: we should do save first when turn to result page
        if (newPageIndex == RESULT_PAGE_INDEX) {
            if (masterPage.isDirty()) {
                masterPage.doSave(null);
            }
            setSaveActionButtonState(false);
        }
        // TDQ-11422~

        changeListener();

        super.pageChange(newPageIndex);

        if (isRefreshResultPage) {
            resultPage.refresh(getMasterPage());
            isRefreshResultPage = false;
        } else {
            // Added TDQ-9241
            if (resultPage != null) {
                EventManager.getInstance().publish(resultPage.getAnalysisHandler().getAnalysis(),
                        EventEnum.DQ_DYNAMIC_SWITCH_MASTER_RESULT_PAGE, null);
            }
        }
    }

    /**
     * Getter for masterPage.
     *
     * @return the masterPage
     */
    @Override
    public AbstractAnalysisMetadataPage getMasterPage() {
        return (AbstractAnalysisMetadataPage) masterPage;
    }

    /**
     * Getter for resultPage.
     *
     * @return the resultPage
     */
    public AbstractAnalysisResultPage getResultPage() {
        return resultPage;
    }

    public void performGlobalAction(String id) {
        if (id.equals(RunAnalysisAction.ID)) {
            // TDQ-10748: make the ref project analysis can not run when press F6
            IRepositoryNode currentRepNode = this.getMasterPage().getCurrentRepNode();
            boolean isMainProject = false;
            if (currentRepNode instanceof ReportAnalysisRepNode) {
                isMainProject = ((ReportAnalysisRepNode) currentRepNode).getProject().isMainProject();
            } else if (currentRepNode instanceof AnalysisRepNode) {
                isMainProject = ((AnalysisRepNode) currentRepNode).getProject().isMainProject();
            }
            if (isMainProject && !ProxyRepositoryManager.getInstance().isReadOnly()) {
                runAction.run();
            }
            // TDQ-10748~
            return;
        }
        if (analysisType == AnalysisType.MULTIPLE_COLUMN) {
            ((ColumnAnalysisDetailsPage) masterPage).performGlobalAction(id);
        }
        if (analysisType == AnalysisType.TABLE) {
            ((BusinessRuleAnalysisDetailsPage) masterPage).performGlobalAction(id);
        }
    }

    public void setRefreshResultPage(boolean isRefreshRP) {
        this.isRefreshResultPage = isRefreshRP;
    }

    public AnalysisType getAnalysisType() {
        return analysisType;
    }

    /**
     * DOC bZhou Comment method "setRunActionButtonState".
     *
     * @param state
     */
    public void setRunActionButtonState(boolean state) {
        if (runAction != null) {
            runAction.setEnabled(state);
        }
    }

    /**
     * DOC bZhou Comment method "setSaveActionButtonState".
     *
     * @param state
     */
    public void setSaveActionButtonState(boolean state) {
        if (saveAction != null) {
            saveAction.setEnabled(state);
        }
    }

    /**
     *
     * DOC mzhao Comment method "getRunAnalysisAction".
     *
     * @return
     */
    public Action getRunAnalysisAction() {
        return runAction;
    }

    /**
     * DOC yyi Comment method "canRun".
     *
     * @return
     */
    public ReturnCode canRun() {
        return getMasterPage().canRun();
    }

    @Override
    public void setFocus() {
        super.setFocus();
        // don't invoke this method here, invoke it in IPartListener.partBroughtToTop()
        // WorkbenchUtils.autoChange2DataProfilerPerspective();
    }

    public ExecutionLanguage getUIExecuteEngin() {
        return this.getMasterPage().getUIExecuteEngin();
    }

    IFormPage activePageInstance;

    private void changeListener() {
        activePageInstance = getActivePageInstance();
        if (activePageInstance instanceof IRuningStatusListener) {
            runAction.setListener((IRuningStatusListener) activePageInstance);
        }

    }

    /**
     * currently will not open the editor of the analysis when running from menu, so, if the editor is opened and not
     * the current active one, the page will not know that the result is changed. so we need to add the event/listener
     * to them to handle this. Added 20130725 TDQ-7639
     *
     * @param analysis
     */
    private void registerUpdateExecutionEvent() {
        // register: check if the analysis need to be saved or if it can run before running it(from menu's RUN)
        checkBeforeRunReceiver = new EventReceiver() {

            @Override
            public boolean handle(Object data) {
                if (isDirty()) {
                    ReturnCode canSave = masterPage.canSave();
                    if (canSave.isOk()) {
                        // save the analysis before running

                        // MOD msjian TDQ-8225 : This save action won't invoke any remote repository action such as svn
                        // commit. TDQ-7508
                        IRepositoryFactory localRepository = RepositoryFactoryProvider
                                .getRepositoriyById(RepositoryConstants.REPOSITORY_LOCAL_ID);
                        IRepositoryFactory oldRepository = ProxyRepositoryFactory.getInstance()
                                .getRepositoryFactoryFromProvider();
                        ProxyRepositoryFactory.getInstance().setRepositoryFactoryFromProvider(localRepository);
                        try {
                            doSave(null);
                        } catch (Exception e) {
                            log.error(e, e);
                        } finally {
                            ProxyRepositoryFactory.getInstance().setRepositoryFactoryFromProvider(oldRepository);
                        }
                        // TDQ-8225~

                        setDirty(false);
                    } else {
                        if (canSave.getMessage() != null && !canSave.getMessage().equals(StringUtils.EMPTY)) {
                            MessageDialogWithToggle
                                    .openError(
                                            PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                                            DefaultMessagesImpl.getString("AbstractAnalysisMetadataPage.SaveAnalysis"), canSave.getMessage()); //$NON-NLS-1$
                        }
                        return false;
                    }
                }
                ReturnCode canRun = canRun();
                if (!canRun.isOk()) {
                    MessageDialogWithToggle.openError(null,
                            DefaultMessagesImpl.getString("RunAnalysisAction.runAnalysisError"), canRun//$NON-NLS-1$
                                    .getMessage());
                    return false;
                }
                // TDQ-8220 change the listener every time( master page or result page)
                changeListener();
                return true;
            }
        };
        EventManager.getInstance().register(getMasterPage().getCurrentModelElement(), EventEnum.DQ_ANALYSIS_CHECK_BEFORERUN,
                checkBeforeRunReceiver);

        // register: refresh the result page after running it from menu
        refreshReceiver = new EventReceiver() {

            @Override
            public boolean handle(Object data) {
                // MOD TDQ-7816: when the result page are not created, no need to refresh, only refresh master page is
                // enough;TDQ-8270 resultpage is null for overview type
                if (resultPage != null && resultPage.getManagedForm() != null) {
                    resultPage.refresh(getMasterPage());
                } else {
                    getMasterPage().refreshGraphicsInSettingsPage();
                }
                return true;
            }
        };
        EventManager.getInstance().register(getMasterPage().getCurrentModelElement(), EventEnum.DQ_ANALYSIS_RUN_FROM_MENU,
                refreshReceiver);

        // register: reopen this editor after reload its depended connection
        reopenEditor = new EventReceiver() {

            @Override
            public boolean handle(Object data) {
                // TDQ-13275 remove 'Display.getDefault().asyncExec' to avoid error with Multi-thread open editors.
                WorkbenchUtils.refreshCurrentAnalysisEditor(getMasterPage().getCurrentModelElement().getName());

                return true;
            }
        };
        EventManager.getInstance().register(getMasterPage().getCurrentModelElement().getName(),
                EventEnum.DQ_ANALYSIS_REOPEN_EDITOR, reopenEditor);

        // ADD msjian TDQ-8860 2014-4-30:only for column set analysis, when there have pattern(s) when java engine,show
        // all match indicator in the Indicators section.
        if (analysisType.equals(AnalysisType.COLUMN_SET)) {
            // register: refresh the dataprovider combobox when the name of the data provider is changed.
            refresh2ShowMatchIndicator = new EventReceiver() {

                @Override
                public boolean handle(Object data) {
                    ((ColumnSetAnalysisDetailsPage) getMasterPage()).refreshIndicatorsSection();
                    return true;
                }
            };
            EventManager.getInstance().register(getMasterPage().getCurrentModelElement(),
                    EventEnum.DQ_COLUMNSET_SHOW_MATCH_INDICATORS, refresh2ShowMatchIndicator);
        }
        // TDQ-8860~

        // Added TDQ8787 2014-06-16 yyin: for dynamic chart, create all charts before execute the analysis
        if (masterPage instanceof DynamicAnalysisMasterPage) {
            registerDynamicEvent = new EventReceiver() {

                @Override
                public boolean handle(Object data) {
                    if (masterPage.equals(activePageInstance)) {
                        ((DynamicAnalysisMasterPage) masterPage).registerDynamicEvent();
                    } else {
                        // register result page
                        if (resultPage != null) {
                            if (resultPage instanceof ColumnAnalysisResultPage) {
                                ((ColumnAnalysisResultPage) resultPage).registerDynamicEvent();
                            } else if (resultPage instanceof BusinessRuleAnalysisResultPage) {
                                ((BusinessRuleAnalysisResultPage) resultPage).registerDynamicEvent();
                            }
                        }
                    }
                    return true;
                }
            };
            EventManager.getInstance().register(getMasterPage().getCurrentModelElement(),
                    EventEnum.DQ_DYNAMIC_REGISTER_DYNAMIC_CHART, registerDynamicEvent);

            unRegisterDynamicEvent = new EventReceiver() {

                @Override
                public boolean handle(Object data) {
                    if (masterPage.equals(activePageInstance)) {
                        ((DynamicAnalysisMasterPage) masterPage).unRegisterDynamicEvent();
                    } else {
                        // register result page
                        if (resultPage != null) {
                            if (resultPage instanceof ColumnAnalysisResultPage) {
                                ((ColumnAnalysisResultPage) resultPage).unRegisterDynamicEvent();
                            } else if (resultPage instanceof BusinessRuleAnalysisResultPage) {
                                ((BusinessRuleAnalysisResultPage) resultPage).unRegisterDynamicEvent();
                            }
                        }
                    }
                    return true;
                }
            };
            EventManager.getInstance().register(getMasterPage().getCurrentModelElement(),
                    EventEnum.DQ_DYNAMIC_UNREGISTER_DYNAMIC_CHART, unRegisterDynamicEvent);

        }
    }

    /**
     * unregister the update execution event Added 20130725 TDQ-7639
     */
    @Override
    public void dispose() {
        EventManager.getInstance().clearEvent(getMasterPage().getCurrentModelElement(), EventEnum.DQ_ANALYSIS_CHECK_BEFORERUN);
        EventManager.getInstance().clearEvent(getMasterPage().getCurrentModelElement(), EventEnum.DQ_ANALYSIS_RUN_FROM_MENU);
        EventManager.getInstance().clearEvent(getMasterPage().getCurrentModelElement().getName(),
                EventEnum.DQ_ANALYSIS_REOPEN_EDITOR);

        // ADD msjian TDQ-8860 2014-4-30:only for column set analysis, when there have pattern(s) when java engine,show
        // all match indicator in the Indicators section.
        if (analysisType.equals(AnalysisType.COLUMN_SET)) {
            EventManager.getInstance().clearEvent(getMasterPage().getCurrentModelElement(),
                    EventEnum.DQ_COLUMNSET_SHOW_MATCH_INDICATORS);
        }
        // TDQ-8860~
        // Added TDQ8787 2014-06-16 yyin: for dynamic chart, unregister the create all chart event
        if (masterPage instanceof DynamicAnalysisMasterPage) {
            EventManager.getInstance().clearEvent(getMasterPage().getCurrentModelElement(),
                    EventEnum.DQ_DYNAMIC_REGISTER_DYNAMIC_CHART);
            EventManager.getInstance().clearEvent(getMasterPage().getCurrentModelElement(),
                    EventEnum.DQ_DYNAMIC_UNREGISTER_DYNAMIC_CHART);
        }// ~

        super.dispose();
    }
}
