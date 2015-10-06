**How to configure the library:**
  * Place the the latest library jar in your web application WEB-INF/lib folder.
  * Include the tag library in your JSF page as follows:
```
 <%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
```
or if you are using Facelets:
```
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:ui="http://java.sun.com/jsf/facelets"
      xmlns:h="http://java.sun.com/jsf/html"
      xmlns:f="http://java.sun.com/jsf/core"
      xmlns:m="http://code.google.com/p/gmaps4jsf/">

```

If you are using GMaps4JSF <= 1.1.4, you should include the following:
```
<script

src="http://maps.google.com/maps?file=api&amp;v=2&amp;
key=ABQIAAAAxrVS1QxlpJHXxQ2Vxg2bJBT2yXp_ZAY8_ufC3CFXhHIE1NvwkxS9AOPy_YJl48ifAy4mq6I8SgK8fg"
type="text/javascript">

</script>     
```

and if you are using GMaps4JSF >= 3.0.0, you do not need a key anymore as shown:
```
    <script type="text/javascript"
      src="https://maps.googleapis.com/maps/api/js?sensor=true">
    </script> 
```

  * Done!!!

**Example of usages:**
  * To create a simple map (Using longitude and latitude), do the following:
```
 <m:map width="500px" height="500px" latitude="30.01" longitude="31.14" />
```

This will create a simple map of height = 500px and width = 500px, and the map will point to latitude = 30.01 and longitude = 31.41.

  * To create a map with markers and HTML information windows, do the following:
```
 <m:map width="500px" height="500px" latitude="30.01" longitude="31.14">

    	<m:marker latitude="30.01" longitude="31.14"/>

    	<m:htmlInformationWindow latitude="30.01" longitude="31.14" 

    	htmlText="Egypt"/>

</m:map>
```

This will add a marker and an HTML information window to your map at latitude = 30.01 and
longitude = 31.41, and the HTML information window will display the value in the
(htmlText) attribute.

  * To create a simple map (Using address), do the following:
```
  <m:map address="#{addressBean.address}">
	 <m:marker/>
	 <m:htmlInformationWindow htmlText="#{addressBean.address}"/>
  </m:map>
```

This will create a simple map that will point to the specific address with the marker and an information window containing the address.

  * To attach events to your map or marker, use the <m:eventListener.../> tag:
```
    <m:map width="90%" height="90%" latitude="24" longitude="15">   			
	<m:eventListener eventName="moveend" jsFunction="mapMoveEndHandler"/>		
    </m:map>
```
This will create an event listener to the map, which will execute the (mapMoveEndHandler) function when the map (moveend) event occurs.

  * To attach a polygon (a closed set of lines) to your map, use the <m:polygon .../> tag:
```
   	<m:polygon lineWidth="4">
   		<m:point latitude="30.01" longitude="31.14"/>
   		<m:point latitude="-33" longitude="19"/>    				
   		<m:point latitude="39" longitude="-101"/>  	
   		<m:point latitude="30.01" longitude="31.14"/>
    	</m:polygon>
```
This will attach a polygon of the listed four points to the map. Note that the first point should be the same as the last one.

  * To attach a polyline (an opened set of lines) to your map, use the <m:polyline .../> tag:
```
    	<m:polyline>
    		<m:point latitude="30.01" longitude="31.14"/>
    		<m:point latitude="-33" longitude="19"/>    				
    		<m:point latitude="39" longitude="-101"/>
    	</m:polyline>
```
This will attach a polyline of the listed three points to the map.

  * To attach an image to your map, use the <m:groundoverlay .../> tag:
```
    <m:map width="90%" height="90%" latitude="24" longitude="15" zoom="2">
                 <m:groundoverlay imageURL="http://www.jroller.com/HazemBlog/resource/gmaps4jsf-logo.png" 
                  startLatitude="7" endLatitude="23" 
    		  startLongitude="-54" endLongitude="84" />    			
    </m:map>
```
This will attach the GMaps4JSF logo to the map, placing the image in the box starting from latlng(7, -54), and ending at latlng(23, 84).

  * To create a street view panorama, use the <m:streetViewPanorama .../> tag:
```
    <m:streetViewPanorama width="500px" height="500px" 
    			  latitude="42.345573" longitude="-71.098326" />

```
This will create a simple street view panorama of height = 500px and width = 500px, and the panorama will point to latitude = 42.345573 and longitude = -71.098326.

  * For more information about how to use the several components, please see the examples in the demo, and the tag library documentation.