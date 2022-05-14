# Automation-Framework

[![Latest release](https://badgen.net/github/release/Naereen/Strapdown.js)](https://github.com/Naereen/Strapdown.js/releases) [![Generic badge](https://img.shields.io/badge/Version-1.4-<COLOR>.svg)](https://shields.io/)

Creating My own Framework for a Quick Start in Automation with any Working Website. 

## Vision For this Project

<h3 align='center'>
  Any one Can Automate
</h3>

<h3 align='center'>
  Any Website on Internet can be Automated
</h3>

## Requirements

- Jdk must be Installed and Environment Variables should be done.
- Eclipse is Advisable because of `Maven Dependencies` to Clone and Import this project.
- Basic Java and Selenium Knowledge.
- Knowledge about this Tool and How it Works.


## Installation

Install My-Project with git clone

- Fork this Repository and Clone from your Github Account for Pull Requests and Contributions.
- To Use this Framework, Copy the Below Link.

```bash
https://github.com/seeniforu/Automation-Framework.git
```
Use `Latest Version` When Selecting Branch.

- After Copying above link, Open Eclipse -> Window -> Show View -> Other.
- Expand Git -> Click on Git Repositories.
- Click Icon `Clone a Git Repository`.
- Paste Cloned Link and Click Next.
- Select `Latest Version Branch` from list and Click Finish.
- After Goto File -> Import.
- Expand Maven -> Existing Maven Projects.
- Select the Cloned Repository from your Local system.
- After Click Finish. Now Project is Imported in Eclipse.
- Otherwise User can use `Git Bash or CMD` to clone the project and Follow Steps from File -> Import.
```bash
git clone https://github.com/seeniforu/Automation-Framework.git
```

## Things to do After Installation

* Fill All the Mandatory Fields in (`ProjectSettings.properties`) 
* Modify `URL`, `Report Name`, `Broswer Execution`, `Headless & Incognito Mode`, `Implicit Wait & Pageload` etc., as per User Need.
* Give any URL at any point of Website in `WebUrl` field.


## How to Run Existing Testcase

To Run this project or any Testcase

- Goto `src\test\java` --> `testPackage` --> `SampleTests.java` file and Try Running Existing Testcase after completeing **Things to do After Installation** Section. 

## Create New Testcases and Run

* Users can Create their own Package in `src\test\java` and create a (Eg:`NewTestFile.java`) to write new Testcases. 
* New Testfile Follows one Major Rule
  * `NewTestFile.java` must  Inherit `ProjectBaseTwo.java` or `SampleTests.java` to use Existing Methods and Existing Testcases.
  * `SampleTests.java` will be a Example for Writing New Testcases.
 

## Features

- Tool is `Independent of Website` and also can adopt Major Changes in Webpage or in a Flow. Any Website can be Easily Automated with this Tool.
- User can Start Automate by Pasting `Direct or Cookie URL`. 
- User can Start Writing `Own Testcases` using Existing Methods. 
- It has `Multi-Browser Execution`. Give Driver Path and Name of the Browser to Execute in `ProjectSettings.properties`.
- It has `HEADLESS MODE` Feature, If enabled in `ProjectSettings.properties`, User cannot see Driver Execution but you'll get all results Once execution is completed. 
- User Can Run Test in `INCOGNITO MODE` by Enabling It in `ProjectSettings.properties`.
- User Can Setup their `Own PROXY` for Safe Execution in `ProjectSettings.properties`.
- Everything is Logged using `Extent Report`, When Execution is Completed It'll Automatically open the Results in HTML Format. (In Console You can Find Path of the Result)
- `Element HighLight` during Exceution is available in all methods. The Given Xpath's Element is Highlighted, Useful when Reviewing Screenshots.
   - User can set `Custom Highlight Colour` in `ProjectSettings.properties`.
- It has both Selenium based and My own methods of Element accessing using xpath, classname, id etc.,
  - Xpaths, Classname, id's can be Seperated for Easy Maintenance.
  - My Own Methods have Arguments like Primary Id or Classname and Alternate Xpath. The method is Configured in a way, If Primary Fails It'll try to access the element using Alternate Xpath.
  <p align="center">
  <img src="https://user-images.githubusercontent.com/91478125/167070849-1961d47f-2cf0-4f7c-8f68-f0c0dfb87967.png" width= 700/>
  </p>
- It has a Special Method Where User No Need to Create Xpath. Give `Id` or `Any Attribute` as one Parameter and it's `Value` as another Parameter. 
 <p align="center">
  <img src="https://user-images.githubusercontent.com/91478125/167070646-d96ff078-f2f7-4486-aed1-5ae64da0d63e.svg" width=700/>
</p>


## Additional Existing Methods and it's Features

- It has Some of Existing Testcases Which is Common for all websites like, (All are Independent Testcases)
  - Case 1 - It'll Ensure the Given URL is Working or not, by Checking whether it returns Status code (200).
  - Case 2 - It'll Launch the Given URL and check for Elements of the page, Count All Elements, Classify According to Tags and Logged to Report.
  - Case 3 - It'll Launch the Given URL and Fetches all Anchor tag contains HREF and Checks Whether All Other Links In Page are Returning Status code (200). Success, Failure With URL Which is Returning Which Code Everything Logged in Report.
  - Case 4 - It'll Launch the Given URL (Once Completely Loaded) Count the No.of elements and Refreshes the Page. Once Refresh is done Again Count the No.of elements and Verify both Count is Equal or not to Know Stability of Page.
  - Case 5 - It'll Launch the Given URL, If Input Field was found in the Page It'll try to pass Some inputs to the Field (Can be Configured with Positive and Negative values) to do a Noraml Input Field Check.
  - Case 6 - It'll Launch the Given URL, Sort elements according to tag name given.

## When, Where and Who can Use this Tool

This Project is Useful for :

- A **Developer** or **Tester** or **Freelancers** who works in Multiple Projects will get Benifited from this Tool.
- A **Beginner User Friendly Tool** where **Any one** can Automate and **Any Websites** can be Automated.
- This tool will be helpful for **Quick Start in Automation**. User can quickly write a flow to Test a website.
- Quick Automation Test To check the **Stability of Newly Developed or Under Development** Webpage.
- **Quick Automation Feasibility** can be done.

## Drawbacks 

- If any Errors Notify me Using Issues. Thank you.

## Issues Need to be Resolved

* Not Tested in any Test environments.
* Case - 3 takes so much time to Execute if More Anchor tags are available. Threads need to included.
* Switching Frames and Counting elements Takes so much time.
* Safari Is not Tested. Don't know will work or not.
* opera has some issues in headless mode and it is also not tested both UI, Headless, Proxy Execution. It needs different selenium version to updated in POM.
* HtmlUnitDriver also has some issues. Execution is improper. Not working
* phantomJs driver Pom needs to be updated with jar file. Not working.
* Edge need to be updated with headless, incognito and Proxy.

## Feedback

If you have any feedback, please reach out to me at srinivasanforu7@gmail.com
