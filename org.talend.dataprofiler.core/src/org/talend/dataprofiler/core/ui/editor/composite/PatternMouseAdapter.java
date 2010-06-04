// ============================================================================
//
// Copyright (C) 2006-2010 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataprofiler.core.ui.editor.composite;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;
import org.talend.cwm.db.connection.ConnectionUtils;
import org.talend.cwm.helper.DataProviderHelper;
import org.talend.cwm.softwaredeployment.TdDataProvider;
import org.talend.dataprofiler.core.i18n.internal.DefaultMessagesImpl;
import org.talend.dataprofiler.core.model.ModelElementIndicator;
import org.talend.dataprofiler.core.pattern.PatternUtilities;
import org.talend.dataprofiler.core.ui.editor.analysis.AbstractAnalysisMetadataPage;
import org.talend.dataprofiler.core.ui.editor.preview.IndicatorUnit;
import org.talend.dataprofiler.core.ui.utils.MessageUI;
import org.talend.dataquality.analysis.Analysis;
import org.talend.dataquality.domain.pattern.Pattern;
import org.talend.dq.helper.resourcehelper.PatternResourceFileHelper;
import org.talend.resource.ResourceManager;
import orgomg.cwm.foundation.softwaredeployment.DataManager;

/**
 * 
 * DOC mzhao Feature 13040 . 2010-05-21
 */
public class PatternMouseAdapter extends MouseAdapter {

    private AbstractColumnDropTree columnDropTree = null;

    private AbstractAnalysisMetadataPage masterPage;

    private Analysis analysis = null;

    private ModelElementIndicator meIndicator = null;

    private TreeItem treeItem = null;

    private ViewerFilter[] filters;

    public PatternMouseAdapter(AbstractColumnDropTree columnDropTree, AbstractAnalysisMetadataPage masterPage,
            ModelElementIndicator meIndicator, TreeItem treeItem) {
        this.masterPage = masterPage;
        this.analysis = masterPage.getAnalysis();
        this.meIndicator = meIndicator;
        this.treeItem = treeItem;
        this.columnDropTree = columnDropTree;
    }

    @Override
    public void mouseDown(MouseEvent e) {
        DataManager dm = analysis.getContext().getConnection();
        if (dm == null) {
            masterPage.doSave(null);
        }

        // TODO 10238
        if (dm != null && dm instanceof TdDataProvider) {
            TdDataProvider dp = (TdDataProvider) dm;
            if (ConnectionUtils.isMdmConnection(DataProviderHelper.getTdProviderConnection(dp).getObject())) {
                MessageUI.openWarning(DefaultMessagesImpl.getString("AnalysisColumnTreeViewer.dontSupport"));
                return;
            }
        }

        IFolder libProject = ResourceManager.getLibrariesFolder();

        CheckedTreeSelectionDialog dialog = PatternUtilities.createPatternCheckedTreeSelectionDialog(libProject);

        if (null != filters) {
            for (ViewerFilter filter : filters) {
                dialog.addFilter(filter);
            }
        }

        // IFile[] selectedFiles =
        // PatternUtilities.getPatternFileByIndicator(clmIndicator);
        // dialog.setInitialSelections(selectedFiles);
        if (dialog.open() == Window.OK) {
            for (Object obj : dialog.getResult()) {
                if (obj instanceof IFile) {
                    IFile file = (IFile) obj;
                    IndicatorUnit addIndicatorUnit = PatternUtilities.createIndicatorUnit(file, meIndicator, analysis);
                    if (addIndicatorUnit != null) {
                        columnDropTree.createOneUnit(treeItem, addIndicatorUnit);
                        columnDropTree.setDirty(true);
                    } else {
                        Pattern pattern = PatternResourceFileHelper.getInstance().findPattern(file);
                        MessageUI.openError(DefaultMessagesImpl.getString("AnalysisColumnTreeViewer.IndicatorSelected") //$NON-NLS-1$
                                + pattern.getName());
                    }
                }
            }
        }
    }

    public void addFilter(ViewerFilter... filters) {
        this.filters = filters;
    }
}
