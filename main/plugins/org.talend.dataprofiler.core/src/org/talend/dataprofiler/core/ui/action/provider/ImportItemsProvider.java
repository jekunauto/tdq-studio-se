// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataprofiler.core.ui.action.provider;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.TreeSelection;
import org.talend.dataprofiler.core.ui.imex.action.ImportItemAction;
import org.talend.repository.model.RepositoryNode;



public class ImportItemsProvider extends AbstractCommonActionProvider {

    public ImportItemsProvider() {
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     *
     * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
     */
    @Override
    public void fillContextMenu(IMenuManager menu) {
        // TODO Auto-generated method stub
        if (isShowMenu()) {
            ImportItemAction importAction = new ImportItemAction();
            menu.add(importAction);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.talend.dataprofiler.core.ui.action.provider.AbstractCommonActionProvider#isShowMenu()
     */
    @Override
    public boolean isShowMenu() {
        if(!super.isShowMenu()){
            return  false;
        }
        Object obj = ((TreeSelection) this.getContext().getSelection()).getFirstElement();
        if (obj instanceof RepositoryNode) {
            RepositoryNode node = (RepositoryNode) obj;
            if (isVirturalNode(node) || node.isBin()) {
                return false;
            }

        }
        return true;
    }

}
