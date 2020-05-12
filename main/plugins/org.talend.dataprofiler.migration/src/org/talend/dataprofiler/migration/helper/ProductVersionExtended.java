// ============================================================================
//
// Copyright (C) 2006-2020 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataprofiler.migration.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.talend.utils.ProductVersion;


/**
 * created by msjian on 2020年5月12日
 * Handles version numbers such as 7.3.1.20200417 or 7.3.1.20200417_1111-patch or 7.3.1.XXX
 *
 */
public class ProductVersionExtended extends ProductVersion {

    private static final Pattern EXTENDED_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)\\.(.*)"); //$NON-NLS-1$

    private String extraVersionInfo;
    
    public String getExtraVersionInfo() {
        return this.extraVersionInfo;
    }

    public void setExtraVersionInfo(String extraVersionInfo) {
        this.extraVersionInfo = extraVersionInfo;
    }

    /**
     * ProductVersionDate constructor.
     * 
     * @param major
     * @param minor
     * @param micro
     * @param extraVersionInfo
     */
    public ProductVersionExtended(int major, int minor, int micro, String extraVersionInfo) {
        super(major, minor, micro);
        this.setExtraVersionInfo(extraVersionInfo);
    }

    /**
     * NOTE: this ProductVersionExtended constructor used for DQ migration task only!!!
     * 
     * @param migration task Version
     * @param migration task Date
     */
    public ProductVersionExtended(ProductVersion taskVersion, Date taskDate) {
        super(taskVersion.getMajor(), taskVersion.getMinor(), taskVersion.getMicro());
        taskDate.setMonth(taskDate.getMonth() - 1);
        this.setExtraVersionInfo(new SimpleDateFormat("yyyyMMdd").format(taskDate)); //$NON-NLS-1$
    }

    public static ProductVersionExtended fromString(String version) {
        Matcher matcher = EXTENDED_PATTERN.matcher(version);
        if (matcher.find()) {
            int major = Integer.parseInt(matcher.group(1));
            int minor = Integer.parseInt(matcher.group(2));
            String microStr = matcher.group(3);
            String extraVersionInfo = matcher.group(4);
            if (microStr != null) {
                int micro = Integer.parseInt(microStr);
                if (extraVersionInfo != null) {
                    return new ProductVersionExtended(major, minor, micro, extraVersionInfo);
                }
                return new ProductVersionExtended(major, minor, micro, ""); //$NON-NLS-1$
            } else {
                return new ProductVersionExtended(major, minor, 0, ""); //$NON-NLS-1$
            }
        }
        return null;
    }
    
    public int compareTo(ProductVersionExtended other) {
        int diff = super.compareTo(other);
        if (diff != 0) {
            return diff;
        }
        return extraVersionInfo.compareTo(other.extraVersionInfo);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("."); //$NON-NLS-1$
        stringBuilder.append(extraVersionInfo);
        return stringBuilder.toString();
    }

}
