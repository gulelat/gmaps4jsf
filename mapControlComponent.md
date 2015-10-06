It can be a child of the (map) component.

## Attributes ##
Beside the JSF UIComponent standard attributes.

Before GMaps4JSF 3.0.0:
| Component Attribute   | Required | Description | Default value |
|:----------------------|:---------|:------------|:--------------|
| name                  | true     | The control name of the map. It can take on the following values:  (GLargeMapControl - a large pan/zoom control used on Google Maps. Appears in the top left corner of the map by default, GSmallMapControl - a smaller pan/zoom control used on Google Maps. Appears in the top left corner of the map by default, GSmallZoomControl - a small zoom control (no panning controls) used in the small map blowup windows used to display driving directions steps on Google Maps, GScaleControl - a map scale, GMapTypeControl - buttons that let the user toggle between map types (such as Map and Satellite), GHierarchicalMapTypeControl - a selection of nested buttons and menu items for placing many map type selectors, GOverviewMapControl - a collapsible overview map in the corner of the screen). | NA            |
| position              | false    | This attribute lets you specify the position of the map control. It can take on the following values: (G\_ANCHOR\_TOP\_RIGHT, G\_ANCHOR\_TOP\_LEFT, G\_ANCHOR\_BOTTOM\_RIGHT, G\_ANCHOR\_BOTTOM\_LEFT). | G\_ANCHOR\_TOP\_LEFT |
| offsetWidth           | false    | This attribute lets you specify the offset width of the map control position. | 10            |
| offsetHeight          | false    | This attribute lets you specify the offset height of the map control position. | 10            |


Since GMaps4JSF 3.0.0:
| Component Attribute   | Required | Description | Default value |
|:----------------------|:---------|:------------|:--------------|
| name                  | true     | The control name of the map. It can take on the following values:  (mapTypeControl, panControl, zoomControl,  scaleControl, rotateControl, and overviewMapControl). | NA            |
| position              | false    | This attribute lets you specify the position of the map control. It can take on the following values: (TOP\_CENTER, TOP\_LEFT, TOP\_RIGHT, LEFT\_TOP, RIGHT\_TOP, LEFT\_CENTER, RIGHT\_CENTER, LEFT\_BOTTOM, RIGHT\_BOTTOM, BOTTOM\_CENTER, BOTTOM\_LEFT, and BOTTOM\_RIGHT). | LEFT\_TOP     |
| controlStyle          | false    | This attribute lets you specify the style of the map control. For more information about the available styles, check: [https://developers.google.com/maps/documentation/javascript/reference#MapTypeControlStyle](https://developers.google.com/maps/documentation/javascript/reference#MapTypeControlStyle) | NA            |