# USE CASE: 6. Three reports for top X most populated capital cities in world, continent or region where X is manually entered.

## CHARACTERISTIC INFORMATION

### Goal in Context

6. As an employee of World health organisation, I require the top X most populated capital cities in the world, continent or region where I am able to enter the X, so I have quick access to the first capital cities that I will need to contact regarding any health risks.

### Scope

Company.

### Level

Primary task

### Preconditions

Database contains the population and location of capital cities.

### Success End Condition

Three different reports segmented by area are made available to the employees.

Reports:
- The top X populated capital cities in the world where N is provided by the user.
- The top X populated capital cities in a continent where N is provided by the user.
- The top X populated capital cities in a region where N is provided by the user.

### Failed End Condition

No reports are produced.

### Primary Actor

Employee of World Health Organisation

### Trigger

Request to see the top X capital cities by population in different areas.

## MAIN SUCCESS SCENARIO

1. Requests for top X capital cities in the world by largest population.
2. Requests for top X capital cities in the continent by largest population.
3. Requests for top X capital cities in the region by largest population.
4. Employee has easy access to the 3 different reports.

## EXTENSIONS

1. User enters 0 so the report returns 0 values.
2. User enters more values than the table has available.
3. User enters non numerical value.

## SUB-VARIATIONS

None.

## SCHEDULE

12/04/2022: All requested reports provided 