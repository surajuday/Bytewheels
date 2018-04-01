import { Injectable } from '@angular/core';
import { Http, Response } from "@angular/http";
import 'rxjs/Rx';
import 'rxjs/add/operator/map';
import { Constants } from '../_models/constants';

@Injectable()
export class BookingService {

  constructor(private http:Http) {
    
   }


  sendEmail(email:String){
    var reqUri:string=Constants.BASE_URL+"sendEmail?emailId="+email;
    return this.http.get(reqUri);
  }

  makeBooking(from:Date , to:Date, carId:number , email:string){
    var fromDate:string =(from.getMonth())+"/"+from.getDate()+"/"+from.getFullYear();
    var toDate:string =(to.getMonth())+"/"+to.getDate()+"/"+to.getFullYear();
    var reqUri:string=Constants.BASE_URL+"bookCar?from="+fromDate+"&to="+toDate+"&id="+carId+"&email="+email;
    return this.http.get(reqUri);
  }

}
