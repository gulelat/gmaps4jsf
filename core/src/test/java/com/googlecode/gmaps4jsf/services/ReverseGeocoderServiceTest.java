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
package com.googlecode.gmaps4jsf.services.test;

import junit.framework.TestCase;

import com.googlecode.gmaps4jsf.services.GMaps4JSFServiceFactory;

/**
 * @author Hazem Saleh
 * @date Dec 11, 2009
 * The <code>ReverseGeocoderServiceTest</code> is used for testing the <code>ReverseGeocoderService</code>.
 */
public class ReverseGeocoderServiceTest extends TestCase {
    public void testGetPlaceMark() {
        try {
            System.out.println(GMaps4JSFServiceFactory.getReverseGeocoderService().getPlaceMark("40.714224", "-73.961452"));
        } catch (Exception exception) {
            fail("Unable to get the place mark");
        }
    }
}
