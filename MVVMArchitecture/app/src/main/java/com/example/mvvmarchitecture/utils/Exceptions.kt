package com.example.mvvmarchitecture.utils

import java.io.IOException

class ApiException(message : String) : IOException(message)

class InternetException(message: String) : IOException(message)