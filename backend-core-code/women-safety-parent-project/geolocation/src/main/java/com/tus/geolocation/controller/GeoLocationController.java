package com.tus.geolocation.controller;


//import com.tus.geolocation.VO.Tomtom.Summary;
//import com.tus.geolocation.VO.Tomtom.TomTomVO;
import com.tus.geolocation.VO.TomTomVO;
import com.tus.geolocation.services.GeoLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@CrossOrigin(origins = "*")
public class GeoLocationController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    GeoLocationService geoLocationService;


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public TomTomVO.Summary getLocationData(@RequestParam double fromLatitude, @RequestParam double fromLongitude, @RequestParam double toLatitude, @RequestParam double toLongitude) {

        return geoLocationService.getLocationData(fromLatitude,fromLongitude,toLatitude,toLongitude);

    }

}
