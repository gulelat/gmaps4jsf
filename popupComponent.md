This component is used for showing a popup from the marker. It should be a child of the marker component.

Beside the JSF UIComponent standard attributes:
| Component Attribute   | Required | Description | Default value |
|:----------------------|:---------|:------------|:--------------|
| icon                  | true     | The icon to the left of the bubble. | NA            |
| content               | true     | The text to show in the overlay as content. | NA            |
| chartStyle            | false    | The style of the chart. Valid values beyond the default are d\_bubble\_icon\_text\_big, d\_bubble\_icon\_texts\_big and d\_bubble\_texts\_big. | d\_bubble\_icon\_text\_small |
| backgroundColor       | false    | The hexadecimal color of the popup background. | FFFFFF        |
| textColor             | false    | The hexadecimal color of the text inside the popup. | 000000        |