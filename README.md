
Rest Skeleton
----------------------------------------------------------------------------------------

Rest Skeleton is a Web App based on maven  

-> convert to eclipse web project

	mvn eclipse:eclipse -Dwtpversion=2.0

-> add aditional libraries into java buildpath

	servlet-api.jar    --> .../tomcat/lib/servlet-api.jar

-> generate war
	
	mvn clean package

-> deploy

	deploy generated war in tomcat (or another app server or servlet container)
	
-> test using web browser address bar

	Type:			rest service without auth
	Method:		GET
	URL:			http://localhost:8080/RESTSkeleton/user-service/users/1

-> test using rest plugin

	You can install under chrome, POSTMAN extension via chrome web store and import the file called "fdv.json.postman_collection"
	in the root path of this project, or use the following connection string.

	Type:			rest service with auth
	Method:			GET
	URL: 			http://localhost:8080/RestSkeleton/user-service/userscount
	Headers:		AUTHORIZATION_PROPERTY 	Authorization
					AUTHENTICATION_SCHEME 	Basic
