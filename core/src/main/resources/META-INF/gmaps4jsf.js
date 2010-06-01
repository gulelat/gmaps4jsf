(function (window) {
    if (!window.gmaps4jsf) {

        var gmaps4jsf = {window: window};

        gmaps4jsf.maps = {};

        gmaps4jsf.geocode = function (address, callback) {
            var geocoder = new google.maps.ClientGeocoder();
            geocoder.getLatLng(address, callback);
        }

        gmaps4jsf.createMap = function(map, callback) {
            var id = map.id;
            map.gmaps4jsf = this;
            var themap = this.getMap(id);
            if (!themap) {
                var container = document.getElementById(id);
                if (container) {
                    themap = this.maps[id] = new google.maps.Map(container);
                    themap.properties = map;
                    if (map.jsVariable) {
                        this.window[map.jsVariable] = themap;
                    }
                }
            }
            themap.center(map);
            callback(themap);
        };

        gmaps4jsf.getMap = function (map) {
            var id = typeof map === "object" ? map.id : map;
            return this.maps[id];
        };

        window.gmaps4jsf = gmaps4jsf;

    }

})(window);

if (!google.maps.Map.prototype.center) {

    google.maps.Map.prototype.center = function (map) {
        var self = this;
        var props = map ? map : self.properties;
        if (props.location.address) {
            props.gmaps4jsf.geocode(props.location.address, function (location) {
                if (location) {
                    self.setCenter(location, props.zoom);
                }
            });
        } else {
            self.setCenter(new google.maps.LatLng(props.location.latitude, props.location.longitude), props.zoom);
        }
    };

}

if (!google.maps.Map.prototype.buildIcon) {

    google.maps.Map.prototype.buildIcon = function (icon) {
        var iconObject = new google.maps.Icon(G_DEFAULT_ICON);
        iconObject.image = icon.image;
        iconObject.shadow = icon.shadow;
        iconObject.iconSize = new google.maps.Size(icon.iconSize.width, icon.iconSize.height);
        iconObject.shadowSize = new google.maps.Size(icon.shadowSize.width, icon.shadowSize.height);
        iconObject.iconAnchor = new google.maps.Point(icon.iconAnchor.x, icon.iconAnchor.y);
        iconObject.infoWindowAnchor = new google.maps.Point(icon.infoWindowAnchor.x, icon.infoWindowAnchor.y);
        return iconObject;
    };

}

if (!google.maps.Map.prototype.markers) {

    google.maps.Map.prototype.markers = new Array();

    google.maps.Map.prototype.createMarker = function(marker, callback) {
        var self = this;
        var themarker;
        if (marker.address) {
            self.properties.gmaps4jsf.geocode(marker.address, function(location) {
                if (location) {
                    var m = new google.maps.Marker(location, marker.markerOptions);
                    self.addMarker(m);
                    callback(self, m);
                }
            });
        } else if (marker.latitude) {
            themarker = new google.maps.Marker(new google.maps.LatLng(marker.latitude, marker.longitude), marker.markerOptions);
        } else {
            themarker = new google.maps.Marker(self.getCenter(), marker.markerOptions);
        }
        if (themarker) {
            self.addMarker(themarker);
            callback(self, themarker);
        }
    };

    google.maps.Map.prototype.getMarkers = function() {
        return this.markers;
    };

    google.maps.Map.prototype.clearMarkers = function() {
        for (var i = 0; i < this.markers.length; i++) {
            this.markers[i].set_map(null);
        }
        this.markers = new Array();
    };

}
