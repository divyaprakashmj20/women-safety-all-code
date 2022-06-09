import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserManagementService } from 'src/app/services/user-management/user-management.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  userData:any;
  relativeEmailId:any;

  constructor(
    private userManagementService:UserManagementService,
    private _snackBar: MatSnackBar,
  ) { }

  ngOnInit(): void {
      this.userData = this.userManagementService.getUserDataInLocalStorage();
      console.log(this.userData);    
  }

  deleteRelative(relative:any){
    this.userManagementService.deleteUserRelativeById(this.userData.id,relative.id).subscribe(i=>{
      this._snackBar.open(JSON.stringify(i));
      this.updateUserData();
    },err=>{
      this._snackBar.open(err.error.trace);
    })
  }

  updateUserData(){
    this.userManagementService.getUserById(this.userData.id).subscribe((userDetails:any)=>{
      this.userManagementService.userData = userDetails.response;
      this.userManagementService.setUserDataInLocalStorage(userDetails.response);
      this.userData = this.userManagementService.getUserDataInLocalStorage();
    })
  }

  addRelative(){
    this.userManagementService.login(this.relativeEmailId).subscribe((relative:any)=>{
      this.userManagementService.addRelativeToUser(this.userData.id, relative[0].id).subscribe(i=>{
        this._snackBar.open(JSON.stringify(i));
        this.updateUserData();
      },err=>{

      })
    })
  }

}
