// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dq.indicators;

import org.talend.cwm.helper.SwitchHelpers;
import org.talend.cwm.softwaredeployment.TdDataProvider;
import orgomg.cwm.foundation.softwaredeployment.DataProvider;

/**
 * DOC scorreia class global comment. Detailled comment
 */
public class ConnectionEvaluator extends AbstractSchemaEvaluator<DataProvider> {

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dq.indicators.AbstractSchemaEvaluator#getDataManager()
     */
    @Override
    protected TdDataProvider getDataManager() {
        DataProvider dp = this.getAnalyzedElements().iterator().next();
        if (dp != null) {
            TdDataProvider tdp = SwitchHelpers.TDDATAPROVIDER_SWITCH.doSwitch(dp);
            return tdp;
        }
        return null;
    }

}
