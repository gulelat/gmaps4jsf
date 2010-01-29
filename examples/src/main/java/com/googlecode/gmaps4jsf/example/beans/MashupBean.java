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

import org.apache.myfaces.custom.tree2.TreeModelBase;
import org.apache.myfaces.custom.tree2.TreeNode;
import org.apache.myfaces.custom.tree2.TreeNodeBase;

public class MashupBean {

    public MashupBean() {
	TreeNode continentNode = createNode("world", "Continents", false);
	TreeNode africaNode = createNode("continent", "Africa", false);
	TreeNode asiaNode = createNode("continent", "Asia", false);
	TreeNode europeNode = createNode("continent", "Europe", false);
	addNodeToTree(africaNode, continentNode);
	addNodeToTree(asiaNode, continentNode);
	addNodeToTree(europeNode, continentNode);
	addNodeToTree(createNode("country", "Japan, Tokyo", true), asiaNode);
	addNodeToTree(createNode("country", "China, Beijing", true), asiaNode);
	addNodeToTree(createNode("country", "Iran, Tehran", true), asiaNode);
	addNodeToTree(createNode("country", "Egypt, Cairo", true), africaNode);
	addNodeToTree(createNode("country", "Mali, Bamako", true), africaNode);
	addNodeToTree(createNode("country", "Sudan, Khartoum", true),
		      africaNode);
	addNodeToTree(createNode("country", "Austria, Vienna", true),
		      europeNode);
	addNodeToTree(createNode("country", "England, London", true),
		      europeNode);
	addNodeToTree(createNode("country", "Germany, Berlin", true),
		      europeNode);
	treeModelBase = new TreeModelBase(continentNode);
    }

    private static void addNodeToTree(TreeNode childNode, TreeNode parentNode) {
	parentNode.getChildren().add(childNode);
    }

    private static TreeNode createNode(String facetName, String nodeText,
	    boolean isLeaf) {
	
	return new TreeNodeBase(facetName, nodeText, isLeaf);
    }

    public TreeModelBase getData() {
	return treeModelBase;
    }

    private TreeModelBase treeModelBase;
}