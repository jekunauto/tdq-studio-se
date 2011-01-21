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
package org.talend.dataprofiler.core.ui.action.actions;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.talend.dataprofiler.core.CorePlugin;
import org.talend.dataprofiler.core.ui.views.DQRespositoryView;
import org.talend.repository.ui.actions.RestoreAction;

/**
 * @author qiongli Restore recycle bin element
 */
public class DQRestoreAction extends RestoreAction {

    private static Logger log = Logger.getLogger(DQRestoreAction.class);

    /**
	 * 
	 */
    public DQRestoreAction() {
        // setImageDescriptor(ImageLib.getImageDescriptor(ImageLib.ADD_ACTION));
        super();
        // setText(DefaultMessagesImpl.getString("DQRestoreAction.restore"));
    }

    @Override
    public void run() {
        super.run();

        CorePlugin.getDefault().refreshDQView();

        CorePlugin.getDefault().refreshWorkSpace();

    }

    @Override
    public void init(TreeViewer viewer, IStructuredSelection selection) {

    }

    @Override
    public ISelection getSelection() {
        DQRespositoryView findView = CorePlugin.getDefault().getRepositoryView();
        ISelection selection = findView.getCommonViewer().getSelection();
        return selection;
    }

}
