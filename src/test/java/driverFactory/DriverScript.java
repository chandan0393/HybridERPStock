package driverFactory;

import org.openqa.selenium.WebDriver;

import commonFunctions.functionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
	WebDriver driver;
	String inputpath = "./FileInput/Controller.xlsx";
	String outputpath = "./FileOutput/HybridResults.xlsx";
	String TCSheet = "MasterTestCases";
	public void startTest() throws Throwable
	{
		String Module_Status ="";
		String Module_New ="";

		//create reference object for accessing methods
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		for(int i=1;i<=xl.rowCount(TCSheet);i++)
		{
			if(xl.getCellData(TCSheet, i, 2).equalsIgnoreCase("Y"))
			{
				//read testcases from TCSheet
				String TCModule = xl.getCellData(TCSheet, i, 1);
				//Iterate all rows in TCmodule sheet
				for(int j=1;j<=xl.rowCount(TCModule);j++)
				{
					//read all cells from TCModule sheet
					String Description = xl.getCellData(TCModule, j, 0);
					String ObjectType = xl.getCellData(TCModule, j, 1);
					String Ltype = xl.getCellData(TCModule, j, 2);
					String Lvalue = xl.getCellData(TCModule, j, 3);
					String TestData = xl.getCellData(TCModule, j, 4);
					try {
						if(ObjectType.equalsIgnoreCase("startBrowser"))		
						{
							driver = functionLibrary.startBrowser();
						}
						if(ObjectType.equalsIgnoreCase("OpenUrl"))
						{
							functionLibrary.OpenUrl();
						}
						if(ObjectType.equalsIgnoreCase("waitForElement"))
						{
							functionLibrary.waitForElement(Ltype, Lvalue, TestData);
						}
						if(ObjectType.equalsIgnoreCase("typeAction"))
						{
							functionLibrary.typeAction(Ltype, Lvalue, TestData);
						}
						if(ObjectType.equalsIgnoreCase("clickAction"))
						{
							functionLibrary.clickAction(Ltype, Lvalue);
						}
						if(ObjectType.equalsIgnoreCase("validateTitle"))
						{
							functionLibrary.validateTitle(TestData);
						}
						if(ObjectType.equalsIgnoreCase("closeBrowser"))
						{
							functionLibrary.closeBrowser();
						}
						if(ObjectType.equalsIgnoreCase("dropDownAction"))
						{
							functionLibrary.dropDownAction(Ltype, Lvalue, TestData);
						}
						if(ObjectType.equalsIgnoreCase("captureStock"))
						{
							functionLibrary.captureStock(Ltype, Lvalue);
						}
						if(ObjectType.equalsIgnoreCase("stockTable"))
						{
							functionLibrary.stockTable();
						}
						if(ObjectType.equalsIgnoreCase("capturesup"))
						{
							functionLibrary.capturesup(Ltype, Lvalue);
						}
						if(ObjectType.equalsIgnoreCase("suppliertable"))
						{
							functionLibrary.suppliertable();
						}
						if(ObjectType.equalsIgnoreCase("capturecus"))
						{
							functionLibrary.capturecus(Ltype, Lvalue);
						}
						if(ObjectType.equalsIgnoreCase("customertable"))
						{
							functionLibrary.customertable();
						}


						//write as pass into status cell in TCmodule sheet
						xl.setCellData(TCModule, j, 5, "Pass", outputpath);
						Module_Status = "True";
					} catch (Exception e) {
						//write as fail into status cell in TCmodule sheet
						xl.setCellData(TCModule, j, 5, "Fail", outputpath);
						Module_New ="False";
					}
					if(Module_Status.equalsIgnoreCase("True"))
					{
						//write as pass into TCSheet in status cell
						xl.setCellData(TCSheet, i, 3, "Pass", outputpath);
					}
					if(Module_New.equalsIgnoreCase("False"))
					{
						//write as fail into TCSheet in status cell
						xl.setCellData(TCSheet, i, 3, "Fail", outputpath);
					}

				}

			}
			else
			{
				//write as blocked in Status column for testcase which are flagto N in ExecutionStatus column
				xl.setCellData(TCSheet, i, 3, "Blocked", outputpath);
			}
		}


	}

}
