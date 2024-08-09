package com.ems.gatling

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class EmployeeSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080") // Base URL for your application
    .acceptHeader("application/json")

  val scn = scenario("Employee Management Scenario")
    .exec(
      http("Get All Employees")
        .get("/employees/display")
        .check(status.is(200))
    )
    .pause(1)
    .exec(
      http("Create Employee")
        .post("/employees/create")
        .body(StringBody("""{ "name": "Satish", "position": "Software Engg", "salary": 50000 }"""))
        .asJson
        .check(status.is(201))
    )
    .pause(1)
    .exec(
      http("Get Employee by ID")
        .get("/employees/id/1")
        .check(status.is(200))
    )

  setUp(
    scn.inject(atOnceUsers(2))
  ).protocols(httpProtocol)
}
