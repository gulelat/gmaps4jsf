This component is an extended version of the HTMLInformationWindow. It can have multiple child tabs. It should be a child of the marker component.

Beside the JSF UIComponent standard attributes:
| Component Attribute   | Required | Description | Default value |
|:----------------------|:---------|:------------|:--------------|
| regular               | true     | Content of the information window. | NA            |
| summary               | false    | Content of the information window when in maximized state. | NA            |
| maxTitle              | false    | Title of the mazimized information window. | NA            |
| selectedTab           | false    | Index or name of the selected tab once maximized. | 0             |
| maximized             | false    | Determines if the information window state is maximized. | false         |
| showCloseButton       | false    | Determines if the close button should be visible. | true          |
| showMinimizeButton    | false    | Determines if the minimize button should be visible. | true          |
| showMaximizeButton    | false    | Determines if the maximize button should be visible. | true          |
| onClose               | false    | JS code to execute on window close. | true          |