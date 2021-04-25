# Base [![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)
#### Here is a base module that we use in our projects

------------


## list of Base classes with it's methods :

------------


1. **BaseActivity**

	- onCreate()
	- attachBaseContext()
	- showLoading()
	- hideLoading()
	- onError()
	- showMessage()
	- hideKeyboard()
	- onNetworkError()
	- onResume()
	- onStop()
	
	
2. **BaseFragment** &&  **BaseDialogFragment**

	- initLayout()
	- getLayoutResource()
	- onCreateView()
    - onViewCreated()
    - showLoading()
    - hideLoading()
    - onError()
    - showMessage()
    - hideKeyboard()
    - onNetworkError()
    - keyboardDismissOnTouchOutside()


3. **BaseHolder<T>**

	 - bind()


4. **BasePresenter**

	- attachView()
	- onViewDestroyed()


5. **MvpPresenter**

	- attachView()


6. **MvpView**

	- showLoading()
    - hideLoading()
    - onError()
    - showMessage()
    - hideKeyboard()
    - onNetworkError()

