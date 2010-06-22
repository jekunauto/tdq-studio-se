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
package org.talend.dataprofiler.core.ui.action.provider;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.part.ISetSelectionTarget;
import org.talend.cwm.helper.TaggedValueHelper;
import org.talend.dataprofiler.core.ImageLib;
import org.talend.dataprofiler.core.PluginConstant;
import org.talend.dataprofiler.core.i18n.internal.DefaultMessagesImpl;
import org.talend.dataquality.indicators.definition.IndicatorCategory;
import org.talend.dataquality.indicators.definition.IndicatorDefinition;
import org.talend.dq.helper.UDIHelper;
import org.talend.dq.indicators.definitions.DefinitionHandler;
import org.talend.dq.writer.AElementPersistance;
import org.talend.dq.writer.EMFSharedResources;
import org.talend.dq.writer.impl.ElementWriterFactory;
import org.talend.resource.ResourceManager;
import orgomg.cwm.objectmodel.core.ModelElement;

/**
 * DOC yyi 2009-09-07, Feature:8882.
 */
public class DuplicateSystemIndicatorProvider extends CommonActionProvider {

    @Override
    public void fillContextMenu(IMenuManager menu) {
        TreeSelection selection = (TreeSelection) this.getContext().getSelection();
        if (!selection.isEmpty()) {
            Object[] objs = selection.toArray();
            IndicatorDefinition[] definitions = new IndicatorDefinition[objs.length];
            for (int i = 0; i < objs.length; i++) {
                IndicatorDefinition definition = (IndicatorDefinition) objs[i];
                definitions[i] = definition;
            }

            // MOD klliu 2010-06-17 13645: the sql template is not correct about the duplicate system indicator
            DuplicateSystemIndicationAction duplicate = new DuplicateSystemIndicationAction(definitions);
            if (!checkDefinitionForDuplicate(definitions)) {
                menu.add(duplicate);
            }
           
        }
      
    }

    /**
     * DOC klliu Comment method "checkDefinitionForDuplicate".
     * 
     * @return
     */
    private boolean checkDefinitionForDuplicate(IndicatorDefinition[] definitions) {
        // TODO Auto-generated method stub
        for (int i = 0; i < definitions.length; i++) {
            IndicatorDefinition definition = (IndicatorDefinition) definitions[i];
            String label = definition.getLabel();
            if (label.equals(PluginConstant.MINIMAL_LENGTH) || label.equals(PluginConstant.UNIQUE_COUNT)
                    || label.equals(PluginConstant.DISTINCT_COUNT) || label.equals(PluginConstant.BLANK_COUNT)
                    || label.equals(PluginConstant.DEFAULT_VALUE_COUNT) || label.equals(PluginConstant.MAXIMAL_LENGTH)
                    || label.equals(PluginConstant.ROW_COUNT) || label.equals(PluginConstant.DUPLICATE_COUNT)
                    || label.equals(PluginConstant.NULL_COUNT)) {
                return false;
            }


        }
        return true;
    }

	/**
     * DOC yyi 2009-09-07, Feature:8882.
     */
    private class DuplicateSystemIndicationAction extends Action {

        private IndicatorDefinition[] definitions;

        public DuplicateSystemIndicationAction(IndicatorDefinition[] definitions) {
            super(DefaultMessagesImpl.getString("DuplicateCWMResourceAction.Duplicate")); //$NON-NLS-1$
            setImageDescriptor(ImageLib.getImageDescriptor(ImageLib.EDIT_COPY));
            this.definitions = definitions;
        }

        @Override
        public void run() {
            if (definitions != null && definitions.length > 0) {
                for (IndicatorDefinition definition : definitions) {

                    boolean validStatus = true;
                    ModelElement oldObject = (ModelElement) EMFSharedResources.getInstance().copyEObject(definition);
                    if (oldObject != null) {
                        ModelElement newObject = (ModelElement) EMFSharedResources.getInstance().copyEObject(oldObject);
                        TaggedValueHelper.setValidStatus(validStatus, newObject);

                        IFile newFile = getNewFile(definition);
                        newObject.setName("copy of " + newObject.getName()); //$NON-NLS-1$

                        // ADD xqliu 2009-09-27 bug 9200
                        ((IndicatorDefinition) newObject).setLabel(""); // clear the label of indicator definition
                        setUDICategory((IndicatorDefinition) newObject, (IndicatorDefinition) oldObject);
                        // ~

                        // MOD yyi 2009-09-09 feature: 8882 add .properties file for duplicate indicator.
                        AElementPersistance elementPersistance = ElementWriterFactory.getInstance().create(
                                newFile.getFileExtension());

                        elementPersistance.save(newObject, newFile);

                        selectAndReveal(newFile);
                    }
                }
            }
        } // end of run

        /**
         * DOC xqliu Comment method "setUDICategory". bug 9200
         * 
         * @param newID
         * @param oldID
         */
        private void setUDICategory(IndicatorDefinition newID, IndicatorDefinition oldID) {
            // TODO FIXME
            Collection<IndicatorCategory> userDefinedIndicatorCategoryList = DefinitionHandler.getInstance()
                    .getUserDefinedIndicatorCategoryList();
            IndicatorCategory category = UDIHelper.getUDICategory(oldID);
            if (userDefinedIndicatorCategoryList.contains(category)) {
                UDIHelper.setUDICategory(newID, category);
            } else {
                UDIHelper.setUDICategory(newID, DefinitionHandler.getInstance().getUserDefinedCountIndicatorCategory());
            }
        }

        /**
         * DOC yyi Comment method "getNewFile".
         * 
         * @param file
         * @return
         */
        private IFile getNewFile(IndicatorDefinition definition) {
            IFile newFile = null;
            int idx = 1;
            while (true) {
                final String newFilename = "copy" + idx + definition.getName() + ".definition"; //$NON-NLS-1$ //$NON-NLS-2$
                newFile = ResourceManager.getUDIFolder().getFile(newFilename);
                if (!newFile.exists()) {
                    break;
                }
                idx++;
            }
            return newFile;
        } // end of getNewFile

        /**
         * DOC yyi 2009-09-07, Feature:8882.
         * 
         * Selects and reveals the newly added resource in all parts of the active workbench window's active page.
         * 
         * @param resource
         */
        private void selectAndReveal(IResource resource) {
            IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
            IWorkbenchPage page = workbenchWindow.getActivePage();
            final IWorkbenchPart activePart = page.getActivePart();
            if (activePart instanceof ISetSelectionTarget) {
                final ISelection selection = new StructuredSelection(resource);
                Display.getDefault().asyncExec(new Runnable() {

                    public void run() {
                        ((ISetSelectionTarget) activePart).selectReveal(selection);
                    }
                });
            }
        }

    } // end of DuplicateSystemIndicationAction

}
