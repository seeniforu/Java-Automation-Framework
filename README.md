# Automation-Framework
Creating My own Framework for Automation.

## Vision For this Project

<h3 align='center'>
  Any one Can Automate
</h3>

<h3 align='center'>
  Any Website on Internet can be Automated
</h3>

## Requirements

- Eclipse is Advisable to Clone or Import this project.
- Basic Java and Selenium Knowledge.
- Knowledge about this Tool and How it Works.

## Features 

- Tool is Independent of Website, Any Website can be Easily Automated with this Tool.
  - Just Give URL in Properties File. 
  - **Fill All the Mandatory Fields in ProjectSettings.properties** file to Modify **URL, Report Name, Broswer to Exceute and Driver Path**.
- Everything is Logged using Extent report, So When Execution is Completed It'll Automatically open the Results in HTML Format. (In Console You can Find Path of the Result)
- It has Some of Existing Testcases Which is Common for all websites like, (All are Independent Testcases)
  - Case 1 - It'll Ensure the Given URL is Working or not, by Checking whether it returns Status code (200).
  - Case 2 - It'll Launch the Given URL and check for Elements of the page, Count All Elements, Classify According to Tags and Logged to Report.
  - Case 3 - It'll Launch the Given URL and Fetches all Anchor tag contains HREF and Checks Whether All Other Links In Page are Returning Status code (200). Success, Failure With URL Which is Returning Which Code Everything Logged in Report.
  - Case 4 - It'll Launch the Given URL (Once Completely Loaded) Count the No.of elements and Refreshes the Page. Once Refresh is done Again Count the No.of elements and Verify both Count is Equal or not to Know Stability of Page.
  - Case 5 - It'll Launch the Given URL, If Input Field was found in the Page It'll try to pass Some inputs to the Field (Can be Configured with Positive and Negative values) to do a Noraml Input Field Check.
  - Case 6 - It'll Launch the Given URL, Sort elements according to tag name given.

### Above are some common Default Testcases. User can also do,

- Start Writing Own Testcases. 
- Navigate to Next pages using a Part of URL. 
- It has both Selenium based and My own methods of Element accessing using xpath, classname, id etc.,
  - These have Arguments like Primary Id or Classname. The method is Configured in a way, If Primary Fails It'll try to access the element using Alternate Xpath.
  - Xpaths, Classname, id are Seperated for Easy Maintenance.
- It has **Multi-Browser Execution**. All you need to do is Give Driver Path and Name of the Browser User want to Execute in Project Settings File.
- It has **Headless Mode** Feature, If enabled in Project Settings File, User cannot see Driver Execution but you'll get all results Once execution is completed. 
- User Can Run Test in **Incognito Mode** by Enabling It in Project Settings File.

## When, Where and Who can Use this Tool

- A **Beginner User Friendly Tool** where **Any one** can Automate and **Any Websites** can be Automated.
- This tool will be helpful for Quick Start. User can quickly write a flow to Test a website.
- Quick Automation Test To check the Stability of Newly Developed Webpage.
- Quick Automation Feasibility also can be done.
- A Developer or Tester who works in Multiple Projects, Freelancers will get Benifited for Quick Automation.

### Drawbacks 

- If any Errors Notify me Using Issues. Thank you.

### Issues Need to be Resolved

* Safari Is not Tested. Don't know will work or not.
* opera has some issues in headless mode and it is also not tested both UI and Headless Execution. It needs different selenium version to updated in POM.
* HtmlUnitDriver also has some issues. Execution is improper. Not working
* phantomJs driver Pom needs to be updated with jar file. Not working.
* Edge need to be updated with headless and incognito.

