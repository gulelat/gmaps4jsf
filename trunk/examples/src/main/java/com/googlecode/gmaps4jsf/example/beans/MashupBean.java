package com.googlecode.gmaps4jsf.example.beans;



import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.myfaces.custom.tree2.TreeModelBase;
import org.apache.myfaces.custom.tree2.TreeNode;
import org.apache.myfaces.custom.tree2.TreeNodeBase;

/**
 * The Tree2Bean class is used as a managed bean 
 * for the GMaps4JSF Mashups examples
 * @date 22 August 2008
 * @author Hazem Saleh
 */
public class MashupBean {
	
	private TreeModelBase treeModelBase;
	
	/*
	 * We will implement the following sample tree ...
	 * Continents
	 * |- Asia
	 *        |- Japan
	 *        |- China
	 *        |- Iran        
	 * |- Europe
	 *        |- Austria
	 *        |- England
	 *        |- Germany   
	 * |- Africa
	 *        |- Egypt
	 *        |- Mali
	 *        |- Sudan        	
	*/
	public MashupBean() {
	
		// create base nodes.
		TreeNode continentNode = createNode(Tree2Constants.WORLD_FACET_NAME,
				"Continents", false);
		TreeNode africaNode = createNode(Tree2Constants.CONTINENT_FACET_NAME,
				"Africa", false);
		TreeNode asiaNode = createNode(Tree2Constants.CONTINENT_FACET_NAME,
				"Asia", false);
		TreeNode europeNode = createNode(Tree2Constants.CONTINENT_FACET_NAME,
				"Europe", false);

		// add continents.
		addNodeToTree(africaNode, continentNode);
		addNodeToTree(asiaNode, continentNode);
		addNodeToTree(europeNode, continentNode);		
		
		// add asia countries.
		addNodeToTree(createNode(Tree2Constants.COUNTRY_FACET_NAME, "Japan, Tokyo",
				true), asiaNode);
		addNodeToTree(createNode(Tree2Constants.COUNTRY_FACET_NAME, "China, Beijing",
				true), asiaNode);
		addNodeToTree(createNode(Tree2Constants.COUNTRY_FACET_NAME, "Iran, Tehran",
				true), asiaNode);

		// add africa countries
		addNodeToTree(createNode(Tree2Constants.COUNTRY_FACET_NAME, "Egypt, Cairo",
				true), africaNode);
		addNodeToTree(createNode(Tree2Constants.COUNTRY_FACET_NAME, "Mali, Bamako",
				true), africaNode);
		addNodeToTree(createNode(Tree2Constants.COUNTRY_FACET_NAME, "Sudan, Khartoum",
				true), africaNode);

		// add europe countries
		addNodeToTree(createNode(Tree2Constants.COUNTRY_FACET_NAME, "Austria, Vienna",
				true), europeNode);
		addNodeToTree(createNode(Tree2Constants.COUNTRY_FACET_NAME, "England, London",
				true), europeNode);
		addNodeToTree(createNode(Tree2Constants.COUNTRY_FACET_NAME, "Germany, Berlin",
				true), europeNode);		
		
		// Update the treeModelBase
		treeModelBase = new TreeModelBase(continentNode);
	}

	/*
	 * This addNodeToTree() static method is used for adding a tree node
	 * (childNode) to a parent tree node (parentNode).
	 */
	private static void addNodeToTree(TreeNode childNode, TreeNode parentNode) {
		parentNode.getChildren().add(childNode);
	}

	/*
	 * This createNode() static method is used for creating a tree node.
	 */
	private static TreeNode createNode(String facetName, String nodeText, boolean isLeaf) {

		/*
		 * The TreeNodeBase constructor paramaters are :
		 * 1. type : the Facet Name
		 * 2. description : the Node text 
		 * 3. isLeaf : Determines where the node is a leaf or not		
		 */ 
		return new TreeNodeBase(facetName, nodeText, isLeaf);
	}	

	/**
	 * The getData() method is used for returning the TreeModel that would be
	 * used by the tree component value attribute.
	 * 
	 * @return TreeModel
	 */
	public TreeModelBase getData() {
        return treeModelBase;
	}
}
