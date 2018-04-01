export class BookingModel {



  constructor(private carid: number, private fromDate: Date, private toDate: Date, private emailId: string, private amount: number) {
    this.carid = carid;
    this.fromDate = fromDate;
    this.toDate = toDate;
    this.emailId = emailId;
    this.amount = amount;
  }

  public getCarid(): number {
    return this.carid;
  }

  public setCarid(value: number) {
    this.carid = value;
  }
  public getAmount(): number {
    return this.amount;
  }

  public setAmount(value: number) {
    this.amount = value;
  }

  public getFromDate(): Date {
    return this.fromDate;
  }

  public setFromDate(value: Date) {
    this.fromDate = value;
  }


  public getToDate(): Date {
    return this.toDate;
  }

  public setToDate(value: Date) {
    this.toDate = value;
  }


  public getEmail(): string {
    return this.emailId;
  }

  public setEmail(value: string) {
    this.emailId = value;
  }


}