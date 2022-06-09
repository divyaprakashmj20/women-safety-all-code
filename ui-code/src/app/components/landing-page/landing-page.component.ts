import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { SosManagementService } from 'src/app/services/sos-management/sos-management.service';
import { UserManagementService } from 'src/app/services/user-management/user-management.service';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css']
})
export class LandingPageComponent implements OnInit {

  name = '';
  email = '';
  phoneNumber = '';
  relatives = '';


  constructor(
    private userManagementService:UserManagementService,
    private _snackBar: MatSnackBar,
    private router: Router,
    private sosManagementService: SosManagementService
    ) { }

  ngOnInit(): void {
  }

  login(){
    if(this.email){
      console.log(this.email);
      this.userManagementService.login(this.email).subscribe((i:any)=>{
        this._snackBar.open(JSON.stringify(i[0]));
        this.userManagementService.getUserById(i[0].id).subscribe((userDetails:any)=>{
          this.userManagementService.userData = userDetails.response;
          this.userManagementService.setUserDataInLocalStorage(userDetails.response);
          this.router.navigateByUrl('/home')
          this.sosManagementService.updateLoginState("LoggedIn");
        })
      },err=>{
        console.log(err);
        
        this._snackBar.open(err.error.trace);
      })
    }
  }

  register(){
      this.userManagementService.registerUser({
        name:this.name,
        email:this.email,
        phoneNumber:this.phoneNumber,
        relatives: []
      }).subscribe((success)=>{
        this._snackBar.open("registered successfully")
      },(error)=>{
        console.log(error);
        
        this._snackBar.open(JSON.stringify(error.error));
      })
  }

}
