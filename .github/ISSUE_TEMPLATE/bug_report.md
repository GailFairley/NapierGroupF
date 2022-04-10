---
name: Bug report
about: Create a report to help us improve
title: 'improvements to code bug'
labels: bug
assignees: 'Gail Fairley and Josh McQueen'

---

**Describe the bug**
Tried to make an improvement to the code by updating the Dockerfile with a different port and a shorter connection time. Communication between containers failed, due to incorrect port, 10 attempts made. Port 3306 changed to 3307 and connection time changed from 30000 to 3000. Adjustment to code made on 10/04/2022 @ ~15:31

**To Reproduce**
Steps to reproduce the behavior:

    Change port and connection time in Dockerfile.
    Push & Commit to Github
    Check GitHub Actions
    Detected the error - red x
    Fill out bug report

**Expected behavior**
Change to Dockerfile allows communication between containers on new port and new connection time is acceptable.

**Screenshots**
app_1 | Error: Failed to connect to database attempt 9
app_1 | Communications link failure
app_1 |
app_1 | The last packet sent successfully to the server was 0 milliseconds ago. The driver has not received any packets from the server.
app_1 | Exception in thread "main" java.lang.NullPointerException: Cannot invoke "java.sql.Connection.createStatement()" because "this.con" is null
app_1 | at com.napier.NapierGroupF.App.executeQuery(App.java:311)
app_1 | at com.napier.NapierGroupF.App.getCountries(App.java:337)
app_1 | at com.napier.NapierGroupF.App.getCountriesOrganisedByPopulation(App.java:510)
app_1 | at com.napier.NapierGroupF.App.getAndDisplayAllReports(App.java:141)
app_1 | at com.napier.NapierGroupF.App.main(App.java:48)
napiergroupf_app_1 exited with code 1
Stopping napiergroupf_db_1 …
Stopping napiergroupf_db_1 … done
1
Aborting on container exit…
Error: Process completed with exit code 1.

**Desktop (please complete the following information):**
• OS: Windows 11
• Browser Firefox
• Version 98.0.2 (64-bit)

**Smartphone (please complete the following information):**
n/a - not tested

**Additional context**
Error detected early before merging into master branch, before release to end users.
