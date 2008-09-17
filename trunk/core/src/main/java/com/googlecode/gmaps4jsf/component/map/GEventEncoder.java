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

import com.googlecode.gmaps4jsf.component.geventlistener.GEventListener;
import com.googlecode.gmaps4jsf.util.ComponentConstants;

/**
 * @author Hazem Saleh
 * @date Sep 17, 2008
 * The GEventEncoder is used for encoding the event listeners for all the event sources.
 */
public class GEventEncoder {

	private static void encodeEventListener(FacesContext facesContext,
			GEventListener gEventListener, ResponseWriter writer,
			String eventSourceBaseVariable) throws IOException {

		writer.write(ComponentConstants.JS_GEVENT_OBJECT + ".addListener("
				+ eventSourceBaseVariable + ", \""
				+ gEventListener.getEventName() + "\", "
				+ gEventListener.getJsFunction()
				+ ");");

	}

	public static void encodeEventListenersFunctionScript(
			FacesContext facesContext, UIComponent eventSource,
			ResponseWriter writer, String eventSourceBaseVariable)
			throws IOException {

		writer.write("function "
				+ ComponentConstants.JS_CREATE_EVENT_LISTENERS_FUNCTION_PREFIX
				+ eventSource.getId() + "(" + eventSourceBaseVariable + ") {");
		for (Iterator iterator = eventSource.getChildren().iterator(); iterator
				.hasNext();) {
			UIComponent component = (UIComponent) iterator.next();

			if (component instanceof GEventListener) {
				encodeEventListener(facesContext, (GEventListener) component,
						writer, eventSourceBaseVariable);
			}
		}
		writer.write("}");
	}

	public static void encodeEventListenersFunctionScriptCall(
			FacesContext facesContext, UIComponent eventSource,
			ResponseWriter writer, String eventSourceBaseVariable)
			throws IOException {

		writer.write(ComponentConstants.JS_CREATE_EVENT_LISTENERS_FUNCTION_PREFIX
						+ eventSource.getId()
						+ "("
						+ eventSourceBaseVariable
						+ ");");
	}
}
