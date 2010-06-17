(function (window) {

    if (!window.gmaps4jsf) {

        var gmaps4jsf = {window: window};

        gmaps4jsf.geocode = function (address, callback) {
            var geocoder = new google.maps.ClientGeocoder();
            geocoder.getLatLng(address, callback);
        };

        window.gmaps4jsf = gmaps4jsf;

    }

})(window);