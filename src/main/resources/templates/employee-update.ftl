<!DOCTYPE html>
<html>
    <body>
        <div style="width: 50%; margin: 0 auto; text-align: center">
            <h2>Updating data about ${employee.name}</h2>
            <form action="/updateEmployee" method="post">
                <#if employeeExistsError??>
                    <p style="color: red; font-size: 9px">${employeeExistsError}</p>
                </#if>
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
                        <#if salaryError??>
                            Salary: <input type="number" name="salary" value="100" placeholder="Salary"/>
                        <#else>
                            Salary: <input type="number" name="salary" value="${employee.salary?string.computer}" placeholder="Salary"/>
                        </#if>
                    </div>
                </div>
                <div style="padding-bottom: 5px">
                    <#if loginError??>
                        <p style="color: red; font-size: 9px">${loginError}</p>
                    </#if>
                    <div>
                        Login: <input type="text" name="login" value="${employee.login}" placeholder="Login"/>
                    </div>
                </div>
                <div style="padding-bottom: 5px">
                    <#if passwordError??>
                        <p style="color: red; font-size: 9px">${passwordError}</p>
                    </#if>
                    <div>
                        Password: <input type="text" name="password" value="${employee.password}" placeholder="Password"/>
                    </div>
                </div>
                <br>
                <input type="submit" value="OK" />
                <input type="button" value="To main" onclick="window.location.href = '/'"/>
            </form>
        </div>
    </body>
</html>