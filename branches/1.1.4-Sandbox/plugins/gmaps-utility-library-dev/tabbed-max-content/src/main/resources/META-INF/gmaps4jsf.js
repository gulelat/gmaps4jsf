if (!google.maps.Map.prototype.tabbedContent) {

    google.maps.Map.prototype.gSelectTabFunctions = null;

    google.maps.Map.prototype.addTabFunction = function (tab, func) {
        if (tab && tab.id && func) {
            if (this.gSelectTabFunctions === null) {
                this.gSelectTabFunctions = {};
                this.activateTabSelection();
            }
            if (!this.gSelectTabFunctions[tab.id]) {
                this.gSelectTabFunctions[tab.id] = [];
            }
            this.gSelectTabFunctions[tab.id].push(func);
        }
    };

    google.maps.Map.prototype.encodeTab = function(id, title, content, node, onSelect) {
        var t = new MaxContentTab(title, content ? content : document.createElement('div'));
        t.id = id;
        if (node) {
            this.addTabFunction(t, function(tab) {
                var domNode = document.getElementById(node);
                if (domNode) {
                    domNode.style.display = 'block';
                    domNode.style.width = '98%';
                    domNode.style.height = '98%';
                    tab.getContentNode().appendChild(domNode);
                }
            });
        }
        this.addTabFunction(t, onSelect);
        return t;
    };

    google.maps.Map.prototype.activateTabSelection = function() {
        var map = this;
        GEvent.addListener(map.getTabbedMaxContent(), 'selecttab', function(tab) {
            var selection = map.gSelectTabFunctions[tab.id];
            if (selection) {
                for (var index = 0; selection.length > index; index++) {
                    if (selection[index]) {
                        selection[index](tab);
                    }
                }
            }
        });
    };

    google.maps.Map.prototype.createTabbedInfoWindow = function(infoWindow, getData) {
        this._createTabbedInfoWindow(infoWindow, getData, this, this.getCenter());
    };

    google.maps.Marker.prototype.createTabbedInfoWindow = function(infoWindow, getData) {
        var self = this;
        google.maps.Event.addListener(this, 'click', function(latlng) {
            self.parentMap._createTabbedInfoWindow(infoWindow, getData, self, self.parentMap);
        });
    };

    google.maps.Map.prototype._createTabbedInfoWindow = function(infoWindow, getData, parent, target) {
        var data = getData(parent);
        this.tabbedContent(parent, target, infoWindow.regular, infoWindow.summary, infoWindow.maxTitle, infoWindow.selectedTab, infoWindow.maximized, data.tabs, infoWindow.onClose);
    };

    google.maps.Map.prototype.tabbedContent = function(parent, target, regular, summary, maxTitle, selectedTab, maximized, buttons, tabs, onClose) {
        parent.openMaxContentTabsHtml(target, regular, summary, tabs, {
            maxTitle: maxTitle,
            selectedTab: selectedTab,
            maximized: maximized,
            noCloseOnClick: true,
            buttons: buttons,
            onCloseFn : onClose
        });
    };

}