import { Component, OnInit } from '@angular/core';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { FormArray, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { CarsService } from '../_services/cars.service';
import { CarModel } from '../_models/car.model';
import { BookingModel } from '../_models/booking.model';
import { MatSnackBar } from '@angular/material';
import { BookingService } from '../_services/booking.service';
import { InvoiceModel } from '../_models/invoice.model';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { AlertComponent } from '../alert/alert.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  animations: [trigger('popOverState', [
    state('true', style({})),
    state('false', style({
      opacity: 0
    })),
    transition(':enter', [

      // styles at start of transition
      style({ opacity: 0 }),

      // animation and styles at end of transition
      animate('.6s', style({ opacity: 1 }))
    ])
  ])]
})
export class HomeComponent implements OnInit {
  minDate_from = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate());
  maxDate = new Date(2020, 0, 1);
  minDate_to = new Date();
  fromDate = new Date();
  toDate = new Date();
  fromSelected: boolean = false;
  toSelected: boolean = false;
  cars: CarModel[] = [];
  selectedCar: CarModel = null;
  isSubmit: boolean = false;
  bookingBean: BookingModel;
  amount: number;
  // //////////////////////////////////////////////
  isLinear = true;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  progress: number = 33;
  invoice: InvoiceModel = null;
  spinner:boolean;





  constructor(private carsService: CarsService, private bookingService:
    BookingService, private fb: FormBuilder, public snackBar: MatSnackBar, private _formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.minDate_from = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate());
    this.maxDate = new Date(2020, 0, 1);
    this.minDate_to = new Date();
    this.fromDate = null;
    this.toDate = null;
    this.fromSelected = false;
    this.toSelected = false;
    this.cars = [];
    this.selectedCar = null;
    this.isSubmit = false;
    this.progress = 33;
    //////////////////////////////////////////////

    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required]
    });

    this.spinner=false;
  }

  addFromDate(event: MatDatepickerInputEvent<Date>) {
    this.ngOnInit();
    this.fromDate = event.value;
    this.minDate_to = this.fromDate;
    this.fromSelected = true;

  }
  backEventCapture(progress: number) {
    this.progress = progress;
  }
  addToDate(event: MatDatepickerInputEvent<Date>) {
    this.toDate = event.value;
    this.toSelected = true;
  }
  carSelected(car: CarModel) {
    this.progress = 99;
    this.selectedCar = car;
    this.amount = this.amountCalculator(this.fromDate, this.toDate, this.selectedCar.getCost());
    this.bookingBean = new BookingModel(this.selectedCar.getId(), this.fromDate, this.toDate, null, this.selectedCar.getCost());
  }
  openSnackBar(bookedBean: BookingModel) {
    this.spinner=true;
    if (null != bookedBean.getEmail()) {
      this.bookingService.makeBooking(bookedBean.getFromDate(), bookedBean.getToDate(), bookedBean.getCarid(), bookedBean.getEmail())
        .subscribe((response) => {
          if (response.json().id == 0) {
            if (null != response.json().error) {
              this.snackBar.open('Sorry, The car had already been booked. Pleaset ry another car', null, { duration: 10000, });
              this.progress = 66;
              this.spinner=false;
            } else {
              this.snackBar.open('Error occured during booking, Please try later', null, { duration: 10000, });
              this.spinner=false;
              this.ngOnInit();
            }
          } else {
            this.invoice = new InvoiceModel(new CarModel(response.json().car.id, response.json().car.name, response.json().car.type, response.json().car.cost, response.json().booking.fromDate, response.json().booking.toDate),
              new BookingModel(response.json().car.id, response.json().booking.fromDate, response.json().booking.toDate, bookedBean.getEmail(), response.json().booking.amount), response.json().id);
            this.progress = 100;
            this.spinner=false;
          }
        });
    } else {
      var message = "Your booking has Failed due to multiple wrong Captchas, Please try again";
      let snackRef = this.snackBar.open(message, null, {
        duration: 10000,
      });
      this.ngOnInit();
    }
  }

  showCars() {
    if (null != this.fromDate && null != this.toDate) {
      this.cars = [];
      var car: CarModel = new CarModel(null, null, "Compact", 20, this.fromDate, this.toDate);
      this.cars.push(car);
      var car: CarModel = new CarModel(null, null, "Luxury", 50, this.fromDate, this.toDate);
      this.cars.push(car);
      var car: CarModel = new CarModel(null, null, "Large", 40, this.fromDate, this.toDate);
      this.cars.push(car);
      var car: CarModel = new CarModel(null, null, "Full", 30, this.fromDate, this.toDate);
      this.cars.push(car);
      this.isSubmit = true;
      this.progress = 66;
    } else {
      this.snackBar.openFromComponent(AlertComponent, {
        duration: 2000,
      });
    }

  }
  amountCalculator(fromDate: Date, toDate: Date, cost: number): number {
    var diff = toDate.getTime() - fromDate.getTime();
    if (diff == 0) {
      return cost;
    } else {
      var one_day = 1000 * 60 * 60 * 24;
      var days = diff / one_day;
      return cost * days;
    }


  }

  goBackHome() {
    this.cars = [];
    this.isSubmit = false;
    this.ngOnInit();
  }



}
