package com.googlecode.gmaps4jsf.example.beans;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;

import com.googlecode.gmaps4jsf.component.marker.Marker;
import com.googlecode.gmaps4jsf.component.common.Position;

public class TestMarker extends Marker {
	
    public void update(ValueChangeEvent event) throws AbortProcessingException {
    	Position value = (Position) event.getNewValue();
    	
        Marker source = (Marker) event.getSource();
        String markerID = source.getJsVariable();
        
        String message = markerID + " is moved to the following location: " + value.toString();
        
        System.out.println(message);
        
        FacesContext.getCurrentInstance().getApplication().createValueBinding("#{markerList.status}").setValue(FacesContext.getCurrentInstance(), 
        																							    	   message);
    }	
}
