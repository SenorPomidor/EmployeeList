<!DOCTYPE html>
<html>
    <body>
        <div style="width: 50%; margin: 0 auto; text-align: center">
            <h2>Authorization</h2>
            <form action="/login" method="post">
                <#if loginError?? || passwordError??>
                    <p style="color: red; font-size: 9px">Login and password can't be empty!</p>
                </#if>
                <#if dataError??>
                    <p style="color: red; font-size: 9px">Suck my dick!</p>
                </#if>
                <div>
                <div style="padding-bottom: 5px">
                    <div>
                        Login: <input type="text" name="login" placeholder="Login" />
                    </div>
                </div>
                <div style="padding-bottom: 5px">
                    <div>
                        Password: <input type="password" name="password" placeholder="Password" />
                    </div>
                </div>
                <br>
                <input type="submit" value="Sign in" />
                <input type="button" value="Sign Out" onclick="window.location.href = 'registration'">
            </form>
        </div>
    </body>
</html>