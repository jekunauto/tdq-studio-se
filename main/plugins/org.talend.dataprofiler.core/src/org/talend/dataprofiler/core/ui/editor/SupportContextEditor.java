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
package org.talend.dataprofiler.core.ui.editor;

/**
 * the editors support the context view
 * created by msjian on 2014-6-25 Detailled comment
 *
 */
public abstract class SupportContextEditor extends CommonFormEditor {

    public SupportContextEditor() {
        super();
    }

    /**
     * use context link file to check whether the value is the same with repository context, if not, ask you whether to
     * do context propagation.
     * (almost the same logic with ProcessUpdateManager.checkContext(boolean onlySimpleShow))
     * 
     */
    public abstract void checkAndUpdateContext();
}
