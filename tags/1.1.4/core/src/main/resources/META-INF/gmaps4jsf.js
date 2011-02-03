(function (window) {
	
    if (typeof(window.gmaps4jsf) === "undefined") {

        var gmaps4jsf = {window: window, pageLoaded: window.document.readyState ? window.document.readyState === "complete" : false};

        gmaps4jsf.addOnLoad = function (func) {
            if (! this.pageLoaded) {
                var oldonload = window.onload;
                if (typeof window.onload !== 'function'){
                    window.onload = func;
                } else {
                    window.onload = function() {
                        oldonload();
                        func();
                    };
                }
            } else {
                func();
            }
        };

        gmaps4jsf.addOnLoad(function() {
            this.pageLoaded = true;
        });

        gmaps4jsf.geocode = function (address, callback) {
            var geocoder = new google.maps.ClientGeocoder();
            geocoder.getLatLng(address, callback);
        };

        window.gmaps4jsf = gmaps4jsf;

    }

})(window);