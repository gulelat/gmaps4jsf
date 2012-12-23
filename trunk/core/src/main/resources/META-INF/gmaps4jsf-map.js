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
                    themap.setMapTypeId(map.mapType);
                    if (map.jsVariable) {
                        this.window[map.jsVariable] = themap;
                    }
                }
            }
            
            if (map.showDefaultControls  === 'false') {
            	themap.set("disableDefaultUI", true);
            } else {
            	themap.set("disableDefaultUI", false);            	
            }
            
            themap.centeralize(map, callback);

            var mapClickFunction = function (m) {
                return function(event) {
                    /* Save the map state. */
                    document.getElementById(m.mapStateHiddenFieldID).value = event.latLng;
                    
                    /* Submit the form on marker value change if required. */
                    if (m.submitOnValueChange === 'true') {
                        setTimeout(function() {
                            document.getElementById(m.parentFormID).submit();
                        }, 500);
                    }
                    
                    /* Invoke Ajax Script on other components ... */
                    m.ajaxBehavior(event);
                };
            };
            google.maps.event.addListener(themap, 'click', mapClickFunction(map));
        };

        gmaps4jsf.getMap = function (map, partiallyTriggered) {
            if (partiallyTriggered) {
                /* If it is partially triggered then it is always updated on the response rendering */
                return null;
            }
            var id = typeof map === "object" ? map.id : map;
            return this.maps[id];
        };

    }

    if (!google.maps.Map.prototype.centeralize) {

        google.maps.Map.prototype.centeralize = function (map, callback) {
            var self = this;
            var props = map ? map : self.properties;
            if (props.location.address) {
                props.gmaps4jsf.geocode(props.location.address, function (results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {
                        self._center(results[0].geometry.location, props.zoom, callback);
                    }
                });
            } else {
                self._center(new google.maps.LatLng(props.location.latitude, props.location.longitude), props.zoom, callback);
            }
        };

        google.maps.Map.prototype._center = function (latlng, zoom, callback) {
            this.setCenter(latlng);
            this.setZoom(zoom);
            this._createMapCallback(callback);
        };

        google.maps.Map.prototype._createMapCallback = function(callback) {
            callback(this);
        };

        google.maps.Map.prototype.reshape = function(latlng) {
            this.setBounds(latlng);
            if (this.properties.autoReshape) {
                var sw = this.bounds.getSouthWest();
                sw = new google.maps.LatLng(sw.lat() - 0.005, sw.lng() - 0.005);
                var ne = this.bounds.getNorthEast();
                ne = new google.maps.LatLng(ne.lat() + 0.005, ne.lng() + 0.005);
                var extra = new google.maps.LatLngBounds(sw, ne);
                this.setCenter(extra.getCenter());
                this.fitBounds(extra);
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
        	return icon.image; /* To be enhanced in later releases */
        };

    }

    if (!google.maps.Map.prototype.markers) {

        google.maps.Map.prototype.markers = [];

        google.maps.Map.prototype.createMarker = function(marker, callback) {
            var self = this;
            if (marker.address) {
                self.properties.gmaps4jsf.geocode(marker.address, function(results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {
    					var m = new google.maps.Marker({
    						position: results[0].geometry.location,
    						map: self,
    						draggable: marker.markerOptions.draggable,
    						icon: marker.markerOptions.icon    						
    					});                   	
                    	
                        self._markerCreationCallback(m, marker, callback);
                    }
                });
            } else {
                var themarker;
                if (marker.latitude) {
                	themarker = new google.maps.Marker({
						position: new google.maps.LatLng(marker.latitude, marker.longitude),
						map: self,
						draggable: marker.markerOptions.draggable,
						icon: marker.markerOptions.icon
					});                   	
                	
                } else {
                	themarker = new google.maps.Marker({
						position: self.getCenter(),
						map: self,
						draggable: marker.markerOptions.draggable,
						icon: marker.markerOptions.icon						
					});         
                }
                self._markerCreationCallback(themarker, marker, callback);
            }
        };
        
        google.maps.Map.prototype.updateMarkersState = function (markerOptions, newLatLng) {
            var i, markersState = document.getElementById(markerOptions.stateHiddenFieldID).value;
            
            if (markersState.indexOf(markerOptions.markerID + '=') !== -1) {
                 var markersArray = markersState.split('&');
                 var updatedMarkersState = "";
                 for (i = 0; markersArray.length > i; ++i) {
                    if (markersArray[i].indexOf(markerOptions.markerID + '=') === -1) {
                        updatedMarkersState += markersArray[i];
                        if (markersArray.length !== 1 && ((markersArray.length - 1) > i)) {
                            updatedMarkersState += '&';
                        }
                    }
                 }
                 markersState = updatedMarkersState;
            }
            
            if (markersState !== null && markersState !== "") {
                markersState = '&' + markersState;
            }
            
            if (newLatLng) {
            	markersState = markerOptions.markerID + '=' + newLatLng + markersState;
            } else {
            	var latlng = new google.maps.LatLng(markerOptions.latitude, markerOptions.longitude);
            	
            	markersState = markerOptions.markerID + '=' + latlng + markersState;
            }
            
            /* Save the marker state. */
            document.getElementById(markerOptions.stateHiddenFieldID).value = markersState;        	
        };

        google.maps.Map.prototype._dragEnd = function (m) {
            return function(mouseEvent) {
            	var newLatLng = mouseEvent.latLng;

            	this.updateMarkersState(m, newLatLng);
            	
                /* Submit the form on marker value change if required. */
                if (m.submitOnValueChange === 'true') {
                    setTimeout(function() {
                        document.getElementById(m.parentFormID).submit();
                    }, 500);
                }
            };
        };

        google.maps.Map.prototype._markerCreationCallback = function(marker, markerOptions, callback) {
            var latlng = marker.getPosition();
            
            if (! markerOptions.latitude) {
                markerOptions.latitude = latlng.lat();
                markerOptions.longitude = latlng.lng();
            }
            marker.properties = markerOptions;
            marker.parentMap = this;
            if (markerOptions.jsVariable) {
                this.properties.gmaps4jsf.window[markerOptions.jsVariable] = marker;
            }
            /* store marker state */
        	this.updateMarkersState(markerOptions);            
            google.maps.event.addListener(marker, 'dragend', this._dragEnd(markerOptions));
            callback(this, marker);
            
            this.reshape(latlng);            
        };     

        google.maps.Map.prototype.getMarkers = function() {
            return this.markers;
        };

        google.maps.Map.prototype.clearMarkers = function() {
            var i;
            for (i = 0; i < this.markers.length; i++) {
                this.markers[i].set_map(null);
            }
            this.markers = [];
        };

    }

    if (!google.maps.Map.prototype.createInfoWindow) {

        google.maps.Map.prototype.createInfoWindow = function(infoWindowObj, parentMap, callback) {
            var pos = infoWindowObj.latitude ? new google.maps.LatLng(infoWindowObj.latitude, infoWindowObj.longitude) : this.getCenter();
			var infoWindow = new google.maps.InfoWindow({
				content: infoWindowObj.htmlText
			});			

			infoWindow.setPosition(pos);			
			infoWindow.open(parentMap);			
				
			callback(infoWindow);            
        };

    }

    if (!google.maps.Marker.prototype.createInfoWindow) {

        google.maps.Marker.prototype.createInfoWindow = function(infoWindowObj, parentMarker, callback) {
            var showWindowHandler = function (markerObj, infoWindowObj) {
                return function(latlng) {
                	infoWindow.open(parentMarker.map, parentMarker);
                };
            };

			var infoWindow = new google.maps.InfoWindow({
				content: infoWindowObj.htmlText
			});			
			
            google.maps.event.addListener(parentMarker, parentMarker.properties.showInformationEvent, showWindowHandler(parentMarker, infoWindow));
				
			callback(infoWindow);
        };

    }

    if (!google.maps.Map.prototype.createPolyline) {

        google.maps.Map.prototype.createPolyline = function (polyline, getContained) {
            var contained = getContained({});
            var line = new google.maps.Polyline({
            		path: contained.points, 
            		strokeColor: polyline.hexaColor, 
            		strokeWeight: polyline.lineWidth, 
            		strokeOpacity: polyline.opacity, 
            		geodesic: polyline.geodesic
            });
            
            line.setMap(this);
            
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
            var poly = new google.maps.Polygon({
            	path: contained.points, 
            	strokeColor: polygon.hexStrokeColor, 
            	strokeWeight: polygon.lineWidth, 
            	strokeOpacity: polygon.strokeOpacity, 
            	fillColor: polygon.hexFillColor, 
            	fillOpacity: polygon.fillOpacity
            });

            poly.setMap(this);
            
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
            var goverlay = new google.maps.GroundOverlay(
            	overlay.imageURL, 
            	bounds
            );
            
            goverlay.setMap(this);
            
            if (overlay.jsVariable) {
                this.properties.gmaps4jsf.window[overlay.jsVariable] = goverlay;
            }
            
            callback(this, goverlay);
        };

    }

    /*
    if (!google.maps.Map.prototype.addDirection) {

        google.maps.Map.prototype.addDirection = function (direction) {
            var panel = this.properties.gmaps4jsf.window.document.getElementById(direction.attachNodeId);
            
            var directions = new google.maps.Directions(this, panel);
            
            directions.load("from: " + direction.fromAddress + " to: " + direction.toAddress, direction);
        };

    }
    */

    if (!google.maps.Map.prototype.createControl) {

        google.maps.Map.prototype.createControl = function (control) {
        	this.set(control.name, true);
        	
        	if (control.style != "") {
	        	this.set(control.name + "Options", {
	        		position: control.position
	        	});
        	} else {
	        	this.set(control.name + "Options", {
	        		position: control.position,
	        		style: control.style
	        	});        		
        	}
        };

    }
    
    if (!google.maps.Map.prototype.createCircle) {

        google.maps.Map.prototype.createCircle = function (circle) {
            var bounds = new google.maps.LatLngBounds();
            var a, circleObject, circlePoints = [];
            var d = circle.raduis / 3963.189;	/* radians  */
            if (circle.unit === 'KM') {
                d = circle.raduis / 6378.8;	/* radians */
            }
            var lat1 = (Math.PI / 180) * circle.latitude; /* radians */
            var lng1 = (Math.PI / 180) * circle.longitude; /* radians */
            for (a = 0; a <= 360; a++) {
                var tc = (Math.PI / 180) * a;
                var y = Math.asin(Math.sin(lat1) * Math.cos(d) + Math.cos(lat1) * Math.sin(d) * Math.cos(tc));
                var dlng = Math.atan2(Math.sin(tc) * Math.sin(d) * Math.cos(lat1), Math.cos(d) - Math.sin(lat1) * Math.sin(y));
                var x = ((lng1 - dlng + Math.PI) % (2 * Math.PI)) - Math.PI ; /* MOD function */
                var point = new google.maps.LatLng(parseFloat(y * (180 / Math.PI)), parseFloat(x * (180 / Math.PI)));
                circlePoints.push(point);
                bounds.extend(point);
            }
            if (d < 1.5678565720686044) {
                circleObject = new google.maps.Polygon({
                	path: circlePoints, 
                	strokeColor: circle.strokeColour, 
                	strokeWeight: circle.lineWidth, 
                	strokeOpacity: circle.strokeOpacity, 
                	fillColor: circle.fillColour, 
                	fillOpacity: circle.fillOpacity
                });
            }
            else {
                circleObject = new google.maps.Polygon({
                	path: circlePoints, 
                	strokeColor: circle.strokeColour, 
                	strokeWeight: circle.lineWidth, 
                	strokeOpacity: circle.strokeOpacity
                });
            }
            
            circleObject.setMap(this);
        };

    }

})(window);