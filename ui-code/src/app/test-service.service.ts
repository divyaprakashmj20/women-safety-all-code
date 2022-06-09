import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from './models/user';

@Injectable({
  providedIn: 'root'
})
export class TestServiceService {

  constructor(private http: HttpClient) { }

  testGetApi(){
    console.log('dsdds');
    return this.http.get<any>("http://192.168.0.8:8080/getTestData");
  }


  getJsonData() {
   return this.http.get<User[]>('https://jsonplaceholder.typicode.com/users')
  }

}
