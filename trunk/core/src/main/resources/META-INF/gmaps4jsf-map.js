(function (window) {
    var google = window.google;
    var gmaps4jsf = window.gmaps4jsf;

    if (!gmaps4jsf.createMap) {

        gmaps4jsf.maps = {};

        gmaps4jsf.createMap = function(map, callback, partiallyTriggered) {
            var id = map.id;
            map.gmaps4jsf = this;
            var themap = this.getMap(id, partiallyTriggered);
            if (! themap) {
                var container = document.getElementById(id);
                if (container) {
                    themap = this.maps[id] = new google.maps.Map(container);
                    themap.properties = map;
                    themap.setMapType(map.mapType);
                    if (map.jsVariable) {
                        this.window[map.jsVariable] = themap;
                    }
                }
            }
            if (map.enableScrollWheelZoom) {
                themap.enableScrollWheelZoom();
            } else {
                themap.disableScrollWheelZoom();
            }
            themap.center(map, callback);
            
            /* add a drag-end listener to the marker */
            var mapClickFunction = function (m) {
                return function(overlay, latlng) {
                    /* Save the map state. */
                    document.getElementById(m.mapStateHiddenFieldID).value = latlng;
                    
                    /* Submit the form on marker value change if required. */
                    if (m.submitOnValueChange == 'true') {
                        setTimeout(function() {
                            document.getElementById(m.parentFormID).submit();
                        }, 500);
                    }
                }
            };
            google.maps.Event.addListener(themap, 'click', mapClickFunction(map));
        };

        gmaps4jsf.getMap = function (map, partiallyTriggered) {
            if (partiallyTriggered) {
                // If it is partially triggered then it is always updated on the response rendering
                return null;
            }
            var id = typeof map === "object" ? map.id : map;
            return this.maps[id];
        };

    }

    if (!google.maps.Map.prototype.center) {

        google.maps.Map.prototype.center = function (map, callback) {
            var self = this;
            var props = map ? map : self.properties;
            if (props.location.address) {
                props.gmaps4jsf.geocode(props.location.address, function (location) {
                    if (location) {
                        self._center(location, props.zoom, callback)
                    }
                });
            } else {
                self._center(new google.maps.LatLng(props.location.latitude, props.location.longitude), props.zoom, callback);
            }
        };

        google.maps.Map.prototype._center = function (latlng, zoom, callback) {
            this.setCenter(latlng, zoom);
            this._createMapCallback(callback);
        };

        google.maps.Map.prototype._createMapCallback = function(callback) {
            callback(this);
        };

        google.maps.Map.prototype.reshape = function(latlng) {
            this.setBounds(latlng);
            if (this.properties.autoReshape && (this.markers.length > 1)) {
                var sw = this.bounds.getSouthWest();
                sw = new google.maps.LatLng(sw.lat() - 0.005, sw.lng() - 0.005);
                var ne = this.bounds.getNorthEast();
                ne = new google.maps.LatLng(ne.lat() + 0.005, ne.lng() + 0.005);
                var extra = new google.maps.LatLngBounds(sw, ne);
                this.setZoom(this.getBoundsZoomLevel(extra));
                this.setCenter(extra.getCenter());
            }
        };

        google.maps.Map.prototype.setBounds = function(latlng) {
            if (!this.bounds) {
                this.bounds = new google.maps.LatLngBounds();
            }
            this.bounds.extend(latlng);
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
            if (marker.address) {
                self.properties.gmaps4jsf.geocode(marker.address, function(location) {
                    if (location) {
                        var m = new google.maps.Marker(location, marker.markerOptions);
                        self._markerCreationCallback(m, marker, callback);
                    }
                });
            } else {
                var themarker;
                if (marker.latitude) {
                    themarker = new google.maps.Marker(new google.maps.LatLng(marker.latitude, marker.longitude), marker.markerOptions);
                } else {
                    themarker = new google.maps.Marker(self.getCenter(), marker.markerOptions);
                }
                self._markerCreationCallback(themarker, marker, callback);
            }
        };

        google.maps.Map.prototype._dragEnd = function (m) {
            return function(latlng) {
                var markersState = document.getElementById(m.stateHiddenFieldID).value;
                if (markersState.indexOf(m.markerID + '=') != -1) {
                     var markersArray = markersState.split('&');
                     var updatedMarkersState = "";
                     for (i = 0; markersArray.length > i; ++i) {
                        if (markersArray[i].indexOf(m.markerID + '=') == -1) {
                            updatedMarkersState += markersArray[i];
                            if (markersArray.length != 1 && ((markersArray.length - 1) > i)) {
                                updatedMarkersState += '&';
                            }
                        }
                     }
                     markersState = updatedMarkersState;
                }
                if (markersState != null && markersState != "") {
                    markersState = '&' + markersState;
                }
                markersState = m.markerID + '=' + latlng + markersState;
                /* Save the marker state. */
                document.getElementById(m.stateHiddenFieldID).value = markersState;
                /* Submit the form on marker value change if required. */
                if (m.submitOnValueChange == 'true') {
                    setTimeout(function() {
                        document.getElementById(m.parentFormID).submit();
                    }, 500);
                }
            }
        };

        google.maps.Map.prototype._markerCreationCallback = function(marker, markerOptions, callback) {
            var latlng = marker.getLatLng();
            if (!markerOptions.latitude) {
                markerOptions.latitude = latlng.lat();
                markerOptions.longitude = latlng.lng();
            }
            marker.properties = markerOptions;
            marker.parentMap = this;
            if (markerOptions.jsVariable) {
                this.properties.gmaps4jsf.window[markerOptions.jsVariable] = marker;
            }
            /* add a drag-end listener to the marker */
            google.maps.Event.addListener(marker, 'dragend', this._dragEnd(markerOptions));
            callback(this, marker);
            if (this.addMarker(marker)) {
                this.reshape(latlng);
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

    if (!google.maps.Map.prototype.createInfoWindow) {

        google.maps.Map.prototype.createInfoWindow = function(infoWindow, parentMap, callback) {
            var pos = infoWindow.latitude ? new google.maps.LatLng(infoWindow.latitude, infoWindow.longitude) : this.getCenter();
            this.openInfoWindowHtml(pos, infoWindow.htmlText);
            callback(this.getInfoWindow());
        };

    }

    if (!google.maps.Marker.prototype.createInfoWindow) {

        google.maps.Marker.prototype.createInfoWindow = function(infoWindow, parentMarker, callback) {
            var showWindowHandler = function (markerObj, infoWindowObj) {
                return function(latlng) {
                    markerObj.openInfoWindowHtml(infoWindowObj.htmlText);
                }
            };
            google.maps.Event.addListener(parentMarker,  parentMarker.properties.showInformationEvent, showWindowHandler(parentMarker, infoWindow));
            callback(parentMarker.parentMap.getInfoWindow());            
        };

    }

    if (!google.maps.Map.prototype.createPolyline) {

        google.maps.Map.prototype.createPolyline = function (polyline, getContained) {
            var contained = getContained({});
            var line = new google.maps.Polyline(contained.points, polyline.hexaColor, polyline.lineWidth, polyline.opacity, {geodesic: polyline.geodesic});
            this.addOverlay(line);
            if (polyline.jsVariable) {
                this.properties.gmaps4jsf.window[polyline.jsVariable] = line;
            }
            contained.callback(line);
            return line;
        };

    }

    if (!google.maps.Map.prototype.createPolygon) {

        google.maps.Map.prototype.createPolygon = function(polygon, getContained) {
            var contained = getContained();
            var poly = new google.maps.Polygon(contained.points, polygon.hexStrokeColor, polygon.lineWidth, polygon.strokeOpacity, polygon.hexFillColor, polygon.fillOpacity);
            this.addOverlay(poly);
            if (polygon.jsVariable) {
                this.properties.gmaps4jsf.window[polygon.jsVariable] = poly;
            }
            contained.callback(poly);
            return poly;
        };

    }

    if (!google.maps.Map.prototype.createOverlay) {

        google.maps.Map.prototype.createOverlay = function (overlay, callback) {
            var bounds = new google.maps.LatLngBounds(new google.maps.LatLng(overlay.startLatitude, overlay.startLongitude), new google.maps.LatLng(overlay.endLatitude, overlay.endLongitude));
            var goverlay = new google.maps.GroundOverlay(overlay.imageURL, bounds);
            this.addOverlay(goverlay);
            if (overlay.jsVariable) {
                this.properties.gmaps4jsf.window[overlay.jsVariable] = goverlay;
            }
            callback(this, goverlay);
        };

    }

    if (!google.maps.Map.prototype.addDirection) {

        google.maps.Map.prototype.addDirection = function (direction) {
            var panel = this.properties.gmaps4jsf.window.document.getElementById(direction.attachNodeId);
            var directions = new google.maps.Directions(this, panel);
            directions.load("from: " + direction.fromAddress + " to: " + direction.toAddress, direction);
        };

    }

    if (!google.maps.Map.prototype.createControl) {

        google.maps.Map.prototype.createControl = function (control) {
            var mapControlPosition = null;
            if (control.position) {
                var position = eval(control.position);
                mapControlPosition = new google.maps.ControlPosition(position, new google.maps.Size(control.offsetWidth, control.offsetHeight));
            }
            var ctl = eval("new " + control.name + "()");
            this.addControl(ctl, mapControlPosition);
        };

    }
    
    if (!google.maps.Map.prototype.createCircle) {

        google.maps.Map.prototype.createCircle = function (circle) {
        	var bounds = new GLatLngBounds();
        	var circlePoints = Array();
        	var circleObject;
        	
        	with (Math) {
        		if (circle.unit == 'KM') {
        			var d = circle.raduis / 6378.8;	/* radians */
        		}
        		else { /* miles */
        			var d = circle.raduis / 3963.189;	/* radians  */
        		}
        		var lat1 = (PI / 180) * circle.latitude; /* radians */
        		var lng1 = (PI / 180) * circle.longitude; /* radians */
        		for (var a = 0; a < 361; a++) {
        			var tc = (PI / 180) * a;
        			var y = asin(sin(lat1) * cos(d) + cos(lat1) * sin(d) * cos(tc));
        			var dlng = atan2(sin(tc) * sin(d) * cos(lat1), cos(d) - sin(lat1) * sin(y));
        			var x = ((lng1 - dlng + PI) % (2 * PI)) - PI ; /* MOD function */
        			var point = new GLatLng(parseFloat(y * (180 / PI)), parseFloat(x * (180 / PI)));
        			circlePoints.push(point);
        			bounds.extend(point);
        		}
        		if (d < 1.5678565720686044) {
        			circleObject = new GPolygon(circlePoints, circle.strokeColour, circle.lineWidth, circle.strokeOpacity, circle.fillColour, circle.fillOpacity);	
        		}
        		else {
        			circleObject = new GPolygon(circlePoints, circle.strokeColour, circle.lineWidth, circle.strokeOpacity);	
        		}
        		this.addOverlay(circleObject);	
			};

		}
	}   
    
    /* End of controls */

})(window);