// ============================================================================
//
// Copyright (C) 2006-2009 Talend Inc. - www.talend.com
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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.talend.cwm.helper.SwitchHelpers;
import org.talend.cwm.relational.TdTable;
import org.talend.cwm.softwaredeployment.TdDataProvider;
import org.talend.dataprofiler.core.ImageLib;
import org.talend.dataprofiler.core.PluginConstant;
import org.talend.dataprofiler.core.exception.DataprofilerCoreException;
import org.talend.dataprofiler.core.i18n.internal.DefaultMessagesImpl;
import org.talend.dataprofiler.core.model.TableIndicator;
import org.talend.dataprofiler.core.ui.action.actions.RunAnalysisAction;
import org.talend.dataprofiler.core.ui.editor.composite.AnalysisTableTreeViewer;
import org.talend.dataprofiler.core.ui.editor.composite.DataFilterComp;
import org.talend.dataprofiler.core.ui.editor.preview.CompositeIndicator;
import org.talend.dataprofiler.core.ui.editor.preview.TableIndicatorUnit;
import org.talend.dataprofiler.core.ui.editor.preview.model.ChartTypeStatesOperator;
import org.talend.dataprofiler.core.ui.editor.preview.model.states.IChartTypeStates;
import org.talend.dataprofiler.core.ui.utils.ChartUtils;
import org.talend.dataquality.analysis.Analysis;
import org.talend.dataquality.analysis.ExecutionLanguage;
import org.talend.dataquality.indicators.Indicator;
import org.talend.dq.analysis.TableAnalysisHandler;
import org.talend.dq.helper.EObjectHelper;
import org.talend.dq.helper.resourcehelper.AnaResourceFileHelper;
import org.talend.dq.helper.resourcehelper.PrvResourceFileHelper;
import org.talend.dq.indicators.preview.EIndicatorChartType;
import org.talend.utils.sugars.ReturnCode;
import orgomg.cwm.objectmodel.core.ModelElement;
import orgomg.cwm.resource.relational.Table;

/**
 * DOC xqliu class global comment. Detailled comment
 */
public class TableMasterDetailsPage extends AbstractAnalysisMetadataPage implements PropertyChangeListener {

    private static Logger log = Logger.getLogger(TableMasterDetailsPage.class);

    private String execLang = ExecutionLanguage.SQL.getLiteral();

    AnalysisTableTreeViewer treeViewer;

    public AnalysisTableTreeViewer getTreeViewer() {
        return treeViewer;
    }

    DataFilterComp dataFilterComp;

    TableAnalysisHandler analysisHandler;

    public TableAnalysisHandler getAnalysisHandler() {
        return analysisHandler;
    }

    private TableIndicator[] currentTableIndicators;

    private String stringDataFilter;

    private Composite chartComposite;

    private ScrolledForm form;

    public ScrolledForm getForm() {
        return form;
    }

    public void setForm(ScrolledForm form) {
        this.form = form;
    }

    private static final int TREE_MAX_LENGTH = 400;

    private Composite[] previewChartCompsites;

    public Composite[] getPreviewChartCompsites() {
        return previewChartCompsites;
    }

    public void setPreviewChartCompsites(Composite[] previewChartCompsites) {
        this.previewChartCompsites = previewChartCompsites;
    }

    private AnalysisEditor currentEditor;

    private Section dataFilterSection = null;

    private Section analysisTableSection = null;

    private Section previewSection = null;

    private List<ExpandableComposite> previewChartList = null;

    /**
     * DOC xqliu TableMasterDetailsPage constructor comment.
     * 
     * @param editor
     * @param id
     * @param title
     */
    public TableMasterDetailsPage(FormEditor editor, String id, String title) {
        super(editor, id, title);
        currentEditor = (AnalysisEditor) editor;
    }

    public TableIndicator[] getCurrentTableIndicators() {
        return currentTableIndicators;
    }

    public void initialize(FormEditor editor) {
        super.initialize(editor);
        analysisHandler = new TableAnalysisHandler();
        analysisHandler.setAnalysis((Analysis) this.currentModelElement);
        stringDataFilter = analysisHandler.getStringDataFilter();
        EList<ModelElement> analyzedTables = analysisHandler.getAnalyzedTables();
        List<TableIndicator> tableIndicatorList = new ArrayList<TableIndicator>();
        for (ModelElement element : analyzedTables) {
            TdTable tdTable = SwitchHelpers.TABLE_SWITCH.doSwitch(element);
            if (tdTable == null) {
                continue;
            }
            TableIndicator currentTableIndicator = new TableIndicator(tdTable);
            Collection<Indicator> indicatorList = analysisHandler.getIndicators(tdTable);
            currentTableIndicator.setIndicators(indicatorList.toArray(new Indicator[indicatorList.size()]));
            tableIndicatorList.add(currentTableIndicator);
        }
        currentTableIndicators = tableIndicatorList.toArray(new TableIndicator[tableIndicatorList.size()]);
    }

    @Override
    protected void createFormContent(IManagedForm managedForm) {
        this.form = managedForm.getForm();
        Composite body = form.getBody();

        body.setLayout(new GridLayout());
        SashForm sForm = new SashForm(body, SWT.NULL);
        sForm.setLayoutData(new GridData(GridData.FILL_BOTH));

        topComp = toolkit.createComposite(sForm);
        topComp.setLayoutData(new GridData(GridData.FILL_BOTH));
        topComp.setLayout(new GridLayout());
        metadataSection = creatMetadataSection(form, topComp);
        form.setText(DefaultMessagesImpl.getString("TableMasterDetailsPage.tableAna")); //$NON-NLS-1$
        metadataSection.setText(DefaultMessagesImpl.getString("TableMasterDetailsPage.analysisMeta")); //$NON-NLS-1$
        metadataSection.setDescription(DefaultMessagesImpl.getString("TableMasterDetailsPage.setPropOfAnalysis")); //$NON-NLS-1$

        createAnalysisTablesSection(form, topComp);

        createDataFilterSection(form, topComp);

        Composite previewComp = toolkit.createComposite(sForm);
        previewComp.setLayoutData(new GridData(GridData.FILL_BOTH));
        previewComp.setLayout(new GridLayout());

        createPreviewSection(form, previewComp);

        currentEditor
                .registerSections(new Section[] { analysisTableSection, metadataSection, dataFilterSection, previewSection });
    }

    void createAnalysisTablesSection(final ScrolledForm form, Composite anasisDataComp) {
        analysisTableSection = createSection(form, anasisDataComp, DefaultMessagesImpl
                .getString("TableMasterDetailsPage.analyzeTable"), false, null); //$NON-NLS-1$

        Composite topComp = toolkit.createComposite(analysisTableSection);
        topComp.setLayout(new GridLayout());

        Hyperlink tblBtn = toolkit.createHyperlink(topComp,
                DefaultMessagesImpl.getString("TableMasterDetailsPage.selectTable"), SWT.NONE); //$NON-NLS-1$
        GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).applyTo(tblBtn);
        tblBtn.addHyperlinkListener(new HyperlinkAdapter() {

            public void linkActivated(HyperlinkEvent e) {
                openTableSelectionDialog();
            }

        });

        Composite actionBarComp = toolkit.createComposite(topComp);
        GridLayout gdLayout = new GridLayout();
        gdLayout.numColumns = 2;
        actionBarComp.setLayout(gdLayout);

        ImageHyperlink collapseAllImageLink = toolkit.createImageHyperlink(actionBarComp, SWT.NONE);
        collapseAllImageLink.setToolTipText(DefaultMessagesImpl.getString("TableMasterDetailsPage.collapseAllTables")); //$NON-NLS-1$
        collapseAllImageLink.setImage(ImageLib.getImage(ImageLib.COLLAPSE_ALL));
        collapseAllImageLink.addHyperlinkListener(new HyperlinkAdapter() {

            public void linkActivated(HyperlinkEvent e) {
                TreeItem[] items = treeViewer.getTree().getItems();
                expandTreeItems(items, false);
                packOtherColumns();
            }
        });

        ImageHyperlink expandAllImageLink = toolkit.createImageHyperlink(actionBarComp, SWT.NONE);
        expandAllImageLink.setToolTipText(DefaultMessagesImpl.getString("TableMasterDetailsPage.expandAllTables")); //$NON-NLS-1$
        expandAllImageLink.setImage(ImageLib.getImage(ImageLib.EXPAND_ALL));
        expandAllImageLink.addHyperlinkListener(new HyperlinkAdapter() {

            public void linkActivated(HyperlinkEvent e) {
                TreeItem[] items = treeViewer.getTree().getItems();
                expandTreeItems(items, true);
                packOtherColumns();

            }
        });

        Composite treeComp = toolkit.createComposite(topComp, SWT.NONE);
        GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(treeComp);
        treeComp.setLayout(new GridLayout());
        ((GridData) treeComp.getLayoutData()).heightHint = TREE_MAX_LENGTH;

        treeViewer = new AnalysisTableTreeViewer(treeComp, this);
        treeViewer.setDirty(false);
        treeViewer.addPropertyChangeListener(this);
        analysisTableSection.setClient(topComp);

    }

    /**
     * 
     * DOC xqliu Comment method "packOtherColumns".
     */
    private void packOtherColumns() {
        TreeColumn columns[] = treeViewer.getTree().getColumns();
        for (TreeColumn column : columns) {
            column.pack();
        }
    }

    /**
     * 
     * DOC xqliu Comment method "expandTreeItems".
     * 
     * @param items
     * @param expandOrCollapse
     */
    private void expandTreeItems(TreeItem[] items, boolean expandOrCollapse) {
        for (TreeItem item : items) {
            item.setExpanded(expandOrCollapse);
            TreeItem[] its = item.getItems();
            if (its != null && its.length > 0) {
                expandTreeItems(its, expandOrCollapse);
            }
        }
    }

    public void openTableSelectionDialog() {
        TableIndicator[] tableIndicators = treeViewer.getTableIndicator();
        List<Table> tableList = new ArrayList<Table>();
        for (TableIndicator tableIndicator : tableIndicators) {
            tableList.add(tableIndicator.getTdTable());
        }
        TablesSelectionDialog dialog = new TablesSelectionDialog(null, DefaultMessagesImpl
                .getString("TableMasterDetailsPage.tableSelection"), tableList, DefaultMessagesImpl //$NON-NLS-1$
                .getString("TableMasterDetailsPage.tableSelections")); //$NON-NLS-1$
        if (dialog.open() == Window.OK) {
            Object[] tables = dialog.getResult();
            treeViewer.setInput(tables);
            return;
        }
    }

    /**
     * @param form
     * @param toolkit
     * @param anasisDataComp
     */
    void createDataFilterSection(final ScrolledForm form, Composite anasisDataComp) {
        dataFilterSection = createSection(
                form,
                anasisDataComp,
                DefaultMessagesImpl.getString("TableMasterDetailsPage.dataFilter"), false, DefaultMessagesImpl.getString("TableMasterDetailsPage.editDataFilter")); //$NON-NLS-1$ //$NON-NLS-2$

        Composite sectionClient = toolkit.createComposite(dataFilterSection);
        dataFilterComp = new DataFilterComp(sectionClient, stringDataFilter);
        dataFilterComp.addPropertyChangeListener(this);
        dataFilterSection.setClient(sectionClient);
    }

    void createPreviewSection(final ScrolledForm form, Composite parent) {
        previewSection = createSection(form, parent, DefaultMessagesImpl.getString("TableMasterDetailsPage.graphics"), true, //$NON-NLS-1$
                DefaultMessagesImpl.getString("TableMasterDetailsPage.space")); //$NON-NLS-1$
        previewSection.setLayoutData(new GridData(GridData.FILL_BOTH));
        Composite sectionClient = toolkit.createComposite(previewSection);
        sectionClient.setLayout(new GridLayout());
        sectionClient.setLayoutData(new GridData(GridData.FILL_BOTH));

        Composite actionBarComp = toolkit.createComposite(sectionClient);
        GridLayout gdLayout = new GridLayout();
        gdLayout.numColumns = 2;
        actionBarComp.setLayout(gdLayout);

        ImageHyperlink collapseAllImageLink = toolkit.createImageHyperlink(actionBarComp, SWT.NONE);
        collapseAllImageLink.setToolTipText(DefaultMessagesImpl.getString("TableMasterDetailsPage.collapseAllTables")); //$NON-NLS-1$
        collapseAllImageLink.setImage(ImageLib.getImage(ImageLib.COLLAPSE_ALL));
        collapseAllImageLink.addHyperlinkListener(new HyperlinkAdapter() {

            public void linkActivated(HyperlinkEvent e) {
                if (previewChartList != null && !previewChartList.isEmpty()) {
                    for (ExpandableComposite comp : previewChartList) {
                        comp.setExpanded(false);
                        comp.getParent().pack();
                    }
                }
            }
        });

        ImageHyperlink expandAllImageLink = toolkit.createImageHyperlink(actionBarComp, SWT.NONE);
        expandAllImageLink.setToolTipText(DefaultMessagesImpl.getString("TableMasterDetailsPage.expandAllTables")); //$NON-NLS-1$
        expandAllImageLink.setImage(ImageLib.getImage(ImageLib.EXPAND_ALL));
        expandAllImageLink.addHyperlinkListener(new HyperlinkAdapter() {

            public void linkActivated(HyperlinkEvent e) {
                if (previewChartList != null && !previewChartList.isEmpty()) {
                    for (ExpandableComposite comp : previewChartList) {
                        comp.setExpanded(true);
                        comp.getParent().pack();
                    }
                }

            }
        });

        ImageHyperlink refreshBtn = toolkit.createImageHyperlink(sectionClient, SWT.NONE);
        refreshBtn.setText(DefaultMessagesImpl.getString("TableMasterDetailsPage.refreshGraphics")); //$NON-NLS-1$
        refreshBtn.setImage(ImageLib.getImage(ImageLib.SECTION_PREVIEW));
        final Label message = toolkit.createLabel(sectionClient, DefaultMessagesImpl
                .getString("TableMasterDetailsPage.spaceWhite")); //$NON-NLS-1$
        message.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
        message.setVisible(false);
        GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).applyTo(sectionClient);

        chartComposite = toolkit.createComposite(sectionClient);
        chartComposite.setLayout(new GridLayout());
        chartComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

        final Analysis analysis = analysisHandler.getAnalysis();

        refreshBtn.addHyperlinkListener(new HyperlinkAdapter() {

            public void linkActivated(HyperlinkEvent e) {

                for (Control control : chartComposite.getChildren()) {
                    control.dispose();
                }

                boolean analysisStatue = analysis.getResults().getResultMetadata() != null
                        && analysis.getResults().getResultMetadata().getExecutionDate() != null;

                if (!analysisStatue) {
                    boolean returnCode = MessageDialog.openConfirm(null, DefaultMessagesImpl
                            .getString("TableMasterDetailsPage.string0"), //$NON-NLS-1$
                            DefaultMessagesImpl.getString("TableMasterDetailsPage.string1")); //$NON-NLS-1$

                    if (returnCode) {
                        new RunAnalysisAction(TableMasterDetailsPage.this).run();
                        message.setVisible(false);
                    } else {
                        createPreviewCharts(form, chartComposite, false);
                        message.setText(DefaultMessagesImpl.getString("TableMasterDetailsPage.warning")); //$NON-NLS-1$
                        message.setVisible(true);
                    }
                } else {
                    createPreviewCharts(form, chartComposite, true);
                }

                chartComposite.layout();
                form.reflow(true);
            }

        });

        previewSection.setClient(sectionClient);
    }

    public void createPreviewCharts(final ScrolledForm form, final Composite composite, final boolean isCreate) {
        previewChartList = new ArrayList<ExpandableComposite>();
        for (final TableIndicator tableIndicator : this.treeViewer.getTableIndicator()) {
            final TdTable table = tableIndicator.getTdTable();
            ExpandableComposite exComp = toolkit.createExpandableComposite(composite, ExpandableComposite.TREE_NODE
                    | ExpandableComposite.CLIENT_INDENT);
            exComp.setText(DefaultMessagesImpl.getString("TableMasterDetailsPage.table") + table.getName()); //$NON-NLS-1$
            exComp.setLayout(new GridLayout());
            exComp.setLayoutData(new GridData(GridData.FILL_BOTH));
            exComp.setData(tableIndicator);
            previewChartList.add(exComp);

            final Composite comp = toolkit.createComposite(exComp);
            comp.setLayout(new GridLayout());
            comp.setLayoutData(new GridData(GridData.FILL_BOTH));

            if (tableIndicator.getIndicators().length != 0) {
                IRunnableWithProgress rwp = new IRunnableWithProgress() {

                    public void run(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                        monitor.beginTask(DefaultMessagesImpl.getString("TableMasterDetailsPage.createPreview") //$NON-NLS-1$
                                + table.getName(), IProgressMonitor.UNKNOWN);
                        Display.getDefault().syncExec(new Runnable() {

                            public void run() {
                                Map<EIndicatorChartType, List<TableIndicatorUnit>> indicatorComposite = CompositeIndicator
                                        .getInstance().getTableIndicatorComposite(tableIndicator);
                                for (EIndicatorChartType chartType : indicatorComposite.keySet()) {
                                    List<TableIndicatorUnit> units = indicatorComposite.get(chartType);
                                    if (!units.isEmpty()) {
                                        final IChartTypeStates chartTypeState = ChartTypeStatesOperator.getChartStateTable(
                                                chartType, units, tableIndicator);

                                        List<JFreeChart> charts = chartTypeState.getChartList();
                                        if (charts != null) {
                                            for (JFreeChart chart : charts) {
                                                final ChartComposite chartComp = new ChartComposite(comp, SWT.NONE, chart, true);

                                                GridData gd = new GridData();
                                                gd.widthHint = 550;
                                                gd.heightHint = 250;
                                                chartComp.setLayoutData(gd);

                                                addListenerToChartComp(chartComp, chartTypeState);
                                            }
                                        }
                                    }
                                }
                            }
                        });

                        monitor.done();
                    }

                };

                try {
                    new ProgressMonitorDialog(getSite().getShell()).run(true, false, rwp);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            exComp.addExpansionListener(new ExpansionAdapter() {

                @Override
                public void expansionStateChanged(ExpansionEvent e) {
                    getChartComposite().layout();
                    form.reflow(true);
                    composite.pack();
                }

            });

            exComp.setExpanded(true);
            exComp.setClient(comp);
        }
        if (!previewChartList.isEmpty()) {
            this.previewChartCompsites = previewChartList.toArray(new Composite[previewChartList.size()]);
        }
    }

    private void addListenerToChartComp(final ChartComposite chartComp, final IChartTypeStates chartTypeState) {
        chartComp.addChartMouseListener(new ChartMouseListener() {

            public void chartMouseClicked(ChartMouseEvent event) {
                final String referenceLink = chartTypeState.getReferenceLink();
                if (event.getTrigger().getButton() == 1 && referenceLink != null) {
                    Menu menu = new Menu(chartComp.getShell(), SWT.POP_UP);
                    chartComp.setMenu(menu);

                    MenuItem item = new MenuItem(menu, SWT.PUSH);
                    item.setText(DefaultMessagesImpl.getString("TableMasterDetailsPage.what")); //$NON-NLS-1$
                    item.addSelectionListener(new SelectionAdapter() {

                        @Override
                        public void widgetSelected(SelectionEvent e) {
                            ChartUtils.openReferenceLink(referenceLink);
                        }
                    });

                    menu.setVisible(true);
                }
            }

            public void chartMouseMoved(ChartMouseEvent event) {

            }

        });
    }

    public Composite getChartComposite() {
        return chartComposite;
    }

    @Override
    protected ReturnCode canRun() {
        TableIndicator[] tableIndicators = treeViewer.getTableIndicator();
        if (tableIndicators == null || tableIndicators.length == 0) {
            return new ReturnCode(DefaultMessagesImpl.getString("TableMasterDetailsPage.NoTableAssigned"), false); //$NON-NLS-1$
        }
        for (TableIndicator tableIndicator : tableIndicators) {
            if (tableIndicator.getIndicators().length == 0) {
                return new ReturnCode(DefaultMessagesImpl.getString("TableMasterDetailsPage.NoIndicatorAssigned"), false); //$NON-NLS-1$
            }
        }
        return new ReturnCode(true);
    }

    @Override
    protected ReturnCode canSave() {
        List<Table> analyzedTables = new ArrayList<Table>();
        for (TableIndicator tableIndicator : treeViewer.getTableIndicator()) {
            analyzedTables.add(tableIndicator.getTdTable());
        }
        return new ReturnCode(true);
    }

    @Override
    public void saveAnalysis() throws DataprofilerCoreException {
        analysisHandler.clearAnalysis();
        TableIndicator[] tableIndicators = treeViewer.getTableIndicator();
        TdDataProvider tdProvider = null;
        Analysis analysis = analysisHandler.getAnalysis();
        analysis.getParameters().setExecutionLanguage(ExecutionLanguage.get(execLang));
        if (tableIndicators != null) {
            if (tableIndicators.length != 0) {
                tdProvider = EObjectHelper.getTdDataProvider(tableIndicators[0].getTdTable());
                analysis.getContext().setConnection(tdProvider);
            }
            for (TableIndicator tableIndicator : tableIndicators) {
                analysisHandler.addIndicator(tableIndicator.getTdTable(), tableIndicator.getIndicators());
            }
        }
        analysisHandler.setStringDataFilter(dataFilterComp.getDataFilterString());

        String urlString = analysis.eResource() != null ? analysis.eResource().getURI().toFileString()
                : PluginConstant.EMPTY_STRING;

        ReturnCode saved = AnaResourceFileHelper.getInstance().save(analysis);
        if (saved.isOk()) {
            if (tdProvider != null) {
                PrvResourceFileHelper.getInstance().save(tdProvider);
            }
            if (log.isDebugEnabled()) {
                log.debug("Saved in  " + urlString + " successful"); //$NON-NLS-1$ //$NON-NLS-2$
            }
        } else {
            throw new DataprofilerCoreException(DefaultMessagesImpl.getString(
                    "TableMasterDetailsPage.problem", analysis.getName(), urlString, saved.getMessage())); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }

        treeViewer.setDirty(false);
        dataFilterComp.setDirty(false);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (PluginConstant.ISDIRTY_PROPERTY.equals(evt.getPropertyName())) {
            currentEditor.firePropertyChange(IEditorPart.PROP_DIRTY);
            currentEditor.setRefreshResultPage(true);
        } else if (PluginConstant.DATAFILTER_PROPERTY.equals(evt.getPropertyName())) {
            this.analysisHandler.setStringDataFilter((String) evt.getNewValue());
        }
    }

    @Override
    public void refresh() {
        if (chartComposite != null) {
            try {
                for (Control control : chartComposite.getChildren()) {
                    control.dispose();
                }
                createPreviewCharts(form, chartComposite, true);
                chartComposite.getParent().layout();
                chartComposite.layout();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public boolean isDirty() {
        return super.isDirty() || treeViewer.isDirty() || dataFilterComp.isDirty();
    }

    @Override
    public void dispose() {
        super.dispose();
        if (this.treeViewer != null) {
            this.treeViewer.removePropertyChangeListener(this);
        }
        if (dataFilterComp != null) {
            this.dataFilterComp.removePropertyChangeListener(this);
        }
    }
}
