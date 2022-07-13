package com.turtlemint.assignment.util

import java.io.IOException

class ApiException(message: String = "Error Occurred") : IOException(message)
class NoInternetException(message: String = "No Connectivity") : IOException(message)
class ConnectionTimedOutException(message: String = "Connection timed out") : IOException(message)