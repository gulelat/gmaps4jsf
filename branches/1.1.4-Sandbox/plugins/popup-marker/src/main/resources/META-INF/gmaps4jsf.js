if (!google.maps.Map.prototype.addLabel) {

    google.maps.Map.prototype.addLabel = function(marker, url) {
        var img = new Image();
        img.src = url;
        var map = this;
        img.onload = function() {
            map._addLabel(marker, img);
        };
    };

    google.maps.Map.prototype._addLabel = function(marker, img) {
        var overlay = this._createLabel(marker, img);
        this._createLabelEvent(marker, img, overlay);
    };

    google.maps.Map.prototype._createLabel = function(marker, img) {
        var origin = marker.getLatLng();
        var point = this.fromLatLngToContainerPixel(origin);
        var end = new GPoint(point.x + img.width, point.y - img.height);
        var ne = this.fromContainerPixelToLatLng(end);
        var bounds = new GLatLngBounds(origin, ne);
        var overlay = new GGroundOverlay(img.src, bounds);
        this.addOverlay(overlay);
        return overlay;
    };

    google.maps.Map.prototype._createLabelEvent = function(marker, img, overlay) {
        var map = this;
        var dglst = GEvent.addListener(marker, 'dragstart', function() {
            GEvent.removeListener(dglst);
            map.removeOverlay(overlay);
        });
        var dgelst = GEvent.addListener(marker, 'dragend', function() {
            GEvent.removeListener(dgelst);
            GEvent.clearInstanceListeners(marker);
            map._addLabel(marker, img);
        });
        var lst = GEvent.addListener(map, 'zoomend', function() {
            GEvent.removeListener(lst);
            map.removeOverlay(overlay);
            map._addLabel(marker, img);
        });
    };

}