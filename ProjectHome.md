**Abstract**

GMaps4JSF aims at integrating Google maps with JSF. JSF users will be also able to construct complex Streetview Panoramas and Maps with just few lines of code (JSF tags).

GMaps4JSF is one of the JSF Mashups libraries that enables JSF users to build web 2.0 mashup applications in JSF easily.

<a href='http://www.youtube.com/watch?feature=player_embedded&v=J8eFDTP6GIg' target='_blank'><img src='http://img.youtube.com/vi/J8eFDTP6GIg/0.jpg' width='425' height=344 /></a>

**The library provides JSF tags that make it easy to**
  * Create the map using (latitude and longitude) or (address).
  * Add marker(s) to the map.
  * Add information text(s) to the map.
  * Add control(s) to the map.
  * Create event listener(s) on the map objects.
  * Draw polyline(s) on the map.
  * Draw polygon(s) on the map.
  * Draw circle(s) on the map.
  * Add groundOverlay(s) on the map.
  * Perform different operations on the map like zoom in and out, switching between map types, ...etc.
  * Create a Streetview Panorama and integrate it simply with the map.

**GMaps4JSF 3.0.1, In Progress, will be released November 2014**
  * Fixing Marker "onclick" Ajaxified Action problem [Issue 108](http://code.google.com/p/gmaps4jsf/issues/detail?id=108).

**What is new in GMaps4JSF 3.0.0 "depends on >= JSF 2.0" (Latest Release)**
  * Supporting Google Maps version v 3.10.
  * Supporting Ajaxified Map actions: [http://www.mashups4jsf.com/gmaps4jsf-examples2-3.0.0/pages/mapAjax.xhtml](http://www.mashups4jsf.com/gmaps4jsf-examples2-3.0.0/pages/mapAjax.xhtml)
  * Supporting Ajaxified Marker actions: [http://www.mashups4jsf.com/gmaps4jsf-examples2-3.0.0/pages/markersAjax.xhtml](http://www.mashups4jsf.com/gmaps4jsf-examples2-3.0.0/pages/markersAjax.xhtml)
  * Drag the marker on the map to know the place name and weather information: [http://www.mashups4jsf.com/gmaps4jsf-examples2-3.0.0/pages/theater1.xhtml](http://www.mashups4jsf.com/gmaps4jsf-examples2-3.0.0/pages/theater1.xhtml)
  * Mashup World Integrated (Google Maps + Twitter + YouTube + Yahoo! Weather): [http://www.mashups4jsf.com/gmaps4jsf-examples2-3.0.0/pages/theater2.xhtml](http://www.mashups4jsf.com/gmaps4jsf-examples2-3.0.0/pages/theater2.xhtml)
  * Demo is available at [http://www.mashups4jsf.com/gmaps4jsf-examples2-3.0.0/](http://www.mashups4jsf.com/gmaps4jsf-examples2-3.0.0/).

**What is new in GMaps4JSF 1.1.4**
  * The library is re-written from scratch.
  * The performance is enhanced 10x compared to the 1.1.3 release.
  * Supporting the (Map) component serverside events.
  * The (markerCluster) experimental component.
  * The (direction) component.
  * Fixing the marker eventListener execution for markers inside `<ui:repeat/>`.
  * The Map (renderOnWindowLoad) attribute is renamed to (partiallyTriggered) attribute.

**What is new in GMaps4JSF 1.1.3**
  * Facelets `<ui:repeat>` support for `<m:point>` component to allow creating dynamic polylines and polygons creation.
  * Enable map zooming on mouse scroll wheel.
  * Creating the plugin module to attach gmaps-dev-utility project components to the library without touching the library core.
  * Adding the (dragZoom) component (Experimental).
  * Adding the (extendedHtmlInformationWindow) component (Experimental).
  * Adding the Popup marker component (Experimental).
  * Enhancing auto-reshape feature to get an optimal view for small areas.
  * Fixing the issue of "valueChangeListener marker attribute does not work inside `<t:dataTable>`".
  * Supporting Reverse Geocoding.
  * Adding the circle component.
  * Adding the resource component.

**What is new in GMaps4JSF 1.1.2**
  * Complete integration with JSF 2.0 Ajax.
  * Map autoReshape feature.
  * Having a Maven2 repository for the library artifacts.
  * Supporting server side events for the library components not just client side events.
  * adding (showLocationNotFoundMessage) attribute to the map and marker components. To determine whether to show an error message when the location is not found.
  * Fixing markers' issues with IE8.
  * Fixing XHTML compliance issues.
  * Fixing the polygon issue inside the facelets `<ui:repeat>` tag.
  * Fixing the markers statelessness issue.
  * Fixing icon coordAnchor issue.
  * Fixing supporting client side events for HTMLInformationWindow.

**What is new in GMaps4JSF 1.1.1**
  * Adding the icon component to simplify the marker image customization.
  * Allowing adding notes to the marker component, and adding the (showInformationEvent) attribute to the marker component.
  * Direct Facelets support.
  * Enable adding events to the HTMLInformationWindow component using the eventListener component.
  * Adding (address) attribute to the streetViewPanorama component.
  * Adding (address) attribute to the marker component.
  * Facelets `<ui:repeat>` support.
  * Solving 1.1.0 defects.


**Adding GMaps4JSF to your Maven2 application**
  * all what you should do is adding this to your pom.xml:
```
    <repositories>
    	...
        <repository>
            <id>googlecode.com</id>
            <url>http://gmaps4jsf.googlecode.com/svn/trunk/gmaps4jsf-repo</url>
        </repository>
        
    </repositories>    

    <dependencies>
	...    
        <dependency>
            <groupId>com.googlecode.gmaps4jsf</groupId>
            <artifactId>gmaps4jsf-core</artifactId>
            <version>1.1.1</version>
        </dependency>  
    </dependencies>
```

To check a working example, please see the following Mashups4JSF example project configuration:
http://code.google.com/p/mashups4jsf/source/browse/trunk/examples/pom.xml


**Notes**
  * GMaps4JSF do not lock the door in the Javascript face as it allows accessing the map from Javascript by providing the (jsVariable) attribute in the components.
  * GMaps4JSF supports Facelets.
  * GMaps4JSF supports Portlets.
  * All the GMaps4JSF features are shown in the Demo http://www.mashups4jsf.com/gmaps4jsf-examples/home.jsf.

**How To Use The Library**
  * [HowToUseTheLibrary](HowToUseTheLibrary.md) shows how to configure and use the library several components.

**Facelets support**
  * In GMaps4JSF 1.1.0, GMaps4JSF can work with Facelets. To know how, you will have to do the following steps [FaceletsSupport](FaceletsSupport.md).
  * In GMaps4JSF 1.1.1 and later, you will not have to do anything to work with Facelets!!!
  * You can check the online demo here: http://www.mashups4jsf.com/mashups4jsf-examples/pages/theater2.xhtml

**Documentation**
  * Please check the components' documentation here [TagLibraryDocumentation](TagLibraryDocumentation.md).