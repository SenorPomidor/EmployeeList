<!DOCTYPE html>
<html>
    <body>
        <div style="width: 800px; margin: 0 auto; text-align: center">
            <h2>Wellcome, ${name} ${surname}!</h2>
            <input type="button" value="Your profile" onclick="window.location.href = 'updateEmployee/' + ${id}"/>
            <h2>All Employees</h2>
            <#if noEmployees??>
                <p style="color: red; font-size: 16px; font-weight: bold;">${noEmployees}</p>
            <#else>
                <#list allEmps>
                    <table style="border-collapse: collapse">
                        <tr>
                            <th style="width: 100px; background-color: #ccddff">Name</th>
                            <th style="width: 100px; background-color: #ccddff">Surname</th>
                            <th style="width: 100px; background-color: #ccddff">Department</th>
                            <th style="width: 100px; background-color: #ccddff">Salary</th>
                            <th style="width: 150px; background-color: #ccddff">Completed tasks</th>
                            <th style="width: 250px; background-color: #ccddff">Operations</th>
                        </tr>
                        <#items as emp>
                            <tr class="${emp?item_parity}Row">
                                <td style="background-color: #e6eeff">${emp.name}</td>
                                <td style="background-color: #e6eeff">${emp.surname}</td>
                                <td style="background-color: #e6eeff">${emp.department}</td>
                                <td style="background-color: #e6eeff">${emp.salary}</td>
                                <td style="background-color: #e6eeff">${allCompletedTasks[emp?index]} / ${allTasks[emp?index]}</td>
                                <td style="background-color: #e6eeff">
                                    <input type="button" value="Tasks" onclick="window.location.href = 'tasksList/' + ${emp.id}">
                                    <input type="button" value="Edit profile" onclick="window.location.href = 'updateEmployee/' + ${emp.id}"/>
                                    <input type="button" value="Delete" onclick="window.location.href = 'deleteEmployee/' + ${emp.id}"/>
                                </td>
                            </tr>
                        </#items>
                    </table>
                </#list>
                <br>
            </#if>
            <input type="button" value="Add" onclick="window.location.href = 'addNewEmployee'"/>
            <input type="button" value="Sign Out" onclick="window.location.href = 'logout'">
        </div>
    </body>
</html>
