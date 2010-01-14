/*
 * Copyright (C) 2009 JavaRosa
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.javarosa.form.api;

import org.javarosa.core.model.FormDef;
import org.javarosa.core.model.FormIndex;
import org.javarosa.core.model.QuestionDef;
import org.javarosa.core.model.data.IAnswerData;
import org.javarosa.core.model.instance.TreeElement;
import org.javarosa.core.util.OrderedHashtable;

public class FormEntryPrompt {

	FormDef form;
	FormIndex index;
    QuestionDef mQuestionDef;
    TreeElement mTreeElement;

    public FormEntryPrompt(FormDef form, FormIndex index) {
    	this.form = form;
    	this.index = index;
    	this.mQuestionDef = (QuestionDef)form.getChild(index);
    	this.mTreeElement = form.getDataModel().resolveReference(index.getReference());
    }
    
    public FormEntryPrompt(QuestionDef mQuestionDef, TreeElement mTreeElement) {
        this.mTreeElement = mTreeElement;
        this.mQuestionDef = mQuestionDef;
    }

    public int getControlType() {
        return mQuestionDef.getControlType();
    }

    public int getDataType() {
        return mTreeElement.dataType;
    }

    // attributes available in the bind, instance and body
    public String getPromptAttributes() {
        return null;
    }

    public IAnswerData getAnswerValue() {
        return mTreeElement.getValue();
    }

    public String getAnswerText() {
        return mTreeElement.getValue().getDisplayText();
    }

    public String getConstraintText() {
        return mTreeElement.getConstraint().constraintMsg;
    }

    public String getLongText() {
        return substituteStringArgs(mQuestionDef.getLongText());
    }
    
    public OrderedHashtable getSelectItems() {
        return mQuestionDef.getSelectItems();
    }
 
    public String getShortText() {
    	return substituteStringArgs(mQuestionDef.getShortText());
    }
    
    public String getHelpText() {
        return mQuestionDef.getHelpText();
    }

    public boolean isRequired() {
        return mTreeElement.required;
    }

    public boolean isReadOnly() {
        return !mTreeElement.isEnabled();
    }
    
    public String substituteStringArgs (String templateStr) {
		return form.fillTemplateString(templateStr, index.getReference());
    }

}
