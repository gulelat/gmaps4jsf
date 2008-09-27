/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.googlecode.gmaps4jsf.component.map;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.googlecode.gmaps4jsf.component.icon.Icon;
import com.googlecode.gmaps4jsf.util.ComponentConstants;

/**
 * @author Hazem Saleh
 * @date September 27, 2008
 * The IconEncoder is used for encoding the marker icons.
 */
public class IconEncoder {

	public static void encodeIconFunctionScript(FacesContext facesContext,
			Icon iconComponent, ResponseWriter writer) throws IOException {

		writer.write("function "
				+ ComponentConstants.JS_CREATE_ICON_FUNCTION_PREFIX
				+ iconComponent.getId() + "() {");

		writer.write("var iconObject = new "
				+ ComponentConstants.JS_GIcon_OBJECT + "("
				+ ComponentConstants.JS_CONST_G_DEFAULT_ICON_OBJECT + ");");

		if (iconComponent.getShadowImageURL() != null) {
			writer.write("iconObject.shadow = " + "\""
					+ iconComponent.getShadowImageURL() + "\";");
		}

		writer.write("iconObject.iconSize = " + "new "
				+ ComponentConstants.JS_GSize_OBJECT + "("
				+ iconComponent.getWidth() + ", " + iconComponent.getHeight()
				+ ");");

		writer.write("iconObject.shadowSize = new "
				+ ComponentConstants.JS_GSize_OBJECT + "("
				+ iconComponent.getShadowWidth() + ", "
				+ iconComponent.getShadowHeight() + ");");

		writer.write("iconObject.iconAnchor = new "
				+ ComponentConstants.JS_GPoint_OBJECT + "("
				+ iconComponent.getXCoordAnchor() + ", "
				+ iconComponent.getYCoordAnchor() + ");");

		writer.write("iconObject.infoWindowAnchor = new "
				+ ComponentConstants.JS_GPoint_OBJECT + "("
				+ iconComponent.getXCoordInfoWindowAnchor() + ", "
				+ iconComponent.getYCoordInfoWindowAnchor() + ");");

		writer.write("iconObject.image = \"" + iconComponent.getImageURL()
				+ "\";");

		writer.write("return iconObject;");
		writer.write("}");
	}

	public static String getIconFunctionScriptCall(FacesContext facesContext,
			Icon iconComponent, ResponseWriter writer) throws IOException {

		return ComponentConstants.JS_CREATE_ICON_FUNCTION_PREFIX
				+ iconComponent.getId() + "()";
	}
}
