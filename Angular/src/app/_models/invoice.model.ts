import { CarModel } from "./car.model";
import { BookingModel } from "./booking.model";

export class InvoiceModel {

    constructor(private car: CarModel, private booking: BookingModel, private bookingId: number) {
        this.bookingId = bookingId;
        this.car = car;
        this.booking = booking;
    }

    public getCar(): CarModel {
        return this.car;
    }

    public setCar(value: CarModel) {
        this.car = value;
    }

    public getbooking(): BookingModel {
        return this.booking;
    }

    public setbooking(value: BookingModel) {
        this.booking = value;
    }

}