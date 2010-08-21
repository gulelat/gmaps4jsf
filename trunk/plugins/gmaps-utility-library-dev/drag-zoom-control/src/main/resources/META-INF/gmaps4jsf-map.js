if (!google.maps.Map.prototype.dragZoom) {

    google.maps.Map.prototype.dragZoom = function(url, zoomUrl, width) {
        var box = {
            opacity: .2,
            border: "2px solid red"
        };
        var opts = {
            buttonHTML: '<img src="' + url + '" alt="" />',
            buttonZoomingHTML: '<img src="' + zoomUrl + '" alt="" />',
            buttonStartingStyle: {
                width: width,
                height: width
            }
        };
        this.addControl(new DragZoomControl(box, opts, {}));
    };

}