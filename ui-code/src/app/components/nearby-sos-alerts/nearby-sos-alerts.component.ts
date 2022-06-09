import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SosManagementService } from 'src/app/services/sos-management/sos-management.service';
import { UserManagementService } from 'src/app/services/user-management/user-management.service';
import { Geolocation } from '@awesome-cordova-plugins/geolocation/ngx';
import tt from '@tomtom-international/web-sdk-maps';

@Component({
  selector: 'app-nearby-sos-alerts',
  templateUrl: './nearby-sos-alerts.component.html',
  styleUrls: ['./nearby-sos-alerts.component.css']
})
export class NearbySosAlertsComponent implements OnInit {

  nearbySosData:any = [];
  activeSos:any = null;

  map:any;
  marker:any;
  marker2:any;

  activeHelpInterval:any;

  constructor(
    private sosManagementService : SosManagementService,
    private userManagementService : UserManagementService,
    private _snackBar: MatSnackBar,
    private geolocation: Geolocation
  ) { }

  ngOnInit(): void {
    this.getAssociatedSOS();
  }

  getAssociatedSOS(){
    this.sosManagementService.getNearbySosAlerts(this.userManagementService.userData.id).subscribe(i=>{
      console.log(i);
      
      this.nearbySosData = i;
      this.nearbySosData.map((sos:any)=>{
        if(sos.helpState == "ACTIVE"){
          this.activeSos = i;

          setTimeout(()=>{
            this.renderMap();
          },1000)
       
        }
      })
    })
  }

  help(sos:any){
    this.sosManagementService.acceptToHelp(sos.sosUserId.sosId, sos.sosUserId.userId).subscribe(i=>{
      this.getAssociatedSOS();
    })
  }

  renderMap(){
    
    this.map = tt.map({
      key: 'HFIEN2N8PyYo32M6zjeAEgRmQyrLQAAl',
      container: 'map',
      // style: 'tomtom://vector/1/basic-main',
      zoom:2
    });
    this.map.addControl(new tt.NavigationControl());

    var ttmarker = new tt.Marker();
    var tt2marker = new tt.Marker();

    clearInterval(this.activeHelpInterval);

    this.activeHelpInterval = setInterval(()=>{

      console.log(this.activeSos);
      this.geolocation.getCurrentPosition().then(resp=>{
        tt2marker.remove();
        this.marker2 = document.createElement('div');
        this.marker2.innerHTML = "<img id = 'marker2' src='https://www.pngall.com/wp-content/uploads/2017/05/Map-Marker-PNG-Pic.png' style='width: 30px; height: 30px';>";
        tt2marker = new tt.Marker({ element: this.marker2 })
        .setLngLat([resp.coords.longitude,53.4217536])
        .addTo(this.map);
      })
      
      this.sosManagementService.getSosLocation(this.activeSos[0].sosUserId.sosId).subscribe((sdata:any)=>{
        console.log(sdata);
        if(sdata.sosState != 'ACTIVE'){
          clearInterval(this.activeHelpInterval);
          this.activeSos = null;
          this.getAssociatedSOS();
        }
        this.map.flyTo({
          center:{lat:sdata.latitude, lng: sdata.longitude},
          zoom:15
        })
        
        ttmarker.remove();

        this.marker = document.createElement('div');
        this.marker.innerHTML = "<img id = 'marker1' src='https://www.pngall.com/wp-content/uploads/2017/05/Map-Marker-Free-Download-PNG.png' style='width: 30px; height: 30px';>";
  
        ttmarker = new tt.Marker({ element: this.marker })
        .setLngLat([sdata.longitude,sdata.latitude])
        .addTo(this.map);

      })

    },1000)

    // this.geolocation.getCurrentPosition().then((resp) => {
    //   console.log(resp.coords.latitude);

    //   this.marker = document.createElement('div');
    //   this.marker.innerHTML = "<img id = 'marker1' src='https://image.similarpng.com/very-thumbnail/2021/06/Warning-icon.-The-attention-icon.png' style='width: 30px; height: 30px';>";

    //   new tt.Marker({ element: this.marker })
    //   .setLngLat([resp.coords.longitude,resp.coords.latitude])
    //   .addTo(this.map);


    //   this.marker.addEventListener("click", (i:any)=>{
    //     console.log(i);
    //   });

      
    //   // console.log(this.marker);

    //   // this.marker._onMapClick()
      
      
    //   // resp.coords.latitude
    //   // resp.coords.longitude
    //  }).catch((error) => {
    //    console.log('Error getting location', error);
    //  });
  }

  stopSos(){
    console.log("stoppp");
    
    this.sosManagementService.stopSosHelp(this.activeSos[0].sosUserId.sosId, this.activeSos[0].sosUserId.userId).subscribe(i=>{
      clearInterval(this.activeHelpInterval);
      this.activeSos = null;
      this.getAssociatedSOS();

    })
  }

}
