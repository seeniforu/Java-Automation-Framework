# Automation-Framework
Creating My own Framework for Automation.


<h3 align='center'>
  Any one Can Automate
</h3>

<h3 align='center'>
  Any Website on Internet can be Automated
</h3>

### Features 

- Any Website can Easily Automated with this Tool.
  - Just Give URL in Properties File. 
  - **Fill All the Mandatory Fields in ProjectSettings.properties** file to Modify **URL, Report Name, Broswer to Exceute and Driver Path**.
- Everything is Logged using Extent report, So When Execution is Completed It'll Automatically open the Results in HTML Format. (In Console You can Find Path of the Result)
- It has Some of Existing Testcases Which is Common for all websites like, (All are Independent Testcases)
  - Case 1 - It'll Ensure the Given URL is Working or not, by Checking whether it returns Status code (200).
  - Case 2 - It'll Launch the Given URL and now check for Elements of the page, Count All Elements, Classify According to Tags and Logged to Report.
  - Case 3 - It'll Launch the Given URL and Fetches all Anchor tag contains HREF and Checks Whether All Other Links In Page are Returning Status code (200). Success, Failure With URL Which is Returning Which Code Everything Logged in Report.
  - Case 4 - It'll Launch the Given URL (Once Completely Loaded) Count the No.of elements and Refreshes the Page. Once Refresh is done Again Count the No.of elements and Verify both Count is Equal or not to Know Stability of Page.
  - Case 5 - 
