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
