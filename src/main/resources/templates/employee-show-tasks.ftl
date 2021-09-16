<!DOCTYPE html>
<html>
<body>
<div style="width: 750px; margin: 0 auto; text-align: center">
    <h2>Your tasks</h2>
    <#if noTasks??>
        <p style="color: red; font-size: 16px; font-weight: bold;">${noTasks}</p>
    <#else>
        <#list allTasks>
            <table style="border-collapse: collapse">
                <tr>
                    <th style="width: 400px; padding: 2px 10px; background-color: #ccddff">Description</th>
                    <th style="width: 150px; padding: 2px 10px; background-color: #ccddff">Complete</th>
                    <th style="width: 200px; padding: 2px 10px; background-color: #ccddff">Operations</th>
                </tr>
                <#items as task>
                    <tr class="${task?item_parity}Row">
                        <td style="background-color: #e6eeff">${task.description}</td>
                        <td style="background-color: #e6eeff">${task.complete?c}</td>
                        <td style="background-color: #e6eeff">
                            <#if task.complete == true>
                                Sent!
                            <#else>
                                <input type="button" value="Complete task" onclick="window.location.href = '/returnTask/' + ${task.id}"/>
                            </#if>
                        </td>
                    </tr>
                </#items>
            </table>
            <br>
        </#list>
    </#if>
    <input type="button" value="To main" onclick="window.location.href = '/'">
</div>
</body>
</html>
