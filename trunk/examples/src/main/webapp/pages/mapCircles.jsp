<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<!DOCTYPE html "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
    <f:view>
    
    <HEAD> 
    <title>Welcome to GMaps4JSF</title> 
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />        
    <m:resources key="ABQIAAAAxrVS1QxlpJHXxQ2Vxg2bJBQdkFK-tWRbPPQS4ACM1pq_e-PltxQXeyH20wQuqDaQ_6EM5UeGGVpnIw"/>
    
    <style>
        html, body { height: 100% }
    </style>  
    </HEAD>
    
    <body onunload="GUnload()">
        <h:form id="form">
              <div>Multiple Circles</div>        
            <m:map width="90%" height="90%" latitude="24" longitude="15" zoom="3">
                <m:circle latitude="26.745610382199022" longitude="31.2890625" lineWidth="1" hexStrokeColor="#cc0033" raduis="500" />
                <m:circle latitude="39" longitude="-101"  raduis="1000"  lineWidth="10" hexStrokeColor="#336600" hexFillColor="#339900"/>            
                <m:circle latitude="23.241346102386135" longitude="79.453125"  lineWidth="2" raduis="1000" hexStrokeColor="#000000" hexFillColor="#000000"/>                
                <m:circle latitude="-1.7575368113083125" longitude="22.8515625"  lineWidth="4"  raduis="1000" hexStrokeColor="#000000" hexFillColor="#ff3300"/>
                <m:circle latitude="39.36827914916013" longitude="35.859375"  lineWidth="5"   raduis="500" hexStrokeColor="#ff0033" hexFillColor="#ff0099"/>
            </m:map>        
        </h:form>
    <%@include file="../templates/footer.jspf" %>       
    </body>
    </f:view>    
</HTML>  
