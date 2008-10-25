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
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.googlecode.gmaps4jsf.component.map.Map;
import com.googlecode.gmaps4jsf.component.mapcontrol.MapControl;
import com.googlecode.gmaps4jsf.util.ComponentConstants;

/**
 * @author Hazem Saleh
 * @date Sep 16, 2008
 * The MapControlEncoder is used for encoding the map controls.
 */
public class MapControlEncoder {

	private static void encodeMapControl(FacesContext facesContext,
			Map mapComponent, MapControl mapControl, ResponseWriter writer)
			throws IOException {

		if (mapControl.getPosition() != null) {

			writer.write("var mapControlPosition_" + mapControl.getId()
					+ " = new " + ComponentConstants.JS_GControlPosition_OBJECT
					+ "(" + mapControl.getPosition() + ", new "
					+ ComponentConstants.JS_GSize_OBJECT + "("
					+ mapControl.getOffsetWidth() + ","
					+ mapControl.getOffsetHeight() + ")" + ");");

			writer.write(ComponentConstants.JS_GMAP_BASE_VARIABLE
					+ ".addControl(new " + mapControl.getName()
					+ "(), mapControlPosition_" + mapControl.getId() + ");");
		} else {

			writer.write(ComponentConstants.JS_GMAP_BASE_VARIABLE
					+ ".addControl(new " + mapControl.getName() + "());");

		}
	}

	public static void encodeMapControlsFunctionScript(
			FacesContext facesContext, Map mapComponent, ResponseWriter writer)
			throws IOException {

		writer.write("function "
				+ ComponentConstants.JS_CREATE_MAP_CONTROLS_FUNCTION_PREFIX
				+ mapComponent.getId() + "("
				+ ComponentConstants.JS_GMAP_BASE_VARIABLE + ") {");
		for (Iterator iterator = mapComponent.getChildren().iterator(); iterator
				.hasNext();) {
			UIComponent component = (UIComponent) iterator.next();

			if (component instanceof MapControl  && component.isRendered()) {
				encodeMapControl(facesContext, mapComponent,
						(MapControl) component, writer);
			}
		}
		writer.write("}");
	}

	public static void encodeMapControlsFunctionScriptCall(
			FacesContext facesContext, Map mapComponent, ResponseWriter writer)
			throws IOException {

		writer.write(ComponentConstants.JS_CREATE_MAP_CONTROLS_FUNCTION_PREFIX
				+ mapComponent.getId() + "("
				+ ComponentConstants.JS_GMAP_BASE_VARIABLE + ");");
	}
}
