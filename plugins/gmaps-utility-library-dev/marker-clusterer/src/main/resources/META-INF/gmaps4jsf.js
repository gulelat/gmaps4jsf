if (!google.maps.Map.prototype.setCluster) {

    google.maps.Map.prototype.cluster = null;

    google.maps.Map.prototype.setCluster = function(clt) {
        this.cluster = clt;
    };

    google.maps.Map.prototype.addMarker = function(marker) {
        this.markers.push(marker);
        if (this.cluster == null)  {
            this.addOverlay(marker);
        } else {
            if (this.cluster.timer) {
                clearTimeout(this.cluster.timer);
            }
            this.cluster.timer = setTimeout(this.getClusterFunction(this), 250);
        }
    };

    google.maps.Map.prototype.getClusterFunction = function(map) {
        return function() {
            map.showClusters();
        };
    };

    google.maps.Map.prototype.showClusters = function() {
        this.cluster.timer = null;
        this.cluster.addMarkers(this.getMarkers());
    };

}
