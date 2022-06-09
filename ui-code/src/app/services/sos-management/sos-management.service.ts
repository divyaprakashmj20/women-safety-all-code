import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { UserManagementService } from '../user-management/user-management.service';
import { Geolocation } from '@awesome-cordova-plugins/geolocation/ngx';
import { BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class SosManagementService {

  locationUpdateInterval:any;
  sos_id:any;

  private current_state_source = new BehaviorSubject<any>([]);
  current_state = this.current_state_source.asObservable();

  private login_state_source = new BehaviorSubject<any>([]);
  login_state = this.login_state_source.asObservable();  
  // current_state:any;

  constructor(
    private http: HttpClient, 
    private userManagementService:UserManagementService,
    private geolocation: Geolocation,
    ) { }

  updateSosState(state:any){
    this.current_state_source.next(state);
  }

  updateLoginState(state:any){
    this.login_state_source.next(state);
  }

  registerSos(sosData:any){
   return this.http.post(environment.sosManagement + '/sos', sosData); 
  }

  getActiveSosOfUser(user_id:any){
    return this.http.get(environment.sosManagement + '/sos/' + user_id); 
  }

  deleteSos(sos_id:any){
    return this.http.delete(environment.sosManagement + '/sos/' + sos_id); 
  }

  updateSosLocation(updateDetails:any){
    return this.http.put(environment.sosManagement + '/sos/location', updateDetails);
  }

  updateLocationTimeInterval(){
    this.locationUpdateInterval = setInterval(()=>{
      // console.log('updating location');
      this.geolocation.getCurrentPosition().then((resp) => {
        console.log(resp.coords.latitude);
        console.log(resp.coords.longitude);
        this.updateSosLocation({
          "sosId": this.sos_id,
          "userId": this.userManagementService.userData.id,
          "latitude": resp.coords.latitude,
          "longitude": resp.coords.longitude
        }).subscribe(i=>{
  
        })
      })

    },1000)
  }

  deactivateLocationTimeInterval(){
    clearInterval(this.locationUpdateInterval)
  }

  getSosAlertsForUser(user_id:any){
    return this.http.get(environment.notificationManagement + '/check/user/' + user_id); 
  }

  updateLocationToCachingService(userDetails:any){
    return this.http.post(environment.cachingManagement + '/user/location', userDetails);
  }

  checkDistanceBetweenTwoPoints(fromLatitude:any, fromLongitude:any, toLatitude:any, toLongitude:any){
    // return this.http.get(`${environment.geoLocation}/test?fromLatitude=${fromLatitude}&fromLongitude=${fromLongitude}&toLatitude=${toLatitude}&toLongitude=${toLongitude}`);
    return this.http.get("https://api.tomtom.com/routing/1/calculateRoute/" + fromLatitude + "," + fromLongitude + ":" + toLatitude + "," + toLongitude + "/json?key=HFIEN2N8PyYo32M6zjeAEgRmQyrLQAAl")
    .pipe(
      map((response:any)=>{
        return response.routes[0].summary;
      })
    )
  }

  associateUserWithSosAsNearbyUser(userData:any){
    return this.http.post(`${environment.notificationManagement}/sos/nearby/user`, [userData]);
  }

  getAllSosAssociatedWithUser(user_id:any){
    return this.http.get(`${environment.sosManagement}/sos/nearby/${user_id}`)
  }

  getNearbySosAlerts(user_id:any){
    return this.http.get(`${environment.sosManagement}/sos/nearby/${user_id}`)
  }

  acceptToHelp(sos_id:any, user_id:any){
    return this.http.put(`${environment.sosManagement}/sos/nearby/${sos_id}/${user_id}`,{})
  }

  getSosLocation(sos_id:any){
    return this.http.get(`${environment.sosManagement}/sos/location/${sos_id}`);
  }

  stopSosHelp(sos_id:any, user_id:any){
    return this.http.put(`${environment.sosManagement}/sos/location/${sos_id}/${user_id}`,{})
  }


}
