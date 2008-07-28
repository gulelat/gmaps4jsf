<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<HTML>
    <HEAD> 
    <title>Welcome to GMaps4JSF</title> 
    <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAxrVS1QxlpJHXxQ2Vxg2bJBT2yXp_ZAY8_ufC3CFXhHIE1NvwkxS9AOPy_YJl48ifAy4mq6I8SgK8fg"
            type="text/javascript"></script>    
    </HEAD>
    <body>
	<f:view>
    	<h:form id="form">
    		<m:map width="500" height="500" latitude="30.01" longitude="31.14" />
    	</h:form>
	</f:view>
    </body>
</HTML>  
