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

import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Utilities to manage text files.
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public final class FileReaderUtils {

    private FileReaderUtils() {
        throw new AssertionError("Do not instantiate this utility class");
    }

    /**
     * Reads a file from classpath and returns the content as an array of lines.
     *
     * @param path any
     * @return a non null array
     */
    public static String[] readLines(String path) {
        List lines = new ArrayList();
        BufferedReader reader = null;
        try {
            URL file = FileReaderUtils.class.getResource(path);
            reader = new BufferedReader(new InputStreamReader(file.openStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() > 0) {
                    lines.add(line);
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        String[] readed = new String[lines.size()];
        lines.toArray(readed);
        return readed;
    }

    /**
     * Delegates to getResourceContent(resourceName, null).
     *
     * @param resourceName a path relative to the /META-INF folder
     * @return a non null string
     */
    public final static String getResourceContent(String resourceName) {
        return getResourceContent(resourceName, null);
    }

    /**
     * Reads a file contents from classpath (inside the META-INF directory) and
     * appends the separator parameter to each readed line.
     *
     * @param resourceName a path relative to the /META-INF folder
     * @param separator the string that will be used to concatenate the lines in the resource
     * @return a non null string
     */
    public final static String getResourceContent(String resourceName, String separator) {
        String[] array = FileReaderUtils.readLines("/META-INF/" + resourceName);
        int arraySize = array.length;
        int bufSize = (arraySize == 0 ? 0 : ((array[0] == null ? 16 : array[0].toString().length()) + 1) * arraySize);
        StringBuffer buf = new StringBuffer(bufSize);
        for (int i = 0; i < arraySize; i++) {
            if ((separator != null) && (i > 0)) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

}
