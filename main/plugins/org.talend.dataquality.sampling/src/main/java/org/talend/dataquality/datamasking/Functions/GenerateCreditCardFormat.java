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
package org.talend.dataquality.datamasking.Functions;

/**
 * created by jgonzalez on 19 juin 2015. This class will generate a valid credit card number. First it will try to check
 * if it's from a known type. It can be used on String and Long values.
 *
 */
public abstract class GenerateCreditCardFormat<T2> extends GenerateCreditCard<T2> {

    @Override
    public abstract T2 generateMaskedRow(T2 t);
}
