(function (window) {
    if (!window.gmaps4jsf) {

        var gmaps4jsf = {window: window};

        gmaps4jsf.maps = {};

        gmaps4jsf.createMap = function(map) {
            var id = map.id;
            var themap = this.getMap(id);
            if (!themap) {
                var container = document.getElementById(id);
                if (container) {
                    themap = this.maps[id] = new google.maps.Map(container);
                    themap.properties = map;
                }
            }
            themap.center(map);
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
            var geocoder = new google.maps.ClientGeocoder();
            geocoder.getLatLng(props.location.address, function (location) {
                if (location) {
                    self.setCenter(location, props.zoom);
                }
            });
        } else {
            self.setCenter(new google.maps.LatLng(props.location.latitude, props.location.longitude), props.zoom);
        }
    };

}

if (!google.maps.Map.prototype.markers) {

    google.maps.Map.prototype.markers = new Array();

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
