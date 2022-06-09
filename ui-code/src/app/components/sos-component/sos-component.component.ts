import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SosManagementService } from 'src/app/services/sos-management/sos-management.service';
import { UserManagementService } from 'src/app/services/user-management/user-management.service';
import { Geolocation } from '@awesome-cordova-plugins/geolocation/ngx';

@Component({
  selector: 'app-sos-component',
  templateUrl: './sos-component.component.html',
  styleUrls: ['./sos-component.component.css']
})
export class SosComponentComponent implements OnInit {

  sosData:any;

  constructor(
    private sosManagementService : SosManagementService,
    private userManagementService : UserManagementService,
    private _snackBar: MatSnackBar,
    private geolocation: Geolocation
  ) { }

  ngOnInit(): void {
    this.getUserActiveSos();
  }

  getUserActiveSos(){
      this.sosManagementService.getActiveSosOfUser(this.userManagementService.userData.id).subscribe((sos:any)=>{
        this.sosData = sos;
        if(sos){
          this.sosManagementService.updateSosState("DANGER");
          // this.sosManagementService.current_state = "DANGER"
          this.sosManagementService.deactivateLocationTimeInterval();
          this.sosManagementService.sos_id = sos.sosId;
          this.sosManagementService.updateLocationTimeInterval();
        }
      });
  }

  registerSos(){

    this.geolocation.getCurrentPosition().then((resp) => {

      var sosRegsitrationData = {
        "userId": this.userManagementService.userData.id,
        "latitude": resp.coords.latitude,
        "longitude": resp.coords.longitude
      }
  
      this.sosManagementService.registerSos(sosRegsitrationData).subscribe((i:any)=>{
        this.getUserActiveSos();
        // this.sosManagementService.sos_id = i.sosId;
        // this.sosManagementService.updateLocationTimeInterval();
      },err=>{
        this._snackBar.open(err.error.trace);
      })

    })
  }

  deleteSos(){
    this.sosManagementService.deleteSos(this.sosData.sosId).subscribe(i=>{
      // this._snackBar.open(i);
      console.log("deleted");
      this.sosManagementService.updateSosState("NORMAL");
      // this.sosManagementService.current_state = "NORMAL"
      this.getUserActiveSos();
      this.sosManagementService.deactivateLocationTimeInterval();
    },err=>{
      this._snackBar.open(err.error.trace);
    })
  }

}
