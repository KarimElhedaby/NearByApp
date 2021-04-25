# Utils [![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)
#### that is most common utils functions that we use it our projects

------------


## list of Utils files with methods :

------------


1. **Common**

	- fullScreen()  	
		- used to make full screen activity  
	- changeLang() 
		- used to change application language
	- showLoadingDialog() 
	- arabicToDecimal() 
	
	
		 - used to convert arabic number `١ ٢ ٣` to `1 2 3`



	- changeStatusBarNavigationColor() 
	
	
		 - used to change status bar and navigation bar color.



	- getDate() 
	
	
		 - used to convert `MilliSeconds`  to `RelativeTimeSpanString`


	- getMacAddr() 


	- printKeyHash() 
	
	
		 -  used to print debug key hash for facebook sdk.


	- generateImageName() 
	
	
		 - used to generate name for images for image that had unrecognized characters .


	- generateUniqueId() 
	
	
2. **Extension**

	- takeIfAs() 


	- setAllOnClickListener() 
	
	
		 - use to set click listener for whole view inside group in constraint layout.
		 
		 

	- hideKeyboard() && showKeyboard() 

	-  toInt() && toEnum() 

	- fromJson() && fromJson() 

	- inflate() 
		 - used to inflate ayout to `onCreateView` in adapter.
		 

3. **GlideModule**

	 Use to generate Glide module app to can load images  with custom options .


4. **Multimedia**

	- isVideoFile() 

	- isImageFile() 

	- requireFileSize() 
	 	 - used to check file size .
	- getVideoThumbnails() 
		 - used to generate thumbnail image from video.
	- checkYoutubeUrlValidation() 

	- compressImage() 

	- getFileFromBitmap() 

	- generateDarkColorFromImage() 
		 - used to generate dark color from image .


5. **PagingRequestHelper**


	- used with google paging library on boundry callback to make sure that paging send single request .


6. **ThemeHelper**


	- used to switch between modes` Dark Mode` `Light Mode` `Battery Saver Mode` `Default`.


7. **RxNetworkDetector**


	- getConnectivityStatusForOnce() 
		 - used to check if we has internet connection or not.
	- getConnectivityStatus() 
		 - used to observe internet connection during app running
		 


8. **Permission**

	- checkStorageAccessPermission() 

	- checkCameraPermission() 

	- checkLocationAccessPermission() 

	- goToImageSettings() 

	- goToLocationPermissionSettings() 

	- isLocationServiceEnabled() 
	 - check if gps is enabled
	- isInFlightMode() 