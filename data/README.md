# Data [![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)
#### Here is our data module which responsible for send and fetch data from Api , database and shared preference

------------

## WIKI

### For Api

1. In `graphql` folder add your query , mutation or subscription inside it then build

2. In `Model` package write your `POGOs` model inside it

3. In 'Api' inside `retrofit` package write your abstract method for retrofit call

4. Inside `ApiHelper` write your abstract methods for Rest and graphql

5. Inside `AppApiHelper` write your `ApiHelper`  implementation for Rest and graphql

### For Shared Preference

1- in `PreferencesHelper` write your abstract methods

2- in `AppPreferencesHelper` implement `PreferencesHelper` methods

### For Database

1- Make sure you created entity model  in `Model` pacakge

2- create DAO interface inside `db` package and add your recommended methods

3- in `DbHelper` write your abstract methods

4- in `AppDbHelper` implement `DbHelper` methods



# On DataManager
   ####implement override method and add return data inside it