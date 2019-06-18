package com.jbsummer2019.bmnboyzmapapp

public class RouteResponse {

    var routes: List<Route>? = null

    val points: String?
        get() = this.routes!![0].overview_polyline!!.points

    inner class Route {
        var overview_polyline: OverviewPolyline? = null
    }

    inner class OverviewPolyline {
        var points: String? = null
    }
}