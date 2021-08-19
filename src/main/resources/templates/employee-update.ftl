<!DOCTYPE html>
<html>
    <body>
        <div style="width: 50%; margin: 0 auto; text-align: center">
            <h2>Update Employee</h2>
            <form action="/updateEmployee" method="post">
                <input type="hidden" name="id" value="${employee.id}">
                <div style="padding-bottom: 5px">
                    <#if nameError??>
                        <p style="color: red; font-size: 9px">${nameError}</p>
                    </#if>
                    <div>
                        Name: <input type="text" name="name" value="${employee.name}" placeholder="Name" />
                    </div>
                </div>
                <div style="padding-bottom: 5px">
                    <#if surnameError??>
                        <p style="color: red; font-size: 9px">${surnameError}</p>
                    </#if>
                    <div>
                        Surname: <input type="text" name="surname" value="${employee.surname}" placeholder="Surname"/>
                    </div>
                </div>
                <div style="padding-bottom: 5px">
                    <#if departmentError??>
                        <p style="color: red; font-size: 9px">${departmentError}</p>
                    </#if>
                    <div>
                        Department: <input type="text" name="department" value="${employee.department}" placeholder="Department"/>
                    </div>
                </div>
                <div style="padding-bottom: 5px">
                    <#if salaryError??>
                        <p style="color: red; font-size: 9px">${salaryError}</p>
                    </#if>
                    <div>
                        <#if employeeSalary??>
                            Salary: <input type="number" name="salary" value="${employee.salary}" placeholder="Salary"/>
                        <#else>
                            Salary: <input type="number" name="salary" value="100" placeholder="Salary"/>
                        </#if>
                    </div>
                </div>
                <br>
                <input type="submit" value="OK" />
            </form>
        </div>
    </body>
</html>