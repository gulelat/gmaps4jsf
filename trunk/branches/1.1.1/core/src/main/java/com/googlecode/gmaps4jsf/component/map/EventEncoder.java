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
import com.googlecode.gmaps4jsf.component.eventlistener.EventListener;
import com.googlecode.gmaps4jsf.component.htmlInformationWindow.HTMLInformationWindow;

public class EventEncoder {
  
    public EventEncoder() {
    }

    private static void encodeEventListener(FacesContext facesContext,
	    UIComponent eventSource, EventListener eventListener,
	    ResponseWriter writer, String eventSourceBaseVariable)
	    throws IOException {
	
	if (eventSource instanceof HTMLInformationWindow)
	    writer.write("GEvent.clearInstanceListeners("
		    + eventSourceBaseVariable + ");");
	writer.write("GEvent.addListener(" + eventSourceBaseVariable + ", \""
		+ eventListener.getEventName() + "\", "
		+ eventListener.getJsFunction() + ");");
    }

    public static void encodeEventListenersFunctionScript(
	    FacesContext facesContext, UIComponent eventSource,
	    ResponseWriter writer, String eventSourceBaseVariable)
	    throws IOException {
	
	writer.write("function createEventListenersFunction"
		+ eventSource.getId() + "(" + eventSourceBaseVariable + ") {");
	
	Iterator iterator = eventSource.getChildren().iterator();
	
	do {
	    if (!iterator.hasNext()) {
		break;
	    }
	    
	    UIComponent component = (UIComponent) iterator.next();
	    
	    if (component instanceof EventListener) {
		encodeEventListener(facesContext, eventSource,
			(EventListener) component, writer,
			eventSourceBaseVariable);
	    }
	} while (true);
	writer.write("}");
    }

    public static void encodeEventListenersFunctionScriptCall(
	    FacesContext facesContext, UIComponent eventSource,
	    ResponseWriter writer, String eventSourceBaseVariable)
	    throws IOException {
	
	writer.write("createEventListenersFunction" + eventSource.getId() + "("
		+ eventSourceBaseVariable + ");");
    }
}