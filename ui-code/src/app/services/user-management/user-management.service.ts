import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserManagementService {

  userData:any;

  constructor(private http: HttpClient) { }

  registerUser(userDetails: any){
    return this.http.post(environment.userManagement + "/user",userDetails);
  }

  login(email:string){
    return this.http.get(environment.userManagement + "/user/email/" + email);
  }

  getUserDetailsListByEmailId(email:string){
    return this.http.get(environment.userManagement + "/user/emails/" + email);
  }

  getUserById(id:any){
    return this.http.get(environment.userManagement + "/user/" + id); 
  }
  
  deleteUserRelativeById(user_id:any, relative_id:any){
    return this.http.delete(environment.userManagement + `/user/${user_id}/relative/${relative_id}`); 
  }

  setUserDataInLocalStorage(i: any) {
    localStorage.setItem("user",JSON.stringify(i));
    console.log(localStorage.getItem("user"));
    
  }

  getUserDataInLocalStorage(){
    this.userData = JSON.parse(localStorage.getItem("user")||'{}');
    return this.userData;
  }

  updateUserDetails(user_id:any,userDetails: any){
    return this.http.put(environment.userManagement + "/user/" + user_id,userDetails);
  }

  addRelativeToUser(user_id:any, relative_id:any){
    return this.http.put(environment.userManagement + `/user/${user_id}/relative/${relative_id}`,{});
  }
}
