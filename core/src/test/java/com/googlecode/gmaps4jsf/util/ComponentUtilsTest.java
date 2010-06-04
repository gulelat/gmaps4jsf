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
package com.googlecode.gmaps4jsf.util;

import junit.framework.TestCase;
import com.googlecode.gmaps4jsf.component.map.Map;

/**
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public class ComponentUtilsTest extends TestCase {

    private Map map;

    protected void setUp() throws Exception {
        map = new Map();
    }

    public void testGetMapWidth() {
        map.setWidth("500px");
        assertEquals("500px is returned without change", "500px", ComponentUtils.getMapWidth(map));
        map.setWidth("500");
        assertEquals("A number is parsed", "500px", ComponentUtils.getMapWidth(map));
    }

    public void testIsNumber() {
        assertFalse("Null is not a number", ComponentUtils.isNumber(null));
        assertFalse("500px is not a number", ComponentUtils.isNumber("500px"));
        assertTrue("500 is a number", ComponentUtils.isNumber("500"));
    }

    public void testUnicode() {
        assertTrue("Null is maintained", ComponentUtils.unicode(null).length() == 0);
        assertEquals("String is not modified", "something", ComponentUtils.unicode("something"));
        assertEquals("Unicoded", "some\\u0027thing", ComponentUtils.unicode("some'thing"));
    }

}
