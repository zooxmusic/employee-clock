# For My Assesment
to run you need java installed and then you can simply run the following from a command prompt

```
./gradlew bootRun
```

I have not used any UI in a very long time and I have been known to spend an inordinate amount of time on UI components so I admit they are not to my standards but I don't believe that is what you seek.


I also started using the spring-boot state machine for Employee Working States but there is something simple I am missing on implementing the spring version so I did a lightweight hand-rolled version. 

I did follow the approach that you have 4 states

NOT_WORKING
WORKING
ON_LUNCH
ON_BREAK

The events I defined are 

punchIn
* Your current state must be NOT_WORKING, ON_BREAK or ON_LUNCH
* You will transform to WORKING
takeBreak
* Your current state must be WORKING
* You will transform to ON_BREAK
takeLunch
* Your current state must be WORKING
* You will transform to ON_LUNCH
punchOut
* Your current state must be WORKING
* You will transform to NOT_WORKING


I have a check that you cannot submit the same state twice in a row
and I enforce most of this through UI controls by disabling them

