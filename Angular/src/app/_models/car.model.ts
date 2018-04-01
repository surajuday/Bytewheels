

export class CarModel {


  constructor(private id: number,private name: string,private type: string,private cost: number, private from: Date, private to: Date) {
    this.id=id;
    this.name=name;
    this.type=type;
    this.cost=cost;
    this.from=from;
    this.to=to;
  }


  public getId(): number {
    return this.id;
  }

  public setId(value: number) {
    this.id = value;
  }

  public getName(): string {
    return this.name;
  }

  public setName(value: string) {
    this.name = value;
  }


  public getType(): string {
    return this.type;
  }

  public setType(value: string) {
    this.type = value;
  }

  public getCost(): number {
    return this.cost;
  }

  public setCost(value: number) {
    this.cost = value;
  }


  public getFrom(): Date {
    return this.from;
  }

  public setFrom(value: Date) {
    this.from = value;
  }

  public getTo(): Date {
    return this.to;
  }

  public setTo(value: Date) {
    this.to = value;
  }

}
