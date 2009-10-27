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

import java.awt.event.MouseEvent;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.talend.cwm.helper.ColumnHelper;
import org.talend.cwm.helper.ColumnSetHelper;
import org.talend.cwm.helper.DataProviderHelper;
import org.talend.cwm.helper.SwitchHelpers;
import org.talend.cwm.relational.TdTable;
import org.talend.cwm.softwaredeployment.TdDataProvider;
import org.talend.dataprofiler.core.CorePlugin;
import org.talend.dataprofiler.core.ImageLib;
import org.talend.dataprofiler.core.PluginConstant;
import org.talend.dataprofiler.core.i18n.internal.DefaultMessagesImpl;
import org.talend.dataprofiler.core.ui.editor.preview.TopChartFactory;
import org.talend.dataprofiler.core.ui.editor.preview.model.ChartTableMenuGenerator;
import org.talend.dataprofiler.core.ui.editor.preview.model.MenuItemEntity;
import org.talend.dataprofiler.core.ui.editor.preview.model.dataset.CustomerDefaultCategoryDataset;
import org.talend.dataprofiler.core.ui.utils.ChartDecorator;
import org.talend.dataquality.analysis.Analysis;
import org.talend.dataquality.indicators.Indicator;
import org.talend.dataquality.indicators.columnset.RowMatchingIndicator;
import org.talend.dq.analysis.AnalysisHandler;
import org.talend.dq.analysis.explore.RowMatchExplorer;
import org.talend.dq.dbms.DbmsLanguage;
import org.talend.dq.dbms.DbmsLanguageFactory;
import org.talend.dq.indicators.preview.table.ChartDataEntity;
import org.talend.dq.indicators.preview.table.PatternChartDataEntity;
import org.talend.utils.format.StringFormatUtil;
import orgomg.cwm.resource.relational.Column;

/**
 * DOC rli class global comment. Detailled comment
 */
public class ColumnsComparisonAnalysisResultPage extends AbstractAnalysisResultPage {

    private static final String NOT_MATCHING = DefaultMessagesImpl.getString("ColumnsComparisonAnalysisResultPage.Not_matching"); //$NON-NLS-1$

    private static final String MATCHING = DefaultMessagesImpl.getString("ColumnsComparisonAnalysisResultPage.Matching"); //$NON-NLS-1$

    private ColumnsComparisonMasterDetailsPage masterPage;

    private RowMatchingIndicator rowMatchingIndicatorA;

    private RowMatchingIndicator rowMatchingIndicatorB;

    private String executeData;

    private Composite analyzedColumnSetsComp;

    private Composite analysisResultsComp;

    private boolean isHasDeactivatedIndicator;

    private String setAMatchPercent;

    private String setBMatchPercent;

    private Section resultSection = null;

    private Section columnSetSection = null;

    /**
     * DOC rli ColumnsComparisonAnalysisResultPage constructor comment.
     * 
     * @param editor
     * @param id
     * @param title
     */
    public ColumnsComparisonAnalysisResultPage(FormEditor editor, String id, String title) {
        super(editor, id, title);
        AnalysisEditor analysisEditor = (AnalysisEditor) editor;
        this.masterPage = (ColumnsComparisonMasterDetailsPage) analysisEditor.getMasterPage();
    }

    @Override
    protected void createFormContent(IManagedForm managedForm) {
        super.createFormContent(managedForm);
        analyzedColumnSetsComp = toolkit.createComposite(topComposite);
        analyzedColumnSetsComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING));
        analyzedColumnSetsComp.setLayout(new GridLayout());
        createAnalyzedColumnSetsSection(analyzedColumnSetsComp);
        analysisResultsComp = toolkit.createComposite(topComposite);
        analysisResultsComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING));
        analysisResultsComp.setLayout(new GridLayout());
        createResultSection(analysisResultsComp);

        // resultComp = toolkit.createComposite(topComposite);
        // resultComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING));
        // resultComp.setLayout(new GridLayout());
        // createResultSection(resultComp);

        form.reflow(true);
    }

    @Override
    protected void createSummarySection(ScrolledForm form, Composite parent, AnalysisHandler analysisHandler) {
        super.createSummarySection(form, parent, analysisHandler);
        executeData = analysisHandler.getExecuteData();
    }

    private void createAnalyzedColumnSetsSection(Composite parent) {
        columnSetSection = createSection(form, parent, DefaultMessagesImpl
                .getString("ColumnsComparisonAnalysisResultPage.analyzedColumnSets"), null); //$NON-NLS-1$
        Composite sectionClient = toolkit.createComposite(columnSetSection);
        sectionClient.setLayout(new GridLayout());
        sectionClient.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        TableViewer elementsTableViewer = new TableViewer(sectionClient, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        Table table = elementsTableViewer.getTable();
        GridDataFactory.fillDefaults().applyTo(table);
        ((GridData) table.getLayoutData()).heightHint = 280;
        ((GridData) table.getLayoutData()).widthHint = 510;
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        table.setDragDetect(true);
        table.setToolTipText(DefaultMessagesImpl.getString("ColumnsComparisonAnalysisResultPage.dragAndDropToolTip")); //$NON-NLS-1$
        final TableColumn columnHeader1 = new TableColumn(table, SWT.CENTER);
        columnHeader1.setWidth(260);
        columnHeader1.setAlignment(SWT.CENTER);
        final TableColumn columnHeader2 = new TableColumn(table, SWT.CENTER);
        columnHeader2.setWidth(260);
        columnHeader2.setAlignment(SWT.CENTER);
        Analysis analysis = this.masterPage.getAnalysisHandler().getAnalysis();
        isHasDeactivatedIndicator = analysis.getParameters().getDeactivatedIndicators().size() != 0;
        EList<Indicator> indicators = analysis.getResults().getIndicators();

        if (indicators.size() != 0) {
            rowMatchingIndicatorA = (RowMatchingIndicator) indicators.get(0);
            rowMatchingIndicatorB = (RowMatchingIndicator) indicators.get(1);
            String columnName = rowMatchingIndicatorA.getColumnSetA().size() > 0 ? ColumnHelper.getColumnSetOwner(
                    rowMatchingIndicatorA.getColumnSetA().get(0)).getName() : PluginConstant.EMPTY_STRING;
            columnHeader1.setText(columnName.equals(PluginConstant.EMPTY_STRING) ? columnName : DefaultMessagesImpl.getString(
                    "ColumnsComparisonAnalysisResultPage.elementsFrom", columnName)); //$NON-NLS-1$
            columnName = rowMatchingIndicatorA.getColumnSetA().size() > 0 ? ColumnHelper.getColumnSetOwner(
                    rowMatchingIndicatorA.getColumnSetB().get(0)).getName() : PluginConstant.EMPTY_STRING;
            columnHeader2.setText(columnName.equals(PluginConstant.EMPTY_STRING) ? columnName : DefaultMessagesImpl.getString(
                    "ColumnsComparisonAnalysisResultPage.elementsFrom", columnName)); //$NON-NLS-1$
        }
        ColumnPairsViewerProvider provider = new ColumnPairsViewerProvider();
        elementsTableViewer.setContentProvider(provider);
        elementsTableViewer.setLabelProvider(provider);
        elementsTableViewer.setInput(rowMatchingIndicatorA);
        columnSetSection.setClient(sectionClient);
    }

    @Override
    protected void createResultSection(Composite parent) {
        resultSection = createSection(form, parent, DefaultMessagesImpl
                .getString("ColumnsComparisonAnalysisResultPage.analysisResults"), ""); //$NON-NLS-1$ //$NON-NLS-2$
        Composite sectionClient = toolkit.createComposite(resultSection);
        sectionClient.setLayout(new GridLayout(2, false));
        sectionClient.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        resultSection.setClient(sectionClient);
        if (executeData == null || executeData.equals(PluginConstant.EMPTY_STRING)) {
            return;
        }
        Table resultTable = new Table(sectionClient, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        resultTable.setLinesVisible(true);
        resultTable.setHeaderVisible(true);
        final TableColumn columnHeader0 = new TableColumn(resultTable, SWT.CENTER);
        columnHeader0.setWidth(150);
        columnHeader0.setAlignment(SWT.CENTER);
        final TableColumn columnHeader1 = new TableColumn(resultTable, SWT.CENTER);
        columnHeader1.setWidth(120);
        columnHeader1.setAlignment(SWT.CENTER);
        // add by hcheng for 6503:change the "set A" and "set B" labels
        Analysis analysis = this.masterPage.getAnalysisHandler().getAnalysis();
        EList<Indicator> indicators = analysis.getResults().getIndicators();
        rowMatchingIndicatorA = (RowMatchingIndicator) indicators.get(0);
        rowMatchingIndicatorB = (RowMatchingIndicator) indicators.get(1);

        // MOD yyi 2009-10-27 9100: not save when remove element in comparison analysis
        int sizeA = rowMatchingIndicatorA.getColumnSetA().size();
        int sizeB = rowMatchingIndicatorA.getColumnSetB().size();
        if (sizeA > 0 && sizeB > 0) {

            String tableNameA = ColumnHelper.getColumnSetOwner(rowMatchingIndicatorA.getColumnSetA().get(0)).getName();
            String tableNameB = ColumnHelper.getColumnSetOwner(rowMatchingIndicatorA.getColumnSetB().get(0)).getName();
            // ~
            columnHeader1.setText(tableNameA);
            // columnHeader1.setText(DefaultMessagesImpl.getString("ColumnsComparisonAnalysisResultPage.SetA")); // TODO scorreia put here table name instead //$NON-NLS-1$
            if (!isHasDeactivatedIndicator) {
                final TableColumn columnHeader2 = new TableColumn(resultTable, SWT.CENTER);
                columnHeader2.setWidth(120);
                columnHeader2.setAlignment(SWT.CENTER);
                columnHeader2.setText(tableNameB);
                //  columnHeader2.setText(DefaultMessagesImpl.getString("ColumnsComparisonAnalysisResultPage.SetB")); // TODO scorreia put here table name instead //$NON-NLS-1$
            }
            createTableItems(resultTable);

            creatChart(sectionClient, tableNameA, tableNameB);
            StringBuilder description = new StringBuilder();
            description.append(setAMatchPercent);
            description.append(DefaultMessagesImpl.getString(
                    "ColumnsComparisonAnalysisResultPage.ASetFoundInB", tableNameA, tableNameB)); //$NON-NLS-1$
            if (!isHasDeactivatedIndicator) {
                description.append("\n"); //$NON-NLS-1$
                description.append(setBMatchPercent);
                description.append(DefaultMessagesImpl.getString(
                        "ColumnsComparisonAnalysisResultPage.BSetFoundInA", tableNameB, tableNameA)); //$NON-NLS-1$
            } else {
                description.append("."); //$NON-NLS-1$
            }
            resultSection.setDescription(description.toString());
        }
        resultSection.layout();
    }

    private void createTableItems(final Table resultTable) {
        Long columnSetARows = rowMatchingIndicatorA.getMatchingValueCount() + rowMatchingIndicatorA.getNotMatchingValueCount();

        TableItem item1 = new TableItem(resultTable, SWT.NULL);
        item1.setText(0, DefaultMessagesImpl.getString("ColumnsComparisonAnalysisResultPage.%Match")); //$NON-NLS-1$
        setAMatchPercent = StringFormatUtil.format(
                (rowMatchingIndicatorA.getMatchingValueCount().doubleValue()) / columnSetARows.doubleValue(),
                StringFormatUtil.PERCENT).toString();
        item1.setText(1, setAMatchPercent);
        TableItem item2 = new TableItem(resultTable, SWT.NULL);
        item2.setText(0, DefaultMessagesImpl.getString("ColumnsComparisonAnalysisResultPage.%NotMatch")); //$NON-NLS-1$
        item2.setText(1, StringFormatUtil.format(
                (rowMatchingIndicatorA.getNotMatchingValueCount().doubleValue()) / columnSetARows.doubleValue(),
                StringFormatUtil.PERCENT).toString());
        TableItem item3 = new TableItem(resultTable, SWT.NULL);
        item3.setText(0, DefaultMessagesImpl.getString("ColumnsComparisonAnalysisResultPage.Match")); //$NON-NLS-1$
        item3.setText(1, rowMatchingIndicatorA.getMatchingValueCount().toString());
        TableItem item4 = new TableItem(resultTable, SWT.NULL);
        item4.setText(0, DefaultMessagesImpl.getString("ColumnsComparisonAnalysisResultPage.NotMatch")); //$NON-NLS-1$
        item4.setText(1, rowMatchingIndicatorA.getNotMatchingValueCount().toString());
        TableItem item5 = new TableItem(resultTable, SWT.NULL);
        item5.setText(0, DefaultMessagesImpl.getString("ColumnsComparisonAnalysisResultPage.rows")); //$NON-NLS-1$
        item5.setText(1, columnSetARows.toString());

        if (!isHasDeactivatedIndicator) {
            Long columnSetBRows = rowMatchingIndicatorB.getMatchingValueCount()
                    + rowMatchingIndicatorB.getNotMatchingValueCount();
            setBMatchPercent = StringFormatUtil.format(
                    (rowMatchingIndicatorB.getMatchingValueCount().doubleValue()) / columnSetBRows.doubleValue(),
                    StringFormatUtil.PERCENT).toString();
            item1.setText(2, setBMatchPercent);
            item2.setText(2, StringFormatUtil.format(
                    (rowMatchingIndicatorB.getNotMatchingValueCount().doubleValue()) / columnSetBRows.doubleValue(),
                    StringFormatUtil.PERCENT).toString());
            item3.setText(2, rowMatchingIndicatorB.getMatchingValueCount().toString());
            item4.setText(2, rowMatchingIndicatorB.getNotMatchingValueCount().toString());
            item5.setText(2, columnSetBRows.toString());
        }
        // add by hcheng for 6530 (add menus for table)
        final TableCursor cursor = new TableCursor(resultTable, SWT.NONE);

        cursor.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                int column = cursor.getColumn();

                if (column == 1) {
                    resultTable.setMenu(createMenu(resultTable, rowMatchingIndicatorA));
                }

                if (column == 2) {
                    resultTable.setMenu(createMenu(resultTable, rowMatchingIndicatorB));
                }
            }
        });
        // ~
    }

    /**
     * 
     * DOC hcheng Comment method "createMenu".
     * 
     * @param resultTable
     * @param indicator
     * @return
     */
    private Menu createMenu(final Table resultTable, final RowMatchingIndicator indicator) {

        if (indicator == null) {
            return null;
        }

        final TdTable columnSet = SwitchHelpers.TABLE_SWITCH.doSwitch(indicator.getAnalyzedElement());

        getAnalysisHandler().getAnalyzedColumns();

        if (columnSet == null) {
            return null;
        }
        Menu menu = new Menu(resultTable);
        MenuItem itemMatch = new MenuItem(menu, SWT.PUSH);
        MenuItem itemNotMatch = new MenuItem(menu, SWT.PUSH);
        MenuItem itemRow = new MenuItem(menu, SWT.PUSH);

        itemMatch.setText(DefaultMessagesImpl.getString("ColumnsComparisonAnalysisResultPage.ViewMatch")); //$NON-NLS-1$
        itemMatch.setImage(ImageLib.getImage(ImageLib.EXPLORE_IMAGE));
        itemNotMatch.setText(DefaultMessagesImpl.getString("ColumnsComparisonAnalysisResultPage.ViewNotMatch")); //$NON-NLS-1$
        itemNotMatch.setImage(ImageLib.getImage(ImageLib.EXPLORE_IMAGE));
        itemRow.setText(DefaultMessagesImpl.getString("ColumnsComparisonAnalysisResultPage.ViewRows")); //$NON-NLS-1$
        itemRow.setImage(ImageLib.getImage(ImageLib.EXPLORE_IMAGE));

        itemMatch.addListener(SWT.Selection, new Listener() {

            public void handleEvent(Event event) {

                TdDataProvider provider = DataProviderHelper.getDataProvider(columnSet);

                RowMatchExplorer rowMatchExplorer = new RowMatchExplorer();
                rowMatchExplorer.setAnalysis(masterPage.analysis);
                rowMatchExplorer.setEnitty(new ChartDataEntity(indicator, "", "")); //$NON-NLS-1$ //$NON-NLS-2$

                String query = rowMatchExplorer.getRowsMatchStatement();
                CorePlugin.getDefault().runInDQViewer(provider, query, columnSet.getName());
            }

        });

        itemNotMatch.addListener(SWT.Selection, new Listener() {

            public void handleEvent(Event event) {

                TdDataProvider provider = DataProviderHelper.getDataProvider(columnSet);

                RowMatchExplorer rowMatchExplorer = new RowMatchExplorer();
                rowMatchExplorer.setAnalysis(masterPage.analysis);
                rowMatchExplorer.setEnitty(new ChartDataEntity(indicator, "", "")); //$NON-NLS-1$ //$NON-NLS-2$

                String query = rowMatchExplorer.getRowsNotMatchStatement();
                CorePlugin.getDefault().runInDQViewer(provider, query, columnSet.getName());
            }
        });

        itemRow.addListener(SWT.Selection, new Listener() {

            public void handleEvent(Event event) {

                TdDataProvider provider = DataProviderHelper.getDataProvider(columnSet);

                DbmsLanguage dbmsLanguage = DbmsLanguageFactory.createDbmsLanguage(provider);

                String query = "SELECT * " + dbmsLanguage.from() + dbmsLanguage.quote(ColumnSetHelper.getParentCatalogOrSchema(columnSet).getName()) //$NON-NLS-1$
                        + "." + dbmsLanguage.quote(columnSet.getName()); //$NON-NLS-1$

                CorePlugin.getDefault().runInDQViewer(provider, query, columnSet.getName());
            }

        });
        return menu;
    }

    private void creatChart(Composite parent, String tableA, String tableB) {
        CustomerDefaultCategoryDataset dataset = new CustomerDefaultCategoryDataset();
        // DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(rowMatchingIndicatorA.getNotMatchingValueCount(), NOT_MATCHING, tableA);
        dataset.addValue(rowMatchingIndicatorA.getMatchingValueCount(), MATCHING, tableA);
        // add by hcheng
        PatternChartDataEntity dataEntityA = new PatternChartDataEntity();
        dataEntityA.setLabel(tableA);
        dataEntityA.setIndicator(rowMatchingIndicatorA);
        dataEntityA.setNumMatch(rowMatchingIndicatorA.getMatchingValueCount().toString());
        dataEntityA.setNumNoMatch(rowMatchingIndicatorA.getNotMatchingValueCount().toString());

        dataset.addDataEntity(dataEntityA);

        if (!isHasDeactivatedIndicator) {
            dataset.addValue(rowMatchingIndicatorB.getNotMatchingValueCount(), NOT_MATCHING, tableB);
            dataset.addValue(rowMatchingIndicatorB.getMatchingValueCount(), MATCHING, tableB);

            PatternChartDataEntity dataEntityB = new PatternChartDataEntity();
            dataEntityB.setLabel(tableB);
            dataEntityB.setIndicator(rowMatchingIndicatorB);
            dataEntityB.setNumMatch(rowMatchingIndicatorB.getMatchingValueCount().toString());
            dataEntityB.setNumNoMatch(rowMatchingIndicatorB.getNotMatchingValueCount().toString());

            dataset.addDataEntity(dataEntityB);
        }
        JFreeChart chart = TopChartFactory.createStackedBarChart(DefaultMessagesImpl
                .getString("ColumnsComparisonAnalysisResultPage.ColumnsComparison"), dataset, //$NON-NLS-1$
                PlotOrientation.HORIZONTAL);
        ChartDecorator.decorate(chart);

        GridData gd = new GridData();
        gd.heightHint = 180;
        gd.widthHint = 450;

        final ChartComposite chartComp = new ChartComposite(parent, SWT.NONE, chart);
        chartComp.setLayoutData(gd);

        // add by hcheng for 6530(add menu to "View query result" for chart )
        chartComp.addChartMouseListener(new ChartMouseListener() {

            public void chartMouseClicked(ChartMouseEvent event) {

                boolean flag = event.getTrigger().getButton() != MouseEvent.BUTTON3;

                chartComp.setDomainZoomable(flag);
                chartComp.setRangeZoomable(flag);

                if (flag) {
                    return;
                }

                ChartEntity chartEntity = event.getEntity();
                if (chartEntity != null && chartEntity instanceof CategoryItemEntity) {
                    CategoryItemEntity cateEntity = (CategoryItemEntity) chartEntity;
                    CustomerDefaultCategoryDataset dataEntity = (CustomerDefaultCategoryDataset) cateEntity.getDataset();

                    Menu menu = new Menu(chartComp.getShell(), SWT.POP_UP);
                    chartComp.setMenu(menu);

                    ChartDataEntity currentDataEntity = null;
                    ChartDataEntity[] dataEntities = dataEntity.getDataEntities();
                    if (dataEntities.length == 1) {
                        currentDataEntity = dataEntities[0];
                    } else {
                        for (ChartDataEntity entity : dataEntities) {
                            if (cateEntity.getColumnKey().compareTo(entity.getLabel()) == 0) {
                                currentDataEntity = entity;
                            } else {
                                if (cateEntity.getRowKey().compareTo(entity.getLabel()) == 0) {
                                    currentDataEntity = entity;
                                }
                            }
                        }
                    }

                    if (currentDataEntity != null) {
                        RowMatchExplorer explorer = new RowMatchExplorer();
                        final Analysis analysis = getAnalysisHandler().getAnalysis();
                        final Indicator indicator = currentDataEntity.getIndicator();

                        MenuItemEntity[] itemEntities = ChartTableMenuGenerator.generate(explorer, analysis, currentDataEntity);
                        for (final MenuItemEntity itemEntity : itemEntities) {
                            MenuItem menuItem = new MenuItem(menu, SWT.NONE);
                            menuItem.setText(itemEntity.getLabel());
                            menuItem.setImage(ImageLib.getImage(ImageLib.EXPLORE_IMAGE));
                            menuItem.addSelectionListener(new SelectionAdapter() {

                                public void widgetSelected(SelectionEvent e) {
                                    Display.getDefault().asyncExec(new Runnable() {

                                        public void run() {
                                            TdDataProvider tdDataProvider = SwitchHelpers.TDDATAPROVIDER_SWITCH.doSwitch(analysis
                                                    .getContext().getConnection());
                                            String query = itemEntity.getQuery();
                                            String editorName = indicator.getAnalyzedElement().getName();
                                            CorePlugin.getDefault().runInDQViewer(tdDataProvider, query, editorName);
                                        }

                                    });
                                }
                            });
                        }
                    }

                    menu.setVisible(true);
                }
            }

            /*
             * (non-Javadoc)
             * 
             * @see org.jfree.chart.ChartMouseListener#chartMouseMoved(org.jfree.chart.ChartMouseEvent)
             */
            public void chartMouseMoved(ChartMouseEvent event) {

            }
        });
        // ~
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.dataprofiler.core.ui.editor.analysis.AbstractAnalysisResultPage#refresh(org.talend.dataprofiler.core
     * .ui.editor.analysis.AbstractAnalysisMetadataPage)
     */
    @Override
    public void refresh(AbstractAnalysisMetadataPage masterPage) {
        this.masterPage = (ColumnsComparisonMasterDetailsPage) masterPage;
        this.summaryComp.dispose();
        this.analyzedColumnSetsComp.dispose();
        this.analysisResultsComp.dispose();
        createFormContent(getManagedForm());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataprofiler.core.ui.editor.AbstractFormPage#setDirty(boolean)
     */
    @Override
    public void setDirty(boolean isDirty) {

    }

    @Override
    protected AnalysisHandler getAnalysisHandler() {
        return this.masterPage.getAnalysisHandler();
    }

    /**
     * The provider for displaying the pair of <Code>Column</Code>.
     */
    class ColumnPairsViewerProvider extends LabelProvider implements ITableLabelProvider, IStructuredContentProvider {

        public Image getColumnImage(Object element, int columnIndex) {
            if (element instanceof ColumnPair) {
                return ImageLib.getImage(ImageLib.TD_COLUMN);
            }
            return null;
        }

        public String getColumnText(Object element, int columnIndex) {
            String text = PluginConstant.EMPTY_STRING;
            if (element instanceof ColumnPair) {
                ColumnPair columnPair = (ColumnPair) element;
                switch (columnIndex) {
                case 0:
                    text = columnPair.getAOfPair().getName();
                    return text;
                case 1:
                    text = columnPair.getBOfPair().getName();
                    return text;
                default:
                    break;
                }
                return text;
            }

            return text;
        }

        public Object[] getElements(Object inputElement) {
            if (inputElement instanceof RowMatchingIndicator) {
                RowMatchingIndicator rowMatchingIndicator = (RowMatchingIndicator) inputElement;
                return ColumnPair.createColumnPairs(rowMatchingIndicator.getColumnSetA(), rowMatchingIndicator.getColumnSetB());
            }
            return null;
        }

        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

        }

    }

    /**
     * The pair of columnA and columnB.
     */
    static class ColumnPair {

        private Column columnA;

        private Column columnB;

        public ColumnPair(Column columnA, Column columnB) {
            this.columnA = columnA;
            this.columnB = columnB;

        }

        public Column getAOfPair() {
            return columnA;
        }

        public Column getBOfPair() {
            return columnB;
        }

        public static ColumnPair[] createColumnPairs(List<Column> columnListA, List<Column> columnListB) {
            if (columnListA.size() != columnListB.size()) {
                return null;
            }
            ColumnPair[] columnPairs = new ColumnPair[columnListA.size()];
            for (int i = 0; i < columnListA.size(); i++) {
                columnPairs[i] = new ColumnPair(columnListA.get(i), columnListB.get(i));
            }
            return columnPairs;
        }
    }
}
