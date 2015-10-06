The direction component allows displaying routes and directions on the map. This component is not available since GMaps4JSF 3.0.0.

## Attributes ##
Beside the JSF UIComponent standard attributes.
| Component Attribute   | Required | Description | Default value |
|:----------------------|:---------|:------------|:--------------|
| for                   | true     | The map id to attach direction to. | NA            |
| fromAddress           | true     | The direction from address. | NA            |
| toAddress             | true     | The direction to address. | NA            |
| locale                | false    | The direction locale. default is en\_US. | en\_US        |
| travelMode            | false    | The direction travel mode. Possible values are (G\_TRAVEL\_MODE\_WALKING, G\_TRAVEL\_MODE\_DRIVING). | G\_TRAVEL\_MODE\_DRIVING |
| avoidHighways         | false    | This flag determines to avoid the highway. default is false. | false         |
| preserveViewport      | false    | This flag determines whether to preserve the map original viewport after attaching the map to. | false         |
| attachNodeId (Added since 1.1.4) | false    | The DOM node id where the step by step explanation will be placed. | NA            |