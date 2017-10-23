# gbc-java-web-ass1
# ChangeLog
EmployeeServlet-
	line 105- EmployeeValidations.hasNumbers(inputlName) == true REPLACED WITH
		EmployeeValidations.isAlphabetic(inputlName) == false

	line 108- errorMessage += "First name cannot have numbers or special characters<br>"
		REPLACED WITH
		errorMessage += "Last name cannot have numbers or special characters<br>"
