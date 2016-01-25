// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataquality.record.linkage.attribute;

import org.apache.commons.codec.language.DoubleMetaphone;
import org.apache.commons.lang.StringUtils;
import org.talend.dataquality.record.linkage.constant.AttributeMatcherType;
import org.talend.dataquality.record.linkage.utils.StringComparisonUtil;

/**
 * DOC scorreia class global comment. Detailled comment
 */
public class DoubleMetaphoneMatcher extends AbstractAttributeMatcher {

    private final DoubleMetaphone algorithm = new DoubleMetaphone();

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.record.linkage.attribute.IAttributeMatcher#getMatchType()
     */
    @Override
    public AttributeMatcherType getMatchType() {
        return AttributeMatcherType.doubleMetaphone;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.record.linkage.attribute.AbstractAttributeMatcher#getWeight(java.lang.String,
     * java.lang.String)
     */
    @Override
    public double getWeight(String str1, String str2) {
        String code1 = algorithm.encode(str1);
        String code2 = algorithm.encode(str2);
        int maxLengh = Math.max(code1.length(), code2.length());
        // check specific case when input strings are numeric values such as 23
        if (maxLengh == 0) {
            if (StringUtils.equalsIgnoreCase(str1, str2)) {
                return 1d; // as both strings identically zero length
            } else {
                return 0.0; // strings are different but both yield to an empty code
            }
        }
        return StringComparisonUtil.difference(code1, code2) / (double) maxLengh;
    }

}
