import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators, FormGroup, FormBuilder } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { BookingModel } from '../_models/booking.model';
import { BookingService } from '../_services/booking.service';
import { timer } from 'rxjs/observable/timer';
import { take, map } from 'rxjs/operators';
import { Constants } from '../_models/constants';
import { MatSnackBar } from '@angular/material';
import {BrowserModule} from '@angular/platform-browser';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.css']
})
export class BookingComponent implements OnInit {


  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);
  captchaFormControl = new FormControl('', [
    Validators.required
  ]);
  isCaptchaSent: boolean ;
  matcher = new MyErrorStateMatcher();
  captcha: string;
  @Input() carType: string;
  @Input() carName: string;
  @Input('booking') bookingBean: BookingModel;
  @Output() bookedBean = new EventEmitter<BookingModel>();
  @Input('amountToBePaid') amount: number;
  resendEmail: boolean = false;
  retryCount:number;
  countdown;
  viewCount;
  captchaExpired : boolean = true;
  showProgress:boolean = false;

  ngOnInit() {
    this.retryCount=1;
    this.isCaptchaSent=false;
  }

  constructor(private bookingService: BookingService,private snackBar:MatSnackBar) {}

  sendEmail() {
    this.captchaExpired = false;
    this.bookingBean.setEmail(this.emailFormControl.value);
    this.bookingService.sendEmail(this.emailFormControl.value)
      .subscribe(
        (response) => {
          this.captcha = response.json().captcha;
        });
    this.isCaptchaSent = true;
    this.retryCount=1;
    this.viewCount=Constants.TimeoutTime / 60;
    setTimeout(function () {
      this.captcha = null;
      this.captchaFormControl.value=null;
      this.captchaExpired = true;
    }.bind(this), Constants.TimeoutTime*1000);
    let count = Constants.TimeoutTime + 1;
    this.countdown = timer(1, 1000).pipe(
      take(count),
      map(() => --count)
    );

  }

  submitCaptcha() {
    this.showProgress=true;
    this.retryCount++;
    if (this.captcha == this.captchaFormControl.value ) {
      this.bookingBean.setEmail(this.emailFormControl.value);
      this.bookedBean.emit(this.bookingBean);
    } else {
      if (this.retryCount > 3) {
        this.bookingBean.setEmail(null);
        this.bookedBean.emit(this.bookingBean);
        this.isCaptchaSent = false;
      }else{
        let snackBarRef=this.snackBar.open('Wrong Captcha',null,{duration: 2000,});
      }
    }
  }


}


export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}