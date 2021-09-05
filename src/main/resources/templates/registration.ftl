<!DOCTYPE html>
<html>
<body>
<div style="width: 50%; margin: 0 auto; text-align: center">
    <h2>Registration for directors</h2>
    <form action="/registration" method="post">
        <#if employeeExistsError??>
            <p style="color: red; font-size: 9px">${employeeExistsError}</p>
        </#if>
        <div style="padding-bottom: 5px">
            <#if nameError??>
                <p style="color: red; font-size: 9px">${nameError}</p>
            </#if>
            <div>
                Name: <input type="text" name="name" placeholder="Name"/>
            </div>
        </div>
        <div style="padding-bottom: 5px">
            <#if surnameError??>
                <p style="color: red; font-size: 9px">${surnameError}</p>
            </#if>
            <div>
                Surname: <input type="text" name="surname" placeholder="Surname"/>
            </div>
        </div>
        <div style="padding-bottom: 5px">
            <#if departmentError??>
                <p style="color: red; font-size: 9px">${departmentError}</p>
            </#if>
            <div>
                Department: <input type="text" name="department" placeholder="Department"/>
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
                    Salary: <input type="number" name="salary" placeholder="Salary"/>
                </#if>
            </div>
        </div>
        <div style="padding-bottom: 5px">
            <#if loginError??>
                <p style="color: red; font-size: 9px">${loginError}</p>
            </#if>
            <div>
                Login: <input type="text" name="login" placeholder="Login"/>
            </div>
        </div>
        <div style="padding-bottom: 5px">
            <#if passwordError??>
                <p style="color: red; font-size: 9px">${passwordError}</p>
            </#if>
            <div>
                Password: <input type="password" name="password" placeholder="Password"/>
            </div>
        </div>
        <input type="submit" value="Sign up"/>
        <input type="button" value="Back" onclick="window.location.href = 'login'">
    </form>
</div>
</body>
</html>