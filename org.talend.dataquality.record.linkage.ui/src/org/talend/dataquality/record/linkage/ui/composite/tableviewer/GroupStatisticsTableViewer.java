// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataquality.record.linkage.ui.composite.tableviewer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.swt.widgets.Composite;
import org.talend.dataquality.analysis.Analysis;
import org.talend.dataquality.record.linkage.ui.composite.tableviewer.provider.GroupStatisticsTableLabelProvider;
import org.talend.dataquality.record.linkage.ui.composite.tableviewer.provider.MatchAnalysisTableContentProvider;
import org.talend.dataquality.record.linkage.utils.MatchAnalysisConstant;
import org.talend.dataquality.rules.KeyDefinition;
import org.talend.dataquality.rules.MatchRuleDefinition;

/**
 * created by zhao on Aug 22, 2013 Detailled comment
 *
 */
public class GroupStatisticsTableViewer extends AbstractMatchAnalysisTableViewer {

    private List<String> tableHeaders = new ArrayList<String>();

    /**
     * DOC zhao GroupStatisticsTableViewer constructor comment.
     *
     * @param parent
     * @param style
     */
    public GroupStatisticsTableViewer(Composite parent, int style) {
        super(parent, style, Boolean.TRUE);
        initHeaders();
        initTable(tableHeaders);
    }

    /**
     * DOC zhao Comment method "initHeaders".
     */
    private void initHeaders() {
        tableHeaders.add(MatchAnalysisConstant.GROUP_SIZE);
        tableHeaders.add(MatchAnalysisConstant.GROUP_COUNT);
        tableHeaders.add(MatchAnalysisConstant.RECORD_COUNT);
        tableHeaders.add(MatchAnalysisConstant.RECORDS_PERCENTAGE);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.talend.dataquality.record.linkage.ui.composite.tableviewer.AbstractMatchAnalysisTableViewer#getTableHeightHint
     * ()
     */
    @Override
    protected int getTableHeightHint() {
        return 160;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.talend.dataquality.record.linkage.ui.composite.tableviewer.AbstractMatchAnalysisTableViewer#addContextMenu()
     */
    @Override
    public void addContextMenu() {
        // no context menu for this table.

    }

    /*
     * (non-Javadoc)
     *
     * @see org.talend.dataquality.record.linkage.ui.composite.tableviewer.AbstractMatchAnalysisTableViewer#
     * getHeaderDisplayWeight()
     */
    @Override
    protected int getHeaderDisplayWeight() {
        return 10;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.talend.dataquality.record.linkage.ui.composite.tableviewer.AbstractMatchAnalysisTableViewer#getTableLabelProvider
     * ()
     */
    @Override
    protected IBaseLabelProvider getTableLabelProvider() {
        return new GroupStatisticsTableLabelProvider();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.talend.dataquality.record.linkage.ui.composite.tableviewer.AbstractMatchAnalysisTableViewer#
     * getTableContentProvider()
     */
    @Override
    protected IContentProvider getTableContentProvider() {
        return new MatchAnalysisTableContentProvider();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.talend.dataquality.record.linkage.ui.composite.tableviewer.AbstractMatchAnalysisTableViewer#getTableCellModifier
     * ()
     */
    @Override
    protected ICellModifier getTableCellModifier() {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.talend.dataquality.record.linkage.ui.composite.tableviewer.AbstractMatchAnalysisTableViewer#getCellEditor
     * (java.util.List)
     */
    @Override
    protected CellEditor[] getCellEditor(List<String> headers) {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.talend.dataquality.record.linkage.ui.composite.tableviewer.AbstractMatchAnalysisTableViewer#addElement(java
     * .lang.String, org.talend.dataquality.analysis.Analysis)
     */
    @Override
    public boolean addElement(String columnName, Analysis analysis) {
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.talend.dataquality.record.linkage.ui.composite.tableviewer.AbstractMatchAnalysisTableViewer#removeElement
     * (java.lang.String, org.talend.dataquality.analysis.Analysis)
     */
    @Override
    public void removeElement(String columnName, Analysis analysis) {
        // No implementation

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.talend.dataquality.record.linkage.ui.composite.tableviewer.AbstractMatchAnalysisTableViewer#addElement(java
     * .lang.String, org.talend.dataquality.rules.MatchRuleDefinition)
     */
    @Override
    public boolean addElement(String columnName, MatchRuleDefinition matchRuleDef) {
        // No implementation
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.talend.dataquality.record.linkage.ui.composite.tableviewer.AbstractMatchAnalysisTableViewer#removeElement
     * (org.talend.dataquality.rules.KeyDefinition, org.talend.dataquality.analysis.Analysis)
     */
    @Override
    public void removeElement(KeyDefinition keyDef, Analysis analysis) {
        // No implementation

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.talend.dataquality.record.linkage.ui.composite.tableviewer.AbstractMatchAnalysisTableViewer#removeElement
     * (org.talend.dataquality.rules.KeyDefinition, org.talend.dataquality.rules.MatchRuleDefinition)
     */
    @Override
    public void removeElement(KeyDefinition keyDef, MatchRuleDefinition matchRuleDef) {
        // No implementation

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.dataquality.record.linkage.ui.composite.tableviewer.AbstractMatchAnalysisTableViewer#moveUpElement
     * (org.talend.dataquality.rules.KeyDefinition, org.talend.dataquality.rules.MatchRuleDefinition)
     */
    @Override
    public void moveUpElement(KeyDefinition keyDef, MatchRuleDefinition matchRuleDef) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.dataquality.record.linkage.ui.composite.tableviewer.AbstractMatchAnalysisTableViewer#moveDownElement
     * (org.talend.dataquality.rules.KeyDefinition, org.talend.dataquality.rules.MatchRuleDefinition)
     */
    @Override
    public void moveDownElement(KeyDefinition keyDef, MatchRuleDefinition matchRuleDef) {
        // TODO Auto-generated method stub

    }

}