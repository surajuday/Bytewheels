import { Injectable } from '@angular/core';
import { Http, Response } from "@angular/http";
import 'rxjs/Rx';
import 'rxjs/add/operator/map';
import { Constants } from '../_models/constants';

@Injectable()
export class CarsService {

  constructor(private http:Http) { }

  showCars(from:Date , to:Date, type:String, name: String){
    var fromDate:string =from.getMonth()+"/"+from.getDate()+"/"+from.getFullYear();
    var toDate:string =to.getMonth()+"/"+to.getDate()+"/"+to.getFullYear();
    if(null==type && null == name){
      var reqUri:string=Constants.BASE_URL+"getCars?from="+fromDate+"&to="+toDate;
    }else if(null!=type && null==name){
      var reqUri:string=Constants.BASE_URL+"getCars?from="+fromDate+"&to="+toDate+"&type="+type;
    }else if(null!=type && null!=name){
      var reqUri:string=Constants.BASE_URL+"getCars?from="+fromDate+"&to="+toDate+"&type="+type+"&name="+name;
    }else{
      var reqUri:string=Constants.BASE_URL+"getCars?from="+fromDate+"&to="+toDate;
    }
    return this.http.get(reqUri);
  }

  

}
