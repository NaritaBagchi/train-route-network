	Install JDK (JDK8 recommended)
	Install sbt (https://www.scala-sbt.org/1.0/docs/Setup.html)
	On Windows – 
		Ensure java is set in the path
		Ensure sbt is set in the path 
		cd <to the Project folder>
		run sbt 
		Within sbt environment, run the following - 
			clean
			compile
			run <arguments>
		About <arguments> 
			arg[0] represents the stations. For the given problem, ABCDE are the stations.
			arg[1] represents the routes. For the given problem, AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7 are the routes.
			arg[2] represents the expression according to a problem statement. 
		Details of arg[2] - It follows the following format - <ProblemType>_<Path>-<LimitType>-<LimitValue>
			ProblemType - The program categorises the problems into 3 types. Possible values for ProblemType are Distance , AllRoutes and ShortestDistance.
				Distance – Represents the problem to compute distance between the given path.
				AllRoutes – Represents the problem to compute all possible routes between the given pair of stations.
				ShortestDistance - Represents the problem to compute the shortest distance between the given pair of stations.
			Path – Represents the path. Hyphen/Minus (-) is used as the delimiter.
			LimitType – Possible values are Hops, Exact, and Distance.
			LimitValue – Represent the value used by LimitType to limit the conditionals.

	List of arguments for the given set of problems –
	<graph> = ABCDE AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7
	
	Problem	                             | Program Arguments                 | Result
	-------------------------------------|-----------------------------------|------------- 
	The distance of the route A-B-C.     |<graph> Distance_ A-B-C	         |9
	The distance of the route A-D	     |<graph> Distance_A-D	             |5
	The distance of the route A-D-C	     |<graph> Distance_A-D-C	         |13
	The distance of the route A-E-B-C-D	 |<graph> Distance_ A-E-B-C-D	     |22
	The distance of the route A-E-D	     |<graph> Distance_ A-E-D	         |NO such route for A-E-D
	The number of trips starting at C    |
	and ending at C with a maximum of 3  |
	stops.                           	 |<graph> AllRoutes_C-C-Hops-3	     |2
	The number of trips starting at A 
	and ending at C with exactly 4 
	stops.                          	 |<graph> AllRoutes_A-C-Exact-5	     |3
	The length of the shortest route 
	(in terms of distance to travel) 
	from A to C.	                     |<graph> ShortestDistance_A-C	     |9
	The length of the shortest route
	(in terms of distance to travel)
	from B to B	                         |<graph> ShortestDistance_B-B	     |9
	The number of different routes 
	from C to C with a distance of 
	less than 30.  						 |<graph> AllRoutes_C-C-Distance-30	 |7



