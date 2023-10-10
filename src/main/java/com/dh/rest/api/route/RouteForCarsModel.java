package com.dh.rest.api.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dh.rest.api.model.Cars;
import com.dh.rest.api.service.ServiceForCars;

@Component
public class RouteForCarsModel extends RouteBuilder {

	@Autowired
	private ServiceForCars serviceForCars;

	@Override
	public void configure() {

		restConfiguration().bindingMode(RestBindingMode.auto);

		rest()
				// route 1
				// to view all cars
				.get("/view/all").produces("application/xml").to("direct:view-all-cars")

				// route 2 to view car by model number
				.post("/viewcar").type(Cars.class).consumes("application/json").to("direct:view-car")

				// route 3
				// to add a new car
				.post("/add").type(Cars.class).consumes("application/json").to("direct:add-car");

		from("direct:view-all-cars").log("enter view all cars-").to("bean:serviceForCars?method=viewAllCars");

		from("direct:view-car").log("enter view car by model number- ")
				.to("bean:serviceForCars?method=viewCarByModelno");

		from("direct:add-car").log("${body}").bean(serviceForCars, "addCars");
	}
}
