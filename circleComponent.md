It can be a child of the (map) component.

## Attributes ##
Beside the JSF UIComponent standard attributes.
| Component Attribute   | Required | Description | Default value |
|:----------------------|:---------|:------------|:--------------|
| longitude             | false    | The longitude center of the circle. | 31.14         |
| latitude              | false    | The latitude center of the circle. | 30.01         |
| raduis                | false    | The raduis of the circle. | 200           |
| unit                  | false    | The unit of the raduis can have two values: 'KM' or 'MILES'. | KM            |
| lineWidth             | false    | The circle line width. | 2             |
| hexStrokeColor        | false    | The circle stroke hex color. | #000000       |
| strokeOpacity         | false    | The opacity of the circle stroke. | 1             |
| hexFillColor          | false    | The fill hexa color of the circle. | #ff0000       |
| fillOpacity           | false    | The fill opacity of the circle. | 0.2           |
| jsVariable            | false    | This flag determines the Javascript variable you want to use for referring to the circle. Use this attribute if you want to use write additional Javascript code for the circle to customize it. | NA            |