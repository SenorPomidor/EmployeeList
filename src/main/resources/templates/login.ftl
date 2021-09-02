<!DOCTYPE html>
<html>
    <body>
        <div style="width: 50%; margin: 0 auto; text-align: center">
            <h2>Authorization</h2>
            <form action="/login" method="post">
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
                <input type="button" value="Sign up" onclick="window.location.href = 'registration'">
            </form>
        </div>
    </body>
</html>