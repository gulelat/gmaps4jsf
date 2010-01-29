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
import javax.faces.event.ValueChangeEvent;

/**
 * @author Hazem Saleh
 * @date May 23, 2009
 * The MapBean is used for the server side events example of GMaps4JSF.
 */
public class MapBean {
    String firstMarkerStatus;
    String secondMarkerStatus;

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
   
    public void processValueChangeForFirstMarker(ValueChangeEvent event) throws AbortProcessingException {
        firstMarkerStatus = event.getNewValue().toString();
    }

    public void processValueChangeForSecondMarker(ValueChangeEvent event) throws AbortProcessingException {
        secondMarkerStatus = event.getNewValue().toString();
    }    
}
