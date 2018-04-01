import { Component, OnInit, Input, Output, EventEmitter, ViewChild, OnChanges } from '@angular/core';
import { CarModel } from '../_models/car.model';
import { MatTableDataSource, MatSort, MatIconRegistry } from '@angular/material';
import { CarsService } from '../_services/cars.service';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { DomSanitizer } from '@angular/platform-browser';
@Component({
  selector: 'app-car-list',
  templateUrl: './car-list.component.html',
  styleUrls: ['./car-list.component.css'],
  animations:[trigger('popOverState', [
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
export class CarListComponent implements OnInit,OnChanges {

  @Input() cars: CarModel[] = [];
  carsByType:CarModel[] = []
  @Output() carEventEmitter = new EventEmitter<CarModel>();
  @Output() backEvent=new EventEmitter<number>();
  showElement: boolean = false;
  typeSelected : boolean = false;
  colSize:number;

  displayedColumns = ['id', 'name', 'type', 'cost'];
  // dataSource = new MatTableDataSource<CarModel>(this.cars);
  dataSource = this.cars;

  @ViewChild(MatSort) sort: MatSort;

  constructor(private carService:CarsService,iconRegistry: MatIconRegistry, sanitizer: DomSanitizer) {
    iconRegistry.addSvgIcon(
      'thumbs-up',
      sanitizer.bypassSecurityTrustResourceUrl('assets/img/examples/thumbup-icon.svg'));
   }

  ngOnInit() {
    
    this.showElement = true;
    
  }
  
  ngOnChanges(){
    this.colSize=12/this.cars.length;
  }
  back(){
    this.cars=[];
    this.carsByType=[];
    this.backEvent.emit(33);
  }

  selectedType(car: CarModel) {
    this.typeSelected = true;
    this.carsByType = [];
    this.carService.showCars(car.getFrom(),car.getTo(),car.getType(),null)
    .subscribe(response=>{
      response.json().cars.forEach(element => {
        var carz: CarModel = new CarModel(element.id, element.name, element.type, element.cost, car.getFrom(),car.getTo());
        this.carsByType.push(carz);
      });
    });
    // this.carEventEmitter.emit(car);
  }


  selectedCar(car:CarModel){
    this.carService.showCars(car.getFrom(),car.getTo(),car.getType(),car.getName())
    .subscribe(response=>{
      response.json().cars.forEach(element => {
        var carz: CarModel = new CarModel(element.id, element.name, element.type, element.cost, element.from,element.to);
        this.carEventEmitter.emit(carz);
      });
    });
  }

}

