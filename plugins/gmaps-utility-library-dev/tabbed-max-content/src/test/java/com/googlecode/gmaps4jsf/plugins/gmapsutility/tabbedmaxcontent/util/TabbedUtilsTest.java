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
package com.googlecode.gmaps4jsf.plugins.gmapsutility.tabbedmaxcontent.util;

import junit.framework.TestCase;

/**
 *
 * @author Jose Noheda
 */
public class TabbedUtilsTest extends TestCase {

    public void testParse() {
        assertEquals("Null is parsed", "", TabbedUtils.parse((String) null).toString());
        assertEquals("No parsing needed", "hi", TabbedUtils.parse("hi").toString());
        assertEquals("Parsed quote", "john\\\\\'s hat", TabbedUtils.parse(new StringBuffer("  john's hat ")).toString());
        assertEquals("Parsed double quote", "\\\\\\\"mike\\\\\\\"", TabbedUtils.parse(new StringBuffer("  \"mike\" ")).toString());
    }

}
