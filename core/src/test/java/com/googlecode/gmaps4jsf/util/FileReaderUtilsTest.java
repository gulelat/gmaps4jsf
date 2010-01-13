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

/**
 * Tests class FileReaderUtils.
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public class FileReaderUtilsTest extends TestCase {
    
    public FileReaderUtilsTest(String testName) {
        super(testName);
    }

    public void testReadLines() {
        assertEquals("Two lines readed", 2, FileReaderUtils.readLines("/META-INF/plugins.txt").length);
    }

    public void testGetResourceContent() {
        assertEquals("Content returned", "com.googlecode.gmaps4jsf.plugins.PluginEncoderTest$PluginImplcom.googlecode.gmaps4jsf.plugins.PluginEncoderTest$AnotherPluginImpl", FileReaderUtils.getResourceContent("plugins.txt"));
        assertEquals("Content returned", "com.googlecode.gmaps4jsf.plugins.PluginEncoderTest$PluginImpl|||com.googlecode.gmaps4jsf.plugins.PluginEncoderTest$AnotherPluginImpl", FileReaderUtils.getResourceContent("plugins.txt", "|||"));
    }

}
