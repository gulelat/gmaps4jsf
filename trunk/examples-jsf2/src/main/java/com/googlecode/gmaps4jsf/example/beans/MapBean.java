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
package com.googlecode.gmaps4jsf.example.beans;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

import com.googlecode.gmaps4jsf.component.map.Map;
import com.googlecode.gmaps4jsf.component.marker.Marker;
import com.googlecode.gmaps4jsf.services.GMaps4JSFServiceFactory;
import com.googlecode.gmaps4jsf.services.data.PlaceMark;

import com.googlecode.gmaps4jsf.component.common.Position;

/**
 * @author Hazem Saleh
 * @date Dec 23, 2012
 * The MapBean is used for the server side events example of GMaps4JSF.
 */
public class MapBean {
    private static final String NOT_AVAILABLE = "Not available";
    String firstMarkerStatus;
    String secondMarkerStatus;
    String firstLocationInformation;
    String secondLocationInformation;
    
    String markerNotification;

    public String getMarkerNotification() {
		return markerNotification;
	}

	public void setMarkerNotification(String markerNotification) {
		this.markerNotification = markerNotification;
	}

	public String getFirstMarkerStatus() {
        return firstMarkerStatus;
    }

    public void setFirstMarkerStatus(String firstMarkerStatus) {
        this.firstMarkerStatus = firstMarkerStatus;
    }

    public String getSecondMarkerStatus() {
        return secondMarkerStatus;
    }

    public void setSecondMarkerStatus(String secondMarkerStatus) {
        this.secondMarkerStatus = secondMarkerStatus;
    }
   
    /*
    public void processValueChangeForFirstMarker(ValueChangeEvent event) throws AbortProcessingException {
        firstMarkerStatus = "Marker A status:" + event.getNewValue().toString();
        Position position = (Position) event.getNewValue();
        
        try {
            PlaceMark placeMark = GMaps4JSFServiceFactory.getReverseGeocoderService().getPlaceMark(position.getLatitude(), position.getLongitude());
            
            firstLocationInformation = "Selected Latitude, Longitude: " + position.getLatitude() + ", " + position.getLongitude()
                                     + "<br>Address: " + ignoreNull(placeMark.getAddress()) 
                                     + "<br>Country code: " + ignoreNull(placeMark.getCountryCode())
                                     + "<br>Country name: "+ ignoreNull(placeMark.getCountryName())
                                     + "<br>Accuracy: "+ placeMark.getAccuracy();                                     
        } catch (Exception ex) {
            firstLocationInformation = NOT_AVAILABLE;
        }
    }
	*/
    
    public void processValueChangeForSecondMarker(ValueChangeEvent event) throws AbortProcessingException {    	
        secondMarkerStatus = "Marker B status:" + event.getNewValue().toString();
        Position position = (Position) event.getNewValue();
        
        try {
            PlaceMark placeMark = GMaps4JSFServiceFactory.getReverseGeocoderService().getPlaceMark(position.getLatitude(), position.getLongitude());
            
            secondLocationInformation = "Selected Latitude, Longitude: " + position.getLatitude() + ", " + position.getLongitude()
                                      + "<br>Address: " + ignoreNull(placeMark.getAddress()) 
                                      + "<br>Country code: " + ignoreNull(placeMark.getCountryCode())
                                      + "<br>Country name: "+ ignoreNull(placeMark.getCountryName())
                                      + "<br>Accuracy: "+ placeMark.getAccuracy();       
        } catch (Exception ex) {
            secondLocationInformation = NOT_AVAILABLE;
        }
    }
    
    public void processValueChangeForMarker(ValueChangeEvent event) throws AbortProcessingException {
        System.out.println("maker is dragged to: " + ((Position) event.getNewValue()).toString());
    }
    
    public void processClickForMarker(AjaxBehaviorEvent event) throws AbortProcessingException {
    	Marker marker = ((Marker) event.getSource());
    	
        System.out.println("marker is clicked: Latitude = " + marker.getLatitude() + ", Longitude = " + marker.getLongitude());    	
        
        firstMarkerStatus = "marker is clicked: Latitude = " + marker.getLatitude() + ", Longitude = " + marker.getLongitude();    
    }    

    public String getFirstLocationInformation() {
        return firstLocationInformation;
    }

    public void setFirstLocationInformation(String firstLocationInformation) {
        this.firstLocationInformation = firstLocationInformation;
    }
    
    public String getSecondLocationInformation() {
        return secondLocationInformation;
    }

    public void setSecondLocationInformation(String secondLocationInformation) {
        this.secondLocationInformation = secondLocationInformation;
    }    
    
    private static String ignoreNull(String attributeValue) {
        if (attributeValue == null) {
            return "";
        }
        
        return attributeValue;
    }
    
    public void addMarkerHere(ActionEvent actionEvent) {
    	Map map = (Map) actionEvent.getComponent();
    	
    	Marker marker = new Marker();
    	
    	marker.setLongitude(((Position) map.getValue()).getLongitude());
    	marker.setLatitude(((Position) map.getValue()).getLatitude());
    	    	
    	map.getChildren().add(marker);
    }
    
    public void handleMapClick(ActionEvent actionEvent) {
    	Map map = (Map) actionEvent.getComponent();
    	
    	markerNotification = "A new marker is added with the following: " +  map.getValue();
    }    
}
