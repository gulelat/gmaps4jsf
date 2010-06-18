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
import com.googlecode.gmaps4jsf.component.window.HTMLInformationWindow;
import com.googlecode.gmaps4jsf.util.ComponentConstants;

/**
 * @author Hazem Saleh
 * @date Sep 17, 2008
 * The EventEncoder is used for encoding the event listeners for all the event sources.
 */
public class EventEncoder {
    private static final String JS_FUNC_CLEAR_INSTANCE_LISTENERS = "clearInstanceListeners";
    
	public static void encodeEventListeners(FacesContext facesContext,
			                                UIComponent eventSource, 
			                                ResponseWriter writer,
			                                String eventSourceBaseVariable) 
	                                        throws IOException {

		for (Iterator iterator = eventSource.getChildren().iterator(); iterator.hasNext();) {
			UIComponent component = (UIComponent) iterator.next();

			if (component instanceof EventListener) {
				encodeEventListener(facesContext, eventSource,
						(EventListener) component, writer,
						eventSourceBaseVariable);
			}
		}
	}   

    public static void encodeEventListenersFunctionScript(
                       FacesContext facesContext, UIComponent eventSource,
                       ResponseWriter writer, String eventSourceBaseVariable)
                       throws IOException {

        writer.write(ComponentConstants.JS_FUNCTION
                    + ComponentConstants.JS_CREATE_EVENT_LISTENERS_FUNCTION_PREFIX
                    + eventSource.getId() + "(" + eventSourceBaseVariable + ") {");
        
        for (Iterator iterator = eventSource.getChildren().iterator(); iterator.hasNext();) {
            UIComponent component = (UIComponent) iterator.next();

            if (component instanceof EventListener) {
                encodeEventListener(facesContext, eventSource, (EventListener) component,
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
                    + "); ");
    }
    
    private static void encodeEventListener(FacesContext facesContext,
                        UIComponent eventSource, EventListener eventListener,
                        ResponseWriter writer, String eventSourceBaseVariable)
                        throws IOException {

        if (eventSource instanceof HTMLInformationWindow) {
            
            // Incase of HTMLInformationWindow, all of the previous event listeners
            // should be removed
            writer.write(ComponentConstants.JS_GEVENT_OBJECT
                        + "." 
                        + JS_FUNC_CLEAR_INSTANCE_LISTENERS 
                        + "(" + eventSourceBaseVariable
                        + "); ");
        }
        
        writer.write(ComponentConstants.JS_GEVENT_OBJECT + ".addListener("
                    + eventSourceBaseVariable + ", '"
                    + eventListener.getEventName() + "', "
                    + eventListener.getJsFunction() + "); ");

    }
}
