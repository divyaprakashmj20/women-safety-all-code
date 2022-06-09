import { Component, OnInit } from '@angular/core';
import tt from '@tomtom-international/web-sdk-maps';
import { User } from 'src/app/models/user';
import { TestServiceService } from 'src/app/test-service.service';
import { Geolocation } from '@awesome-cordova-plugins/geolocation/ngx';


@Component({
  selector: 'app-sos-test',
  templateUrl: './sos-test.component.html',
  styleUrls: ['./sos-test.component.css']
})
export class SosTestComponent implements OnInit {


  title = 'test-app';
  tab:any = "1";
  data:any;
  // userData:any;
  userData:User[] =[];
  map:any;
  marker:any;
  constructor(public testServiceService: TestServiceService, private geolocation: Geolocation){}

  ngOnInit(){

    /*
      Route distance
      https://api.tomtom.com/routing/1/calculateRoute/52.50931%2C13.42936%3A52.50274%2C13.43872/json?maxAlternatives=0&language=en-GB&departAt=now&vehicleMaxSpeed=0&vehicleWeight=0&vehicleAxleWeight=0&vehicleLength=0&vehicleWidth=0&vehicleHeight=0&key=*****
    */
    // setInterval((i:any)=>{
      // console.log('lollollol');
      // this.callSos();
    // },1000)

    this.map = tt.map({
      key: 'HFIEN2N8PyYo32M6zjeAEgRmQyrLQAAl',
      container: 'map',
      // style: 'tomtom://vector/1/basic-main',
      zoom:2.2
    });
    this.map.addControl(new tt.NavigationControl());
    // this.map.setMyLocationEnabled(true); 

    this.geolocation.getCurrentPosition().then((resp) => {
      console.log(resp.coords.latitude);

      this.marker = document.createElement('div');
      this.marker.innerHTML = "<img id = 'marker1' src='https://image.similarpng.com/very-thumbnail/2021/06/Warning-icon.-The-attention-icon.png' style='width: 30px; height: 30px';>";

      new tt.Marker({ element: this.marker })
      .setLngLat([resp.coords.longitude,resp.coords.latitude])
      .addTo(this.map);


      this.marker.addEventListener("click", (i:any)=>{
        console.log(i);
      });

      
      // console.log(this.marker);

      // this.marker._onMapClick()
      
      
      // resp.coords.latitude
      // resp.coords.longitude
     }).catch((error) => {
       console.log('Error getting location', error);
     });



    // this.getJsonData();
  }

  switchTab(tab: any){
    this.tab = tab;
  }
  callSos(){
    console.log("dasdas");
    // this.testServiceService.testGetApi().subscribe(i=>{
      // console.log(i);
      // this.data = i;
    const audio = new Audio();
    audio.src = 'assets/alarm.mp3';
    audio.load();
    audio.play();
    // });
  }

  getJsonData(){


    // this.testServiceService.getJsonData().subscribe((res:any) => {
    //   this.userData = res;
    //   // for (var i=0;i<this.userData.length;i++) {
    //     this.marker = new tt.Marker({draggable:false})
    //         .setLngLat([-7.77832031,53.2734])
    //         .addTo(this.map);
    //   // }
    // });
  }


  




}
