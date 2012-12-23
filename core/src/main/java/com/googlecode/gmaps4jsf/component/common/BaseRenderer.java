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
package com.googlecode.gmaps4jsf.component.common;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

/**
 * @author Hazem Saleh
 * @date  Dec 23, 2012
 * <p>
 * The (BaseRenderer) is the base renderer for all 
 * the google maps components.
 * </p>
 */
public class BaseRenderer extends Renderer {
	
    protected void decodeClientBehaviors(FacesContext context, UIComponent component)  {

        if (!(component instanceof ClientBehaviorHolder)) {
            return;
        }

        Map<String, List<ClientBehavior>> behaviors = ((ClientBehaviorHolder) component).getClientBehaviors();
        
        if (behaviors.isEmpty()) {
            return;
        }

        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        String behaviorEvent = params.get("javax.faces.behavior.event");

        if (null != behaviorEvent) {
            List<ClientBehavior> clientBehaviors = behaviors.get(behaviorEvent);

            if (clientBehaviors != null && ! clientBehaviors.isEmpty()) {
               String behaviorSource = params.get("javax.faces.source");
               String clientId = component.getClientId();

               if (behaviorSource != null && clientId.startsWith(behaviorSource)) {
                   for (ClientBehavior behavior: clientBehaviors) {
                       behavior.decode(context, component);
                   }
               }
            }
        }
    }

	public String getAjaxInvocationScript(ClientBehaviorHolder component, FacesContext context) {
        java.util.Map<String, List<ClientBehavior>> behaviorEvents = component.getClientBehaviors();
        String invocationScript = "{";
        
        if (! behaviorEvents.isEmpty()) {
            String clientId = ((UIComponent) component).getClientId(context);
            List<ClientBehaviorContext.Parameter> params = Collections.emptyList();
            
            for (Iterator<String> eventIterator = behaviorEvents.keySet().iterator(); eventIterator.hasNext();) {
                String event = eventIterator.next();

                invocationScript += event + ": function(event) {";                
                
                for (Iterator<ClientBehavior> behaviorIter = behaviorEvents.get(event).iterator(); behaviorIter.hasNext();) {
                    ClientBehavior behavior = behaviorIter.next();
                    ClientBehaviorContext cbc = ClientBehaviorContext.createClientBehaviorContext(context, 
                    							(UIComponent) component, event, clientId, params);
                    
                    String script = behavior.getScript(cbc);  

                    if (script != null) {
                    	invocationScript += script + ";";
                    }
                }
                
                invocationScript += "}";
            }   
        }
        
        invocationScript += "}";
        
        return invocationScript;
	}
}
