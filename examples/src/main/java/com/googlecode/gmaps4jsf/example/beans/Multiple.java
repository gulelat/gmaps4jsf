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

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public class Multiple {

    private List locations;
    private LocationBean location;

    public Multiple() {
        locations = new ArrayList();
        for (int i = 0; i < 500; i++) {
            LocationBean bean = new LocationBean();
            bean.setLatitude(random());
            bean.setLongitude(random());
            locations.add(bean);
        }
    }

    public void addLocation(ActionEvent e) {
        locations.add(location.clone());
    }

    private String random() {
        return String.valueOf(Math.random() * 60);
    }

    public List getLocations() {
        return locations;
    }

    public void setLocations(List locations) {
        this.locations = locations;
    }

    public LocationBean getLocation() {
        return location;
    }

    public void setLocation(LocationBean location) {
        this.location = location;
    }

}
