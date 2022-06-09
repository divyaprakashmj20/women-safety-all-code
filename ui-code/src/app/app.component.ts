import { Component, OnInit } from '@angular/core';
import { User } from './models/user';
import { TestServiceService } from './test-service.service';
import tt from '@tomtom-international/web-sdk-maps'
import { SosManagementService } from './services/sos-management/sos-management.service';
import { UserManagementService } from './services/user-management/user-management.service';
import { Geolocation } from '@awesome-cordova-plugins/geolocation/ngx';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(
    private sosManagementService: SosManagementService,
    private userManagementService: UserManagementService,
    private geolocation: Geolocation,
  ) {

  }

  locationIntervalNotificationService: any;
  userData: any;

  ngOnInit() {
    this.sosManagementService.login_state.subscribe((i: any) => {
      if (i == 'LoggedIn') {
        this.loadInitialData();
      }
    })
    this.loadInitialData();
  }

  loadInitialData() {
    this.userData = this.userManagementService.getUserDataInLocalStorage();
    this.geolocation.getCurrentPosition().then((resp) => {
      this.sosManagementService.updateLocationToCachingService({
        "user_id": this.userData.id,
        "latitude": resp.coords.latitude,
        "longitude": resp.coords.longitude
      }).subscribe((i: any) => {
        console.log(i);
      });
    })
    this.sosManagementService.current_state.subscribe(i => {
      console.log("SOS STATE CHANGED" + i);
      if (i == 'DANGER') {
        clearInterval(this.locationIntervalNotificationService);
      } else if (i == 'NORMAL') {
        clearInterval(this.locationIntervalNotificationService);
        this.locationIntervalNotificationService = setInterval(() => {
          this.sosManagementService.getSosAlertsForUser(this.userData.id).subscribe((relatedSosData: any) => {
            console.log(relatedSosData);
            relatedSosData.map((sosData: any) => {
              this.geolocation.getCurrentPosition().then((resp) => {
                this.sosManagementService.checkDistanceBetweenTwoPoints(resp.coords.latitude, resp.coords.longitude, sosData.latitude, sosData.longitude).subscribe((pointsData: any) => {
                  if (pointsData.lengthInMeters <= 500) {
                    var newSosData = sosData;
                    newSosData.id = this.userData.id
                    newSosData.latitude = resp.coords.latitude
                    newSosData.longitude = resp.coords.longitude
                    this.sosManagementService.associateUserWithSosAsNearbyUser(newSosData).subscribe(i=>{
                      console.log(i);
                      
                    })
                    console.log(sosData);
                    const audio = new Audio();
                    audio.src = 'assets/alarm.mp3';
                    audio.load();
                    audio.play();
                    setTimeout(() => {
                      audio.pause();
                    }, 5000)
                  }
                })
              })
            })


            
          });
        }, 5000)
      }
    })

    this.sosManagementService.getActiveSosOfUser(this.userManagementService.getUserDataInLocalStorage().id).subscribe((sos: any) => {
      if (sos) {
        this.sosManagementService.updateSosState("DANGER");
      } else {
        this.sosManagementService.updateSosState("NORMAL");
      }
    })




  }

}
