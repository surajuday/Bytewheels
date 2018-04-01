import { Component, OnInit, Input, EventEmitter, Output, OnChanges } from '@angular/core';
import { InvoiceModel } from '../_models/invoice.model';

@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html',
  styleUrls: ['./invoice.component.css']
})
export class InvoiceComponent implements OnInit, OnChanges {
  @Input('invoiceBean') invoice:InvoiceModel=null;
  newInvoice:InvoiceModel;
  @Output() goBackEvent=new EventEmitter<any>();


  constructor() {
    // this.invoice=null;
   }

  ngOnInit() {
    // this.invoice=null;
  }

  ngOnChanges(){
    this.newInvoice=this.invoice;
  }
  goToHome(){
    this.goBackEvent.emit();
  }
}
